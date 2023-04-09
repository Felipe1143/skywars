package felipe221.skywars.object;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {
	private String name;
	private List<ItemStack> items;
	private List<ItemStack> armor;
	private int price;
	private String description;
	private String permission;

	public Kit(String name, List<ItemStack> items, List<ItemStack> armor, int price, String description, String permission) {
		this.name = name;
		this.items = items;
		this.armor = armor;
		this.price = price;
		this.description = description;
		this.permission = permission;

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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
}
