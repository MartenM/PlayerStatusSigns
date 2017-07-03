package tk.martenm.playerstatussigns.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.martenm.playerstatussigns.MainClass;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;
import tk.martenm.playerstatussigns.utils.PUtils;

/**
 * Created by Marten on 2-7-2017.
 * Default Listener for the playerInteractEvent
 */
public class OnPlayerInteractEvent implements Listener {

    private MainClass plugin;
    public OnPlayerInteractEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        if(!(event.getClickedBlock().getState() instanceof Sign)){
            return;
        }

        PlayerStatusSign sign = PUtils.getPlayerStatusSign(plugin, event.getClickedBlock().getLocation());

        if(sign == null){
            return;
        }

        if(plugin.getConfig().getBoolean("clickable signs")) {
            sign.click(event.getPlayer());
        }
    }
}
