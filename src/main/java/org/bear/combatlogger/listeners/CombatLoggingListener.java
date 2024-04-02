package org.bear.combatlogger.listeners;

import org.bear.combatlogger.CombatLogger;
import org.bear.combatlogger.data.CombatLoggedInventories;
import org.bear.combatlogger.data.InCombat;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;
import java.util.Random;

public class CombatLoggingListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent quitEvent){
        if(CombatLogger.disabling) return;
        Player player = quitEvent.getPlayer();
        if (!InCombat.isInCombat(player.getUniqueId()))return;
        //InCombat.removeFromCombat(InCombat.getOtherPlayer(player.getUniqueId()).getUniqueId());
        Inventory inventory = Bukkit.createInventory(null,45);
        inventory.setContents(player.getInventory().getContents().clone());
        Location location = player.getLocation();

        Villager villager = (Villager) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.VILLAGER, false);
        villager.setAI(false);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();

        assert skullMeta != null;
        skullMeta.setOwningPlayer((OfflinePlayer) player);
        skull.setItemMeta(skullMeta);

        player.getInventory().clear();

        Objects.requireNonNull(villager.getEquipment()).setHelmet(skull);//will never be null
        villager.setCustomName(player.getName());




        CombatLoggedInventories.putInLoggedInventories(villager.getUniqueId(), inventory);
        player.setHealth(0);
        Player otherPlayer = InCombat.getOtherPlayer(player.getUniqueId());
        Bukkit.broadcastMessage(ChatColor.YELLOW + bullyText(player) + " combat logged on " + otherPlayer.getName());

        InCombat.removeFromCombat(player.getUniqueId());

    }
    public String bullyText(Player playerToBully){
        String[] bullyStrings  = {
                "It's Dominiover for " + playerToBully.getName() + ". They",
                playerToBully.getName() + " folded because they",
                playerToBully.getName() + " chickened out and"
        };
        Random rand = new Random();
        return bullyStrings[rand.nextInt(bullyStrings.length)];
    }

}
