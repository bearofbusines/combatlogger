package org.bear.combatlogger.data;

import org.bear.combatlogger.config.DataFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class CombatLoggedInventories {
    private static DataFile loggedInventories;//is set in Application though setLoggedInventories

    public static boolean isInLoggedInventories(UUID uuid){
        FileConfiguration configuration = loggedInventories.getConfig();
        return configuration.getList("inventories" + uuid.toString()) != null;
    }

    public static void removeFromLoggedInventories(UUID uuid){
        FileConfiguration configuration = loggedInventories.getConfig();
        configuration.set("inventories" + uuid.toString(), null);
        loggedInventories.saveConfig();
    }

    public static void putInLoggedInventories(UUID uuid, Inventory inventory){
        FileConfiguration configuration = loggedInventories.getConfig();
        ArrayList<ItemStack> itemStack = new ArrayList<>();
        for (ItemStack item : inventory.getContents()){
            if(item == null)continue;
            itemStack.add(item);
        }
        configuration.set("inventories" + uuid.toString(), itemStack);
       loggedInventories.saveConfig();
    }
    public static ArrayList<ItemStack> getInventory(UUID uuid){
        FileConfiguration configuration = loggedInventories.getConfig();

        return (ArrayList<ItemStack>) configuration.getList("inventories" + uuid.toString(), new ArrayList<ItemStack>());
    }

    public static void setLoggedInventories(DataFile loggedInventories) {
        CombatLoggedInventories.loggedInventories = loggedInventories;
//        System.out.println("\033[0;32mCleaning up CombatLogging config\033[0m");
//        HashMap<String, Inventory> inventoryHashMap = new HashMap<String, Inventory>();
//        FileConfiguration config = CombatLoggedInventories.loggedInventories.getConfig();
//        if (config.getConfigurationSection("inventories") == null){
//            config.createSection("inventories");
//        }
//        for (String key : Objects.requireNonNull(config.getConfigurationSection("inventories")).getKeys(false)){
//            if (config.get(key) == null) continue;
//            inventoryHashMap.put(key, (Inventory) config.get(key));
//        }
//        loggedInventories.clearFile();
//        File file = new File(loggedInventories.getPlugin().getDataFolder(), loggedInventories.getName());
//
//        CombatLoggedInventories.loggedInventories.resetFile("inventories", inventoryHashMap);
    }
}
