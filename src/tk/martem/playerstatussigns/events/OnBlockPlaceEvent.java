package tk.martem.playerstatussigns.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import tk.martem.playerstatussigns.MainClass;

/**
 * Created by Marten on 1-7-2017.
 * Simple event handler
 */
public class OnBlockPlaceEvent implements Listener {

    private MainClass plugin;
    public OnBlockPlaceEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof Sign){
            Sign sign = (Sign) event.getBlock().getState();
            if(!event.getPlayer().hasPermission("playerstatussigns.destroy")){

                //Do stuff

            }
        }
    }

}
