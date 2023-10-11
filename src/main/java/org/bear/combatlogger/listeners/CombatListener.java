package org.bear.combatLogger.listeners;

import org.bear.combatLogger.data.InCombat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent attackEvent){
        if (!(attackEvent.getDamager() instanceof Player && attackEvent.getEntity() instanceof Player)) return;
        Player damager = (Player) attackEvent.getDamager();
        Player damagee = (Player) attackEvent.getEntity();
        if (!InCombat.isInCombat(damager.getUniqueId())){
            BossBar bossBar = Bukkit.createBossBar(ChatColor.RED + "You are in combat with " + damagee.getName() + " for 5 seconds", BarColor.RED, BarStyle.SOLID);
            bossBar.addPlayer(damager);
            InCombat.putInCombat(damager.getUniqueId(), 5, damagee, bossBar);//time means nothing rn

        }
        if (!InCombat.isInCombat(damagee.getUniqueId())){
            BossBar bossBar = Bukkit.createBossBar(ChatColor.RED + "You are in combat with " + damager.getName() + " for 5 seconds", BarColor.RED, BarStyle.SOLID);
            bossBar.addPlayer(damagee);
            InCombat.putInCombat(damagee.getUniqueId(), 5, damager, bossBar);//time means nothing rn
        }
    }
}
