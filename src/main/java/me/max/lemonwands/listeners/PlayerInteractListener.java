/*
 *
 *  *
 *  *  *
 *  *  *  * LemonWands - Sell and Compress Wands!
 *  *  *  * Copyright (C) 2018 Max Berkelmans AKA LemmoTresto
 *  *  *  *
 *  *  *  * This program is free software: you can redistribute it and/or modify
 *  *  *  * it under the terms of the GNU General Public License as published by
 *  *  *  * the Free Software Foundation, either version 3 of the License, or
 *  *  *  * (at your option) any later version.
 *  *  *  *
 *  *  *  * This program is distributed in the hope that it will be useful,
 *  *  *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  *  *  * GNU General Public License for more details.
 *  *  *  *
 *  *  *  * You should have received a copy of the GNU General Public License
 *  *  *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  *  *
 *  *
 *
 */

package me.max.lemonwands.listeners;

import me.max.lemonwands.PriceManager;
import me.max.lemonwands.wands.Wand;
import me.max.lemonwands.wands.WandManager;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.Arrays;
import java.util.List;

/*
I'm not proud of this code.
I don't know how it works.
But it works.
 */
public class PlayerInteractListener implements Listener {

    private final List<Material> compressableByNine = Arrays.asList(Material.GOLD_INGOT,
            Material.IRON_INGOT,
            Material.LAPIS_ORE,
            Material.REDSTONE,
            Material.GOLD_NUGGET);
    private final List<Material> compressableByFour = Arrays.asList(Material.GLOWSTONE_DUST);
    private WandManager wandManager;
    private PriceManager priceManager;

    public PlayerInteractListener(WandManager wandManager, PriceManager priceManager) {
        this.wandManager = wandManager;
        this.priceManager = priceManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!event.hasItem()) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        final Material clickedBlock = event.getClickedBlock().getType();
        if (clickedBlock != Material.CHEST && clickedBlock != Material.TRAPPED_CHEST) return;

        ItemStack item = event.getItem();
        Wand wand = wandManager.getWand(item);
        if (wand == null) return;

        Player p = event.getPlayer();
        if (wand.getType() == Wand.Type.SELL) handleSellWand(p, wand, event.getClickedBlock());
        else if (wand.getType() == Wand.Type.COMPRESS) handleCompressWand(p, wand, event.getClickedBlock());
        item.setDurability((short) (item.getDurability() - 1));
        if (item.getDurability() == 0) p.getInventory().remove(item);
    }

    private void handleSellWand(Player p, Wand wand, Block block) {
        if (!p.hasPermission("lemonwands.sellwand")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', wand.getNoPermissionMessage()));
            return;
        }
        priceManager.handleChest(p, (Chest) block.getState());
    }

    private void handleCompressWand(Player p, Wand wand, Block block) {
        if (!p.hasPermission("lemonwands.compresswand")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', wand.getNoPermissionMessage()));
            return;
        }
        Inventory inv = ((Chest) block.getState()).getBlockInventory();
        for (ItemStack stack : inv.getContents()) {
            if (stack == null || stack.getType() == Material.AIR) continue;

            inv.remove(stack);

            final int leftOver = stack.getAmount() % 9;
            switch (stack.getType()) {
                case IRON_INGOT:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.IRON_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.IRON_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.IRON_INGOT, leftOver));
                    break;

                case GOLD_INGOT:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.GOLD_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.GOLD_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.GOLD_INGOT, leftOver));
                    break;

                case COAL:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.COAL_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.COAL_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.COAL, leftOver));
                    break;

                case REDSTONE:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.REDSTONE_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.REDSTONE_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.REDSTONE, leftOver));
                    break;

                case DIAMOND:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.DIAMOND_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.DIAMOND_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.DIAMOND, leftOver));
                    break;

                case EMERALD:
                    if (leftOver == 0) {
                        inv.addItem(new ItemStack(Material.EMERALD_BLOCK, stack.getAmount() / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.EMERALD_BLOCK, (stack.getAmount() - leftOver) / 9));
                    inv.addItem(new ItemStack(Material.EMERALD, leftOver));
                    break;


                case INK_SACK:
                    if (new Dye(DyeColor.BLUE).toItemStack(stack.getAmount()).equals(stack)) {
                        if (leftOver == 0) {
                            inv.addItem(new ItemStack(Material.LAPIS_BLOCK, stack.getAmount() / 9));
                            break;
                        }
                        inv.addItem(new ItemStack(Material.LAPIS_BLOCK, (stack.getAmount() - leftOver) / 9));
                        inv.addItem(new Dye(DyeColor.BLUE).toItemStack(leftOver));
                        break;
                    } else if (new Dye(DyeColor.WHITE).toItemStack(stack.getAmount()).equals(stack)) {
                        if (leftOver == 0) {
                            inv.addItem(new ItemStack(Material.BONE_BLOCK, stack.getAmount() / 9));
                            break;
                        }
                        inv.addItem(new ItemStack(Material.BONE_BLOCK, (stack.getAmount() - leftOver) / 9));
                        inv.addItem(new Dye(DyeColor.WHITE).toItemStack(leftOver));
                        break;
                    }
                    break;

                case GOLD_NUGGET:
                    if (leftOver == 0) {
                        final int secondLeftOver = stack.getAmount() / 9 % 9;
                        if (secondLeftOver == 0) {
                            inv.addItem(new ItemStack(Material.GOLD_BLOCK, stack.getAmount() / 9 / 9));
                            break;
                        }
                        inv.addItem(new ItemStack(Material.GOLD_BLOCK, (stack.getAmount() - secondLeftOver) / 9));
                        inv.addItem(new ItemStack(Material.GOLD_INGOT, secondLeftOver));
                        break;
                    }
                    final int secondLeftOver = stack.getAmount() / 9 % 9;
                    if (secondLeftOver == 0) {
                        inv.addItem(new ItemStack(Material.GOLD_BLOCK, stack.getAmount() / 9 / 9));
                        break;
                    }
                    inv.addItem(new ItemStack(Material.GOLD_INGOT, secondLeftOver));
                    inv.addItem(new ItemStack(Material.GOLD_NUGGET, leftOver));
                    break;
            }

        }
    }
}
