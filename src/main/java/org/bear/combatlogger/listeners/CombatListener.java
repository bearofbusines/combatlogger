package org.bear.combatlogger.listeners;

import org.bear.combatlogger.data.InCombat;
import org.bear.combatlogger.CombatLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CombatListener implements Listener {
    public JavaPlugin plugin;
    public CombatListener(JavaPlugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent attackEvent){
        if(CombatLogger.disableing) return;
        if (!(attackEvent.getDamager() instanceof Player damager && attackEvent.getEntity() instanceof Player damagee)) return;
        //if (!InCombat.isInCombat(damager.getUniqueId()))
        combatHandler(damager, damagee);
        combatHandler(damagee, damager);
    }
    public void editBossBar(BossBar bossBar, int length, int i, Player player, Player secondPlayer){
        bossBar.setProgress(1-((double) (length - i) / length));
        bossBar.setTitle(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for " + (length - (length - i)) + " seconds");
        //ArrayList<Integer> ids = InCombat.getTaskIds(player.getUniqueId());
        //if (!ids.isEmpty())
        //        ids.remove(0);
    }
    public void executeBossBarTimer(int length, UUID combatId, BossBar bossBar, Player player, Player secondPlayer){
        for(int i = 0; i < length; i++){
            //System.out.println(i + " started timer");
            int finalI = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
//                    System.out.println(player.getName() + " inCombat?: " + InCombat.isInCombat(player.getUniqueId()));
//                    System.out.println(player.getName() + " combatId?: " + combatId.toString());
//                    try {
//                        System.out.println(player.getName() + " InCombat.combatId?: " + InCombat.getCombatId(player.getUniqueId()).toString());
//                        System.out.println(player.getName() + " CorrectCombatId?: " + InCombat.getCombatId(player.getUniqueId()).equals(combatId));
//                    }catch (Exception ignored){
//
//                    }
                    boolean triedCheck = InCombat.isInCombat(player.getUniqueId());
                    try{
                        triedCheck = triedCheck && InCombat.getCombatId(player.getUniqueId()).equals(combatId);
                    }catch (Exception ignored){
                        System.out.println(player.getName() + "'s Combat Hashmap didnot exist for some reason. triedCheck Value is still:" + triedCheck);
                    }
                    if(!triedCheck){
                        //System.out.println("killend");
                        return;
                    }
                    editBossBar(bossBar, length, finalI, player, secondPlayer);
                    //System.out.println(player.getName() + " removed from combat");
                    //InCombat.removeFromCombat(player.getUniqueId());
                }
            }, ((length - i) * 20L));
        }
       Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
//                System.out.println(player.getName() + " inCombat?: " + InCombat.isInCombat(player.getUniqueId()));
//                System.out.println(player.getName() + " combatId?: " + combatId.toString());
//                try {
//                    System.out.println(player.getName() + " InCombat.combatId?: " + InCombat.getCombatId(player.getUniqueId()).toString());
//                    System.out.println(player.getName() + " CorrectCombatId?: " + InCombat.getCombatId(player.getUniqueId()).equals(combatId));
//                }catch (Exception ignored){
//
//                }
                boolean triedCheck = InCombat.isInCombat(player.getUniqueId());
                try{
                    triedCheck = triedCheck && InCombat.getCombatId(player.getUniqueId()).equals(combatId);
                }catch (Exception ignored){
                    System.out.println(player.getName() + "'s Combat Hashmap didnot exist for some reason. triedCheck Value is still:" + triedCheck);
                }
                if(!triedCheck){

                    //System.out.println("killend");
                    return;
                }
                System.out.println(player.getName() + " removed from combat");
                InCombat.removeFromCombat(player.getUniqueId());

            }
        }, (length * 20L) + 20);
    }
    public void combatHandler(Player firstPlayer, Player secondPlayer){
        int combatLength = CombatLogger.config.getConfig().getInt("length");
        //System.out.println("they handle");
        if (!InCombat.isInCombat(firstPlayer.getUniqueId())) {
            BossBar bossBar = Bukkit.getServer().createBossBar(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for " + combatLength + " seconds", BarColor.RED, BarStyle.SOLID);
            bossBar.addPlayer(firstPlayer);
            UUID combatId = UUID.randomUUID();
            InCombat.putInCombat(firstPlayer.getUniqueId(), combatId, secondPlayer, bossBar);//time means nothing rn
            executeBossBarTimer(combatLength, combatId, bossBar, firstPlayer, secondPlayer);
        }else{
            //BossBar bossBar = Bukkit.getServer().createBossBar(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for 5 seconds", BarColor.RED, BarStyle.SOLID);
            //InCombat.getBossBar(firstPlayer.getUniqueId()).removePlayer(firstPlayer);
            //bossBar.addPlayer(firstPlayer);
            UUID combatId = UUID.randomUUID();
            BossBar currentBossBar = InCombat.getBossBar(firstPlayer.getUniqueId());
            currentBossBar.setProgress(1);
            currentBossBar.setTitle(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for " + combatLength + " seconds");
            InCombat.setCombatId(firstPlayer.getUniqueId(), combatId);
            executeBossBarTimer(combatLength, combatId, currentBossBar, firstPlayer, secondPlayer);
        }
    }
}
