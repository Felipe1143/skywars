package felipe221.skywars.object;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Chest {
	public enum TypeChest{
		BASICO, NORMAL, OP
	}

	public Chest(List<ItemStack> items, boolean rollback) {
		this.items = items;
		this.rollback = rollback;
	}

	private List<ItemStack> items;
	private boolean rollback;

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;

		//regenerate chest code
	}

	public Inventory createRandom(){
		//check first open
		//if chest break cancel event
		if (isRollback() == false){
			setRollback(true);
		}

		return null;
	}
}
