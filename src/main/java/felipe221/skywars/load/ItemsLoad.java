package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsLoad {
    public enum Items {
        AUTO_JOIN(false, "", 0, Material.AIR, "", new ArrayList<>()),
        COSMETICOS(false, "", 0, Material.AIR, "", new ArrayList<>()),
        EXIT_GAME(false, "", 0, Material.AIR, "", new ArrayList<>()),
        GAME_SELECTOR(false, "", 0, Material.AIR, "", new ArrayList<>()),
        KITS(false, "", 0, Material.AIR, "", new ArrayList<>()),
        LOBBYS(false, "", 0, Material.AIR, "", new ArrayList<>()),
        MODALIDADES(false, "", 0, Material.AIR, "", new ArrayList<>()),
        PLAY_AGAIN(false, "", 0, Material.AIR, "", new ArrayList<>()),
        SHOW_PLAYERS(false, "", 0, Material.AIR, "", new ArrayList<>()),
        HIDE_PLAYERS(false, "", 0, Material.AIR, "", new ArrayList<>()),
        SPECTATOR_TP(false, "", 0, Material.AIR, "", new ArrayList<>()),
        VOTES(false, "", 0, Material.AIR, "", new ArrayList<>());

        private boolean isEnable;
        private String command, name;
        private Material material;
        private int slot;
        private List<String> lore;

        Items(boolean isEnable, String name, int slot, Material material, String command, List<String> lore) {
            this.command = command;
            this.isEnable = isEnable;
            this.name = name;
            this.slot = slot;
            this.material = material;
            this.lore = lore;
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getName() {
            return ChatColor.translateAlternateColorCodes('&', name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public List<String> getLore() {
            this.lore = lore.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());

            return lore;
        }

        public ItemStack getItemStack(){
            return ItemBuilder.start(this.material).name(this.name).lore(this.lore).build();
        }

        public void setLore(List<String> lore) {
            this.lore = lore;
        }
    }

    public static void load(){
        for (Items item : Items.values()){
            item.setLore(Main.getConfigManager().getConfig("items.yml").getStringList("Items." + item.name() + ".Name"));
            item.setMaterial(Material.valueOf(Main.getConfigManager().getConfig("items.yml").getString("Items." + item.name() + ".ID")));
            item.setName(Main.getConfigManager().getConfig("items.yml").getString("Items." + item.name() + ".Name"));
            item.setCommand(Main.getConfigManager().getConfig("items.yml").getString("Items." + item.name() + ".Command-Execute"));
            item.setEnable(Main.getConfigManager().getConfig("items.yml").getBoolean("Items." + item.name() + ".Enable"));
            item.setSlot(Main.getConfigManager().getConfig("items.yml").getInt("Items." + item.name() + ".Slot") -1);
        }
    }
}
