package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ChestController;
import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.load.StatsLoad;
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
		e.setJoinMessage(null);

		Player player = e.getPlayer();
		User user = User.getUser(player);
		StatsLoad.load(player);

		player.getInventory().clear();
		player.setHealth(20);
		player.setMaxHealth(20);
		player.setFoodLevel(20);
		player.getActivePotionEffects().clear();

		user.teleportSpawn();
		giveItems(player);
		ShowListener.showPlayers(player);
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
		if (ItemsLoad.Items.HIDE_PLAYERS.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.HIDE_PLAYERS.getSlot(), ItemsLoad.Items.HIDE_PLAYERS.getItemStack());
		}
		if (ItemsLoad.Items.LOBBYS.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.LOBBYS.getSlot(), ItemsLoad.Items.LOBBYS.getItemStack());
		}
		if (ItemsLoad.Items.COSMETICOS.isEnable()) {
			player.getInventory().setItem(ItemsLoad.Items.COSMETICOS.getSlot(), ItemsLoad.Items.COSMETICOS.getItemStack());
		}

		player.updateInventory();
	}
}
