package tk.martem.playerstatussigns.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.martem.playerstatussigns.MainClass;

/**
 * Created by Marten on 1-7-2017.
 * Class used for the special signs
 */
public class PlayerStatusSignsCommand implements CommandExecutor {

    private MainClass plugin;
    public PlayerStatusSignsCommand(MainClass plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //Do something here



        return false;
    }
}
