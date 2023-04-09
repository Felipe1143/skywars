package felipe221.skywars.object;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import felipe221.skywars.Main;
import felipe221.skywars.controller.DatabaseController;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class User {
	private static HashMap<Player, User> cache = new HashMap<Player, User>();
	
	@Override
	public String toString() {
		return "User [xp=" + xp + ", rankedElo=" + rankedElo + ", wins=" + wins + ", kills="
				+ kills + ", losses=" + losses + ", games=" + games + ", win_effect=" + win_effect + ", kit=" + kit
				+ ", cage=" + cage + ", ballons=" + ballons + ", alive=" + alive + ", arena=" + arena + "]";
	}
	
	private int xp;
	private int rankedElo;
	
	private int wins;
	private int kills;
	private int losses;
	private int games;
	
	private Effect.TypeEffect win_effect;
	private Kit kit;
	private Cage cage;
	private Ballon ballons;
	
	private boolean alive;
	private Arena arena;
	private Player player;
	
	public User(Player player) {
		this.arena = null;
		this.alive = false;
		this.player = player;
	}
	
	public static User getUser(Player player) {
		if (cache.containsKey(player)) {
			return cache.get(player);
		}else {
			return new User(player);
		}
	}

	public void load(){
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players` WHERE username = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (st.next()) {
				this.xp = st.getInt("xp");
				this.rankedElo = st.getInt("rankedElo");
				//this.win_effect = Effect.TypeEffect.valueOf(st.getString("win_effect"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		cache.put(player, this);
	}

	public boolean exist(){
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players` WHERE username = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (st.next()) {
				System.out.println("[Debug - SkyWars] El jugador " + player.getName() + " ya existe en la base de datos");

				return true;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}

		System.out.println("[Debug - SkyWars] El jugador " + player.getName() + " no existe en la base de datos");
		return false;
	}
	
	public int getLevel() {
		if (getXP() >= 0){
			return 1;
		}

		return 0;
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
	
	public int getRankedElo() {
		return rankedElo;
	}
	
	public void setRankedElo(int rankedElo) {
		this.rankedElo = rankedElo;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public int getKills() {
		return kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void setLosses(int losses) {
		this.losses = losses;
	}
	
	public int getGames() {
		return games;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	public Effect.TypeEffect getWin_effect() {
		return win_effect;
	}
	
	public void setWin_effect(Effect.TypeEffect win_effect) {
		this.win_effect = win_effect;
	}
	
	public Kit getKit() {
		return kit;
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
	
	public Ballon getBallons() {
		return ballons;
	}
	
	public void setBallons(Ballon ballons) {
		this.ballons = ballons;
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

	public void teleportSpawn(){
		String WORLD_NAME = Main.getConfigManager().getConfig("config.yml").getString("Main-Spawn.World");
		if (!WORLD_NAME.equals("-")){
			Location SPAWN = BukkitUtil.parseLocation(Bukkit.getWorld(WORLD_NAME), Main.getConfigManager().getConfig("config.yml").getString("Main-Spawn.Location"));
			player.teleport(SPAWN);
		}else{
			System.out.println("[Debug - SkyWars] No se encontró una localización de spawn seteada");
		}
	}
}
