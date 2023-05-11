package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.iSign;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class SignLoad {
    public static void load(){
        ConfigurationSection config = Main.getConfigManager().getConfig("signs.yml").getConfigurationSection("DATA");

        if (config != null) {
            for (Map.Entry<String, Object> entry : config.getValues(false).entrySet()) {
                String idString = entry.getKey();
                int id = Integer.valueOf(idString);
                Arena arena = Arena.getByID(Main.getConfigManager().getConfig("signs.yml").getInt("DATA." + idString + ".Arena"));
                Location location = BukkitUtil.parseLocation(
                        Bukkit.getWorld(Main.getConfigManager().getConfig("signs.yml").getString("DATA." + idString + ".World")),
                        Main.getConfigManager().getConfig("signs.yml").getString("DATA." + idString + ".Location"));

                iSign sign = new iSign(id, arena, location);
                //check if is null
                if (iSign.getiSignArrayList().contains(sign)) {
                    sign.update();
                }
            }
        }

        iSign.setLines(Main.getConfigManager().getConfig("signs.yml").getStringList("Signs.ARENA"));
    }
}
