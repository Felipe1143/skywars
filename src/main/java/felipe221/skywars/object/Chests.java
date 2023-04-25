package felipe221.skywars.object;

import felipe221.skywars.controller.ArenaController;
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

	public Chests(Arena arena) {
		this.rollback = false;
		this.arena = arena;
	}

	private Arena arena;
	private boolean rollback;

	public boolean isRollback() {
		return rollback;
	}

	public void rollback() {
		if (!this.rollback)
		this.rollback = true;

		ArenaController.fillChests(this.arena);
	}
}
