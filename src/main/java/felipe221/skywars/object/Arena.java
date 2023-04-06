package felipe221.skywars.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import felipe221.skywars.Main;
import felipe221.skywars.Util;
import felipe221.skywars.object.Chest.TypeChest;
import felipe221.skywars.object.Mode.TypeMode;

public class Arena {
	private static ArrayList<Arena> listArenas = new ArrayList<Arena>();
	
	public enum Status{
		WAITING,
		STARTING,
		INGAME,
		ENDING
	}
	
	private Status status;
	private int id;
	private String name;
	private TypeChest chest;
	private TypeMode mode;
	private World world;
	
	@Override
	public String toString() {
		return "Arena [status=" + status + ", id=" + id + ", nombre=" + name + ", chest=" + chest + ", mode=" + mode
				+ ", world=" + world + ", usersInArena=" + usersInArena + ", spawns=" + spawns + ", max=" + max
				+ ", min=" + min + ", time=" + time + "]";
	}

	private List<Player> usersInArena;
	private HashMap<Location, Boolean> spawns;
	
	private int max;
	private int min;
	private int time;
	
	public Arena(int id) {
		this.usersInArena = new ArrayList<Player>();
		this.chest = TypeChest.NORMAL;
		this.status = Status.WAITING;
		
		this.id = id;
		this.name = Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Name");
		this.world = Bukkit.getWorld(Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".World-Name"));
		this.max = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Max-Players");
		this.min = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Min-Players");
		this.time = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Time-To-Start");
		
		this.spawns = new HashMap<Location, Boolean>();
		
		for (String locations : Main.getConfigManager().getConfig("arenas.yml").getStringList("Arenas." + id + ".Spawns")) {
			 Location finalLoc = Util.parseLocation(world, locations);
			 this.addSpawn(finalLoc);
		}
		
		listArenas.add(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nombre) {
		this.name = nombre;
	}

	public TypeChest getChest() {
		return chest;
	}

	public void setChest(TypeChest chest) {
		this.chest = chest;
	}

	public TypeMode getMode() {
		return mode;
	}

	public void setMode(TypeMode mode) {
		this.mode = mode;
	}

	public List<Player> getPlayers() {
		return usersInArena;
	}

	public void setPlayers(List<Player> usersInArena) {
		this.usersInArena = usersInArena;
	}
	
	public void addPlayer(Player player) {
		this.usersInArena.add(player);
	}
	
	public void removePlayer(Player player) {
		this.usersInArena.add(player);
	}
	
	public void sendMessage(String text) {
		for (Player all : usersInArena) {
			all.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
		}
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public HashMap<Location, Boolean> getSpawns() {
		return spawns;
	}
	
	public void addSpawn(Location spawn) {
		this.spawns.put(spawn, false);
	}
	
	public Set<Location> getSpawnsLocations() {
		return spawns.keySet();
	}

	public void setSpawns(HashMap<Location, Boolean> spawns) {
		this.spawns = spawns;
	}

	public static ArrayList<Arena> getListArenas() {
		return listArenas;
	}

	public static void setListArenas(ArrayList<Arena> listArenas) {
		Arena.listArenas = listArenas;
	}
}
