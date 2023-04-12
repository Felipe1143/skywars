package felipe221.skywars.listener;

import felipe221.skywars.controller.ChestController;
import felipe221.skywars.controller.KitController;
import felipe221.skywars.load.ArenaLoad;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

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
