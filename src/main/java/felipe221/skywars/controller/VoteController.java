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
                arena.setTimeGame((Time.TypeTime) vote.getWinner());

                continue;
            }

            if (vote.getTypeVote() == Vote.TypeVote.HEARTS){
                arena.setHearts((Hearts.TypeHearts) vote.getWinner());

              continue;

            }
        }
    }
}
