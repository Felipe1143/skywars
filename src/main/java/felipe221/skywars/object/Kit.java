package felipe221.skywars.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Item;

public class Kit {
	ArrayList<Kit> allKits = new ArrayList<>();

	public Kit(String nombre, List<Item> items, int price, String description, String permission) {
		this.nombre = nombre;
		this.items = items;
		this.price = price;
		this.description = description;
		this.permission = permission;
	}

	private String nombre;
	private List<Item> items;
	private int price;
	private String description;
	private String permission;
		
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
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
