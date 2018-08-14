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

import me.max.lemonwands.wands.Wand;
import me.max.lemonwands.wands.WandManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private WandManager wandManager;

    public PlayerInteractListener(WandManager wandManager) {
        this.wandManager = wandManager;
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
        if (wand.getType() == Wand.Type.SELL) handleSellWand(p);
        else if (wand.getType() == Wand.Type.COMPRESS) handleCompressWand(p);
    }

    private void handleSellWand(Player p) {
        //todo
    }

    private void handleCompressWand(Player p) {
        //todo
    }

}
