package felipe221.skywars.command;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                Arena arena = User.getUser(player).getArena();

                if (arena != null) {
                    ArenaController.leave(player, arena);
                }else{
                    player.sendMessage(MessagesLoad.MessagesLine.NOT_ARENA.getMessage());
                }
            }else{
                player.sendMessage("&c¡Utiliza /leave!");
            }
        }

        return true;
    }
}
