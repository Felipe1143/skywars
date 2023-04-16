package felipe221.skywars.object;

import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.Main;
import felipe221.skywars.load.WorldLoad;
import felipe221.skywars.object.Chests.TypeChest;
import felipe221.skywars.object.Mode.TypeMode;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class Arena {
	private static ArrayList<Arena> listArenas = new ArrayList<Arena>();

	public enum Status{
		NONE("Ninguno", Material.GLASS, ChatColor.WHITE),
		WAITING("Esperando", Material.GREEN_STAINED_GLASS, ChatColor.GREEN),
		STARTING("Comenzando", Material.YELLOW_STAINED_GLASS, ChatColor.YELLOW),
		INGAME("En juego", Material.RED_STAINED_GLASS, ChatColor.RED),
		ENDING("Terminando", Material.PURPLE_STAINED_GLASS, ChatColor.DARK_PURPLE),
		RESTARTING("Reiniciando", Material.BLACK_STAINED_GLASS, ChatColor.BLACK);

		private final Material material;
		private final ChatColor color;
		private final String name;

		Status(String name, Material material, ChatColor color){
			this.material = material;
			this.color = color;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public ChatColor getColor() {
			return color;
		}

		public Material getMaterial() {
			return material;
		}
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
	private Scenario.TypeScenario scenario;

	private TypeMode mode;

	//if is team game
	private ArrayList<Teams> teams;
	private int teamSize;

	private List<Player> usersInArena;
	private List<Player> spectators;
	private HashMap<Integer, Location> spawns;
	private HashMap<Location, Boolean> spawnsUse;
	private Location center;
	private List<Chests> chests;

	private int max;
	private int min;
	private int time, timeToStart;

	public Arena(int id) {
		this.spectators = new ArrayList<>();
		this.usersInArena = new ArrayList<Player>();
		this.chests = new ArrayList<>();
		this.votes = new ArrayList<>();

		//votes
		this.chest = TypeChest.NORMAL;
		this.hearts = Hearts.TypeHearts.C10;
		this.projectiles = Projectiles.TypeProjectiles.NORMAL;
		this.time_game = Time.TypeTime.DAY;
		this.scenario = Scenario.TypeScenario.valueOf(Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Scenario"));

		this.status = Status.WAITING;
		this.id = id;
		this.world_name = Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".World-Name");
		this.name = Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Name");
		this.max = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Max-Players");
		this.min = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Min-Players");
		this.timeToStart = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Time-To-Start");
		this.mode = TypeMode.valueOf(Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Mode"));

		this.spawns = new HashMap<Integer, Location>();
		this.spawnsUse = new HashMap<Location, Boolean>();

        //vote create
		this.votes.add(new Vote(Vote.TypeVote.SCENARIOS, Scenario.TypeScenario.LUCKY, Scenario.TypeScenario.SPEED, Scenario.TypeScenario.ANTIKB, Scenario.TypeScenario.SCAFFOLD, Scenario.TypeScenario.TORMENTA));
		this.votes.add(new Vote(Vote.TypeVote.CHESTS,  TypeChest.NORMAL, TypeChest.OP, TypeChest.BASICO));
        this.votes.add(new Vote(Vote.TypeVote.HEARTS, Hearts.TypeHearts.C10, Hearts.TypeHearts.C20, Hearts.TypeHearts.C30));
        this.votes.add(new Vote(Vote.TypeVote.PROJECTILES, Projectiles.TypeProjectiles.NORMAL, Projectiles.TypeProjectiles.TP, Projectiles.TypeProjectiles.EXPLOSIVE));
        this.votes.add(new Vote(Vote.TypeVote.TIME, Time.TypeTime.DAY, Time.TypeTime.NIGHT, Time.TypeTime.SUNSET));

		//team game
		if (Main.getConfigManager().getConfig("arenas.yml").contains("Arenas." + id + ".Team-Size")){
			this.teamSize = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + ".Team-Size");
		}else{
			this.teamSize = 0;
		}

		for (Object a : spawns.keySet()) {
			System.out.println(a);
		}
		//TODO team list create

		//load map

		this.world = WorldLoad.create(this.world_name);
		this.center = BukkitUtil.parseLocation(this.world, Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Center"));

		//load spawns
		int counter = 0;
		for (String locations : Main.getConfigManager().getConfig("arenas.yml").getStringList("Arenas." + id + ".Spawns")) {
			Location finalLoc = BukkitUtil.parseLocation(this.world, locations);
			this.spawns.put(counter, finalLoc);
			this.spawnsUse.put(finalLoc, false);
			counter++;
		}

		if (this.spawns.size() < (this.max / (teamSize == 0 ? 1 : teamSize))){
			for (int i=this.spawns.size(); i< (this.max / (teamSize == 0 ? 1 : teamSize));i++){
				this.spawns.put(i, new Location(this.world,i,i,i));
				this.spawnsUse.put(new Location(this.world,i,i,i), false);
			}
		}

		listArenas.add(this);
	}



	public boolean isSoloGame(){
		if (mode == TypeMode.SOLO || mode == TypeMode.TEAM| mode == TypeMode.ROOMS){
			return true;
		}else{
			return false;
		}
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

	public Location getCenter() {
		return center;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public void setChest(TypeChest chest) {
		this.chest = chest;
	}

	public Scenario.TypeScenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario.TypeScenario scenario) {
		this.scenario = scenario;
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

	public List<Player> getSpectators() {
		return spectators;
	}

	public void addSpectators(Player player) {
		this.spectators.add(player);

		player.setGameMode(GameMode.SPECTATOR);
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
		for (Location location : getSpawnsLocations()){
			boolean inUse = spawnsUse.get(location);

			if (inUse){
				continue;
			}

			//set used
			spawnsUse.put(location, true);
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

	public HashMap<Integer, Location> getSpawns() {
		return spawns;
	}

	public void addSpawn(int number, Location spawn) {
		this.spawns.replace(number, this.spawns.get(number), spawn);
	}

	public Set<Location> getSpawnsLocations() {
		Set<Location> list = new HashSet<>();

		for (Map.Entry<Integer, Location> entry : spawns.entrySet()) {
			list.add(entry.getValue());
		}

		return list;
	}

	public int getTimeToStart() {
		return timeToStart;
	}

	public void setTimeToStart(int timeToStart) {
		this.timeToStart = timeToStart;
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
