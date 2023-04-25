package felipe221.skywars.load;

import felipe221.skywars.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import java.util.*;

public class CageLoad {
    public static HashMap<Material, String> cagesMaterial = new HashMap<Material, String>();
    public static HashMap<Material, Integer> cagesPrices = new HashMap<Material, Integer>();

    public static void load() {
        ConfigurationSection configurationSection = Main.getConfigManager().getConfig("config.yml").getConfigurationSection("Cages-Materials");

        for (Map.Entry<String, Object> entry : configurationSection.getValues(false).entrySet()) {
            Material material = Material.getMaterial(entry.getKey());
            String name = Main.getConfigManager().getConfig("config.yml").getString("Cages-Materials." + entry.getKey() + ".Name");
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Cages-Materials." + entry.getKey() + ".Price");

            if (material == null){
                System.out.println("[SkyWars - ERROR - CAGES] El material con la ID [" + entry.getKey() + "] no existe en Minecraft (config.yml)");
                continue;
            }

            cagesMaterial.put(material, name);
            cagesPrices.put(material, price);
        }
    }

    public static ArrayList<Material> getMaterials(){
        ArrayList<Material> list = new ArrayList<>();
        for (Material material : cagesMaterial.keySet()) {
            list.add(material);
        }
        return list;
    }

    public static int getPriceByMaterial(Material material){
        return cagesPrices.get(material);
    }

    public static String getNameByMaterial(Material material){
        return cagesMaterial.get(material);
    }

    public static boolean exist(Material material){
        return cagesMaterial.containsKey(material);
    }
}
