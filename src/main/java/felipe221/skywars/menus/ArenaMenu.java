package felipe221.skywars.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaMenu {

	public void openSelect(Player player) {
		Inventory menu = Bukkit.createInventory(player, 26, "Arenas: ");
		
		ItemStack solo = new ItemStack(Material.APPLE);
		ItemMeta solo_meta = solo.getItemMeta();
		solo_meta.setDisplayName("partidas solo");
		solo.setItemMeta(solo_meta);
		
		menu.setItem(12, solo);
		
		ItemStack team = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta team_meta = team.getItemMeta();
		team_meta.setDisplayName("partidas team");
		team.setItemMeta(team_meta);
		
		menu.setItem(12, team);
	}
}
