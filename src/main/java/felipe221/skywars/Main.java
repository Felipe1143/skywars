package felipe221.skywars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import felipe221.skywars.controller.ConfigController;
import felipe221.skywars.controller.DatabaseController;
import felipe221.skywars.controller.LevelController;
import felipe221.skywars.listener.JoinListener;
import felipe221.skywars.load.ArenaLoad;

public class Main extends JavaPlugin{
	private static ConfigController configManager;
	private static DatabaseController db;
	private static Main plugin;
	
	public static Main getInstance() {
		return plugin;
	}
	
	public void onEnable() {
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		
		configManager = new ConfigController(this);
		configManager.loadConfigFiles("messages.yml", "config.yml", "arenas.yml");
		
		/*String host = configManager.getConfig("config.yml").getString("MYSQL.host");		
		Integer port = configManager.getConfig("config.yml").getInt("MYSQL.port");
		String name = configManager.getConfig("config.yml").getString("MYSQL.name");	
		String user = configManager.getConfig("config.yml").getString("MYSQL.user");	
		String pass = configManager.getConfig("config.yml").getString("MYSQL.pass");	
		
		db = new DatabaseController(host, port, name, user, pass, this);
		
		db.open();*/
		
		ArenaLoad.load();
		LevelController.load();
	}
	
	public void onDisable() {
		
	}
	
	public static ConfigController getConfigManager() {
		return configManager;
	}
	
	public static DatabaseController getDatabaseManager() {
		return db;
	}
}
