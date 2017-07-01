package tk.martem.playerstatussigns;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.martem.playerstatussigns.commands.PlayerStatusSignsCommand;
import tk.martem.playerstatussigns.events.OnSignChangeEvent;
import tk.martem.playerstatussigns.objects.PlayerStatusSign;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Marten on 29-6-2017.
 *   'There we go again...'
 */

public class MainClass extends JavaPlugin {

    private final Logger logger = getLogger();
    public ArrayList<PlayerStatusSign> signs;
    public Essentials essentials;

    public void onEnable(){

        signs = new ArrayList<>();

        registerConfigs();
        registerCommands();
        registerEvents();

        loadSigns();

        if(Bukkit.getPluginManager().isPluginEnabled("Essentials")){
            essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
            logger.info("Essentials plugin was found. Using it to determine if someone is AFK.");
        }
        else{
            logger.info("Essentials was not found. Cannot determine AFK.");
        }

        logger.info("Enabled player status signs successfully.");
    }

    public void onDisable(){
        saveSigns();

        logger.info("Disabled player status signs successfully.");
    }



    public void registerConfigs(){
        saveDefaultConfig();
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnSignChangeEvent(this), this);
    }

    public void registerCommands(){
        getCommand("playerstatussigns").setExecutor(new PlayerStatusSignsCommand(this));
    }

    public void loadSigns(){

    }

    public void saveSigns(){

    }

}
