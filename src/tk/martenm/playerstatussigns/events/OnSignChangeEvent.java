package tk.martenm.playerstatussigns.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.martenm.playerstatussigns.MainClass;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;

/**
 * Created by Marten on 1-7-2017.
 * Handles the sign change events.
 */
public class OnSignChangeEvent implements Listener {

    private MainClass plugin;
    public OnSignChangeEvent(MainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event){
        if(!event.getPlayer().hasPermission("playerstatussigns.create")){
            return;
        }

        //Probably not needed, but better safe then sorry.
        if(!(event.getBlock().getState() instanceof Sign)){
            return;
        }

        //Sign sign = (Sign) event.getBlock().getState();

        if(!(event.getLine(0).equalsIgnoreCase("[pss]") || event.getLine(0).equalsIgnoreCase("[pstatus]") || event.getLine(0).equalsIgnoreCase("[playerstatus]"))){
            return;
        }

        //Player status sign detected. Begin construction.
        String user = event.getLine(1);
        Player target = plugin.getServer().getPlayer(user);
        if(target == null || user.equalsIgnoreCase("")){
            event.getPlayer().sendMessage(ChatColor.RED + "That player has never played on this server before!");
            event.getBlock().breakNaturally();
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1, 3);
            return;
        }

        if(target == event.getPlayer()){
            if(!event.getPlayer().hasPermission("playerstatussigns.create.other")){
                event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create player status signs for other players.");
                return;
            }
        }

        PlayerStatusSign playerStatusSign = new PlayerStatusSign(plugin, target.getUniqueId(), event.getBlock().getLocation());
        plugin.signs.add(playerStatusSign);

        event.getPlayer().sendMessage(ChatColor.GREEN + "Successfully constructed a player status sign!");

        new BukkitRunnable(){
            @Override
            public void run() {
                playerStatusSign.updateSign();
            }
        }.runTaskLater(plugin, 5);
    }
}
