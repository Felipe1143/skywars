package felipe221.skywars.load;

import java.util.*;

import felipe221.skywars.object.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import felipe221.skywars.Main;
import felipe221.skywars.object.Chests.TypeChest;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestLoad {
	//TYPE CHEST, PROBABILIDAD, ITEM
	public static HashMap<TypeChest, HashMap<Integer, ArrayList<ItemStack>>> items = new HashMap<TypeChest, HashMap<Integer, ArrayList<ItemStack>>>();

	public static void load() {
		ConfigurationSection chest = Main.getConfigManager().getConfig("chest.yml").getConfigurationSection("Chest");
		items.clear();

		//POR CADA TIPO DE COFRE
		for (Map.Entry<String, Object> entry : chest.getValues(false).entrySet()) {
			//TYPE OF CHEST
			TypeChest type = TypeChest.valueOf(entry.getKey());
			HashMap<Integer, ArrayList<ItemStack>> iList = new HashMap<>();


			//SI HAY ITEMS EN 70%
			if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".70").equals("-")){
				//CREA LISTAS
				ArrayList<ItemStack> itemStacks = new ArrayList<>();

				//SUMA ITEMS AL ARRAYLIST
				for (Object setenta : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".70")) {
					if (!(setenta instanceof ItemStack)) {
						continue;
					}

					itemStacks.add((ItemStack) setenta);
				}
				
				//PUT DENTRO DEL HASHMAP GLOBAL DEL 70%
				iList.put(70, itemStacks);

				items.put(type, iList);
			}

			if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".20").equals("-")){
				//CREA LISTAS
				ArrayList<ItemStack> itemStacks = new ArrayList<>();

				//SUMA ITEMS AL ARRAYLIST
				for (Object veinte : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".20")) {
					if (!(veinte instanceof ItemStack)) {
						continue;
					}

					itemStacks.add((ItemStack) veinte);
				}

				//PUT DENTRO DEL HASHMAP GLOBAL DEL 20%
				iList.put(20, itemStacks);

				items.put(type, iList);
			}

			if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".10").equals("-")){
				//CREA LISTAS
				ArrayList<ItemStack> itemStacks = new ArrayList<>();

				//SUMA ITEMS AL ARRAYLIST
				for (Object diez : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".10")) {
					if (!(diez instanceof ItemStack)) {
						continue;
					}

					itemStacks.add((ItemStack) diez);
				}

				//PUT DENTRO DEL HASHMAP GLOBAL DEL 10%
				iList.put(10, itemStacks);

				items.put(type, iList);
			}
		}
	}

	//put in chest all items
	public static void sendToConfig(TypeChest type, Inventory items) {
		ArrayList<ItemStack> itemList_70 = new ArrayList<>();
		ArrayList<ItemStack> itemList_20 = new ArrayList<>();
		ArrayList<ItemStack> itemList_10 = new ArrayList<>();

		Map<Integer, ItemStack> Items = new HashMap<Integer, ItemStack>();

		for(int x = 0;x < items.getSize(); x++ ){
			Items.put(x, items.getItem(x));
		}

		for (Map.Entry<Integer, ItemStack> entry : Items.entrySet()) {
			if (entry.getValue() == null){
				continue;
			}

			if (entry.getValue() .getType() == Material.AIR){
				continue;
			}

			if (entry.getValue() .getType().toString().toUpperCase().contains("GLASS_PANE")){
				continue;
			}

			int slot = entry.getKey();

			//set in 70%
			if (slot >= 0 && slot <= 27) {
				itemList_70.add(entry.getValue());
			}

			//set in 20%
			if (slot > 27 && slot <= 45){
				itemList_20.add(entry.getValue());
			}

			//set in 10%
			if (slot > 45 && slot <= 54){
				itemList_10.add(entry.getValue());
			}
		}

		try {
			Main.getConfigManager().getConfig("chest.yml").set("Chest." + type.toString().toUpperCase() + ".70", itemList_70);
			Main.getConfigManager().getConfig("chest.yml").set("Chest." + type.toString().toUpperCase() + ".20", itemList_20);
			Main.getConfigManager().getConfig("chest.yml").set("Chest." + type.toString().toUpperCase() + ".10", itemList_10);
		} catch (NullPointerException e){
			System.out.println("[Debug - SkyWars - Error] Hubo un problema al cargar los cofres... (" + type.toString().toUpperCase()+")");
			e.printStackTrace();
		}

		Main.getConfigManager().save("chest.yml");

		System.out.println("[Debug - SkyWars] La configuración de los cofres fue actualizada correctamente");
	}

	public static Inventory fromConfig(Player player, TypeChest type) {
		Inventory b = Bukkit.createInventory(player, 54, "Cofre " + type.toString().toUpperCase());

		//0, 26 -- 70% probabilidad
		//27, 45 -- 20% probabilidad
		//45, 53 -- 10% probabilidad
		ItemStack first = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		ItemMeta first_meta = first.getItemMeta();
		first.setDurability((short) 4); //amarillo
		first_meta.setDisplayName(ChatColor.YELLOW + "70% de probabilidad");
		first.setItemMeta(first_meta);
		b.setItem(0, first);
		b.setItem(9, first);
		b.setItem(18, first);

		ItemStack second = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		ItemMeta second_meta = second.getItemMeta();
		second.setDurability((short) 1); //naranja
		second_meta.setDisplayName(ChatColor.GOLD + "20% de probabilidad");
		second.setItemMeta(second_meta);
		b.setItem(27, second);
		b.setItem(36, second);

		ItemStack third = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta third_meta = third.getItemMeta();
		third_meta.setDisplayName(ChatColor.RED + "10% de probabilidad");
		third.setItemMeta(third_meta);
		b.setItem(45, third);

		//set 70% items
		int counter = 1;
		if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".70").equals("-")){
			for (Object setenta : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".70")){
				if (!(setenta instanceof ItemStack)){
					continue;
				}

				if (counter < 27) {
					if ((counter) == 9){
						counter = 10;
					}

					if ((counter) == 18){
						counter = 19;
					}

					b.setItem(counter, (ItemStack) setenta);
				}else{
					//out index
					System.out.println("[Debug - SkyWars] Hay demasiados items en el 70% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");

				}

				counter++;
			}
		}

		//set 20% items
		counter = 28;
		if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".20").equals("-")){
			for (Object veinte : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".20")){
				if (!(veinte instanceof ItemStack)){
					continue;
				}

				if (counter < 45) {
					if ((counter) == 36) {
						counter = 37;
					}

					b.setItem(counter, (ItemStack) veinte);
				} else {
					//out index
					System.out.println("[Debug - SkyWars] Hay demasiados items en el 20% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");
				}

				counter++;
			}
		}

		counter = 46;

		if (!Main.getConfigManager().getConfig("chest.yml").get("Chest." + type.toString().toUpperCase() + ".10").equals("-")) {
			for (Object diez : Main.getConfigManager().getConfig("chest.yml").getList("Chest." + type.toString().toUpperCase() + ".10")){
				if (!(diez instanceof ItemStack)){
					continue;
				}

				if (counter < 54) {
					b.setItem(counter, (ItemStack) diez);
				} else {
					//out index
					System.out.println("[Debug - SkyWars] Hay demasiados items en el 10% del cofre " + type.toString() + ", porfavor borra algunos en la configuración");
				}

				counter++;
			}
		}

		return b;
	}

	public static List<ItemStack> getRandomItems(TypeChest type, int porcent){
		return items.get(type).get(porcent);
	}
}
