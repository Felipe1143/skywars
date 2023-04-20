package felipe221.skywars.menus.lobby;

import felipe221.skywars.load.MenuLoad;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaSelectorMenu {
	public static void open(Player player) {
		int ROWS = MenuLoad.Menus.ARENA_SELECTOR.getRows();
		String TITLE = MenuLoad.Menus.ARENA_SELECTOR.getTitle();
		Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

		for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.ARENA_SELECTOR.getItemsWithSlot().entrySet()) {
			int slot = entry.getKey();
			ItemStack item = entry.getValue();

			menu.setItem(slot, item);
		}

		player.openInventory(menu);
	}
}
