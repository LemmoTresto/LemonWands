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

package me.max.lemonwands;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Map;

public class PriceManager {

    private Map<Material, Integer> prices;

    public PriceManager(Map<Material, Integer> prices) {
        this.prices = prices;
    }

    public int getPrice(Material material) {
        return prices.get(material);
    }

    public int getTotalPrices(Inventory inventory) {
        return Arrays.stream(inventory.getContents())
                     .mapToInt(itemStack -> (getPrice(itemStack.getType()) * itemStack.getAmount())).sum();
    }

}
