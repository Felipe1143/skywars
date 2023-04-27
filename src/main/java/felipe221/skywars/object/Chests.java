package felipe221.skywars.object;

import felipe221.skywars.controller.ArenaController;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Chests {
	public enum TypeChest{
		BASICO("Básico"), NORMAL("Normal"), OP("OP");

		private String name;

		TypeChest(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public Chests(Arena arena) {
		this.rollback = false;
		this.arena = arena;
	}

	private Arena arena;
	private boolean rollback;

	public boolean isRollback() {
		return rollback;
	}

	public void rollback() {
		if (!this.rollback)
		this.rollback = true;

		ArenaController.fillChests(this.arena);
	}

	public String getTimeToRollback(){
		int time = arena.getTime();
		int rollback = 120;

		if (time > rollback){
			return "¡Hecho!";
		}else{
			return seconds2time(time);
		}
	}

	public String seconds2time(int seconds) {
		double hours   = Math.floor(seconds / 3600);
		double minutes = Math.floor((seconds - (hours * 3600)) / 60);
		double secondsa = seconds - (hours * 3600) - (minutes * 60);
		var time = "";

		if (hours != 0) {
			time = hours+":";
		}

		if (minutes != 0 || time != "") {
			String minutesString = (minutes < 10 && time != "") ? "0"+ String.valueOf(minutes) : String.valueOf(minutes);
			time += minutesString+":";
		}

		if (time == "") {
			time = seconds+"s";
		}else {
			time += ((seconds < 10) ? "0"+String.valueOf(secondsa) : String.valueOf(secondsa));
		}

		return time;
	}
}
