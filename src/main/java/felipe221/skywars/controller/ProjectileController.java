package felipe221.skywars.controller;

import felipe221.skywars.object.cosmetics.Projectiles;
import felipe221.skywars.object.User;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileController implements Listener {
    @EventHandler
    public void onArrowLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Arrow)){
            return;
        }

        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player shooter = (Player) e.getEntity().getShooter();
        Arrow arrow = (Arrow) e.getEntity();

        User.getUser(shooter).getTrail().setArrow(arrow).play();
    }

    @EventHandler
    public void onShootBow(ProjectileHitEvent e){
        if (e.getEntity().getShooter() instanceof Player){
            Player player = (Player) e.getEntity().getShooter();
            User user = User.getUser(player);

            if (e.getEntity() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getEntity();

                if (user.getArena() == null) {
                    return;
                }

                if (user.getArena().getProjectiles() == Projectiles.TypeProjectiles.EXPLOSIVE) {
                    Location loc = arrow.getLocation();

                    World world = loc.getWorld();
                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();

                    world.createExplosion(
                            x,
                            y,
                            z,
                            2,
                            false,
                            false);

                    return;
                }

                if (user.getArena().getProjectiles() == Projectiles.TypeProjectiles.TP){
                    Location loc = arrow.getLocation();

                    World world = loc.getWorld();
                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();

                    player.teleport(new Location(world,x,y,z));
                }
            }
        }
    }
}
