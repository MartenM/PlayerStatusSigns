package tk.martenm.playerstatussigns.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.martenm.playerstatussigns.MainClass;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;

import java.util.Iterator;

/**
 * Created by Marten on 1-7-2017.
 * Class used for the special signs
 */
public class PlayerStatusSignsCommand implements CommandExecutor {

    private MainClass plugin;
    public PlayerStatusSignsCommand(MainClass plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length < 1){
            sendHelp(sender);
            return true;
        }

        if(args[0].equalsIgnoreCase("refresh")) {
            if (sender.hasPermission("playerstatussigns.refresh")) {
                Iterator<PlayerStatusSign> iterator = plugin.signs.iterator();
                while (iterator.hasNext()) {
                    PlayerStatusSign sign = iterator.next();

                    if (!sign.signExists()) {
                        iterator.remove();
                    } else {
                        sign.updateSign();
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "Successfully refreshed " + ChatColor.YELLOW + plugin.signs.size() + ChatColor.GREEN + " signs.");
                return true;
            } else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no permission")));
                return true;
            }
        }

        if(args[0].equalsIgnoreCase("save")) {
            if(!sender.hasPermission("playerstatussigns.save")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no permission")));
                return true;
            }

            plugin.saveSigns();
            sender.sendMessage(ChatColor.GREEN + "Successfully saved the signs.");
            return true;
        }

        if(args[0].equalsIgnoreCase("load")) {
            if(!sender.hasPermission("playerstatussigns.load")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no permission")));
                return true;
            }

            plugin.loadSigns();
            sender.sendMessage(ChatColor.GREEN + "Successfully loaded the signs.");
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private String formatCommand(String command, String description){
        return ChatColor.GREEN + "  " + command + ChatColor.DARK_GRAY + " : " + ChatColor.YELLOW + description;
    }

    private void sendHelp(CommandSender sender){
        if(!sender.hasPermission("playerstatussigns.help")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no permission")));
            return;
        }

        sender.sendMessage(ChatColor.DARK_GRAY + "+──────┤ " + ChatColor.GREEN + ChatColor.BOLD + "Player Status Signs" + ChatColor.DARK_GRAY + "├──────+");
        sender.sendMessage(" ");
        if(sender.hasPermission("playerstatussigns.help")) sender.sendMessage(formatCommand("/pss help", "Shows this helpful help."));
        if(sender.hasPermission("playerstatussigns.refresh")) sender.sendMessage(formatCommand("/pss refresh", "Refresh all the signs."));
        if(sender.hasPermission("playerstatussigns.save")) sender.sendMessage(formatCommand("/pss save", "Save the signs."));
        if(sender.hasPermission("playerstatussigns.load")) sender.sendMessage(formatCommand("/pss load", "Load the signs."));
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.DARK_GRAY + "+──────────────────────────+");
    }
}
