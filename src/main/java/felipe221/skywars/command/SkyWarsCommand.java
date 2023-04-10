package felipe221.skywars.command;

import felipe221.skywars.load.KitLoad;
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
                if (args[0].contains("kit")){
                    KitLoad.fromConfigList(player);
                }
            }else{
                player.sendMessage(ChatColor.RED + "¡No tienes permiso para ver esto!");
            }
        }

        return true;
    }
}
