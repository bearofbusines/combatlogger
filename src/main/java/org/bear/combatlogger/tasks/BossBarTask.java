package org.bear.combatlogger.tasks;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarTask extends BukkitRunnable {
    private final JavaPlugin plugin;

    private int counter;

    public BossBarTask(JavaPlugin plugin, int counter) {
        this.plugin = plugin;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        if (counter > 0) {
        } else {
            this.cancel();
        }
    }
}
