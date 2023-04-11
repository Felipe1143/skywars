package felipe221.skywars.listener;

import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        Player player = e.getPlayer();

        User user = User.getUser(player);

        if (user.getArena() != null){
            Arena arena = user.getArena();

            if (arena.getStatus() != Arena.Status.INGAME){
                e.setCancelled(true);
            }
        }
    }
}
