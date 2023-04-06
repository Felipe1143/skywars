package felipe221.skywars.controller;

import java.util.HashMap;

public class LevelController {
	public static HashMap<Integer, Integer> levels = new HashMap<Integer, Integer>();
	
	public static void load() {
		for (int i = 1; i < 100; i++) {
			int upPerLevel = 125;
			double product = upPerLevel * 1.000 / 1000;
			double finalint = (((product * i) * 1000) + 1000);
			int finalmax = (int) finalint;
			
			levels.put(i, finalmax);
			
			System.out.println("LEVEL: " + i + " || XP: " + finalmax);
		}
	}
	
	//POR PARTIDA
	//1 KILLS: 30XP
	//1 ASSIST: 15XP
	//1 WIN: 200XP
}
