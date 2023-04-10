package felipe221.skywars;

import felipe221.skywars.command.SkyWarsCommand;
import felipe221.skywars.controller.*;
import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.listener.BreakListener;
import felipe221.skywars.listener.ClickListener;
import felipe221.skywars.listener.LeaveListener;
import felipe221.skywars.load.ChestLoad;
import felipe221.skywars.load.KitLoad;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
		Bukkit.getPluginManager().registerEvents(new LeaveListener(), this);
		Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);

		Bukkit.getPluginManager().registerEvents(new ChestController(), this);
		Bukkit.getPluginManager().registerEvents(new KitController(), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileController(), this);

		Bukkit.getPluginManager().registerEvents(new MenuGUI(), this);

		this.getCommand("sw").setExecutor(new SkyWarsCommand());

		configManager = new ConfigController(this);
		configManager.loadConfigFiles("messages.yml", "config.yml", "arenas.yml", "chest.yml", "menus.yml", "items.yml", "kits.yml");
		
		String host = configManager.getConfig("config.yml").getString("MYSQL.host");
		int port = configManager.getConfig("config.yml").getInt("MYSQL.port");
		String name = configManager.getConfig("config.yml").getString("MYSQL.name");	
		String user = configManager.getConfig("config.yml").getString("MYSQL.user");	
		String pass = configManager.getConfig("config.yml").getString("MYSQL.password");

		db = new DatabaseController(host, port, name, user, pass, this);
		try {
			db.open();

			System.out.println("[SkyWars] ¡Base de datos conectada correctamente!");
		} catch (Exception e){
			e.printStackTrace();
		}
		
		ArenaLoad.load();
		ChestLoad.load();
		KitLoad.load();

		LevelController.load();
	}
	
	public void onDisable() {
		db.close();
	}
	
	public static ConfigController getConfigManager() {
		return configManager;
	}
	
	public static DatabaseController getDatabaseManager() {
		return db;
	}
}
