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
import felipe221.skywars.object.Mode.TypeMode;


public class User {
	private static HashMap<Player, User> cache = new HashMap<Player, User>();
	
	private int xp;

	//solo stats
	private int solo_wins;
	private int solo_kills;
	private int solo_losses;
	private int solo_games;
	private int solo_block_placed;
	private int solo_block_break;
	private int solo_arrow_hit;

	//team stats
	private int team_wins;
	private int team_kills;
	private int team_losses;
	private int team_games;
	private int team_block_placed;
	private int team_block_break;
	private int team_arrow_hit;

	private String deathMessage;
	private Effect.KillEffect killEffect;
	private Effect.WinEffect winEffect;
	private Projectiles.Trails trail;

	private Kit kit;
	private Cage cage;

	private boolean alive;
	private Arena arena;
	private Player player;
	private FastBoard board;
	private String killTematica;
	
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
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_solo` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (st.next()) {
				this.solo_wins = st.getInt("wins");
				this.solo_losses = st.getInt("losses");
				this.solo_games = st.getInt("games");
				this.solo_kills = st.getInt("kills");
				this.solo_arrow_hit = st.getInt("arrow_hit");
				this.solo_block_break = st.getInt("block_broken");
				this.solo_block_placed = st.getInt("block_placed");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		ResultSet sta = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_team` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
		try {
			if (sta.next()) {
				this.team_wins = st.getInt("wins");
				this.team_losses = st.getInt("losses");
				this.team_games = st.getInt("games");
				this.team_kills = st.getInt("kills");
				this.team_arrow_hit = st.getInt("arrow_hit");
				this.team_block_break = st.getInt("block_broken");
				this.team_block_placed = st.getInt("block_placed");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		cache.put(player, this);
	}

	public void send(){
		Main.getDatabaseManager().query("UPDATE `minecraft`.`players_stats_team` SET " +
				"`wins`='" + this.team_wins + "' " +
				"`kills`='" + this.team_kills + "' " +
				"`losses`='" + this.team_losses + "' " +
				"`games`='" + this.team_games + "' " +
				"`arrow_hit`='" + this.team_arrow_hit + "' " +
				"`block_placed`='" + this.team_block_placed + "' " +
				"`block_broken`='" + this.team_block_break + "' " +
				" WHERE uuid = '" + player.getUniqueId() + "'");

		Main.getDatabaseManager().query("UPDATE `minecraft`.`players_stats_solo` SET " +
				"`wins`='" + this.solo_wins + "', " +
				"`kills`='" + this.solo_kills + "', " +
				"`losses`='" + this.solo_losses + "', " +
				"`games`='" + this.solo_games + "', " +
				"`arrow_hit`='" + this.solo_arrow_hit + "', " +
				"`block_placed`='" + this.solo_block_placed + "', " +
				"`block_broken`='" + this.solo_block_break + "' " +
				" WHERE uuid = '" + player.getUniqueId() + "'");
	}

	public boolean exist(){
		ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_team` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
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

	public String getKillTematica() {
		return killTematica;
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

	public void addSoloWin(){
		this.solo_wins++;
	}

	public void addTeamWin(){
		this.team_wins++;
	}

	public void addSoloGame(){
		this.solo_games++;
	}

	public void addTeamGame(){
		this.team_games++;
	}

	public void addSoloLosses(){
		this.solo_losses++;
	}

	public void addTeamLosses(){
		this.team_losses++;
	}

	public int getSoloWins() {
		return solo_wins;
	}

	public void setSoloWins(int solo_wins) {
		this.solo_wins = solo_wins;
	}

	public int getSoloKills() {
		return solo_kills;
	}

	public void setSoloKills(int solo_kills) {
		this.solo_kills = solo_kills;
	}

	public int getSoloLosses() {
		return solo_losses;
	}

	public void setSoloLosses(int soloLosses) {
		this.solo_losses = soloLosses;
	}

	public int getSoloGames() {
		return solo_games;
	}

	public void setSoloGames(int solo_games) {
		this.solo_games = solo_games;
	}

	public int getSoloBlockPlaced() {
		return solo_block_placed;
	}

	public void setSoloBlockPlaced(int solo_block_placed) {
		this.solo_block_placed = solo_block_placed;
	}

	public int getSoloBlockBreak() {
		return solo_block_break;
	}

	public void setSoloBlockBreak(int solo_block_break) {
		this.solo_block_break = solo_block_break;
	}

	public int getSoloArrowHit() {
		return solo_arrow_hit;
	}

	public void setSoloArrowHit(int solo_arrow_hit) {
		this.solo_arrow_hit = solo_arrow_hit;
	}

	public int getTeamWins() {
		return team_wins;
	}

	public void setTeamWins(int team_wins) {
		this.team_wins = team_wins;
	}

	public int getTeamKills() {
		return team_kills;
	}

	public void setTeamKills(int team_kills) {
		this.team_kills = team_kills;
	}

	public int getTeamLosses() {
		return team_losses;
	}

	public void setTeamLosses(int teamLosses) {
		this.team_losses = teamLosses;
	}

	public int getTeamGames() {
		return this.team_games;
	}

	public void setTeamGames(int team_games) {
		this.team_games = team_games;
	}

	public int getTeamBlockPlaced() {
		return team_block_placed;
	}

	public void setTeamBlockPlaced(int team_block_placed) {
		this.team_block_placed = team_block_placed;
	}

	public int getTeamBlockBreak() {
		return team_block_break;
	}

	public void setTeamBlockBreak(int team_block_break) {
		this.team_block_break = team_block_break;
	}

	public int getTeamArrowHit() {
		return team_arrow_hit;
	}

	public void setTeamArrowHit(int team_arrow_hit) {
		this.team_arrow_hit = team_arrow_hit;
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
