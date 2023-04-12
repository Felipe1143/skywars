package felipe221.skywars.command;

import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.menus.ConfigMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyWarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("skywars.admin") || player.hasPermission("skywars.config")){
                if (args.length == 0){
                    ConfigMenu.openConfigMenu(player);
                }else{
                    if (args[0].contains("test")){
                       player.sendMessage(MessagesLoad.MessagesLine.ARENA_MAX.getMessage());
                    }
                }
            }else{
                player.sendMessage(ChatColor.RED + "¡No tienes permiso para ver esto!");
            }
        }

        return true;
    }
}
