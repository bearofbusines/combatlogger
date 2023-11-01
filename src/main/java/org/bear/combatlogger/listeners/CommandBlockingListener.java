package org.bear.combatlogger.listeners;

import org.bear.combatlogger.CombatLogger;
import org.bear.combatlogger.data.InCombat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandBlockingListener implements Listener {
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        if(InCombat.isInCombat(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
            event.getPlayer().sendMessage("You can't execute commands in combat silly ;)");
        }
    }
}
