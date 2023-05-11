package felipe221.skywars.listener;

import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import felipe221.skywars.object.iStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        Arena arena = User.getUser(player).getArena();

        if (arena == null) {
            if (player.hasPermission("skywars.config") || player.hasPermission("skywars.admin")){
                return;
            }

            e.setCancelled(true);
        }else{
            if (arena.getStatus() != Arena.Status.INGAME){
                e.setCancelled(true);
            }else{
                iStats.TypeStats typeStats = (arena.isSoloGame() == true ? iStats.TypeStats.SOLO : iStats.TypeStats.TEAM);
                User.getUser(player).getStats().addStatValue(typeStats, iStats.Stats.BLOCK_BROKEN, 1);
            }
        }
    }
}
