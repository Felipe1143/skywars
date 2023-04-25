package felipe221.skywars.object;

import felipe221.skywars.controller.ArenaController;
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
	private ArrayList<Player> winner;

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

	private List<Player> alive;
	private List<Player> spectators;
	private HashMap<Integer, Location> spawns;
	private HashMap<Location, Player> spawnsUse;
	private Location center;
	private Location waitSpawn;
	private List<Chests> chests;
	private Chests chestController;

	private int max;
	private int min;
	private int time, timeToStart;

	public Arena(int id) {
		this.winner = null;
		this.spectators = new ArrayList<Player>();
		this.alive = new ArrayList<Player>();
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
		this.center = BukkitUtil.parseLocation(this.world, Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Center"));
		this.time = 0;
		this.spawns = new HashMap<Integer, Location>();
		this.spawnsUse = new HashMap<Location, Player>();

        //vote create
		this.votes.add(new Vote(Vote.TypeVote.SCENARIOS, Scenario.TypeScenario.LUCKY.name(), Scenario.TypeScenario.SPEED.name(), Scenario.TypeScenario.ANTIKB.name(), Scenario.TypeScenario.SCAFFOLD.name(), Scenario.TypeScenario.TORMENTA.name()));
		this.votes.add(new Vote(Vote.TypeVote.CHESTS,  TypeChest.NORMAL.name(), TypeChest.OP.name(), TypeChest.BASICO.name()));
        this.votes.add(new Vote(Vote.TypeVote.HEARTS, Hearts.TypeHearts.C10.name(), Hearts.TypeHearts.C20.name(), Hearts.TypeHearts.C30.name()));
        this.votes.add(new Vote(Vote.TypeVote.PROJECTILES, Projectiles.TypeProjectiles.NORMAL.name(), Projectiles.TypeProjectiles.TP.name(), Projectiles.TypeProjectiles.EXPLOSIVE.name()));
        this.votes.add(new Vote(Vote.TypeVote.TIME, Time.TypeTime.DAY.name(), Time.TypeTime.NIGHT.name(), Time.TypeTime.SUNSET.name()));

		//team game
		if (Main.getConfigManager().getConfig("arenas.yml").contains("Arenas." + id + "Teams.Team-Size")){
			this.teamSize = Main.getConfigManager().getConfig("arenas.yml").getInt("Arenas." + id + "Teams.Team-Size");
		}else{
			this.teamSize = 0;
		}

		if (Main.getConfigManager().getConfig("arenas.yml").contains("Arenas." + id + "Teams.Wait-Lobby")){
			this.waitSpawn = BukkitUtil.parseLocation(this.world, Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + "Teams.Wait-Lobby"));
		}else{
			this.waitSpawn = this.center;
		}

		//load map
		this.world = WorldLoad.create(this.world_name);
		this.center = BukkitUtil.parseLocation(this.world, Main.getConfigManager().getConfig("arenas.yml").getString("Arenas." + id + ".Center"));

		//team create
		if (this.mode == TypeMode.TEAM){
			int teamsCount = this.max / teamSize;

			for (int i=0;i<teamsCount;i++){
				Teams.Colors colorTeam = null;
				for (Teams.Colors colors : Teams.Colors.values()){
					if (i == colors.getId()) {
						colorTeam = colors;
					}
				}
				this.teams.add(new Teams(teamSize, colorTeam, i, this, this.spawns.get(i)));
			}
		}

		//load spawns
		int counter = 0;
		for (String locations : Main.getConfigManager().getConfig("arenas.yml").getStringList("Arenas." + id + ".Spawns")) {
			Location finalLoc = BukkitUtil.parseLocation(this.world, locations);
			this.spawns.put(counter, finalLoc);
			this.spawnsUse.put(finalLoc, null);
			counter++;
		}

		if (this.spawns.size() < (this.max / (teamSize == 0 ? 1 : teamSize))){
			for (int i=this.spawns.size(); i< (this.max / (teamSize == 0 ? 1 : teamSize));i++){
				this.spawns.put(i, new Location(this.world,i,i,i));
				this.spawnsUse.put(new Location(this.world,i,i,i), null);
			}
		}

		WorldBorder border = this.world.getWorldBorder();
		border.setCenter(this.center);
		ArenaController.resetMap(this);
		this.chestController = new Chests(this);

		listArenas.add(this);

		System.out.println("[" + (this.id + 1) + "] " + this.name);
	}

	public boolean isSoloGame(){
		if (mode == TypeMode.SOLO || mode == TypeMode.ROOMS){
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

	public Location getWaitSpawn() {
		return waitSpawn;
	}

	public void setWaitSpawn(Location waitSpawn) {
		this.waitSpawn = waitSpawn;
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

	public List<Player> getPlayersAlive() {
		return alive;
	}

	public Chests getChestController() {
		return chestController;
	}

	public void setChestController(Chests chestController) {
		this.chestController = chestController;
	}

	public void setPlayers(List<Player> usersInArena) {
		this.alive = usersInArena;
	}

	public void addAlivePlayer(Player player) {
		this.alive.add(player);
	}

	public void removeAlivePlayer(Player player) {
		this.alive.remove(player);
	}

	public List<Player> getSpectators() {
		return spectators;
	}

	public void addSpectator(Player player){
		this.spectators.add(player);
	}

	public void removeSpectator(Player player){
		this.spectators.remove(player);
	}

	public List<Player> getAllPlayers(){
		List<Player> newList = new ArrayList<>();

		newList.addAll(this.spectators);
		newList.addAll(this.alive);

		return newList;
	}

	public void sendMessage(String text) {
		for (Player all : getAllPlayers()) {
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

	public Location getRandomSpawn(Player player){
		for (Location location : getSpawnsLocations()){
			Player inUse = spawnsUse.get(location);

			if (inUse != null){
				continue;
			}

			spawnsUse.put(location, player);
			return location;
		}

		//no more locations
		return null;
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

	public ArrayList<Player> getWinner() {
		return winner;
	}

	public void setWinner(ArrayList<Player> winner) {
		this.winner = winner;
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

	public void remove(){
		listArenas.remove(this);
	}

	public static Arena getRandomJoineableArena(TypeMode mode){
		Arena lastJoineable = null;
		int size = -1;

		for (Arena arena : listArenas){
			if (mode == arena.getMode()) {
				if (arena.getStatus() == Status.WAITING || arena.getStatus() == Status.STARTING) {
					if (arena.getPlayersAlive().size() < arena.getMax()) {
						if (arena.getPlayersAlive().size() > size) {
							lastJoineable = arena;
							size = arena.getPlayersAlive().size();
						}
					}
				}
			}
        }

		return lastJoineable;
	}

	public static Arena getByID(int id){
		for (Arena arenas : getListArenas()){
			if (arenas.getID() == id){
				return arenas;
			}
		}

		return null;
	}

	public Vote getVoteByEnum(felipe221.skywars.object.Vote.TypeVote typeVote){
		for (Vote votes : getVotes()){
			if (votes.getTypeVote().name().equalsIgnoreCase(typeVote.name())){
				return votes;
			}
		}

		return null;
	}

	public static Arena getByName(String name){
		for (Arena arenas : getListArenas()){
			if (arenas.getName().equalsIgnoreCase(name)){
				return arenas;
			}
		}

		return null;
	}

	public Teams getTeamByID(int id){
		for (Teams teams : getTeams()){
			if (id == teams.getID()){
				return teams;
			}
		}

		return null;
	}
}
