package felipe221.skywars.object;

import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.Main;
import felipe221.skywars.load.WorldLoad;
import felipe221.skywars.object.Chests.TypeChest;
import felipe221.skywars.object.Mode.TypeMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

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
	private String world_name;
	private World world;

	//votes
	private List<Vote> votes;
	private TypeChest chest;
	private Time.TypeTime time_game;
	private Projectiles.TypeProjectiles projectiles;
	private Hearts.TypeHearts hearts;

	private TypeMode mode;

	//if is team game
	private ArrayList<Teams> teams;
	private int teamSize;

	private List<Player> usersInArena;
	private HashMap<Location, Boolean> spawns;
	private List<Chests> chests;

	private int max;
	private int min;
	private int time;

	public Arena(int id) {
		this.usersInArena = new ArrayList<Player>();
		this.chests = new ArrayList<>();
		this.votes = new ArrayList<>();

		//votes
		this.chest = TypeChest.NORMAL;
		this.hearts = Hearts.TypeHearts.C10;
		this.projectiles = Projectiles.TypeProjectiles.NORMAL;
		this.time_game = Time.TypeTime.DAY;

		this.status = Status.WAITING;
		this.id = id;
		this.name = Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Name");
		this.world_name = Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".World-Name");
		this.max = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Max-Players");
		this.min = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Min-Players");
		this.time = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Time-To-Start");

		this.spawns = new HashMap<Location, Boolean>();

        //vote create
        this.votes.add(new Vote(Vote.TypeVote.CHESTS,  TypeChest.NORMAL, TypeChest.OP, TypeChest.BASICO));
        this.votes.add(new Vote(Vote.TypeVote.HEARTS, Hearts.TypeHearts.C10, Hearts.TypeHearts.C20, Hearts.TypeHearts.C30));
        this.votes.add(new Vote(Vote.TypeVote.PROJECTILES, Projectiles.TypeProjectiles.NORMAL, Projectiles.TypeProjectiles.TP, Projectiles.TypeProjectiles.EXPLOSIVE));
        this.votes.add(new Vote(Vote.TypeVote.TIME, Time.TypeTime.DAY, Time.TypeTime.NIGHT, Time.TypeTime.SUNSET));

        //load spawns
        for (String locations : Main.getConfigManager().getConfig("arenas.yml").getStringList("Arenas." + id + ".Spawns")) {
			Location finalLoc = BukkitUtil.parseLocation(world, locations);
			this.addSpawn(finalLoc);
		}

		//load map
		this.world = WorldLoad.create(this.world_name);

		listArenas.add(this);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
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

	public void saveWorld(){
		//first kick player editing
		BukkitUtil.runSync(() -> {
			BukkitUtil.runAsync(() -> {
				WorldLoad.copyWorldMap(this.world);
			});
		});
	}

	public Location getRandomSpawn(){
		for (Location location : getSpawns().keySet()){
			boolean inUse = spawns.get(location);

			if (inUse){
				continue;
			}

			//set used
			spawns.put(location, true);
			return location;
		}

		//no more locations
		return null;
	}

	public Inventory getTeamsInventory(){
		if (getTeams().isEmpty()){
			return null;
		}

		Inventory inventory = Bukkit.createInventory(null, 9*3, "Equipos:");

		int slot = 0;
		for (Teams teams : getTeams()){
			inventory.setItem(slot, teams.getItemStack());
			slot++;
		}
		return inventory;
	}

	public List<Chests> getChests() {
		return chests;
	}

	public void setChests(List<Chests> chests) {
		this.chests = chests;
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

	public ArrayList<Teams> getTeams() {
		return teams;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<Vote> votes) {
		this.votes = votes;
	}

	public void setTeams(ArrayList<Teams> teams) {
		this.teams = teams;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public Time.TypeTime getTimeGame() {
		return time_game;
	}

	public void setTimeGame(Time.TypeTime time_game) {
		this.time_game = time_game;
	}

	public Projectiles.TypeProjectiles getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(Projectiles.TypeProjectiles projectiles) {
		this.projectiles = projectiles;
	}

	public Hearts.TypeHearts getHearts() {
		return hearts;
	}

	public void setHearts(Hearts.TypeHearts hearts) {
		this.hearts = hearts;
	}
}
