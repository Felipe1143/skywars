package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.User;
import felipe221.skywars.util.VoidGenerator;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class WorldLoad {
    //Map folder -> World folder
    public static void copyMapWorld(String mapName) {
        try {
            File arenaWorldFolder = new File(mapName);
            File arenaMapFolder = new File(Main.getInstance().getDataFolder() + "/maps/worlds/" + mapName);

            FileUtils.deleteQuietly(arenaWorldFolder);
            FileUtils.copyDirectory(arenaMapFolder, arenaWorldFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //World folder -> Map folder
    public static void copyWorldMap(World world) {
        if (world == null) {
            return;
        }

        final String dataFolder = Main.getInstance().getDataFolder().getPath();
        final File worldFolder = world.getWorldFolder();
        final File mapFolder = new File(dataFolder + "/maps/worlds/" + world.getName() + "/");

        try {
            FileUtils.deleteDirectory(mapFolder);
            FileUtils.copyDirectory(worldFolder, mapFolder);
            FileUtils.deleteDirectory(new File(worldFolder.getAbsolutePath() + "/data"));
            FileUtils.deleteDirectory(new File(worldFolder.getAbsolutePath() + "/playerdata"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    //Create void world
    public static World create(final String worldName) {
      //  BukkitUtil.runSync(() -> BukkitUtil.runAsync(() -> {
            World world = new WorldCreator(worldName).generateStructures(false).generator(new VoidGenerator()).createWorld();
            WorldBorder worldBorder = world.getWorldBorder();

            worldBorder.setCenter(0.0D, 0.0D);
            worldBorder.setSize(300.0D);
            worldBorder.setDamageAmount(2.0D);
            worldBorder.setDamageBuffer(0.0D);
            world.setThundering(false);
            world.setStorm(false);
            world.setTime(6000L);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.MOB_GRIEFING, false );
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false );
            world.setGameRule(GameRule.DO_FIRE_TICK, false );

            world.setSpawnLocation(0, 63, 0);

            return world;
     //   }));
    }

    //Kicks players from a world
    public static void kickPlayers(final World world) {
        if (world == null) {
            return;
        }

        Collection<Player> worldPlayers = world.getPlayers();

        for (Player player : worldPlayers) {
            if (player.isDead()) {
                player.spigot().respawn();
            }
            User user = User.getUser(player);
            user.teleportSpawn();

        }
    }

    //Unloads a currently loaded world
    public static void unload(World world) {
        if (world == null) {
            return;
        }

        Bukkit.unloadWorld(world, true);
    }

    //Deletes a world folder
    public static void delete(World world) {
        if (world == null) {
            return;
        }

        FileUtils.deleteQuietly(world.getWorldFolder());
    }
}
