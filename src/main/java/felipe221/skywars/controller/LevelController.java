package felipe221.skywars.controller;

import felipe221.skywars.Main;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.User;
import felipe221.skywars.object.iLevel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelController {
	private static HashMap<Integer, iLevel> levels = new HashMap<>();
	private static int maxLevel = 0;

	public static void load() {
		maxLevel = Main.getConfigManager().getConfig("config.yml").getInt("Levels.Max-Level");
		int lastLevelColor = 0;
		String lastColor = "";

		for (int i = 1; i < maxLevel; i++) {
			int upPerLevel = 125;
			double product = upPerLevel * 1.000 / 1000;
			double finalint = (((product * i) * 1000) + 1000);
			int xp = (int) finalint;

			String color = "";
			List<String> commands = new ArrayList<>();

			if (Main.getConfigManager().getConfig("config.yml").contains("Levels.Rewards." + i + ".Color")) {
				color = Main.getConfigManager().getConfig("config.yml").getString("Levels.Rewards." + i + ".Color");
				lastLevelColor = i;
				lastColor = color;
			}else{
				color = lastColor;
			}

			levels.put(i, new iLevel(i, xp, color, commands));
		}
	}

	public static void execute(Player player, int levelActual){
		if (levelActual < maxLevel) {
			User.getUser(player).setXP(0);
			User.getUser(player).setLevel(levelActual + 1);

			List<String> LEVEL_UP = MessagesLoad.MessagesList.LEVEL_UP.setPlayer(player).getMessage();

			for (String lines : LEVEL_UP) {
				player.sendMessage(lines);
			}

			for (String commands : getCommandsByLevel(levelActual + 1)){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),commands);
			}
		}
	}

	public static boolean canLevelUp(Player player, int addXp){
		if (User.getUser(player).getXP() + addXp >= getXPByLevel(User.getUser(player).getLevel())){
			return true;
		}

		return false;
	}

	public static List<String> getCommandsByLevel(int level){
		return levels.get(level).getCommands();
	}

	public static String getColorByLevel(int level){
		return levels.get(level).getColor();
	}

	public static int getXPByLevel(int level){
		if (level <= maxLevel) {
			return levels.get(level).getXP();
		}else{
			return 0;
		}
	}

	public static int getMaxLevel() {
		return maxLevel;
	}

	public static HashMap<Integer, iLevel> getLevels() {
		return levels;
	}
}
