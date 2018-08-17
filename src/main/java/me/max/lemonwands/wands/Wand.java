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

public class Wand {

    private Type type;
    private ItemStack item;
    private String noPermissionMessage;

    public Wand(Type type, ItemStack item, String noPermissionMessage) {
        this.type = type;
        this.item = item;
        this.noPermissionMessage = noPermissionMessage;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public Type getType() {
        return type;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }


    public enum Type {
        SELL,
        COMPRESS;

        public Type matchType(String s) {
            try {
                Type type = Type.valueOf(s.toUpperCase());
                return type;
            } catch (Exception e) {
                if (s.equalsIgnoreCase("SELL") || s.equalsIgnoreCase("SEL")) return Type.SELL;
                if (s.equalsIgnoreCase("COMPRESS") || s.equalsIgnoreCase("COMPRES") || s.equalsIgnoreCase("COPRESS"))
                    return Type.COMPRESS;
            }
            return null;
        }
    }

}
