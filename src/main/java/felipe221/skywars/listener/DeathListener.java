package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        if(player.getHealth()-e.getFinalDamage() > 0) return;

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
            if (winner != null){

            }
        } else {
            Teams team = arenaController.checkWinTeam();

            //has winner
            if (team != null){

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

            return;
        }

        //TODO mensaje de muerte
        //if death cause is other teleport to random player alive
        Player playerRandom = (Player) arena.getPlayers().toArray()[0];
        if (player.getBedSpawnLocation() == null) {
            player.teleport(playerRandom.getLocation());
        }

        player.teleport(playerRandom.getLocation());
    }
}
