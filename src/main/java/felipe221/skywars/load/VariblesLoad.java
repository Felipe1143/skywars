package felipe221.skywars.load;

import felipe221.skywars.Main;

import java.util.ArrayList;
import java.util.List;

public class VariblesLoad {
    private static String chatFormat = "";
    private static String commandShop = "";

    public enum VariablesValue{
        XP_KILL(0),
        XP_WIN(0),
        COINS_WIN(0),
        COINS_KILL(0);

        private int value;
        private String path;

        VariablesValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum VariablesList{
        SHOP_MENU_LORE(new ArrayList<>());

        private List<String> list;
        private String path;

        VariablesList(List<String> list) {
            this.list = list;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public List<String> groupWith(List<String> toAdd){
            for (String s : this.list){
                toAdd.add(s);
            }

            return toAdd;
        }
    }

    public static void load(){
        for (VariablesValue var : VariablesValue.values()){
            var.setValue(Main.getConfigManager().getConfig("config.yml").getInt("Game-Stats." + var.name()));
        }

        VariablesList.SHOP_MENU_LORE.setList(Main.getConfigManager().getConfig("menus.yml").getStringList("Shop-Menu-Lore"));

        chatFormat = Main.getConfigManager().getConfig("config.yml").getString("Chat-Format");
        commandShop = Main.getConfigManager().getConfig("config.yml").getString("Command-Shop-Permission");
    }

    public static String getChatFormat() {
        return chatFormat;
    }
    public static String getCommandShop(){
        return commandShop;
    }
}
