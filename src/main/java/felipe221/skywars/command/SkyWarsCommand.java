package felipe221.skywars.command;

import felipe221.skywars.Main;
import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.load.WorldLoad;
import felipe221.skywars.menus.ConfigMenu;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SkyWarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("skywars.admin") || player.hasPermission("skywars.config")){
                if (args.length == 0){
                    player.sendMessage("§a§m+-------------------------+");
                    player.sendMessage("§e§l            SKYWARS");
                    player.sendMessage("§f");
                    player.sendMessage("§fComandos disponibles:");
                    player.sendMessage("§e- §f/sw setup §e(Configurar arenas)");
                    player.sendMessage("§e- §f/sw setspawn §e(Cambiar spawn principal)");
                    player.sendMessage("§f");
                    player.sendMessage("§fVersión: §a§n0.1.2§e [Felipe221]");
                    player.sendMessage("§a§m+-------------------------+");
                }else{
                    if (args[0].contains("setup")) {
                        ConfigMenu.openConfigMenu(player);
                    }else if (args[0].equalsIgnoreCase("setspawn")){
                        Location locPlayer = player.getLocation();

                        String x = String.valueOf(locPlayer.getBlockX());
                        String y = String.valueOf(locPlayer.getBlockY());
                        String z = String.valueOf(locPlayer.getBlockZ());

                        Main.getConfigManager().getConfig("config.yml").set("Main-Spawn.World", locPlayer.getWorld().getName());
                        Main.getConfigManager().getConfig("config.yml").set("Main-Spawn.Location", x+", "+y+", "+z);

                        Main.getConfigManager().save("config.yml");

                        player.sendMessage(ChatColor.GREEN + "¡Spawn cambiado a tu ubicación actual correctamente!");
                    }
                }
            }else{
                player.sendMessage(ChatColor.RED + "¡No tienes permiso para ver esto!");
            }
        }

        //todref
        return true;
    }
}
