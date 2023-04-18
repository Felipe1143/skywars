package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.events.PlayerLeaveGameEvent;
import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.load.KillsLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Kills;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        if(player.getHealth() - e.getFinalDamage() <= 0){
            if (User.getUser(player).getArena() == null) {
                return;
            }

            Arena arena = User.getUser(player).getArena();

            arena.removeAlivePlayer(player);
            arena.addSpectator(player);

            User.getUser(player).setAlive(false);

            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            ShowListener.deathHide(player);

            if (ItemsLoad.Items.SPECTATOR_TP.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.SPECTATOR_TP.getSlot(), ItemsLoad.Items.SPECTATOR_TP.getItemStack());
            }
            if (ItemsLoad.Items.EXIT_GAME.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.EXIT_GAME.getSlot(), ItemsLoad.Items.EXIT_GAME.getItemStack());
            }
            if (ItemsLoad.Items.PLAY_AGAIN.isEnable()) {
                player.getInventory().setItem(ItemsLoad.Items.PLAY_AGAIN.getSlot(), ItemsLoad.Items.PLAY_AGAIN.getItemStack());
            }


            if (e.getCause() == EntityDamageEvent.DamageCause.VOID){
                arena.sendMessage(KillsLoad.getRandomMessageByTypeKill(User.getUser(player).getKillTematica(), Kills.TypeKill.VOID)
                        .replaceAll("%player%", player.getName()));
            }else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                if (player.getLastDamageCause().getEntity() instanceof Arrow){
                    Arrow arrow = (Arrow) e.getEntity().getLastDamageCause().getEntity();
                    if (arrow.getShooter() instanceof Player){
                        Player killer = (Player) arrow.getShooter();
                        arena.sendMessage(KillsLoad.getRandomMessageByTypeKill(User.getUser(killer).getKillTematica(), Kills.TypeKill.BOW)
                                .replaceAll("%player%", player.getName())
                                .replaceAll("killer", killer.getName()));
                    }
                }
            }else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                if (player.getLastDamageCause().getEntity() instanceof Player) {
                    Player killer = (Player) e.getEntity().getLastDamageCause();

                    arena.sendMessage(KillsLoad.getRandomMessageByTypeKill(User.getUser(killer).getKillTematica(), Kills.TypeKill.BOW)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("killer", killer.getName()));
                }
            }

            if (arena.isSoloGame()) {
                Player winner = ArenaController.checkWinSolo(arena);

                if (winner != null) {
                    ArenaController.endGame(arena);
                }
            } else {
                Teams team = ArenaController.checkWinTeam(arena);

                if (team != null) {
                    ArenaController.endGame(arena);
                }
            }

            player.teleport(arena.getCenter());
        }
    }
}
