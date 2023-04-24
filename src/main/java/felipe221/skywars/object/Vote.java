package felipe221.skywars.object;

import felipe221.skywars.load.MessagesLoad;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Vote {
    public enum TypeVote{
        HEARTS, TIME, CHESTS, SCENARIOS, PROJECTILES, FINALS
    }

    private final TypeVote type_vote;
    private HashMap<String, Integer> votes;
    private HashMap<Player, String> vote_players;
    private String winner;
    private boolean close;

    public Vote(TypeVote type_vote, String... options) {
        this.type_vote = type_vote;
        this.votes = new HashMap<>();
        this.vote_players = new HashMap<>();
        this.close = false;
        this.winner = null;

        //set options
        for (String a : options){
            votes.put(a, 0);
        }
    }

    public int getVotes(String type){
        return votes.getOrDefault(type, 0);
    }

    public void addVote(Player player, String type){
        if (!close) {
            if (player.hasPermission("skywars.vote." + this.type_vote.name().toLowerCase())) {
                if (vote_players.keySet().contains(player)) {
                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_VOTE.getMessage());

                    return;
                }

                int votes_count = votes.get(type);

                votes.put(type, votes_count + 1);
                vote_players.put(player, type);
                player.sendMessage(MessagesLoad.MessagesLine.SUCCESSFULL_VOTE.getMessage());
            }else {
                player.sendMessage(MessagesLoad.MessagesLine.NOT_PERMISSION.getMessage());
            }
        }else{
            player.sendMessage(MessagesLoad.MessagesLine.VOTES_IS_CLOSED.getMessage());
        }
    }

    public void removeVote(Player player){
        if (!close){
            if (vote_players.containsKey(player)) {
                String type = vote_players.get(player);
                int votes_count = votes.get(type);

                votes.put(type, votes_count - 1);
                vote_players.remove(player);
            }
        }
    }

    public void setWinner(){
        int maxVotes = -1;
        String actualWinner = null;

        for (String options : votes.keySet()){
            int vote_count = votes.get(options);

            if (vote_count > maxVotes){
                maxVotes = vote_count;
                actualWinner = options;
            }
        }

        //if not votes
        if (maxVotes == 0){
            this.winner = votes.keySet().toArray()[0].toString();
        }

        this.winner = actualWinner;
    }

    public String getWinner(){
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
        setWinner();
    }
}
