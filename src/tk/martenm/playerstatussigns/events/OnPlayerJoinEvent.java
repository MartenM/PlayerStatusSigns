package tk.martenm.playerstatussigns.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.martenm.playerstatussigns.MainClass;

/**
 * Created by Marten on 1-7-2017.
 * Generic listener
 */
public class OnPlayerJoinEvent implements Listener {

    private MainClass plugin;
    public OnPlayerJoinEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        plugin.refreshSigns();
    }
}
