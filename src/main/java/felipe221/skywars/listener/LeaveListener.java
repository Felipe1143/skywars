package felipe221.skywars.listener;

import felipe221.skywars.FastBoard;
import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ChestController;
import felipe221.skywars.controller.KitController;
import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.load.StatsLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        e.setQuitMessage(null);
        Player player = e.getPlayer();

        FastBoard board = User.getUser(player).getBoard();
        Arena arena = User.getUser(player).getArena();

        if (arena != null) {
            ArenaController.leave(player, arena, true);
        }

        StatsLoad.send(player);

        User.getUser(player).setArena(null);
        User.getUser(player).getStats().send();
        User.getUser(player).remove();

        if (board != null) {
            board.delete();
        }

        //prevent exit on editing chest
        if (ChestController.isEditing(player)){
            ChestController.removeEdit(player);
        }

        //prevent exit on editing kits
        if (KitController.isEditing(player) || KitController.isCreating(player)){
            KitController.removeEditing(player);
            KitController.removeCreating(player);
        }

        //prevent exit on editing maps
        if (ArenaLoad.isEditing(player) || ArenaLoad.isCreating(player)){
            ArenaLoad.removeEditing(player);
            ArenaLoad.removeCreating(player);
        }
    }
}
