package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ChestController;
import felipe221.skywars.load.ItemsLoad;
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
		if (ItemsLoad.Items.GAME_SELECTOR.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.GAME_SELECTOR.getSlot(), ItemsLoad.Items.GAME_SELECTOR.getItemStack());
		}
		player.updateInventory();
	}
}
