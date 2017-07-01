package tk.martenm.playerstatussigns.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.martenm.playerstatussigns.MainClass;

/**
 * Created by Marten on 1-7-2017.
 * Generic listener
 */
public class OnPlayerQuitEvent implements Listener {

    private MainClass plugin;
    public OnPlayerQuitEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerQuitEvent event){
        plugin.refreshSigns();
    }
}
