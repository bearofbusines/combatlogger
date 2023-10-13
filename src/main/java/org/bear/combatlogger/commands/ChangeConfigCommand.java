package org.bear.combatlogger.commands;

import org.bear.combatlogger.CombatLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ChangeConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //getting it//int length = (int) CombatLogger.config.getConfig().getInt("length", 2);
        System.out.println(Arrays.toString(args));
        if (args[0].equals("length")) {
            CombatLogger.config.getConfig().set("length", args[1]);
            CombatLogger.config.saveConfig();
            sender.sendMessage("set combat timer length to: " + CombatLogger.config.getConfig().getInt("length"));
        }else if(args[0].equals("bannedCommands")) {
            CombatLogger.config.getConfig().set("bannedCommands", args[1]);
            CombatLogger.config.saveConfig();
            sender.sendMessage("set combat timer length to: " + CombatLogger.config.getConfig().getInt("bannedCommands"));
        }
        return false;
    }
}
