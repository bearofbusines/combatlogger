package org.bear.combatLogger.listeners;

import org.bear.combatLogger.data.CombatLoggedInventories;
import org.bear.combatLogger.data.InCombat;
import org.bukkit.*;
import org.bukkit.entity.*;
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
        Player player = quitEvent.getPlayer();
        if (!InCombat.isInCombat(player.getUniqueId()))return;

        Inventory inventory = Bukkit.createInventory(null,45);
        inventory.setContents(player.getInventory().getContents().clone());
        Location location = player.getLocation();

        Zombie zombie = (Zombie) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ZOMBIE, false);
        zombie.setAI(false);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();

        assert skullMeta != null;
        skullMeta.setOwningPlayer((OfflinePlayer) player);
        skull.setItemMeta(skullMeta);

        player.getInventory().clear();

        Objects.requireNonNull(zombie.getEquipment()).setHelmet(skull);//will never be null
        zombie.setCustomName(player.getName());




        CombatLoggedInventories.putInLoggedInventories(zombie.getUniqueId(), inventory);
        player.setHealth(0);
        Player otherPlayer = InCombat.getOtherPlayer(InCombat.getIndex(player.getUniqueId()));
        Bukkit.broadcastMessage(ChatColor.GREEN + bullyText(otherPlayer) + " combat logged on " + player.getName());

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
