package felipe221.skywars.object;

import java.util.HashMap;

import felipe221.skywars.FastBoard;
import felipe221.skywars.Main;
import felipe221.skywars.load.KillsLoad;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.cosmetics.Cage;
import felipe221.skywars.object.cosmetics.Effect;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class User {
	private static HashMap<Player, User> cache = new HashMap<Player, User>();

	private int xp;
	private int coins;
	private int level;

	private String deathMessage;
	private Effect.KillEffect killEffect;
	private Effect.WinEffect winEffect;
	private Effect.Trail trail;

	private Kit kit;
	private Cage cage;
	private iStats stats;

	private boolean alive;
	private Arena arena;
	private Player player;
	private FastBoard board;
	private String killTematica;

	private HashMap<Object, Object> data;
	
	public User(Player player) {
		this.arena = null;
		this.alive = false;
		this.player = player;
		this.board = new FastBoard(player);
		this.stats = new iStats(player);
		this.data = new HashMap<>();
	}
	
	public static User getUser(Player player) {
		if (!cache.containsKey(player)) {
			cache.put(player, new User(player));
		}

		return cache.get(player);
	}

	public iStats getStats() {
		return stats;
	}

	public int getLevel() {
		return this.level;
	}

	public Object getData(Object datas) {
		return data.get(datas);
	}

	public void setData(Object datas, Object value) {
		this.data.put(datas, value);
	}

	public String getKillTematica() {
		if (KillsLoad.existTematica(this.killTematica)) {
			return killTematica;
		}else{
			//default msg
			return "NONE";
		}
	}

	public String getKillTematicaName() {
		if (KillsLoad.existTematica(this.killTematica)) {
			if (killTematica.equalsIgnoreCase("NONE")){
				return "Ninguna";
			}

			return killTematica;
		}else{
			return "Ninguna";
		}
	}

	public Teams getTeam(){
		if (this.arena == null){
			return null;
		}

		if (this.arena.isSoloGame()){
			return null;
		}

		for (Teams team : arena.getTeams()){
			if (team.getPlayers().contains(this.player)){
				return team;
			}
		}

		//error?
		return null;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setKillTematica(String killTematica) {
		this.killTematica = killTematica;
	}

	public int getXP() {
		return xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}

	public void addXP(int xp){
		this.xp+=xp;
	}

	public String getDeathMessage() {
		return deathMessage;
	}

	public void setDeathMessage(String deathMessage) {
		this.deathMessage = deathMessage;
	}

	public Effect.KillEffect getKillEffect() {
		return killEffect;
	}

	public void setKillEffect(Effect.KillEffect killEffect) {
		this.killEffect = killEffect;
	}

	public Effect.WinEffect getWinEffect() {
		return winEffect;
	}

	public void setWinEffect(Effect.WinEffect winEffect) {
		this.winEffect = winEffect;
	}

	public Effect.Trail getTrail() {
		return trail;
	}

	public void setTrail(Effect.Trail trail) {
		this.trail = trail;
	}

	public Kit getKit() {
		if (KitLoad.exist(this.kit)) {
			return kit;
		}

		return null;
	}

	public void setLevel(int level){
		this.level = level;
	}
	
	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Cage getCage() {
		return cage;
	}
	
	public void setCage(Cage cage) {
		this.cage = cage;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public void remove(){
		cache.remove(player);
	}

	public FastBoard getBoard() {
		return board;
	}

	public void setBoard(FastBoard board) {
		this.board = board;
	}


	public void teleportSpawn(){
		String WORLD_NAME = Main.getConfigManager().getConfig("config.yml").getString("Main-Spawn.World");
		if (!WORLD_NAME.equals("-")){
			Location SPAWN = BukkitUtil.parseLocation(Bukkit.getWorld(WORLD_NAME), Main.getConfigManager().getConfig("config.yml").getString("Main-Spawn.Location"));
			player.teleport(SPAWN);
		}else{
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			System.out.println("[Debug - SkyWars] No se encontró una localización de spawn seteada");
		}
	}
}
