package org.bear.combatlogger.listeners;

import org.bear.combatlogger.CombatLogger;
import org.bear.combatlogger.data.CombatLoggedInventories;
import org.bukkit.Location;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class CombatLoggerInventoryReplacementListener implements Listener {
    @EventHandler
    public void mobDeath(EntityDeathEvent deathEvent){
        if(CombatLogger.disableing) return;
        if(!(deathEvent.getEntity() instanceof Zombie zombie))return;
        if(!CombatLoggedInventories.isInLoggedInventories(zombie.getUniqueId()))return;

        ArrayList<ItemStack> stupidPlayersInventory = CombatLoggedInventories.getInventory(zombie.getUniqueId());
        Location location = zombie.getLocation();
        for (ItemStack item : stupidPlayersInventory) {
            //System.out.println(item);
            if (item != null)
                Objects.requireNonNull(location.getWorld()).dropItem(location, item);
        }
        CombatLoggedInventories.removeFromLoggedInventories(zombie.getUniqueId());

    }
}
