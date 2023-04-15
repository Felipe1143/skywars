package felipe221.skywars.object;

public class Mode {
	public enum TypeMode{
		SOLO("Solo"),
		TEAM("Team"),
		ROOMS("Rooms");

		private String name;

		TypeMode(String name){
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
