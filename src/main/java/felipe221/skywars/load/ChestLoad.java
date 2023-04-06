package felipe221.skywars.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import felipe221.skywars.Main;
import felipe221.skywars.object.Chest.TypeChest;
import org.bukkit.inventory.meta.ItemMeta;

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
		ItemStack[] itemsStack;
		for (ItemStack inItems : items.getContents()){
			if (inItems.getType() == Material.GLASS_PANE){
				items.remove(inItems);
			}

			//set in 70%
			items.all(inItems).entrySet().forEach(entry -> {
				int slot = entry.getKey();
				
				if 
			});
		}

		Main.getConfigManager().getConfig("chest.yml").set("Chest." + type.toString().toUpperCase(), items.getContents());
	}

	public static Inventory fromConfig(Player player, TypeChest type) {
		Inventory b = Bukkit.createInventory(player, 54, "Cofre " + type.toString().toUpperCase());

		//0, 26 -- 70% probabilidad
		//27, 45 -- 20% probabilidad
		//45, 53 -- 10% probabilidad
		ItemStack first = new ItemStack(Material.GLASS_PANE);
		ItemMeta first_meta = first.getItemMeta();
		first.setDurability((short) 4); //amarillo
		first_meta.setDisplayName(ChatColor.YELLOW + "70% de probabilidad");
		first.setItemMeta(first_meta);
		b.setItem(0, first);
		b.setItem(9, first);
		b.setItem(18, first);

		ItemStack second = new ItemStack(Material.GLASS_PANE);
		ItemMeta second_meta = second.getItemMeta();
		second.setDurability((short) 1); //naranja
		second_meta.setDisplayName(ChatColor.GOLD + "20% de probabilidad");
		second.setItemMeta(second_meta);
		b.setItem(27, second);
		b.setItem(36, second);

		ItemStack third = new ItemStack(Material.GLASS_PANE);
		ItemMeta third_meta = third.getItemMeta();
		third.setDurability((short) 14); //rojo
		third_meta.setDisplayName(ChatColor.RED + "10% de probabilidad");
		third.setItemMeta(third_meta);
		b.setItem(45, third);

		//set 70% items
		int counter = 1;
		for (ItemStack setenta : (ItemStack[]) Objects.requireNonNull(Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".70"))){
			if (counter < 26) {
				if ((counter) == 9){
					counter = 10;
				}

				if ((counter) == 18){
					counter = 19;
				}

				b.setItem(counter, setenta);
			}else{
				//out index
				System.out.println("[Debug] Hay demasiados items en el 70% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");
				break;
			}

			counter++;
		}

		//set 20% items
		counter = 28;
		for (ItemStack veinte : (ItemStack[]) Objects.requireNonNull(Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".20"))){
			if (counter < 44) {
				if ((counter) == 27){
					counter = 28;
				}

				if ((counter) == 36){
					counter = 37;
				}

				b.setItem(counter, veinte);
			}else{
				//out index
				System.out.println("[Debug] Hay demasiados items en el 20% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");
				break;
			}

			counter++;
		}

		counter = 46;
		for (ItemStack diez : (ItemStack[]) Objects.requireNonNull(Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".20"))){
			if (counter < 53) {
				b.setItem(counter, diez);
			}else{
				//out index
				System.out.println("[Debug] Hay demasiados items en el 10% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");
				break;
			}

			counter++;
		}

		b.setContents((ItemStack[]) Objects.requireNonNull(Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase())));

		return b;
	}
}
