package felipe221.skywars.listener;

import felipe221.skywars.controller.ChestController;
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
    }
}
