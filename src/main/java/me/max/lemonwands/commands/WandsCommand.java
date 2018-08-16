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

package me.max.lemonwands.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.max.lemonwands.wands.Wand;
import me.max.lemonwands.wands.WandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@CommandAlias("wands|lw|lemonwands")
public class WandsCommand extends BaseCommand {

    private WandManager wandManager;

    public WandsCommand(WandManager wandManager) {
        this.wandManager = wandManager;
    }

    @Default
    @CatchUnknown
    @HelpCommand
    public void onHelp(CommandSender sender) {
        sender.sendMessage(ChatColor
                .translateAlternateColorCodes('&', "/&ewands give &f[player] [type] [durability] &8-&f Give a Wand to a player!\n" +
                        "/&ewands help &8-&f Shows this menu.\n" +
                        "/&ewands &8-&f Base command."));
    }

    @Subcommand("give")
    public void onWandsGive(CommandSender sender, Player p, String type, int durability) {
        ItemStack stack = wandManager.getWand(Wand.Type.valueOf(type)).getItem();
        stack.setDurability((short) durability);

        Map<Integer, ItemStack> stacks = p.getInventory().addItem(stack);
        if (stacks != null && !stacks.isEmpty()) {
            p.getWorld().dropItem(p.getLocation(), stacks.get(0));
        }

        sender.sendMessage(ChatColor.YELLOW + "Given Wand to " + p.getName());
        p.sendMessage(ChatColor.YELLOW + "You received a Wand!");
    }

}
