package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MenuLoad {
    public enum Menus{
        ARENA_SELECTOR("", 0, new HashMap<>());

        private String title;
        private int rows;
        private HashMap<Integer, ItemStack> items;

        Menus(String title, int rows, HashMap<Integer, ItemStack> items) {
            this.title = title;
            this.rows = rows;
            this.items = items;
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

        public HashMap<Integer, ItemStack> getItemsWithSlot() {
            return items;
        }

        public ArrayList<ItemStack> getItems(){
            return items.values().stream().collect(Collectors.toCollection(ArrayList::new));
        }

        public void setItems(HashMap<Integer, ItemStack> items) {
            this.items = items;
        }
    }

    public static void load(){
        for (Menus menu : Menus.values()){
            menu.setTitle(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Title"));
            menu.setRows(Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Rows"));

            ConfigurationSection itemsConfig = Main.getConfigManager().getConfig("menus.yml").getConfigurationSection("Menus." + menu.name() + ".Items");
            HashMap<Integer, ItemStack> itemsList = new HashMap<>();

            for (Map.Entry<String, Object> entry : itemsConfig.getValues(false).entrySet()) {
                int slot = Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Items." + entry.getKey() + ".Slot");
                Material material = Material.valueOf(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".ID"));
                String name = Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".Name");
                List<String> lore = Main.getConfigManager().getConfig("menus.yml").getStringList("Menus." + menu.name() + ".Items." + entry.getKey() + ".Lore");

                itemsList.put(slot, ItemBuilder.start(material).name(name).lore(lore).build());
            }

            menu.setItems(itemsList);
        }
    }
}
