package felipe221.skywars.menus;

import felipe221.skywars.Main;
import felipe221.skywars.load.MenuLoad;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArenaMenu {
	//first menu
	public static void openSelect(Player player) {
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
