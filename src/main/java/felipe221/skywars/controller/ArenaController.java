package felipe221.skywars.controller;


import felipe221.skywars.Main;
import felipe221.skywars.events.PlayerJoinGameEvent;
import felipe221.skywars.events.PlayerLeaveGameEvent;
import felipe221.skywars.listener.JoinListener;
import felipe221.skywars.load.ChestLoad;
import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.load.WorldLoad;
import felipe221.skywars.object.*;
import felipe221.skywars.object.Arena.Status;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaController{
	public static void join(Player player, Arena arena){
		User user = User.getUser(player);

		if (arena.getPlayersAlive().size() >= arena.getMax()) {
			player.sendMessage(MessagesLoad.MessagesLine.ARENA_MAX.setPlayer(player).setArena(arena).getMessage());

			return;
		}

		if (arena.getStatus() == Status.RESTARTING || arena.getStatus() == Status.NONE || arena.getStatus() == Status.INGAME || arena.getStatus() == Status.ENDING) {
			player.sendMessage(MessagesLoad.MessagesLine.ARENA_STARTED.setPlayer(player).setArena(arena).getMessage());

			return;
		}

		arena.addAlivePlayer(player);
		user.setArena(arena);
		user.setAlive(true);

		player.setLevel(0);
		player.getInventory().clear();

		player.sendMessage(MessagesLoad.MessagesLine.SUCCESSFULL_JOIN.setPlayer(player).setArena(arena).getMessage());
		arena.sendMessage(MessagesLoad.MessagesLine.COUNT_JOIN.setArena(arena).setPlayer(player).getMessage());

		//set cage in arena
		Location spawn_location = arena.getRandomSpawn(player);

		if (spawn_location != null) {
			Cage cage = user.getCage();
			cage.setLocation(spawn_location.getBlock().getLocation());
			cage.create();
			player.teleport(spawn_location.getBlock().getLocation().add(0.5,0,0.5));
		}

		if (ItemsLoad.Items.KITS.isEnable()){
			player.getInventory().setItem(ItemsLoad.Items.KITS.getSlot(), ItemsLoad.Items.KITS.getItemStack());
		}
		if (ItemsLoad.Items.EXIT_GAME.isEnable()){
			player.getInventory().setItem(ItemsLoad.Items.EXIT_GAME.getSlot(), ItemsLoad.Items.EXIT_GAME.getItemStack());
		}
		if (ItemsLoad.Items.VOTES.isEnable()){
			player.getInventory().setItem(ItemsLoad.Items.VOTES.getSlot(), ItemsLoad.Items.VOTES.getItemStack());
		}

		checkStart(arena);

		PlayerJoinGameEvent event = new PlayerJoinGameEvent(arena,player);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	public static void leave(Player player, Arena arena, boolean quit){
		User user = User.getUser(player);

		arena.removeAlivePlayer(player);
		arena.removeSpectator(player);

		PlayerLeaveGameEvent event = new PlayerLeaveGameEvent(arena,player);
		Bukkit.getServer().getPluginManager().callEvent(event);

		if (arena.getStatus() == Status.WAITING || arena.getStatus() == Status.STARTING){
			user.getCage().remove();
		}

		user.setArena(null);
		user.setAlive(false);

		if (!quit) {
			user.teleportSpawn();

			player.getInventory().clear();
			player.setLevel(user.getLevel());

			JoinListener.giveItems(player);
		}
	}

	public static void checkStart(Arena arena) {
		if (arena.getStatus() == Status.WAITING) {
			if (arena.getPlayersAlive().size() == arena.getMin()) {
				startCount(arena);
			}
		}
	}

	public static Player checkWinSolo(Arena arena){
		int alivePlayers = 0;
		Player playerWinner = null;

		if (arena.isSoloGame()) {
			for (Player playerInGame : arena.getPlayersAlive()) {
				if (User.getUser(playerInGame).isAlive()) {
					alivePlayers++;
					playerWinner = playerInGame;
				}
			}

			if (alivePlayers == 1) {
				ArrayList<Player> winner = new ArrayList<>();
				winner.add(playerWinner);

				arena.setWinner(winner);
				return playerWinner;
			}else{
				return null;
			}
		}

		return null;
	}
	public static Teams checkWinTeam(Arena arena){
		int aliveTeams = 0;
		Teams teamWinner = null;

		//solo game
		if (!arena.isSoloGame()) {
			for (Teams teamsInGame : arena.getTeams()){
				if (teamsInGame.isAlive()){
					aliveTeams++;
					teamWinner = teamsInGame;
				}
			}

			if (aliveTeams == 1){
				arena.setWinner(teamWinner.getPlayers());
				return teamWinner;
			}else{
				return null;
			}
		}

		return null;
	}

	public static void endGame(Arena arena){
		for (Player winners : arena.getWinner()){
			User winnerUser = User.getUser(winners);

			//add win
			//add xp
		}

		new BukkitRunnable(){
			@Override
			public void run() {
				resetArena(arena);
			}
		}.runTaskLater(Main.getInstance(), 220L);
	}

	public static void resetArena(Arena arena){
		final int copyID = arena.getID();

		if (!arena.getAllPlayers().isEmpty()) {
			for (Player players : arena.getAllPlayers()) {
				players.getInventory().clear();
				players.setHealth(20);
				User.getUser(players).setArena(null);
				User.getUser(players).teleportSpawn();
				JoinListener.giveItems(players);
			}
		}

		arena.remove();

		resetMap(arena);

		Arena newArena = new Arena(copyID);

		System.out.println("[Debug - SkyWars] La arena " + newArena.getName()+ " [" + copyID + "] fue reiniciada correctamente");
	}

	@SuppressWarnings("deprecation")
	public static void startCount(Arena arena) {
		arena.setStatus(Status.STARTING);

		new BukkitRunnable(){
			int seconds = -arena.getTimeToStart();

			@Override
			public void run() {
				if (arena.getPlayersAlive().size() < arena.getMin()) {
					arena.sendMessage(MessagesLoad.MessagesLine.PLAYER_OUT_MIN.setArena(arena).getMessage());
					arena.setStatus(Status.WAITING);

					cancel();
				}

				if (seconds == 0) {
					BukkitUtil.runSync(new Runnable() {
						@Override
						public void run() {
							start(arena);
							fillChests(arena);
						}
					});
				}

				if (seconds == (arena.getTime() / 2)) {
					//close all votes
					for (Vote votes : arena.getVotes()){
						votes.closeVotes();
					}

					//load votes
					VoteController voteController = new VoteController(arena);
					voteController.load();

					List<String> VOTES_CLOSE = MessagesLoad.MessagesList.VOTES_CLOSE.setArena(arena).getMessage();

					for (String lines : VOTES_CLOSE) {
						arena.sendMessage(lines);
					}

					arena.sendMessage(MessagesLoad.MessagesLine.START_IN.setArena(arena).getMessage());
				}

				arena.setTime(seconds);
				seconds++;
			}

		}.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
	}

	public static void start(Arena arena){
		arena.setStatus(Status.INGAME);
		ScenarioController.setScenario(arena);

		for (Player players : arena.getPlayersAlive()){
			User user = User.getUser(players);

			user.getCage().remove();
			user.setAlive(true);
		}
	}

	//regenerate map
	public static void resetMap(Arena arena){
		BukkitUtil.runSync(() -> {
			//to spawn fix
			WorldLoad.kickPlayers(arena.getWorld());

				WorldLoad.unload(arena.getWorld());
				WorldLoad.delete(arena.getWorld());
				WorldLoad.copyMapWorld(arena.getWorld().getName());
				WorldLoad.create(arena.getWorld().getName());
				arena.setWorld(Bukkit.getWorld(arena.getWorld().getName()));
		});
	}

	public static void fillChests(Arena arena){
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

                    ArrayList<ItemStack> whitelist = new ArrayList<>();

					final Random random = new Random();
					boolean[] chosen = new boolean[chest.getBlockInventory().getSize()];
					int counter = 0;

					//cantidad de items que habra en total del 70%
					for(int i = 0; i < 7; i++) {
						boolean check = false;
						int slot;

						do {
							slot = random.nextInt(chest.getBlockInventory().getSize());
						} while(chosen[slot]);

                        ItemStack addItem = items_70.get(random.nextInt(items_70.size()));
                        while (whitelist.contains(addItem)){
                            addItem = items_70.get(random.nextInt(items_70.size()));
                        }

						chosen[slot] = true;
						chest.getBlockInventory().setItem(slot, addItem);
                        whitelist.add(addItem);
					}

					//cantidad de items que habra en total del 20%
					for(int i = 0; i < 2; i++) {
						boolean check = false;
						int slot;

                        do {
                            slot = random.nextInt(chest.getBlockInventory().getSize());
                        } while(chosen[slot]);

                        ItemStack addItem = items_20.get(random.nextInt(items_20.size()));
                        while (whitelist.contains(addItem)){
                            addItem = items_20.get(random.nextInt(items_20.size()));
                        }

                        chosen[slot] = true;
                        chest.getBlockInventory().setItem(slot, addItem);
                        whitelist.add(addItem);
					}

					//cantidad de items que habra en total del 10%
					for(int i = 0; i < 1; i++) {
						boolean check = false;
						int slot;

                        do {
                            slot = random.nextInt(chest.getBlockInventory().getSize());
                        } while(chosen[slot]);

                        ItemStack addItem = items_10.get(random.nextInt(items_10.size()));
                        while (whitelist.contains(addItem)){
                            addItem = items_10.get(random.nextInt(items_10.size()));
                        }

                        chosen[slot] = true;
                        chest.getBlockInventory().setItem(slot, addItem);
                        whitelist.add(addItem);
					}

					chest.update();
					System.out.println("[Debug - SkyWars] Los cofres de la arena " + arena.getName() + " se llenaron correctamente");
				}
			}
		}
	}
}
