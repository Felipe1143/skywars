package felipe221.skywars.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import felipe221.skywars.Main;
import felipe221.skywars.object.Chest.TypeChest;

public class ChestLoad {
	public static HashMap<TypeChest, ArrayList<ItemStack>> items = new HashMap<TypeChest, ArrayList<ItemStack>>();
	
	public static void load() {
		ConfigurationSection chest = Main.getConfigManager().getConfig("chest.yml").getConfigurationSection("Chest");
		
		for (Map.Entry<String, Object> entry : chest.getValues(false).entrySet()) {
			TypeChest type = TypeChest.valueOf(entry.getKey());
			ArrayList<ItemStack> iList = new ArrayList<ItemStack>();
			
			for (ItemStack item : (ItemStack[]) Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase())) {
				iList.add(item);
			}
			
			items.put(type, iList);
		}
	}
	
	//put in chest all items
	public static void sendToConfig(TypeChest type, Inventory items) {
		Main.getConfigManager().getConfig("chest.yml").set("Chest." + type.toString().toUpperCase(), items.getContents());
	}
	
	public Inventory fromConfig(Player player, TypeChest type) {
		Inventory b = Bukkit.createInventory(player, 54, type.toString().toUpperCase());
		
		b.setContents((ItemStack[]) Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase()));
	
		return b;
	}
}
