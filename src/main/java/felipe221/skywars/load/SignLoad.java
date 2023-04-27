package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SignLoad {
    private static int id = 0;

    public enum Signs{
        TOP, ARENA;

        private HashMap<Sign, Object> signs;
        private HashMap<Sign, Integer> position;
        private List<String> lines;

        Signs() {
            this.signs = new HashMap<>();
            this.position = new HashMap<>();
            this.lines = new ArrayList<>();
        }

        public void addSign(Sign sign, Object value){
            signs.put(sign, value);
        }

        public HashMap<Sign, Object> getSigns() {
            return signs;
        }

        public void setSigns(HashMap<Sign, Object> signs) {
            this.signs = signs;
        }

        public int getPosition(Sign sign) {
            return position.get(sign);
        }

        public void setPosition(Sign sign, int position) {
            this.position.put(sign, position);
        }

        public List<String> getLines() {
            return lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public void update(){
            if (this == ARENA) {
                for (Sign s : signs.keySet()) {
                    List<String> changed = BukkitUtil.replaceVariables(null, Arena.getByName((String) signs.get(s)), lines);

                    for (int a = 0; a < changed.size(); a++) {
                        s.setLine(a, changed.get(a));
                    }

                    s.update();
                }
            }

            if (this == TOP){
                for (Sign s : signs.keySet()) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(TopLoad.TypeTop.valueOf((String) signs.get(s)).getValues().get(this.position.get(s)-1));
                    String topType = TopLoad.TypeTop.valueOf((String) signs.get(s)).getName();
                    int stat = TopLoad.TypeTop.valueOf((String) signs.get(s)).getStat().get(this.position.get(s)-1);
                    String[] replace = {"%player_name%", "%top_type%", "%stat%", "%position%"};
                    String[] to = {offlinePlayer.getName(), topType, "" + stat, "" + this.position};

                    List<String> changed = BukkitUtil.replaceMuchVariableInList(this.lines, replace, to);

                    for (int a = 0; a < changed.size(); a++) {
                        s.setLine(a, changed.get(a));
                    }

                    s.update();
                }
            }
        }
    }

    public static void load(){
        ConfigurationSection idList = Main.getConfigManager().getConfig("signs.yml").getConfigurationSection("DATA");

        int counterMax = -0;
        if (idList != null) {
            for (Map.Entry<String, Object> entry : idList.getValues(false).entrySet()) {
                //id count
                int id = Integer.parseInt(entry.getKey());

                if (id > counterMax) {
                    counterMax = id;
                }

                //add signs
                if (Main.getConfigManager().getConfig("signs.yml").contains("DATA." + entry.getKey() + ".Type")) {
                    if (Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Type").equalsIgnoreCase("TOP")) {
                        //top sign
                        World world = Bukkit.getWorld(Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".World"));
                        Location location = BukkitUtil.parseLocation(world, Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Location"));
                        int position = Main.getConfigManager().getConfig("signs.yml").getInt("DATA." + entry.getKey() + ".Position");
                        Sign sign = null;

                        if (!location.getBlock().getType().name().contains("SIGN")) {
                            location.getChunk().setForceLoaded(true);
                            location.getBlock().setType(Material.OAK_SIGN);
                        }

                        sign = (Sign) location.getBlock();
                        org.bukkit.block.data.type.Sign signtwo = (org.bukkit.block.data.type.Sign) sign.getBlockData();
                        String rotation = Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Facing");
                        signtwo.setRotation(BlockFace.valueOf(rotation));

                        Signs.TOP.addSign(sign, Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Top-Stat"));
                    } else {
                        //arena sign
                        World world = Bukkit.getWorld(Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".World"));
                        Location location = BukkitUtil.parseLocation(world, Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Location"));
                        Sign sign = (Sign) location.getBlock();
                        Signs.TOP.addSign(sign, Main.getConfigManager().getConfig("signs.yml").getString("DATA." + entry.getKey() + ".Arena"));
                    }
                }
            }
        }

        for (Signs signs : Signs.values()){
            signs.setLines(Main.getConfigManager().getConfig("signs.yml").getStringList("Signs." + signs.name()));
        }

        id = counterMax;
    }

    public static void sendConfig(Sign sign, Arena arena, TopLoad.TypeTop topType, int position){
        org.bukkit.block.data.type.Sign signtwo = (org.bukkit.block.data.type.Sign) sign.getBlockData();

        Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "World", sign.getWorld().getName());
        Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) +  "Location", sign.getLocation().getBlockX() + ", " + sign.getLocation().getBlockY() + ", " +sign.getLocation().getBlockZ());
        Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) +  "Facing", signtwo.getRotation().name());

        if (arena == null){
            //top sign
            if (topType != null){
                Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "Type", "TOP");
                Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "Top-Stat", topType.name());
                Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "Position", position);
            }
        }else{
            //arena sign
            Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "Arena", arena.getName());
            Main.getConfigManager().getConfig("signs.yml").set("DATA." + (id+1) + "Type", "ARENA");
        }

        Main.getConfigManager().save("signs.yml");
    }

    public Block getAttachedBlock(Sign sign) {
       return sign.getBlock().getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
    }
}
