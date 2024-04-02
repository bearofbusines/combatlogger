package org.bear.combatlogger;

import org.bear.combatlogger.commands.ChangeConfigCommand;
import org.bear.combatlogger.config.DataFile;
import org.bear.combatlogger.data.CombatLoggedInventories;
import org.bear.combatlogger.listeners.CombatLoggingListener;
import org.bear.combatlogger.listeners.CombatListener;
import org.bear.combatlogger.listeners.CombatLoggerInventoryReplacementListener;
import org.bear.combatlogger.listeners.CommandBlockingListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLogger extends JavaPlugin {

    public static boolean disabling;
    public static DataFile config;
    @Override
    public void onEnable() {
        disabling = false;
        // Plugin startup logic
        CombatLogger.config = new DataFile(this, "config.yml");
        CombatLoggedInventories.setLoggedInventories(new DataFile(this, "loggedInventories.yml"));
        getServer().getPluginManager().registerEvents(new CombatLoggingListener(), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatLoggerInventoryReplacementListener(), this);
        getServer().getPluginManager().registerEvents(new CommandBlockingListener(), this);
        this.getCommand("combatconfig").setExecutor(new ChangeConfigCommand());
        this.getCommand("combatconfig").setTabCompleter(new ChangeConfigCommand());
    }

    @Override
    public void onDisable() {
        disabling = true;
        // Plugin shutdown logic
    }
}
