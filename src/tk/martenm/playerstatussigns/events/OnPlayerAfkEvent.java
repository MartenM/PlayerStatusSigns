package tk.martenm.playerstatussigns.events;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tk.martenm.playerstatussigns.MainClass;

/**
 * Created by Marten on 1-7-2017.
 * Generic listener
 */
public class OnPlayerAfkEvent implements Listener {

    private MainClass plugin;
    public OnPlayerAfkEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAfkEvent(AfkStatusChangeEvent event){
        plugin.refreshSigns();
    }
}
