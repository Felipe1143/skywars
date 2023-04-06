package felipe221.skywars.object;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class User {
	private static HashMap<Player, User> cache = new HashMap<Player, User>();
	
	@Override
	public String toString() {
		return "User [level=" + level + ", xp=" + xp + ", rankedElo=" + rankedElo + ", wins=" + wins + ", kills="
				+ kills + ", losses=" + losses + ", games=" + games + ", win_effect=" + win_effect + ", kit=" + kit
				+ ", cage=" + cage + ", ballons=" + ballons + ", alive=" + alive + ", arena=" + arena + "]";
	}
	
	private int level;
	private int xp;
	private int rankedElo;
	
	private int wins;
	private int kills;
	private int losses;
	private int games;
	
	private Effect win_effect;
	private Kit kit;
	private Cage cage;
	private Ballon ballons;
	
	private boolean alive;
	private Arena arena;
	
	public User(Player player) {
		this.arena = null;
		this.alive = false;
	}
	
	public static User getUser(Player player) {
		if (cache.containsKey(player)) {
			return cache.get(player);
		}else {
			//GET FROM SQL
			
			return cache.put(player, new User(player));
		}
	}
	
	//CREATE AND GET, SEND STATS MYSQL
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getXp() {
		return xp;
	}
	
	public void setXp(int xp) {
		this.xp = xp;
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
	
	public Effect getWin_effect() {
		return win_effect;
	}
	
	public void setWin_effect(Effect win_effect) {
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
}
