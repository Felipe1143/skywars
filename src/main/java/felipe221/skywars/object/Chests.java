package felipe221.skywars.object;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Chests {
	public enum TypeChest{
		BASICO("Básico"), NORMAL("Normal"), OP("OP");

		private String name;

		TypeChest(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public Chests(boolean rollback, Chest chest) {
		this.rollback = rollback;
		this.chests = chest;
	}

	private boolean rollback;
	private Chest chests;

	public Chest getChest() {
		return chests;
	}

	public void setChest(Chest chests) {
		this.chests = chests;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;

		//regenerate chest code
	}
}
