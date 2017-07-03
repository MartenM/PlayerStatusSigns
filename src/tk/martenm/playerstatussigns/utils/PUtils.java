package tk.martenm.playerstatussigns.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import tk.martenm.playerstatussigns.MainClass;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Marten on 2-7-2017.
 * Plugins Utils for storing methods.
 */
public class PUtils {

    public static PlayerStatusSign getPlayerStatusSign(MainClass plugin, Location location){
        for(PlayerStatusSign s : plugin.signs){
            if(s.getLocation().equals(location)){
                return s;
            }
        }
        return null;
    }

    public static String replaceVariables(MainClass plugin, UUID uuid, String s) {
        String message = ChatColor.translateAlternateColorCodes('&', s);

        Player player = plugin.getServer().getPlayer(uuid);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (player != null) {
            //Online - Replace online variables
            message = message
                    .replace("%world%", player.getLocation().getWorld().getName())
                    .replace("%x%", player.getLocation().getBlockX() + "")
                    .replace("%y%", player.getLocation().getBlockY() + "")
                    .replace("%z%", player.getLocation().getBlockZ() + "");

            if(plugin.usesPlaceHolderApi){
                message = PlaceholderAPI.setPlaceholders(player, message);
            }
        } else {
            //Offline - Replace offline variables
            Date date = new Date(offlinePlayer.getLastPlayed());
            DateFormat formatter = new SimpleDateFormat(plugin.getConfig().getString("format.date"));
            String stringDate;

            if(offlinePlayer.getLastPlayed() != 0) {
                stringDate = formatter.format(date).replace(":", " / ");
            } else{
                stringDate = "Unknown";
            }

            message = message
                    .replace("%since%", stringDate);
        }

        //Always replace
        message = message
                .replace("%name%", offlinePlayer.getName())
                .replace("%player%", offlinePlayer.getName());

        if(plugin.essentials != null){
            message = message
                    .replace("%balance%", plugin.essentials.getUser(uuid).getMoney().toString());
        }
        return message;
    }
}
