package felipe221.skywars.load;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import com.grinderwolf.swm.nms.CraftSlimeWorld;
import com.grinderwolf.swm.nms.SlimeNMS;
import com.grinderwolf.swm.plugin.config.ConfigManager;
import com.grinderwolf.swm.plugin.config.WorldData;
import com.grinderwolf.swm.plugin.config.WorldsConfig;
import felipe221.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.logging.Level;

public class WorldsLoad{
    SlimePlugin plugin;
    SlimeLoader loader;
    SlimeNMS slimeNMS;

    public WorldsLoad() {
        this.plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        this.loader = plugin.getLoader("file");
        // Attempt to get slime nms
        try {
            Field field = this.plugin.getClass().getDeclaredField("nms");
            field.setAccessible(true);
            this.slimeNMS = (SlimeNMS) field.get(this.plugin);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            slimeNMS = null;
            e.printStackTrace();
        }
    }

    public World createEmptyWorld(String name, World.Environment environment) {
        WorldData worldData = new WorldData();
        worldData.setSpawn("0, 64, 0");
        SlimePropertyMap propertyMap = worldData.toPropertyMap();
        propertyMap.setString(SlimeProperties.ENVIRONMENT, environment.name());
        propertyMap.setString(SlimeProperties.DIFFICULTY, "normal");

        try {
            SlimeWorld slimeWorld = plugin.createEmptyWorld(loader, name, false, propertyMap);

            plugin.generateWorld(slimeWorld);

            World bukkitWorld = Bukkit.getWorld(name);

            Location location = new Location(bukkitWorld, 0, 61, 0);
            location.getBlock().setType(Material.BEDROCK);

            WorldsConfig config = ConfigManager.getWorldConfig();
            config.getWorlds().put(name, worldData);
            config.save();

            bukkitWorld.setDifficulty(org.bukkit.Difficulty.NORMAL);
            bukkitWorld.setSpawnFlags(true, true);
            bukkitWorld.setPVP(true);
            bukkitWorld.setStorm(false);
            bukkitWorld.setThundering(false);
            bukkitWorld.setWeatherDuration(Integer.MAX_VALUE);
            bukkitWorld.setKeepSpawnInMemory(false);
            bukkitWorld.setTicksPerAnimalSpawns(1);
            bukkitWorld.setTicksPerMonsterSpawns(1);
            bukkitWorld.setAutoSave(false);

            return bukkitWorld;
        } catch (WorldAlreadyExistsException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteWorld(String name, boolean removeFile) {
        Bukkit.unloadWorld(name, false);

        if (removeFile) {
            try {
                loader.deleteWorld(name);
            } catch (UnknownWorldException | IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Something went wrong whilst deleting a world for the arena " + name);
                e.printStackTrace();
            }
        }
    }

    public void saveWorldFile(String worldName){
        File world_map = new File(Main.getInstance().getDataFolder().getAbsoluteFile().getParentFile().getParentFile() + "/slime_worlds", worldName + ".slime");
        File config_folder = new File(Main.getInstance().getDataFolder() + "/maps/worlds", worldName + ".slime");

        Bukkit.getWorld(worldName).save();
        try {
            SlimeWorld slimeWorld = slimeNMS.getSlimeWorld(Bukkit.getWorld(worldName));

            byte[] serializedWorld = ((CraftSlimeWorld) slimeWorld).serialize();

            loader.saveWorld(worldName, serializedWorld, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            copyFile(world_map,config_folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loadWorldConfig(String worldName){
        //deleteWorld(worldName, true);

        File world_map = new File(Main.getInstance().getDataFolder().getAbsoluteFile().getParentFile().getParentFile() + "/slime_worlds", worldName + ".slime");
        File config_folder = new File(Main.getInstance().getDataFolder() + "/maps/worlds", worldName + ".slime");

        if (!config_folder.exists()){
            System.out.println("¡No existe el archivo del mundo con el nombre "+ worldName+ "!");

            return false;
        }
        try {
            copyFile(config_folder, world_map);
        } catch (IOException e) {
            System.out.println("¡Hubo un error al copiar el backup del mundo con el nombre "+ worldName+ "!");

            e.printStackTrace();

            return false;
        }

        WorldsConfig config = ConfigManager.getWorldConfig();
        WorldData worldData = config.getWorlds().get(worldName);
        Bukkit.unloadWorld(worldName, false);


        try {
            SlimeWorld slimeWorld = plugin.loadWorld(loader, worldName, worldData.isReadOnly(), worldData.toPropertyMap());
            plugin.generateWorld(slimeWorld);
        } catch (IOException | CorruptedWorldException | WorldInUseException | NewerFormatException | UnknownWorldException e) {
            System.out.println("¡Hubo un error al cargar el backup del mundo con el nombre "+ worldName+ "!");
            return false;
        }

        this.setWorldSettings(Bukkit.getWorld(worldName));
        return true;
    }

    // UTILS

    public void setWorldSettings(World world) {
        world.setSpawnFlags(true, true);
        world.setPVP(true);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        world.setAutoSave(false);
    }

    private static final String[] ignore = new String[] { "session.dat", "uid.dat" };

    static void copyFile(File src, File dst) throws IOException {
        if (Arrays.binarySearch(ignore, src.getName()) >= 0) {
            return;
        }

        if (src.isDirectory()) {
            if (!dst.exists()) {
                dst.mkdirs();
            } else if (!dst.isDirectory()) {
                throw new IllegalArgumentException("src is a directory, dst is not");
            }
            File[] sub = src.listFiles();
            for (File file : src.listFiles()) {
                copyFile(file, new File(dst, file.getName()));
            }
            return;
        }

        if (dst.isDirectory()) {
            throw new IllegalArgumentException("dst is a directory, src is not");
        }

        Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
