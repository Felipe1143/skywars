package felipe221.skywars.object;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Vote {
    public enum TypeVote{
        HEARTS, TIME, CHESTS, SCENARIOS, PROJECTILES, FINALS
    }

    private final TypeVote type_vote;
    private HashMap<Object, Integer> votes;
    private ArrayList<Player> vote_players;
    private Object winner;
    private boolean close;

    public Vote(TypeVote type_vote, Object... options) {
        this.type_vote = type_vote;
        this.votes = new HashMap<>();
        this.vote_players = new ArrayList<>();
        this.close = false;
        this.winner = null;

        //set options
        for (Object a : options){
            votes.put(options, 0);
        }
    }

    public void addPlayer(Player player){
        vote_players.add(player);
    }

    public int getVotes(Object type){
        return votes.getOrDefault(type, 0);
    }

    public void addVote(Object type){
        if (!close) {
            int votes_count = votes.get(type);

            votes.put(type, votes_count + 1);
        }
    }

    public void removeVote(Object type){
        if (votes.containsKey(type)){
            if (votes.get(type)>0){
                votes.put(type, getVotes(type) - 1);
            }
        }
    }

    public void setWinner(){
        int maxVotes = -1;
        Object actualWinner = null;

        for (Object options : votes.keySet()){
            int vote_count = votes.get(options);

            if (vote_count > maxVotes){
                maxVotes = vote_count;
                actualWinner = options;
            }
        }

        //if not votes
        if (maxVotes == 0){
            this.winner = votes.keySet().toArray()[0];
        }

        this.winner = actualWinner;
    }

    public Object getWinner(){
        return winner;
    }

    public TypeVote getTypeVote() {
        return type_vote;
    }

    public boolean isClose(){
        return close;
    }

    public void closeVotes(){
        close = true;
    }
}
