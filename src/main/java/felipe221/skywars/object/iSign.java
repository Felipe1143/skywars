package felipe221.skywars.object;

import felipe221.skywars.Main;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class iSign {
    private static ArrayList<iSign> iSignArrayList = new ArrayList<>();
    private static List<String> lines = new ArrayList<>();

    private int id;
    private Arena arena;
    private Location location;
    private Block attachedBlock;
    private Sign sign;

    public iSign(Arena arena, Location location) {
        this.id = getMaxID() + 1;
        this.arena = arena;
        this.location = location;
        this.attachedBlock = null;
        this.sign = null;

        if (location.getBlock().getType().name().contains("SIGN")){
            Sign sign = (Sign) location.getBlock().getState();

            this.sign = sign;
            this.attachedBlock = location.getBlock().getRelative(((Directional)sign.getBlockData()).getFacing().getOppositeFace());
        }else{
            delete();

            return;
        }

        update();
        iSignArrayList.add(this);
    }

    public iSign(int id, Arena arena, Location location) {
        this.id = id;
        this.arena = arena;
        this.location = location;
        this.attachedBlock = null;
        this.sign = null;

        if (location.getBlock().getType().name().contains("SIGN")){
            Sign sign = (Sign) location.getBlock().getState();

            this.sign = sign;
            this.attachedBlock = location.getBlock().getRelative(((Directional)sign.getBlockData()).getFacing().getOppositeFace());
        }else{
            delete();

            return;
        }

        update();
        iSignArrayList.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Block getAttachedBlock() {
        return attachedBlock;
    }

    public void setAttachedBlock(Block attachedBlock) {
        this.attachedBlock = attachedBlock;
    }

    public void update(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            if (this.arena != null) {
                ArrayList<String> replaced = new ArrayList<>();

                for (String linesSign : lines) {
                    replaced.add(BukkitUtil.replaceVariables(null, this.arena, linesSign));
                }

                for (int i = 0; i < replaced.size(); i++) {
                    this.sign.setLine(i, replaced.get(i));
                }

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    this.location.getChunk().setForceLoaded(true);
                    if (attachedBlock != null) {
                        this.attachedBlock.setType(this.arena.getStatus().getMaterial());
                    }
                    this.sign.update();
                });
            }else{
               this.delete();
            }
        }, 20);
    }

    public void delete(){
        Main.getConfigManager().getConfig("signs.yml").set("DATA." + this.id, null);
        Main.getConfigManager().save("signs.yml");

        iSignArrayList.remove(this);
    }

    public void send(){
        int x = this.location.getBlockX();
        int y = this.location.getBlockY();
        int z = this.location.getBlockZ();

        Main.getConfigManager().getConfig("signs.yml").set("DATA." + this.id + ".World", this.location.getWorld().getName());
        Main.getConfigManager().getConfig("signs.yml").set("DATA." + this.id + ".Location", x + ", " + y + "," + z);
        Main.getConfigManager().getConfig("signs.yml").set("DATA." + this.id + ".Arena", arena.getID());

        Main.getConfigManager().save("signs.yml");
    }

    private int getMaxID(){
        ConfigurationSection config = Main.getConfigManager().getConfig("signs.yml").getConfigurationSection("DATA");
        int idMax = -1;

        if (config != null) {
            for (Map.Entry<String, Object> entry : config.getValues(false).entrySet()) {
                String id = entry.getKey();

                if (Integer.valueOf(id) > idMax) {
                    idMax = Integer.valueOf(id);
                }
            }
        }

        return idMax;
    }

    public static iSign getSignByID(int id){
        for (iSign sign : iSignArrayList){
            if (sign.getId() == id){
                return sign;
            }
        }

        return null;
    }

    public static ArrayList<iSign> getSignsByArena(Arena arena){
        ArrayList<iSign> list = new ArrayList<>();

        for (iSign sign : iSignArrayList){
            if (sign.getArena().getID() == arena.getID()){
                list.add(sign);
            }
        }

        return list;
    }

    public static iSign getSignBySignLocation(Location location){
        for (iSign sign : iSignArrayList){
            if (sign.getLocation().equals(location)){
                return sign;
            }
        }

        return null;
    }

    public static ArrayList<iSign> getiSignArrayList() {
        return iSignArrayList;
    }

    public static void setiSignArrayList(ArrayList<iSign> iSignArrayList) {
        iSign.iSignArrayList = iSignArrayList;
    }

    public static List<String> getLines() {
        return lines;
    }

    public static void setLines(List<String> lines) {
        iSign.lines = lines;
    }
}
