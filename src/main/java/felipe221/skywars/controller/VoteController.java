package felipe221.skywars.controller;

import felipe221.skywars.object.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class VoteController {
    public static void load(Arena arena){
        for (Vote vote : arena.getVotes()){
            if (vote.getTypeVote() == Vote.TypeVote.TIME){
                arena.setTimeGame(Time.TypeTime.valueOf(vote.getWinner()));
                arena.getWorld().setTime(arena.getTime());

                continue;
            }

            if (vote.getTypeVote() == Vote.TypeVote.HEARTS){
                arena.setHearts(Hearts.TypeHearts.valueOf(vote.getWinner()));

                for (Player players : arena.getPlayersAlive()){
                    players.setMaxHealth(arena.getHearts().getHearts());
                    players.setHealth(arena.getHearts().getHearts());
                }

                continue;
            }

            if (vote.getTypeVote() == Vote.TypeVote.SCENARIOS){
                arena.setScenario(Scenario.TypeScenario.valueOf(vote.getWinner()));
                ScenarioController.setScenario(arena);

                continue;
            }
        }
    }
}
