package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.events.PlayerLeaveEvent;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class DeathListener implements Listener {
    private static HashMap<Player, Player> lastDamage = new HashMap<>();

    @EventHandler
    public void onPlayerLeaveGame(PlayerLeaveEvent e){
        lastDamage.remove(e.getPlayer());
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        if(player.getHealth()-e.getFinalDamage() > 0){
            if (e.getDamager() instanceof Player) {
                lastDamage.put(player, (Player) e.getDamager());
            }
        }else {
            if (User.getUser(player).getArena() == null) {
                return;
            }

            Arena arena = User.getUser(player).getArena();
            ArenaController arenaController = new ArenaController(arena);

            arena.removePlayer(player);
            arena.addSpectators(player);

            User.getUser(player).setAlive(false);

            if (arena.isSoloGame()) {
                Player winner = arenaController.checkWinSolo();

                //has winner
                if (winner != null) {

                }
            } else {
                Teams team = arenaController.checkWinTeam();

                //has winner
                if (team != null) {

                }
            }

            //if killer is player
            if (e.getDamager() instanceof Player) {
                Player killer = (Player) e.getDamager();

                //force respawn teleport in killer location
                if (player.getBedSpawnLocation() == null) {
                    player.teleport(killer.getLocation());
                }

                player.teleport(killer.getLocation());
                killer.setHealth(player.getMaxHealth());

                return;
            }

            //TODO mensaje de muerte
            if (player.getBedSpawnLocation() == null) {
                player.teleport(arena.getCenter());
            }

            player.teleport(arena.getCenter());
        }
    }
}
