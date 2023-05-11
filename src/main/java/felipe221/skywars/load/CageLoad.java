package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.cosmetics.Cage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import java.util.*;

public class CageLoad {
    public static HashMap<Material, String> cagesMaterial = new HashMap<>();
    public static HashMap<Material, Integer> cagesMaterialPrices = new HashMap<>();

    public static void load() {
        ConfigurationSection materials = Main.getConfigManager().getConfig("config.yml").getConfigurationSection("Cages-Materials");

        //materials and price load
        for (Map.Entry<String, Object> entry : materials.getValues(false).entrySet()) {
            Material material = Material.getMaterial(entry.getKey());
            String name = Main.getConfigManager().getConfig("config.yml").getString("Cages-Materials." + entry.getKey() + ".Name");
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Cages-Materials." + entry.getKey() + ".Price");

            if (material == null){
                System.out.println("[SkyWars - ERROR - CAGES] El material con la ID [" + entry.getKey() + "] no existe en Minecraft (config.yml)");
                continue;
            }

            cagesMaterial.put(material, name);
            cagesMaterialPrices.put(material, price);
        }

        ConfigurationSection cagesPrices = Main.getConfigManager().getConfig("config.yml").getConfigurationSection("Cages-Type-Prices");

        //type cage price load
        for (Map.Entry<String, Object> entry : cagesPrices.getValues(false).entrySet()) {
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Cages-Type-Prices." + entry.getKey());

            Cage.TypeCage.valueOf(entry.getKey()).setPrice(price);
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
        return cagesMaterialPrices.get(material);
    }

    public static String getNameByMaterial(Material material){
        return cagesMaterial.get(material);
    }

    public static boolean exist(Material material){
        return cagesMaterial.containsKey(material);
    }
}
