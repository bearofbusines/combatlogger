package org.bear.combatlogger;

import org.bear.combatlogger.config.DataFile;
import org.bear.combatlogger.data.CombatLoggedInventories;
import org.bear.combatlogger.listeners.CombatLoggingListener;
import org.bear.combatlogger.listeners.CombatListener;
import org.bear.combatlogger.listeners.CombatLoggerInventoryReplacementListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLogger extends JavaPlugin {

    public static boolean disableing;
    @Override
    public void onEnable() {
        disableing = false;
        // Plugin startup logic
        CombatLoggedInventories.setLoggedInventories(new DataFile(this, "loggedInventories.yml"));
        getServer().getPluginManager().registerEvents(new CombatLoggingListener(), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatLoggerInventoryReplacementListener(), this);
    }

    @Override
    public void onDisable() {
        disableing = true;
        // Plugin shutdown logic
    }
}
