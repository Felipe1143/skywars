package felipe221.skywars.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import felipe221.skywars.FastBoard;
import felipe221.skywars.Main;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class User {
	private static HashMap<Player, User> cache = new HashMap<Player, User>();
	
	private int xp;
	private int rankedElo;
	
	private int wins;
	private int kills;
	private int losses;
	private int games;

	private String deathMessage;
	private Effect.KillEffect killEffect;
	private Effect.WinEffect winEffect;
	private Projectiles.Trails trail;

	private Kit kit;
	private Cage cage;
	private Ballon ballons;
	
	private boolean alive;
	private Arena arena;
	private Player player;
	private FastBoard board;
	
	public User(Player player) {
		this.arena = null;
		this.alive = false;
		this.player = player;
		this.board = new FastBoard(player);
		//set with mysql
		this.cage = new Cage(Material.GLASS, Cage.TypeCage.COMUN, null);
	}
	
	public static User getUser(Player player) {
		if (cache.containsKey(player)) {
			return cache.get(player);
		}else {
			return new User(player);
		}
	}

	public void load(){
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (st.next()) {
				this.xp = st.getInt("xp");
				this.rankedElo = st.getInt("rankedElo");
				this.wins = st.getInt("wins");
				this.losses = st.getInt("losses");
				this.games = st.getInt("games");
				this.kills = st.getInt("kills");
				this.kit = KitLoad.getKitPerName(st.getString("kit"));
				st.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		cache.put(player, this);
	}

	public boolean exist(){
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (st.next()) {
				System.out.println("[Debug - SkyWars] El jugador " + player.getName() + " ya existe en la base de datos");
				st.close();

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

	public Projectiles.Trails getTrail() {
		return trail;
	}

	public void setTrail(Projectiles.Trails trail) {
		this.trail = trail;
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
			System.out.println("[Debug - SkyWars] No se encontró una localización de spawn seteada");
		}
	}
}
