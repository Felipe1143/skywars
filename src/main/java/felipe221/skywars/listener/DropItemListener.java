package felipe221.skywars.listener;

import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        Arena arena = User.getUser(player).getArena();

        if (arena == null){
            if (player.hasPermission("skywars.config") || player.hasPermission("skywars.admin")){
                return;
            }

            e.setCancelled(true);
        }else{
            if (arena.getStatus() != Arena.Status.INGAME){
                e.setCancelled(true);
            }
        }
    }
}
