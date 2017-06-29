package tk.martem.playerstatussigns;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Marten on 29-6-2017.
 *   'There we go again...'
 */

public class MainClass extends JavaPlugin {

    private final Logger logger = getLogger();

    public void onEnable(){



        logger.info("Enabled player status signs successfully.");
    }

    public void onDisable(){



        logger.info("Disabled player status signs succesfully.");
    }


}
