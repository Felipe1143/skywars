package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.events.PlayerLeaveGameEvent;
import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class DeathListener implements Listener {
    private static HashMap<Player, Player> lastDamage = new HashMap<>();

    @EventHandler
    public void onPlayerLeaveGame(PlayerLeaveGameEvent e){
        lastDamage.remove(e.getPlayer());
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        if(player.getHealth() - e.getFinalDamage() > 0){
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

            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);

            if (ItemsLoad.Items.SPECTATOR_TP.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.SPECTATOR_TP.getSlot(), ItemsLoad.Items.SPECTATOR_TP.getItemStack());
            }
            if (ItemsLoad.Items.EXIT_GAME.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.EXIT_GAME.getSlot(), ItemsLoad.Items.EXIT_GAME.getItemStack());
            }
            if (ItemsLoad.Items.PLAY_AGAIN.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.PLAY_AGAIN.getSlot(), ItemsLoad.Items.PLAY_AGAIN.getItemStack());
            }

            if (arena.isSoloGame()) {
                Player winner = arenaController.checkWinSolo();

                //has winner
                if (winner != null) {
                    arenaController.endGame();
                }
            } else {
                Teams team = arenaController.checkWinTeam();

                //has winner
                if (team != null) {
                    arenaController.endGame();
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
