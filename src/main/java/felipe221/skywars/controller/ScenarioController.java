package felipe221.skywars.controller;

import felipe221.skywars.Main;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Scenario;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ScenarioController implements Listener {
    //SCAFFOLD
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        User user = User.getUser(player);

        if (user.getArena() == null){
            return;
        }

        if (user.getArena().getScenario() != Scenario.TypeScenario.SCAFFOLD){
            return;
        }

        Location location = player.getLocation();
        Block underBlock = location.add(0,-1,0).getBlock();

        if (underBlock.getType() == Material.AIR){
            underBlock.setType(Material.ACACIA_WOOD);
            underBlock.getState().update();
            player.sendMessage("lower");
        }
    }

    //ANTI-KB
    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();

        if(entity instanceof Player) {
            Player player = (Player) e.getEntity();

            if (User.getUser(player).getArena() == null){
                return;
            }

            if (User.getUser(player).getArena().getScenario() == Scenario.TypeScenario.ANTIKB){
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.setVelocity(new Vector()), 1L);
            }
        }
    }

    public static void setScenario(Arena arena) {
        //SPEED
        if (arena.getScenario() == Scenario.TypeScenario.SPEED) {
            for (Player player : arena.getAllPlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            }
            return;
        }

        //TORMENTA
        if (arena.getScenario() == Scenario.TypeScenario.TORMENTA) {
            WorldBorder border = arena.getWorld().getWorldBorder();

            new BukkitRunnable() {
                double counter = 0.0;
                @Override
                public void run() {
                    if (arena.getStatus() != Arena.Status.INGAME){
                        cancel();
                    }

                    if (arena.getTime() > 340){
                        cancel();
                    }

                    border.setSize(300.0D - counter);


                    if (arena.getTime() == 60){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 200));
                    }

                    if (arena.getTime() == 120){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 150));
                    }

                    if (arena.getTime() == 180){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 100));
                    }

                    if (arena.getTime() == 220){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 50));
                    }

                    if (arena.getTime() == 280){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 25));
                    }

                    if (arena.getTime() == 340){
                        arena.sendMessage(MessagesLoad.MessagesLine.BORDER_MOVE.setArena(arena).getMessage().replaceAll("%radius%", "" + 15));
                    }

                    counter = counter + 1.0D;
                }
            }.runTaskTimer(Main.getInstance(), 0,25L);

        }
    }
}
