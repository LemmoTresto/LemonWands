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

import co.aikar.commands.BukkitCommandManager;
import me.max.lemonwands.commands.WandsCommand;
import me.max.lemonwands.listeners.PlayerInteractListener;
import me.max.lemonwands.wands.Wand;
import me.max.lemonwands.wands.WandManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class LemonWands extends JavaPlugin {

    private WandManager wandManager;
    private PriceManager priceManager;
    private Economy econ;
    private BukkitCommandManager manager;

    @Override
    public void onDisable() {
        getLogger().info("Unloading commands..");
        manager.unregisterCommands();
        getLogger().info("Unloaded commands!");
    }

    @Override
    public void onEnable() {

        if (!setupEconomy()) {
            getLogger().severe("Did not find Vault, plugin will not work without it. Disabling..");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Loading data..");
        saveDefaultConfig();

        ConfigurationSection wandsSection = getConfig().getConfigurationSection("wands");
        List<Wand> wands = new ArrayList<>();
        wandsSection.getKeys(false).forEach(s -> wands.add(
                new Wand(
                        Wand.Type.valueOf(s.toUpperCase()),
                        createItemStack(Material.matchMaterial(
                                wandsSection.getString(s + ".material")),
                                wandsSection.getString(s + ".displayname"),
                                wandsSection.getStringList(s + ".lore"),
                                wandsSection.getBoolean(s + ".glowing")),
                        wandsSection.getString(s + ".NO-PERMISSION-MESSAGE"))));
        wandManager = new WandManager(wands);

        Map<Material, Integer> prices = new HashMap<>();
        getConfig().getConfigurationSection("prices").getKeys(false)
                   .forEach(s -> prices.put(Material.matchMaterial(s), getConfig().getInt("prices." + s)));
        priceManager = new PriceManager(prices, econ);
        getLogger().info("Loaded data!");

        getLogger().info("Loading commands..");
        manager = new BukkitCommandManager(this);
        manager.registerCommand(new WandsCommand(wandManager));
        getLogger().info("Loaded commands!");

        getLogger().info("Loading listeners..");
        registerListeners(new PlayerInteractListener(wandManager, priceManager));
        getLogger().info("Loaded listeners!");
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private ItemStack createItemStack(Material type, String displayname, List<String> lore, boolean glowing) {
        ItemStack itemStack = new ItemStack(type, 1);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayname);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        if (glowing) {
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        }
        meta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public PriceManager getPriceManager() {
        return priceManager;
    }

    public WandManager getWandManager() {
        return wandManager;
    }

    public Economy getEcon() {
        return econ;
    }

}
