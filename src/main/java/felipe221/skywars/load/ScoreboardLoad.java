package felipe221.skywars.load;

import felipe221.skywars.FastBoard;
import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreboardLoad {
    public enum Scoreboards{
        LOBBY(Arena.Status.NONE, "", new ArrayList<>()),
        IN_GAME(Arena.Status.INGAME,"", new ArrayList<>()),
        RESTARTING(Arena.Status.RESTARTING,"", new ArrayList<>()),
        ENDING(Arena.Status.ENDING,"", new ArrayList<>()),
        WAITING(Arena.Status.WAITING,"", new ArrayList<>()),
        STARTING(Arena.Status.STARTING,"", new ArrayList<>());

        private String title;
        private List<String> lines;
        private Arena.Status status;

        Scoreboards(Arena.Status status, String title, List<String> lines) {
            this.title = title;
            this.lines = lines;
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getLines() {
            return lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public Arena.Status getStatus() {
            return status;
        }

        public void setStatus(Arena.Status status) {
            this.status = status;
        }
    }

    public static void load(){
        for (Scoreboards scores : Scoreboards.values()){
            scores.setTitle(Main.getConfigManager().getConfig("config.yml").getString("Scoreboards." + scores.name() + ".Title" ));
            List<String> lines = Main.getConfigManager().getConfig("config.yml").getStringList("Scoreboards." + scores.name() + ".Lines");
            scores.setLines(lines);
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    FastBoard board = User.getUser(player).getBoard();

                    updateBoard(board);
                }
            }
        }.runTaskTimer(Main.getInstance(), 20L, 0);
    }

    private static void updateBoard(FastBoard board) {
        Arena arena = User.getUser(board.getPlayer()).getArena();

        if (arena == null){
            board.updateTitle(BukkitUtil.replaceVariables(board.getPlayer(), null,Scoreboards.LOBBY.getTitle()));
            board.updateLines(BukkitUtil.replaceVariables(board.getPlayer(), null, Scoreboards.LOBBY.getLines()));
        }else {
            Arena.Status statusGame = arena.getStatus();

            for (Scoreboards scores : Scoreboards.values()){
                if (scores.getStatus() == statusGame){
                    board.updateLines(BukkitUtil.replaceVariables(board.getPlayer(), arena, scores.getLines()));
                    board.updateTitle(BukkitUtil.replaceVariables(board.getPlayer(), arena,scores.getTitle()));
                }
            }
        }
    }
}
