package felipe221.skywars.menus;

import felipe221.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArenaMenu {
	//first menu
	public static void openSelect(Player player) {
		int ROWS = Main.getConfigManager().getConfig("menus.yml").getInt("Menus.ARENA_SELECTOR.Rows");
		String TITLE = Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.Title");
		Inventory menu = Bukkit.createInventory(player, 9 * ROWS, ChatColor.translateAlternateColorCodes('&', TITLE));
		
		//SOLO ITEM
		ItemStack solo = new ItemStack(Material.getMaterial(Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.SOLO.ID")));
		ItemMeta solo_meta = solo.getItemMeta();
		solo_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.SOLO.Name")));

		List<String> solo_lore = new ArrayList<>(Main.getConfigManager().getConfig("menus.yml").getStringList("Menus.ARENA_SELECTOR.SOLO.Lore"));

		solo_lore = solo_lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());

		solo_meta.setLore(solo_lore);
		solo.setItemMeta(solo_meta);
		menu.setItem(Main.getConfigManager().getConfig("menus.yml").getInt("Menus.ARENA_SELECTOR.SOLO.Slot"), solo);

		//TEAM ITEM
		ItemStack team = new ItemStack(Material.getMaterial(Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.TEAM.ID")));
		ItemMeta team_meta = team.getItemMeta();
		team_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.TEAM.Name")));

		List<String> team_lore = new ArrayList<>(Main.getConfigManager().getConfig("menus.yml").getStringList("Menus.ARENA_SELECTOR.TEAM.Lore"));
		team_lore = team_lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
	
		team_meta.setLore(team_lore);
		team.setItemMeta(team_meta);
		menu.setItem(Main.getConfigManager().getConfig("menus.yml").getInt("Menus.ARENA_SELECTOR.TEAM.Slot"), team);

		//ROOMS ITEM
		ItemStack rooms = new ItemStack(Material.getMaterial(Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.ROOMS.ID")));
		ItemMeta rooms_meta = rooms.getItemMeta();
		rooms_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getConfig("menus.yml").getString("Menus.ARENA_SELECTOR.ROOMS.Name")));

		List<String> rooms_lore = new ArrayList<>(Main.getConfigManager().getConfig("menus.yml").getStringList("Menus.ARENA_SELECTOR.ROOMS.Lore"));
		rooms_lore = rooms_lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());

		rooms_meta.setLore(rooms_lore);
		rooms.setItemMeta(rooms_meta);
		menu.setItem(Main.getConfigManager().getConfig("menus.yml").getInt("Menus.ARENA_SELECTOR.ROOMS.Slot"), rooms);

		player.openInventory(menu);
	}
}
