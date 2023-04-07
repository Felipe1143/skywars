package felipe221.skywars.listener;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ChestController;
import felipe221.skywars.object.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import felipe221.skywars.Main;
import felipe221.skywars.object.User;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		User user = User.getUser(player);
		
		//int LEVEL = user.getLevel();
		
		player.getInventory().clear();
		player.setHealth(20);

		//TEST REFILL IN WORLD
		Arena arena = new Arena(0);
		ArenaController arenaController = new ArenaController(arena);
		arenaController.fillChests();
		//player.setLevel(LEVEL);
		
		//SET IN TABLES
		/*Main.getDatabaseManager().query("INSERT INTO `minecraft`.`players`"
		+ "(`username`,"
				+ "`xp`,"
				+ "`rankedElo`,"
				+ "`win_effect`,"
				+ "`kit`,"
				+ "`cage`,"
				+ "`ballon`)"
				+ "VALUES"
				+ "(" + player.getUniqueId() +","
				+ "0"
				+ "0"
				+ "0"
				+ "0"
				+ "0"
				+ "0);");
		 */

		ChestController.set(player);

	}

	public void giveItems(){
		//selector arena
	}
}
