package felipe221.skywars.controller;

import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Hearts;
import felipe221.skywars.object.Time;
import felipe221.skywars.object.Vote;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class VoteController implements Listener {
    private Arena arena;

    public VoteController(Arena arena){
        this.arena = arena;
    }

    public void load(){
        for (Vote vote : arena.getVotes()){
            if (vote.getTypeVote() == Vote.TypeVote.TIME){
                setTime(vote.getWinner());
                arena.setTimeGame((Time.TypeTime) vote.getWinner());

                continue;
            }

            if (vote.getTypeVote() == Vote.TypeVote.HEARTS){
                setHearts(vote.getWinner());
                arena.setHearts((Hearts.TypeHearts) vote.getWinner());

              continue;

            }
        }
    }

    public void setHearts(Object winner){
        if (winner.equals(Hearts.TypeHearts.C10)){
            return;
        }
        if (winner.equals(Hearts.TypeHearts.C20)) {
            for (Player players : arena.getPlayers()) {
                players.setMaxHealth(40);
                players.setHealth(40);
            }
        }
        if (winner.equals(Hearts.TypeHearts.C30)) {
            for (Player players : arena.getPlayers()) {
                players.setMaxHealth(60);
                players.setHealth(60);
            }
        }
    }

    public void setTime(Object winner){
        if (winner.equals(Time.TypeTime.DAY)){
            arena.getWorld().setTime(12000);
        }

        if (winner.equals(Time.TypeTime.SUNSET)){
            arena.getWorld().setTime(3000);
        }

        if (winner.equals(Time.TypeTime.NIGHT)){
            arena.getWorld().setTime(6000);
        }
    }
}
