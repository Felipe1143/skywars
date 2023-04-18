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
			Main.getDatabaseManager().query("INSERT INTO `minecraft`.`players_stats_solo` SET `uuid`='"+player.getUniqueId()+"';");
			Main.getDatabaseManager().query("INSERT INTO `minecraft`.`players_stats_team` SET `uuid`='"+player.getUniqueId()+"';");
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
		if (ItemsLoad.Items.MODALIDADES.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.MODALIDADES.getSlot(), ItemsLoad.Items.MODALIDADES.getItemStack());
		}
		if (ItemsLoad.Items.AUTO_JOIN.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.AUTO_JOIN.getSlot(), ItemsLoad.Items.AUTO_JOIN.getItemStack());
		}
		if (ItemsLoad.Items.SHOW_PLAYERS.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.SHOW_PLAYERS.getSlot(), ItemsLoad.Items.SHOW_PLAYERS.getItemStack());
		}
		if (ItemsLoad.Items.LOBBYS.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.LOBBYS.getSlot(), ItemsLoad.Items.LOBBYS.getItemStack());
		}

		player.updateInventory();
	}
}
