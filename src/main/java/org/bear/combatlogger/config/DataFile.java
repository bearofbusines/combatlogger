package org.bear.combatlogger.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataFile {

    private FileConfiguration config;
    private File file;
    private String name;
    JavaPlugin plugin;

    public DataFile(JavaPlugin plugin, String name) {
        this.name = name;
        this.plugin = plugin;

        saveDefault();
    }

    public void setCustomDefaults() {
        // Override to add custom default values
    }

    public void reload() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), name);
        }

        config = YamlConfiguration.loadConfiguration(file);

        InputStream stream = plugin.getResource(name);

        if (stream != null) {
            YamlConfiguration yaml_config = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
            config.setDefaults(yaml_config);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reload();
        }

        return config;
    }

    public void saveConfig() {
        if (config == null || file == null) {
            return;
        }

        try {
            getConfig().save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save data.", e);
        }
    }

    public void saveDefault() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), name);
        }

        if (!file.exists()) {
            plugin.saveResource(name, false);

            setCustomDefaults();
        }
    }

    public String getName() {
        return name;
    }

    public void clearFile() {
        file.delete();
        file = null;

        saveDefault();
        reload();
    }
}