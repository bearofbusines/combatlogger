package org.bear.combatlogger.listeners;

import org.bear.combatlogger.CombatLogger;
import org.bear.combatlogger.data.CombatLoggedInventories;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class CombatLoggerInventoryReplacementListener implements Listener {
    @EventHandler
    public void mobDeath(EntityDeathEvent deathEvent){
        if(CombatLogger.disabling) return;
        if(!(deathEvent.getEntity() instanceof Villager villager))return;
        if(!CombatLoggedInventories.isInLoggedInventories(villager.getUniqueId()))return;

        ArrayList<ItemStack> stupidPlayersInventory = CombatLoggedInventories.getInventory(villager.getUniqueId());
        Location location = villager.getLocation();
        for (ItemStack item : stupidPlayersInventory) {
            //System.out.println(item);
            if (item != null)
                Objects.requireNonNull(location.getWorld()).dropItem(location, item);
        }
        CombatLoggedInventories.removeFromLoggedInventories(villager.getUniqueId());

    }
}
