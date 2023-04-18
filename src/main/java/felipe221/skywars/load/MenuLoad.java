package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.menus.ArenaJoinMenu;
import felipe221.skywars.object.Kit;
import felipe221.skywars.object.Mode;
import felipe221.skywars.object.User;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuLoad {
    public enum Menus{
        ARENA_SELECTOR("", 0, new HashMap<>(), new HashMap<>()),
        KITS("", 0, new HashMap<>(), new HashMap<>()),
        TP_SPECTATOR("", 0, new HashMap<>(), new HashMap<>()),
        VOTE("", 0, new HashMap<>(), new HashMap<>()),
        SOLO("", 0, new HashMap<>(), new HashMap<>()),
        SCENARIOS("", 0, new HashMap<>(), new HashMap<>()),
        HEARTS("", 0, new HashMap<>(), new HashMap<>()),
        PROJECTILES("", 0, new HashMap<>(), new HashMap<>()),
        TEAM("", 0, new HashMap<>(), new HashMap<>()),
        ROOMS("", 0, new HashMap<>(), new HashMap<>()),
        COSMETICS("", 0, new HashMap<>(), new HashMap<>());

        private String title;
        private int rows;
        private HashMap<Integer, ItemStack> items;
        private HashMap<Integer, String> entrys;
        private Player player;

        Menus(String title, int rows, HashMap<Integer, ItemStack> items, HashMap<Integer, String> entrys) {
            this.title = title;
            this.rows = rows;
            this.items = items;
            this.entrys = entrys;
            this.player = null;
        }

        public String getTitle() {
            return ChatColor.translateAlternateColorCodes('&',title);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public HashMap<Integer, ItemStack> getItemsWithSlot() {
            return items;
        }

        public ArrayList<ItemStack> getItems(){
            return items.values().stream().collect(Collectors.toCollection(ArrayList::new));
        }

        public HashMap<Integer, String> getEntrys() {
            return entrys;
        }

        public void setEntrys(HashMap<Integer, String> entrys) {
            this.entrys = entrys;
        }

        public void setItems(HashMap<Integer, ItemStack> items) {
            this.items = items;
        }

        public void action(int slot){
            for (Map.Entry<Integer, String> entry : entrys.entrySet()){
                int slotEntry = entry.getKey();
                if (slotEntry == slot){
                    String value = entry.getValue();

                    this.player.closeInventory();

                    if (this == KITS){
                        Kit kit = KitLoad.getKitPerName(value);

                        User.getUser(player).setKit(kit);
                    }

                    if (this == ARENA_SELECTOR){
                        for (Mode.TypeMode modes : Mode.TypeMode.values()){
                            if (modes.name().equals(value)){
                                ArenaJoinMenu.open(this.player, modes);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void load(){
        for (Menus menu : Menus.values()){
            menu.setTitle(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Title"));
            menu.setRows(Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Rows"));

            HashMap<Integer, ItemStack> itemsList = new HashMap<>();
            HashMap<Integer, String> entrysList = new HashMap<>();

            if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items")) {
                ConfigurationSection itemsConfig = Main.getConfigManager().getConfig("menus.yml").getConfigurationSection("Menus." + menu.name() + ".Items");

                for (Map.Entry<String, Object> entry : itemsConfig.getValues(false).entrySet()) {
                    int slot = 0;
                    Material material = Material.CARROT;
                    String name = "";
                    List<String> lore = new ArrayList<>();
                    lore.add("");

                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Slot")) {
                        slot = Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Items." + entry.getKey() + ".Slot");
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".ID")) {
                        material = Material.valueOf(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".ID"));
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Name")) {
                        name = Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".Name");
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Lore")) {
                        lore = Main.getConfigManager().getConfig("menus.yml").getStringList("Menus." + menu.name() + ".Items." + entry.getKey() + ".Lore");
                    }
                    itemsList.put(slot, ItemBuilder.start(material).name(name).lore(lore).build());
                    entrysList.put(slot, entry.getKey());
                }
            }

            menu.setItems(itemsList);
        }
    }
}
