package felipe221.skywars.load;

import felipe221.skywars.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KitLoad {

    public void load(){
        ConfigurationSection kits = Main.getConfigManager().getConfig("kits.yml").getConfigurationSection("Kits");

        for (Map.Entry<String, Object> entry : kits.getValues(false).entrySet()) {
            List<ItemStack> armorItems = new ArrayList<>();
            List<ItemStack> inventoryItems = new ArrayList<>();

            String kitName = entry.getKey();

            for (Object armor : Main.getConfigManager().getConfig("kits.yml").getList("Kits." + kitName +".Armor")) {
                if (!(armor instanceof ItemStack)) {
                    continue;
                }
            }
        }
    }
}
