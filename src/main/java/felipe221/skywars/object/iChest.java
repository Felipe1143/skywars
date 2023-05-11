package felipe221.skywars.object;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.object.cosmetics.Scenario;

public class iChest {
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

	public iChest(Arena arena) {
		this.rollback = false;
		this.arena = arena;
	}

	private Arena arena;
	private boolean rollback;

	public boolean isRollback() {
		return rollback;
	}

	public void rollback() {
		if (arena.getScenario() == Scenario.TypeScenario.LUCKY){
			this.rollback = true;
		}else {
			if (!this.rollback) {
				this.rollback = true;

				ArenaController.fillChests(this.arena);
			}
		}
	}

	public String getTimeToRollback(){
		int time = arena.getTime();
		int rollback = 120;

		if (time > rollback || this.rollback == true){
			return "¡Hecho!";
		}else{
			return "" + (rollback - time);
		}
	}
}
