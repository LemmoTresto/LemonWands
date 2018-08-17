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

package me.max.lemonwands.wands;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WandManager {

    private List<Wand> wands;

    public WandManager(List<Wand> wands) {
        this.wands = wands;
    }

    public List<Wand> getWands() {
        return wands;
    }

    public Wand getWand(Wand.Type type) {
        return wands.stream().filter(wand -> wand.getType() == type).findFirst().orElse(null);
    }

    public Wand getWand(ItemStack itemStack) {
        return wands.stream()
                    .filter(wand -> wand.getItem().getType() == itemStack.getType() && wand.getItem().getItemMeta()
                                                                                           .getLore().equals(itemStack
                                    .getItemMeta().getLore()) && wand.getItem().getItemMeta().getDisplayName()
                                                                     .equals(itemStack.getItemMeta().getDisplayName()))
                    .findFirst().orElse(null);
    }

}
