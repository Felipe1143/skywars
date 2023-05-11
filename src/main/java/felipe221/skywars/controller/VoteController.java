package felipe221.skywars.controller;

import felipe221.skywars.object.*;
import felipe221.skywars.object.cosmetics.Hearts;
import felipe221.skywars.object.cosmetics.Projectiles;
import felipe221.skywars.object.cosmetics.Scenario;
import felipe221.skywars.object.cosmetics.Time;
import org.bukkit.entity.Player;

public class VoteController {
    public static void load(Arena arena){
        for (Vote vote : arena.getVotes()){
            if (vote.getWinner() != null) {
                if (vote.getTypeVote() == Vote.TypeVote.TIME) {
                    arena.setTimeGame(Time.TypeTime.valueOf(vote.getWinner()));
                    arena.getWorld().setTime(arena.getTime());

                    continue;
                }

                if (vote.getTypeVote() == Vote.TypeVote.PROJECTILES) {
                    arena.setProjectiles(Projectiles.TypeProjectiles.valueOf(vote.getWinner()));
                    continue;
                }

                if (vote.getTypeVote() == Vote.TypeVote.CHESTS) {
                    arena.setChest(iChest.TypeChest.valueOf(vote.getWinner()));
                    continue;
                }

                //TODO FINALS

                if (vote.getTypeVote() == Vote.TypeVote.HEARTS) {
                    arena.setHearts(Hearts.TypeHearts.valueOf(vote.getWinner()));

                    for (Player players : arena.getPlayersAlive()) {
                        players.setMaxHealth(arena.getHearts().getHearts());
                        players.setHealth(arena.getHearts().getHearts());
                    }

                    continue;
                }

                if (vote.getTypeVote() == Vote.TypeVote.SCENARIOS) {
                    arena.setScenario(Scenario.TypeScenario.valueOf(vote.getWinner()));
                    ScenarioController.setScenario(arena);

                    continue;
                }
            }
        }
    }
}
