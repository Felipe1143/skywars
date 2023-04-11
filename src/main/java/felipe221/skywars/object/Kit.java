package felipe221.skywars.object;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {
	private String config_name;
	private String name;
	private List<ItemStack> items;
	private List<ItemStack> armor;
	private int price;
	private ItemStack item_menu;
	private ArrayList<String> lore;
	private String permission;

	public Kit(String config_name, String name, List<ItemStack> items, List<ItemStack> armor, int price, ArrayList<String> lore, String permission, ItemStack item_menu) {
		this.config_name = config_name;
		this.name = name;
		this.items = items;
		this.armor = armor;
		this.price = price;
		this.lore = lore;
		this.permission = permission;
		this.item_menu = item_menu;

		//conflict with armor
		for (ItemStack armor_items : armor){
			if (armor_items != null){
				final String typeNameString = armor_items.getType().name();
				if (typeNameString.endsWith("_HELMET")
						|| typeNameString.endsWith("_CHESTPLATE")
						|| typeNameString.endsWith("_LEGGINGS")
						|| typeNameString.endsWith("_BOOTS")) {
					continue;
				}else{
					//remove conflict item in armor
					System.out.println("[Error - SKyWars] Porfavor, elimina el item " + armor_items.getType().toString()+" en la sección de armadura del kit " + name);
					armor.remove(armor_items);
				}
			}
		}
	}

	public String getConfigName() {
		return config_name;
	}

	public void setConfigName(String config_name) {
		this.config_name = config_name;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public List<ItemStack> getArmor() {
		return armor;
	}

	public void setArmor(List<ItemStack> armor) {
		this.armor = armor;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<String> getLore() {
		return lore;
	}

	public void setLore(ArrayList<String> lore) {
		this.lore = lore;
	}

	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public ItemStack getItemMenu() {
		return item_menu;
	}

	public void setItemMenu(ItemStack item_menu) {
		this.item_menu = item_menu;
	}
}
