package felipe221.skywars.load;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;

public class ArenaLoad {
	//from config load worlds and arenas
	public static void load() {
		//get id 
		ConfigurationSection config = Main.getConfigManager().getConfig("arenas.yml").getConfigurationSection("Arenas");
		
		for (Map.Entry<String, Object> entry : config.getValues(false).entrySet()) {
			 String id = entry.getKey();
			 
			 Arena arena = new Arena(Integer.parseInt(id));
			 
			 System.out.println(arena.toString());
		}
	}
}
