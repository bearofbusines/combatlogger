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

import java.util.ArrayList;

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
    public void editBossBar(BossBar bossBar, int length, int i, Player player){
        bossBar.setProgress(1-((double) (length - i) / 5));
        ArrayList<Integer> ids = InCombat.getTaskIds(player.getUniqueId());
        if (!ids.isEmpty())
                ids.remove(0);
    }
    public ArrayList<Integer> executeBossBarTimer(int length, int time, BossBar bossBar, Player player){
        ArrayList<Integer> runnables = new ArrayList<Integer>();
        for(int i = 0; i < length; i++){
            //System.out.println(i + " started timer");
            int finalI = i;
            runnables.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    editBossBar(bossBar, length, finalI, player);
                    InCombat.removeFromCombat(player.getUniqueId());
                    if (!InCombat.getTaskIds(player.getUniqueId()).isEmpty())
                        InCombat.getTaskIds(player.getUniqueId()).remove(0);
                }
            }, ((length - i) * 20L)));
        }
        runnables.add(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                bossBar.removePlayer(player);
                InCombat.removeFromCombat(player.getUniqueId());

            }
        }, (length * 20L) + 20));
        
        return runnables;
    }
    public void combatHandler(Player firstPlayer, Player secondPlayer){
        System.out.println("they handle");
        if (!InCombat.isInCombat(firstPlayer.getUniqueId())) {
            BossBar bossBar = Bukkit.createBossBar(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for 5 seconds", BarColor.RED, BarStyle.SOLID);
            bossBar.addPlayer(firstPlayer);
            int time = (int) System.currentTimeMillis() + 5;
            ArrayList<Integer> runnables = executeBossBarTimer(5, time, bossBar, firstPlayer);
            InCombat.putInCombat(firstPlayer.getUniqueId(), runnables, secondPlayer, bossBar);//time means nothing rn
            
        }else{
            for (int i : InCombat.getTaskIds(firstPlayer.getUniqueId())) {
                try {
                    Bukkit.getScheduler().cancelTask(i);
                }catch (Exception e){
                    //unCommentForDebug
                    //System.out.println("Task ID: " + i +"failed");
                }

            }
            int time = (int) System.currentTimeMillis() + 5;
            BossBar bossBar = Bukkit.createBossBar(ChatColor.RED + "You are in combat with " + secondPlayer.getName() + " for 5 seconds", BarColor.RED, BarStyle.SOLID);

            InCombat.getBossBar(firstPlayer.getUniqueId()).setVisible(false);
            InCombat.getBossBar(firstPlayer.getUniqueId()).removePlayer(firstPlayer);
            bossBar.addPlayer(firstPlayer);
            ArrayList<Integer> runnables = executeBossBarTimer(5, time, bossBar, firstPlayer);
            InCombat.getBossBar(firstPlayer.getUniqueId()).setProgress(1);
            InCombat.setBossbar(firstPlayer.getUniqueId() ,bossBar);
            InCombat.setRunnables(firstPlayer.getUniqueId(), runnables);
        }
    }
}
