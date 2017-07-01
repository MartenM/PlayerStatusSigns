package tk.martenm.playerstatussigns.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import tk.martenm.playerstatussigns.MainClass;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;

import java.util.List;

/**
 * Created by Marten on 1-7-2017.
 */
public class OnBlockBreakEvent implements Listener{

    private MainClass plugin;
    public OnBlockBreakEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof Sign){
            for(PlayerStatusSign pss : plugin.signs){
                if(pss.getLocation() == event.getBlock().getLocation()){
                    plugin.signs.remove(pss);
                    event.getPlayer().sendMessage(ChatColor.YELLOW + "Successfully removed the player status sign.");
                    break;
                }
            }
        }
    }
}
