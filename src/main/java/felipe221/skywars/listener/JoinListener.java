package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ChestController;
import felipe221.skywars.object.Arena;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import felipe221.skywars.Main;
import felipe221.skywars.object.User;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		User user = User.getUser(player);

		if (!user.exist()) {
			Main.getDatabaseManager().query("INSERT INTO `minecraft`.`players`"
					+ "(`username`,"
					+ "`xp`,"
					+ "`rankedElo`,"
					+ "`win_effect`,"
					+ "`kit`,"
					+ "`cage`,"
					+ "`ballon`)"
					+ "VALUES"
					+ "('" + player.getUniqueId() + "',"
					+ "0,"
					+ "0,"
					+ "0,"
					+ "0,"
					+ "0,"
					+ "0);");
		}

		user.load();
		player.getInventory().clear();
		player.setHealth(20);

		user.teleportSpawn();
		giveItems(player);
	}

	public static void giveItems(Player player){
		//SELECTOR ITEM
		ItemStack selector = new ItemStack(Material.getMaterial(Main.getConfigManager().getConfig("items.yml").getString("Items.Game-Selector.ID")));
		ItemMeta selector_meta = selector.getItemMeta();
		selector_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getConfig("items.yml").getString("Items.Game-Selector.Name")));

		List<String> selector_lore = new ArrayList<>(Main.getConfigManager().getConfig("items.yml").getStringList("Items.Game-Selector.Lore"));
		selector_lore = selector_lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());

		selector_meta.setLore(selector_lore);
		selector.setItemMeta(selector_meta);
		player.getInventory().setItem(Main.getConfigManager().getConfig("items.yml").getInt("Items.Game-Selector.Slot") - 1, selector);
		player.updateInventory();
	}
}
