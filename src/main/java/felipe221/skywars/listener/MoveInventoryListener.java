package felipe221.skywars.listener;

import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class MoveInventoryListener implements Listener {
    @EventHandler
    public void onPlayerMoveInventory(InventoryMoveItemEvent e){
        Player player = (Player) e.getSource().getViewers().get(0);

        if (player != null) {
            Arena arena = User.getUser(player).getArena();

            if (arena == null) {
                if (player.hasPermission("skywars.config") || player.hasPermission("skywars.admin")) {
                    return;
                }

                e.setCancelled(true);
            } else {
                if (arena.getStatus() != Arena.Status.INGAME) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
