package org.bear.combatlogger.data;

import org.bear.combatlogger.config.DataFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class CombatLoggedInventories {
    private static DataFile loggedInventories;//is set in Application though setLoggedInventories

    public static boolean isInLoggedInventories(UUID uuid){
        FileConfiguration configuration = loggedInventories.getConfig();
        System.out.println(configuration.getConfigurationSection("inventories"));
        StaticHashMap inventories = (StaticHashMap) configuration.getConfigurationSection("inventories");
        assert inventories != null;
        Inventory inventory = (Inventory) inventories.get(uuid);
        return inventory != null;
    }

    public static void removeFromLoggedInventories(UUID uuid){
        FileConfiguration configuration = loggedInventories.getConfig();
        StaticHashMap inventories = (StaticHashMap) configuration.getConfigurationSection("inventories");
        assert inventories != null;
        inventories.remove(uuid);
        configuration.set("inventories", inventories);
       loggedInventories.saveConfig();
    }

    public static void putInLoggedInventories(UUID uuid, Inventory inventory){
        FileConfiguration configuration = loggedInventories.getConfig();
        System.out.println(configuration.getConfigurationSection("inventories"));
        StaticHashMap inventories = (StaticHashMap) configuration.getConfigurationSection("inventories");
        assert inventories != null;
        inventories.put(uuid, inventory);
        configuration.set("inventories", inventories);
       loggedInventories.saveConfig();
    }
    public static Inventory getInventory(UUID uuid){

        FileConfiguration configuration = loggedInventories.getConfig();
        assert configuration != null;
        StaticHashMap inventories = (StaticHashMap) configuration.getConfigurationSection("inventories");
        assert inventories != null;
        Inventory inventory = (Inventory) inventories.get(uuid);
        assert inventory != null;
        return inventory;
    }

    public static void setLoggedInventories(DataFile loggedInventories) {
        CombatLoggedInventories.loggedInventories = loggedInventories;
        if (!loggedInventories.getConfig().contains("inventories")) {
            System.out.println("BEARS COMBAT-LOGGER creating new loggedInventories.yml");
            StaticHashMap loggers =  new StaticHashMap();
            //CombatLoggedInventories.loggedInventories.getConfig().set("inventories", loggers);
            CombatLoggedInventories.loggedInventories.getConfig().createSection("inventories", loggers);
            CombatLoggedInventories.loggedInventories.saveConfig();
        }
    }
}
