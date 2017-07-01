package tk.martem.playerstatussigns.objects;

import com.sun.org.apache.xerces.internal.xs.StringList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import tk.martem.playerstatussigns.MainClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marten on 1-7-2017.
 * Simple player status sign holder
 */
public class PlayerStatusSign {

    private MainClass plugin;
    private UUID uuid;
    private Location loc;

    public PlayerStatusSign(MainClass plugin, UUID uuid, Location loc){
        this.plugin = plugin;
        this.loc = loc;
        this.uuid = uuid;
    }

    public void updateSign(){
        Sign sign;
        try {
            sign = getSign();
        } catch (Exception ex){
            plugin.signs.remove(this);
            return;
        }

        Player player = plugin.getServer().getPlayer(uuid);

        //Player is online
        if(player.isOnline()){
            if(plugin.essentials != null){
                if(plugin.essentials.getUser(uuid).isAfk()){
                    List<String> list = plugin.getConfig().getStringList("format.afk");
                    for(int i = 0; i < 4; i++){
                        sign.setLine(i, ChatColor.translateAlternateColorCodes('&', list.get(i)
                                .replace("%player%", player.getName())));
                    }
                }
            }

            //Online and not afk
            List<String> list = plugin.getConfig().getStringList("format.online");
            for(int i = 0; i < 4; i++){
                sign.setLine(i, ChatColor.translateAlternateColorCodes('&', list.get(i)
                        .replace("%player%", player.getName())));
            }
        }
        //Player is NOT online
        else{
            Date date = new Date(player.getLastPlayed());
            DateFormat formatter = new SimpleDateFormat(plugin.getConfig().getString("format.date"));
            String stringDate = formatter.format(date);


            List<String> list = plugin.getConfig().getStringList("format.afk");
            for(int i = 0; i < 4; i++){
                sign.setLine(i, ChatColor.translateAlternateColorCodes('&', list.get(i)
                        .replace("%player%", player.getName())
                        .replace("%since%", stringDate)));
            }
        }
    }

    private Sign getSign() throws Exception {
        Block block = loc.getBlock();
        if(block.getState() instanceof Sign){
            return (Sign) block.getState();
        }
        throw new Exception("No sign located");
    }
}
