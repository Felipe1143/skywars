package felipe221.skywars.controller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Arena.Status;
import felipe221.skywars.object.User;

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
			start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void start() {
		arena.setStatus(Status.STARTING);
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new BukkitRunnable() {
			int seconds = arena.getTime();
			
			@Override
			public void run() {
				if (seconds == 0) {
					//start arena
					//drop all players
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
}
