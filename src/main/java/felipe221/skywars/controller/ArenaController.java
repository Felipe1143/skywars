package felipe221.skywars.controller;


import felipe221.skywars.Main;
import felipe221.skywars.events.PlayerJoinGameEvent;
import felipe221.skywars.events.PlayerLeaveGameEvent;
import felipe221.skywars.listener.JoinListener;
import felipe221.skywars.load.*;
import felipe221.skywars.object.*;
import felipe221.skywars.object.Arena.Status;
import felipe221.skywars.object.cosmetics.Cage;
import felipe221.skywars.object.cosmetics.Scenario;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.*;
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

		if (user.getArena() != null){
			player.sendMessage(MessagesLoad.MessagesLine.IN_ARENA.setPlayer(player).setArena(arena).getMessage());

			return;
		}

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
		player.setHealth(20);
		player.setMaxHealth(20);
		player.setFoodLevel(20);
		player.getActivePotionEffects().clear();
		player.setGameMode(GameMode.SURVIVAL);
		player.sendTitle(ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.JOIN_ARENA.setArena(arena).getTitle()),ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.JOIN_ARENA.setArena(arena).getSubTitle()), 10, 50, 10);

		player.sendMessage(MessagesLoad.MessagesLine.SUCCESSFULL_JOIN.setPlayer(player).setArena(arena).getMessage());
		arena.sendMessage(MessagesLoad.MessagesLine.COUNT_JOIN.setArena(arena).setPlayer(player).getMessage());

		//set cage in arena
		if (arena.isSoloGame()) {
			Location spawn_location = arena.getRandomSpawn(player);

			if (spawn_location != null) {
				Cage cage = user.getCage();
				cage.setLocation(spawn_location.getBlock().getLocation());
				cage.create();
				player.teleport(spawn_location.getBlock().getLocation().add(0.5, 0, 0.5));
			}
		}else{
			player.teleport(arena.getWaitSpawn());

			player.getInventory().setItem(ItemsLoad.Items.TEAM_SELECTOR.getSlot(), ItemsLoad.Items.TEAM_SELECTOR.getItemStack());
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

		if (!iSign.getSignsByArena(arena).isEmpty()) {
			for (iSign signs : iSign.getSignsByArena(arena)) {
				signs.update();
			}
		}

		PlayerJoinGameEvent event = new PlayerJoinGameEvent(arena,player);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	public static void leave(Player player, Arena arena, boolean quit){
		User user = User.getUser(player);

		arena.removeAlivePlayer(player);
		arena.removeSpectator(player);

		PlayerLeaveGameEvent event = new PlayerLeaveGameEvent(arena,player);
		Bukkit.getServer().getPluginManager().callEvent(event);

		user.setArena(null);
		user.setAlive(false);
		arena.removeAlivePlayer(player);


		if (arena.getStatus() == Status.WAITING || arena.getStatus() == Status.STARTING){
			for (Vote votes : arena.getVotes()){
				votes.removeVote(player);
			}

			if (arena.isSoloGame()) {
				user.getCage().remove();
			}else{
				for (Teams teams : arena.getTeams()){
					if (teams.getPlayers().contains(player)){
						teams.removePlayer(player);
					}
				}
			}
		}else{
			if (arena.getStatus() == Status.INGAME) {
				iStats.TypeStats typeStats = (arena.isSoloGame() == true ? iStats.TypeStats.SOLO : iStats.TypeStats.TEAM);
				User.getUser(player).getStats().addStatValue(typeStats, iStats.Stats.LOSSES, 1);

				if (arena.isSoloGame()) {
					Player winner = ArenaController.checkWinSolo(arena);

					if (winner != null) {
						ArenaController.endGame(arena);
					}
				} else {
					Teams team = ArenaController.checkWinTeam(arena);

					if (team != null) {
						ArenaController.endGame(arena);
					}
				}
			}
		}

		if (!quit) {
			user.teleportSpawn();
			player.getInventory().clear();
			player.setHealth(20);
			player.setMaxHealth(20);
			player.getActivePotionEffects().clear();
			player.setLevel(user.getLevel());

			JoinListener.giveItems(player);
		}

		if (!iSign.getSignsByArena(arena).isEmpty()) {

			for (iSign signs : iSign.getSignsByArena(arena)) {
				signs.update();
			}
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
		String playerWinner = null;

		if (arena.isSoloGame()) {
			for (Player playerInGame : arena.getPlayersAlive()) {
				if (User.getUser(playerInGame).isAlive()) {
					alivePlayers++;
					playerWinner = playerInGame.getName();
				}
			}

			if (alivePlayers == 1) {
				ArrayList<String> winner = new ArrayList<>();
				winner.add(playerWinner);

				arena.setWinner(winner);
				return Bukkit.getPlayer(playerWinner);
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
				ArrayList<String> winner = new ArrayList<>();

				for (Player playerWinner : teamWinner.getPlayers()) {
					winner.add(playerWinner.getName());
				}

				arena.setWinner(winner);
				return teamWinner;
			}else{
				return null;
			}
		}

		return null;
	}

	public static void endGame(Arena arena){
		arena.setStatus(Status.ENDING);

		for (String winnersName : arena.getWinner()){
			Player winners = Bukkit.getPlayer(winnersName);
			User winnerUser = User.getUser(winners);

			iStats.TypeStats typeStats = (arena.isSoloGame() == true ? iStats.TypeStats.SOLO : iStats.TypeStats.TEAM);

			winnerUser.getStats().addStatValue(typeStats, iStats.Stats.WINS, 1);
			winnerUser.addXP(VariblesLoad.VariablesValue.XP_WIN.getValue());

			winners.sendTitle(ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.WIN_GAME.setArena(arena).getTitle()),ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.WIN_GAME.setArena(arena).getSubTitle()), 10, 50, 10);
		}

		List<String> END_GAME = MessagesLoad.MessagesList.END_GAME.setArena(arena).getMessage();

		for (String lines : END_GAME) {
			arena.sendMessage(lines);
		}

		if (!iSign.getSignsByArena(arena).isEmpty()) {
			for (iSign signs : iSign.getSignsByArena(arena)) {
				signs.update();
			}
		}

		new BukkitRunnable(){
			@Override
			public void run() {
				arena.setStatus(Status.RESTARTING);
				resetArena(arena);
			}
		}.runTaskLater(Main.getInstance(), 260L);
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
		Arena newArena = new Arena(copyID);

		System.out.println("[Debug - SkyWars] La arena " + newArena.getName()+ " [" + copyID + "] fue reiniciada correctamente");
	}

	public static void startCount(Arena arena) {
		arena.setStatus(Status.STARTING);

		if (!iSign.getSignsByArena(arena).isEmpty()) {
			for (iSign signs : iSign.getSignsByArena(arena)) {
				signs.update();
			}
		}

		if (!arena.isSoloGame()) {
			for (Teams teams : arena.getTeams()) {
				Cage cage = new Cage(Material.GLASS, Cage.TypeCage.ESFERA, teams.getSpawn());
				cage.setLocation(teams.getSpawn().getBlock().getLocation());
				cage.create();

				for (Player playersTeam : teams.getPlayers()) {
					playersTeam.teleport(teams.getSpawn().getBlock().getLocation().add(0.5, 0, 0.5));
				}
			}
		}

		new BukkitRunnable(){
			int seconds = -arena.getTimeToStart();

			@Override
			public void run() {
				if (arena.getStatus() == Status.ENDING){
					cancel();
				}

				/*if (arena.getStatus() == Status.STARTING && arena.isSoloGame()) {
					if (arena.getPlayersAlive().size() < arena.getMin()) {
						arena.sendMessage(MessagesLoad.MessagesLine.PLAYER_OUT_MIN.setArena(arena).getMessage());
						arena.setStatus(Status.WAITING);

						cancel();
					}
				}*/

				if (seconds == 120){
					BukkitUtil.runSync(() -> arena.getChestController().rollback());
				}

				if (seconds == 0) {
					BukkitUtil.runSync(() -> {
						for (Vote votes : arena.getVotes()){
							votes.closeVotes();
						}

						//load votes
						BukkitUtil.runSync(() -> VoteController.load(arena));

						List<String> VOTES_CLOSE = MessagesLoad.MessagesList.START_GAME.setArena(arena).getMessage();

						for (String lines : VOTES_CLOSE) {
							arena.sendMessage(lines);
						}

						start(arena);
						fillChests(arena);
					});
				}

				if (seconds > -6 && seconds <0) {
					arena.sendMessage(MessagesLoad.MessagesLine.START_IN.setArena(arena).getMessage().replaceAll("%seconds%", "" + (-seconds)));
					arena.sendTitle(MessagesLoad.TitleLine.START_IN.setArena(arena).getTitle().replaceAll("%seconds%", "" + (-seconds)), MessagesLoad.TitleLine.START_IN.setArena(arena).getSubTitle().replaceAll("%seconds%", "" + (-seconds)));
				}

				arena.setTime(seconds);
				seconds++;
			}

		}.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
	}

	public static void start(Arena arena){
		arena.setStatus(Status.INGAME);

		if (!iSign.getSignsByArena(arena).isEmpty()) {
			for (iSign signs : iSign.getSignsByArena(arena)) {
				signs.update();
			}
		}

		arena.sendTitle(MessagesLoad.TitleLine.START_ARENA.setArena(arena).getTitle(), MessagesLoad.TitleLine.START_ARENA.setArena(arena).getSubTitle());

		for (Player players : arena.getAllPlayers()) {
			User user = User.getUser(players);
			arena.addKillsGame(players, 0);

			user.getCage().remove();
			user.setAlive(true);

			iStats.TypeStats typeStats = (arena.isSoloGame() == true ? iStats.TypeStats.SOLO : iStats.TypeStats.TEAM);

			user.getStats().addStatValue(typeStats, iStats.Stats.GAMES, 1);

			players.getInventory().clear();
			if (user.getKit() != null) {
				user.getKit().give(players);
			}
		}
	}

	public static void fillChests(Arena arena){
		for (Chunk c : arena.getWorld().getLoadedChunks()) {
			for (BlockState b : c.getTileEntities()) {
				if (b instanceof Chest) {
					if (arena.getScenario() == Scenario.TypeScenario.LUCKY){
						b.setType(Material.YELLOW_WOOL);
						b.update();
					}else {
						Chest chest = (Chest) b;
						Inventory inventory = chest.getBlockInventory();
						inventory.clear();

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
						if (items_70.size() != 0) {
							for (int i = 0; i < 7; i++) {
								boolean check = false;
								int slot;

								do {
									slot = random.nextInt(chest.getBlockInventory().getSize());
								} while (chosen[slot]);

								ItemStack addItem = items_70.get(random.nextInt(items_70.size()));

								while (whitelist.contains(addItem)) {
									addItem = items_70.get(random.nextInt(items_70.size()));
								}

								chosen[slot] = true;
								chest.getBlockInventory().setItem(slot, addItem);
								whitelist.add(addItem);
							}
						}

						//cantidad de items que habra en total del 20%
						if (items_20.size() != 0) {
							for (int i = 0; i < 2; i++) {
								boolean check = false;
								int slot;

								do {
									slot = random.nextInt(chest.getBlockInventory().getSize());
								} while (chosen[slot]);

								ItemStack addItem = items_20.get(random.nextInt(items_20.size()));

								chosen[slot] = true;
								chest.getBlockInventory().setItem(slot, addItem);
								whitelist.add(addItem);
							}
						}

						//cantidad de items que habra en total del 10%
						if (items_10.size() != 0) {
							for (int i = 0; i < 1; i++) {
								boolean check = false;
								int slot;

								do {
									slot = random.nextInt(chest.getBlockInventory().getSize());
								} while (chosen[slot]);

								ItemStack addItem = items_10.get(random.nextInt(items_10.size()));

								chosen[slot] = true;
								chest.getBlockInventory().setItem(slot, addItem);
								whitelist.add(addItem);
							}
						}
					}
				}
			}
		}

		System.out.println("[Debug - SkyWars] Los cofres de la arena " + arena.getName() + " se llenaron correctamente");
	}
}
