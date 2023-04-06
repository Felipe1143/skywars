package felipe221.skywars;

import org.bukkit.Location;
import org.bukkit.World;

public class Util {
	public static Location parseLocation(World w, String in) {
		String[] params = in.split(",");
		for (String s : params) {
			s.replace("-0", "0");
		}
		if (params.length == 3 || params.length == 5) {
			double x = Double.parseDouble(params[0]);
			double y = Double.parseDouble(params[1]);
			double z = Double.parseDouble(params[2]);
			Location loc = new Location(w, x, y, z);
			if (params.length == 5) {
				loc.setYaw(Float.parseFloat(params[4]));
				loc.setPitch(Float.parseFloat(params[5]));
			}
			return loc;
		}
		return null;
	}
}
