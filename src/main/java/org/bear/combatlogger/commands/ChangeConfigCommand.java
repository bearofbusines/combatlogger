package org.bear.combatlogger.commands;

import org.bear.combatlogger.CombatLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ChangeConfigCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //getting it//int length = (int) CombatLogger.config.getConfig().getInt("length", 2);
        if (sender instanceof Player player){
            if (!player.isOp()){
                player.sendMessage("You Dont have perms to run this silly ;)");
                return false;
            }
        }
        System.out.println(Arrays.toString(args));
        if (args[0].equals("length")) {
            if (args.length == 2){
                try {
                    int length = Integer.parseInt(args[1]);
                }catch (Exception e){
                    sender.sendMessage("Invalid 3rd Argument");
                    return false;
                }
                
            }
            CombatLogger.config.getConfig().set("length", Integer.parseInt(args[1]));
            CombatLogger.config.saveConfig();
            sender.sendMessage("set combat timer length to: " + CombatLogger.config.getConfig().getInt("length"));
        }else if(args[0].equals("bannedCommands")) {
            CombatLogger.config.getConfig().set("bannedCommands", args[1]);
            CombatLogger.config.saveConfig();
            sender.sendMessage("set combat timer length to: " + CombatLogger.config.getConfig().getInt("bannedCommands"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player){
            if (!player.isOp()){
                return List.of("");
            }
        }
        if(args.length == 1){
            return List.of("length");
        }
        return List.of("");
    }
}