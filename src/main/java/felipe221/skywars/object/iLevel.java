package felipe221.skywars.object;

import felipe221.skywars.controller.LevelController;

import java.util.List;

public class iLevel {
    private int level;
    private int xp;
    private String color;
    private List<String> commands;

    public iLevel(int level, int xp, String color, List<String> commands) {
        this.xp = xp;
        this.level = level;
        this.color = color;
        this.commands = commands;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public int getXP() {
        return xp;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public iLevel getNext(){
        return LevelController.getLevels().get(this.level + 1);
    }

    @Override
    public String toString() {
        return "iLevel{" +
                "level=" + level +
                ", xp=" + xp +
                ", color='" + color + '\'' +
                ", commands=" + commands +
                '}';
    }
}
