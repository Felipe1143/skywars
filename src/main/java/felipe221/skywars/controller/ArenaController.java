package felipe221.skywars.controller;

import felipe221.skywars.load.ChestLoad;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Arena.Status;
import felipe221.skywars.object.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaController implements Listener{
	private Arena arena;
	private Player player;
	private User user;

	public ArenaController(Arena arena){
		this.arena = arena;
	}

	public ArenaController(Arena arena, Player player){
		this.arena = arena;
		this.player = player;
		this.user = User.getUser(player);
	}

	public void join(){
		if (!player.hasPermission("skywars.size")) {
			if (arena.getPlayers().size() >= arena.getMax()) {
				String ARENA_MAX = Main.getConfigManager().getConfig("messages.yml").getString("ARENA_MAX");

				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ARENA_MAX));

				return;
			}
		}

		arena.addPlayer(player);
		user.setArena(arena);

		player.setLevel(0);

		String SUCCESSFULL_JOIN = Main.getConfigManager().getConfig("messages.yml").getString("SUCCESSFULL_JOIN");
		player.sendMessage(SUCCESSFULL_JOIN);

		String COUNT_JOIN = Main.getConfigManager().getConfig("messages.yml").getString("COUNT_JOIN");
		arena.sendMessage(COUNT_JOIN);

		checkStart();
	}

	public void checkStart() {
		if (arena.getStatus() != Status.WAITING) {
			return;
		}

		if (arena.getPlayers().size() == arena.getMin()) {
			startCount();
		}
	}

	@SuppressWarnings("deprecation")
	public void startCount() {
		arena.setStatus(Status.STARTING);

		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new BukkitRunnable() {
			int seconds = arena.getTime();

			@Override
			public void run() {
				if (seconds == 0) {
					//start arena
					//drop all players
					fillChests();
					cancel();
				}

				if (seconds == arena.getTime() / 2) {
					//close votes

					String START_IN = Main.getConfigManager().getConfig("messages.yml").getString("START_IN");
					arena.sendMessage(START_IN);
				}

				seconds--;
			}

		}, 0L, 20);
	}

	public void fillChests(){
		for (Chunk c : arena.getWorld().getLoadedChunks()) {
			for (BlockState b : c.getTileEntities()) {
				if (b instanceof Chest) {
					Chest chest = (Chest) b;
					Inventory inventory = chest.getBlockInventory();

					if (!inventory.isEmpty()) {
						inventory.clear();
					}

					//70% items
					List<ItemStack> items_70 = ChestLoad.getRandomItems(arena.getChest(), 70);
					//20% items
					List<ItemStack> items_20 = ChestLoad.getRandomItems(arena.getChest(), 20);
					//10% items
					List<ItemStack> items_10 = ChestLoad.getRandomItems(arena.getChest(), 10);

					final Random random = new Random();
					boolean[] chosen = new boolean[chest.getBlockInventory().getSize()];
					int counter = 0;

					//cantidad de items que habra en total del 70%
					for(int i = 0; i < 10; i++) {
						boolean check = false;
						int slot;

						do {
							slot = random.nextInt(chest.getBlockInventory().getSize());
						} while(chosen[slot]);

						int randomItem = random.nextInt(items_70.size());

						chosen[slot] = true;
						chest.getBlockInventory().setItem(slot, items_70.get(randomItem));

						//cancel repeat item
						items_70.remove(items_70.get(randomItem));
					}

					//cantidad de items que habra en total del 20%
					for(int i = 0; i < 2; i++) {
						boolean check = false;
						int slot;

						do {
							slot = random.nextInt(chest.getBlockInventory().getSize());
						} while(chosen[slot]);

						int randomItem = random.nextInt(items_20.size());

						chosen[slot] = true;
						chest.getBlockInventory().setItem(slot, items_20.get(randomItem));

						//cancel repeat item
						items_20.remove(items_20.get(randomItem));
					}

					//cantidad de items que habra en total del 10%
					for(int i = 0; i < 1; i++) {
						boolean check = false;
						int slot;

						do {
							slot = random.nextInt(chest.getBlockInventory().getSize());
						} while(chosen[slot]);

						int randomItem = random.nextInt(items_10.size());

						chosen[slot] = true;
						chest.getBlockInventory().setItem(slot, items_10.get(randomItem));

						//cancel repeat item
						items_10.remove(items_10.get(randomItem));
					}

					chest.update();
					System.out.println("[Debug - SkyWars] Los cofres de la arena " + arena.getName() + " se llenaron correctamente");
				}
			}
		}
	}
}
