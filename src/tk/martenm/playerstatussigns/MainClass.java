package tk.martenm.playerstatussigns;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.martenm.playerstatussigns.commands.PlayerStatusSignsCommand;
import tk.martenm.playerstatussigns.events.*;
import tk.martenm.playerstatussigns.objects.PlayerStatusSign;
import tk.martenm.playerstatussigns.utils.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Marten on 29-6-2017.
 *   'There we go again...'
 */

public class MainClass extends JavaPlugin {

    private final Logger logger = getLogger();
    public ArrayList<PlayerStatusSign> signs;
    public Essentials essentials;
    private Config signSaves;

    public void onEnable(){

        signs = new ArrayList<>();

        if(Bukkit.getPluginManager().isPluginEnabled("Essentials")){
            essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
            logger.info("Essentials plugin was found. Using it to determine if someone is AFK.");
        }
        else{
            logger.info("Essentials was not found. Cannot determine AFK.");
        }

        registerConfigs();
        registerCommands();
        registerEvents();

        loadSigns();

        logger.info("Enabled player status signs successfully.");
    }

    public void onDisable(){
        saveSigns();

        logger.info("Disabled player status signs successfully.");
    }



    private void registerConfigs(){
        saveDefaultConfig();
        signSaves = new Config(this, "SignSaves");
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnSignChangeEvent(this), this);
        pm.registerEvents(new OnPlayerJoinEvent(this), this);
        pm.registerEvents(new OnPlayerQuitEvent(this), this);
        pm.registerEvents(new OnBlockBreakEvent(this), this);

        if(essentials != null){
            pm.registerEvents(new OnPlayerAfkEvent(this), this);
            logger.info("Afk listener has been registered.");
        }
    }

    private void registerCommands(){
        getCommand("playerstatussigns").setExecutor(new PlayerStatusSignsCommand(this));
    }

    public void loadSigns(){
        if(!signSaves.contains("signs")){
            return;
        }

        for(String key : signSaves.getConfigurationSection("signs").getKeys(false)){
            try {
                UUID uuid = UUID.fromString(signSaves.getString("signs." + key + ".uuid")) ;
                Location loc = (Location) signSaves.get("signs." + key + ".location");

                signs.add(new PlayerStatusSign(this, uuid, loc));
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        logger.info("Signsaves have been loaded.");
    }

    public void saveSigns(){
        signSaves.set("signs", null);
        for(int i = 0; i < signs.size(); i++){
            PlayerStatusSign sign = signs.get(i);
            signSaves.set("signs." + i + ".uuid", sign.getUuid().toString());
            signSaves.set("signs." + i + ".location", sign.getLocation());
        }
        signSaves.save();
        logger.info("Signs have been saved!");
    }

    public void refreshSigns(){
        new BukkitRunnable(){

            @Override
            public void run() {
                Iterator<PlayerStatusSign> iterator = signs.iterator();
                while (iterator.hasNext()) {
                    PlayerStatusSign sign = iterator.next();

                    if (!sign.signExists()) {
                        iterator.remove();
                    } else {
                        sign.updateSign();
                    }
                }
            }
        }.runTaskLater(this, 5);
    }
}
