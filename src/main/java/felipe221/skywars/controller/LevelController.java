package felipe221.skywars.controller;

import felipe221.skywars.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class LevelController {
	private static HashMap<Integer, Integer> levels = new HashMap<Integer, Integer>();
	private static HashMap<Integer, String> colors = new HashMap<Integer, String>();
	private static int maxLevel = 0;

	public static void load() {
		maxLevel = Main.getConfigManager().getConfig("config.yml").getInt("Levels.Max-Level");

		for (int i = 1; i < maxLevel; i++) {
			int upPerLevel = 125;
			double product = upPerLevel * 1.000 / 1000;
			double finalint = (((product * i) * 1000) + 1000);
			int finalmax = (int) finalint;
			
			levels.put(i, finalmax);
		}

		ConfigurationSection idList = Main.getConfigManager().getConfig("config.yml").getConfigurationSection("Levels.Colors");


		for (Map.Entry<String, Object> entry : idList.getValues(false).entrySet()) {
			int level = Integer.parseInt(entry.getKey());
			String color = (String) entry.getValue();

			colors.put(level, color);
		}
	}

	public static String getColorByLevel(int level){
		int actualLevelColor = -1;

		for (int levelsList : colors.keySet()){
			if (level >= levelsList){
				actualLevelColor=levelsList;
			}else{
				break;
			}
		}

		return getColor(actualLevelColor);
	}

	public static String getColor(int level){
		return colors.get(level);
	}

	public static int getMaxLevel() {
		return maxLevel;
	}

	public static HashMap<Integer, Integer> getLevels() {
		return levels;
	}

	public static void setLevels(HashMap<Integer, Integer> levels) {
		LevelController.levels = levels;
	}

	public static void setMaxLevel(int maxLevel) {
		LevelController.maxLevel = maxLevel;
	}
}
