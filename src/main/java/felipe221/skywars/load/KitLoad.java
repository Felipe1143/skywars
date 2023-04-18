package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.controller.KitController;
import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitLoad {
    private static ArrayList<Kit> kits_global = new ArrayList<>();

    public static void load(){
        ConfigurationSection kits = Main.getConfigManager().getConfig("kits.yml").getConfigurationSection("Kits");
        kits_global.clear();

        for (Map.Entry<String, Object> entry : kits.getValues(false).entrySet()) {
            List<ItemStack> armorItems = new ArrayList<>();
            List<ItemStack> inventoryItems = new ArrayList<>();
            String kitName = entry.getKey();

            //add name
            String name = Main.getConfigManager().getConfig("kits.yml").getString("Kits." + kitName + ".Name");

            //add item menu
            ItemStack item = new ItemStack(Material.getMaterial(Main.getConfigManager().getConfig("kits.yml").getString("Kits." + kitName + ".Item-Menu")));

            //add permission
            String permission = Main.getConfigManager().getConfig("kits.yml").getString("Kits." + kitName + ".Permission");

            //add price
            int price = Main.getConfigManager().getConfig("kits.yml").getInt("Kits." + kitName + ".Price");

            //add lore
            ArrayList<String> lore = new ArrayList<>(Main.getConfigManager().getConfig("kits.yml").getStringList("Kits." + kitName + ".Lore"));

            //add armor
            if (!Main.getConfigManager().getConfig("kits.yml").get("Kits." + kitName + ".Armor").equals("-")){
                for (Object armor : Main.getConfigManager().getConfig("kits.yml").getList("Kits." + kitName +".Armor")) {
                    if (!(armor instanceof ItemStack)) {
                        continue;
                    }

                    armorItems.add((ItemStack) armor);
                }
            }

            //add items
            if (!Main.getConfigManager().getConfig("kits.yml").get("Kits." + kitName + ".Items").equals("-")) {
                for (Object items : Main.getConfigManager().getConfig("kits.yml").getList("Kits." + kitName + ".Items")) {
                    if (!(items instanceof ItemStack)) {
                        continue;
                    }

                    inventoryItems.add((ItemStack) items);
                }
            }

            kits_global.add(new Kit(kitName, name, inventoryItems, armorItems, price, lore, permission, item));
        }
    }

    public static void remove(Player player, Kit kit){
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName(), null);
        Main.getConfigManager().save("kits.yml");

        System.out.println("[Debug - SkyWars] El kit " + kit.getName() + " fue eliminado correctamente");

        KitController.removeEditing(player);
        player.closeInventory();
        KitLoad.load();

        player.sendMessage(ChatColor.GREEN + "¡Kit eliminado correctamente!");
    }

    public static Kit getKitPerName(String name){
        for (Kit kit : getKits()){
            if (kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }

        return null;
    }

    public static void fromConfig(Player player, Kit kit){
        Inventory inventory = Bukkit.createInventory(player, 9*4, kit.getName());

        KitController.addEditing(player, kit);

        //separate items of armor
        ItemStack separate = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(1, separate);
        inventory.setItem(10, separate);
        inventory.setItem(19, separate);
        inventory.setItem(28, separate);

        ItemStack delete = ItemBuilder.start(Material.BARRIER).name("&c&lELIMINAR KIT").build();
        ItemStack price = ItemBuilder.start(Material.DIAMOND).name("&aCambiar precio").lore("&7Precio actual: &a"+ kit.getPrice()).build();
        ItemStack permission = ItemBuilder.start(Material.ACACIA_SIGN).name("&aCambiar permiso").lore("&7Permiso actual: &a" + kit.getPermission()).build();
        ItemStack lore = ItemBuilder.start(Material.PAPER).name("&aCambiar descripción").lore("&7" +
                "¡Debes cambiarlos desde el kits.yml!").build();
        ItemStack name = ItemBuilder.start(Material.NAME_TAG).name("&aCambiar nombre").lore("&7" +
                "¡Debes eliminar el kit y crearlo otra vez!").build();
        ItemStack item_menu = ItemBuilder.start(kit.getItemMenu().getType()).name("&aItem del menú").lore("&7¡Reemplaza este objeto con el item que quieres", "&7que aparezca en el menú!").build();
        ItemStack backMenu = ItemBuilder.start(Material.SLIME_BALL).name("&aVolver al menú de kits").build();

        inventory.setItem(29, backMenu);
        inventory.setItem(30, item_menu);
        inventory.setItem(31, name);
        inventory.setItem(32, lore);
        inventory.setItem(33, permission);
        inventory.setItem(34, price);
        inventory.setItem(35, delete);

        if (!kit.getArmor().isEmpty()) {
            ItemStack helmet = (ItemStack) kit.getArmor().toArray()[0];

            if (helmet != null) {
                inventory.setItem(0, helmet);
            }

            if (kit.getArmor().size() > 1) {
                ItemStack chestplate = (ItemStack) kit.getArmor().toArray()[1];

                if (chestplate != null) {
                    inventory.setItem(9, chestplate);
                }
            }
            if (kit.getArmor().size() > 2){
                ItemStack leggings = (ItemStack) kit.getArmor().toArray()[2];

                if (leggings != null) {
                    inventory.setItem(18, leggings);
                }
            }

            if (kit.getArmor().size() >3){
                ItemStack boots = (ItemStack) kit.getArmor().toArray()[3];

                if (boots != null) {
                    inventory.setItem(27, boots);
                }
            }
        }

        int counter = 2;
        for (ItemStack items : kit.getItems()){
            //glasses
            if (counter == 9 || counter == 10){
                counter = 11;
            }

            if (counter == 18 || counter == 19){
                counter = 20;
            }

            if (counter == 27 || counter == 28){
                counter = 29;
            }

            inventory.setItem(counter, items);

            counter++;
        }

        //put armor with slot

        player.openInventory(inventory);
    }

    public static void fromConfigList(Player player){
        MenuGUI inventory = new MenuGUI("Kits para editar: ", 4);
        inventory.initConfigKit();

        //add kit list
        for (Kit kit : getKits()){
            inventory.addItem(ItemBuilder.start(kit.getItemMenu().getType()).lore(kit.getLore()).name(kit.getName()).build());
        }

        player.openInventory(inventory.getInventory());
    }

    public static void sendToConfig(Player player, Inventory inventory, Kit kit){
        ArrayList<ItemStack> armor = new ArrayList<>();
        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack helmet = inventory.getItem(0);
        if (helmet != null){
            final String typeNameString = helmet.getType().name();
            if (!typeNameString.endsWith("_HELMET")){
                player.sendMessage(ChatColor.RED + "¡No puedes poner el item " + typeNameString + " en la ubicación del casco!");
                player.sendMessage(ChatColor.RED + "Reiniciando configuración...");

                //re-open inventory to configure
                inventory.remove(helmet);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                    }
                }.runTaskLater(Main.getInstance(), 2);

                return;
            }

            armor.add(helmet);
        }

        ItemStack chestplate = inventory.getItem(9);
        if (chestplate != null){
            final String typeNameString = chestplate.getType().name();
            if (!typeNameString.endsWith("_CHESTPLATE")){
                player.sendMessage(ChatColor.RED + "¡No puedes poner el item " + typeNameString + " en la ubicación del peto!");
                player.sendMessage(ChatColor.RED + "Reiniciando configuración...");

                //re-open inventory to configure
                inventory.remove(chestplate);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                    }
                }.runTaskLater(Main.getInstance(), 2);

                return;
            }

            armor.add(chestplate);
        }

        ItemStack LEGGINGS = inventory.getItem(18);
        if (LEGGINGS != null){
            final String typeNameString = LEGGINGS.getType().name();
            if (!typeNameString.endsWith("_LEGGINGS")){
                player.sendMessage(ChatColor.RED + "¡No puedes poner el item " + typeNameString + " en la ubicación del pantalon!");
                player.sendMessage(ChatColor.RED + "Reiniciando configuración...");

                //re-open inventory to configure
                inventory.remove(LEGGINGS);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                    }
                }.runTaskLater(Main.getInstance(), 2);

                return;
            }

            armor.add(LEGGINGS);
        }

        ItemStack BOOTS = inventory.getItem(27);
        if (BOOTS != null){
            final String typeNameString = BOOTS.getType().name();
            if (!typeNameString.endsWith("_BOOTS")){
                player.sendMessage(ChatColor.RED + "¡No puedes poner el item " + typeNameString + " en la ubicación de las botas!");
                player.sendMessage(ChatColor.RED + "Reiniciando configuración...");

                //re-open inventory to configure
                inventory.remove(BOOTS);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                    }
                }.runTaskLater(Main.getInstance(), 2);

                return;
            }

            armor.add(BOOTS);
        }

        Map<Integer, ItemStack> Items = new HashMap<Integer, ItemStack>();

        for(int x = 0;x < inventory.getSize(); x++ ){
            Items.put(x, inventory.getItem(x));
        }

        for (Map.Entry<Integer, ItemStack> entry : Items.entrySet()) {
            int slot = entry.getKey();

            if (slot == 30){
                if (entry.getValue() == null) {
                    Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Item-Menu", Material.WOODEN_AXE);

                    continue;
                }

                ItemStack item_menu = entry.getValue();

                if (item_menu.getType() == Material.AIR){
                    Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Item-Menu", Material.WOODEN_AXE);
                }else{
                    Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Item-Menu", item_menu.getType().name().toUpperCase());
                }
            }

            if (entry.getValue() == null) {
                continue;
            }

            if (entry.getValue().getType() == Material.AIR) {
                continue;
            }

            if (entry.getValue().getType().toString().toUpperCase().contains("GLASS_PANE")) {
                continue;
            }

            if ((slot > 1 && slot <=8) || (slot > 10 && slot <=17) || (slot > 19 && slot <=26)){
                items.add(entry.getValue());
            }
        }

        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Items", items);
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Armor", armor);
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Price", kit.getPrice());
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Lore", kit.getLore());
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Permission", kit.getPermission());
        Main.getConfigManager().getConfig("kits.yml").set("Kits." + kit.getConfigName() + ".Name", kit.getName());

        Main.getConfigManager().save("kits.yml");

        System.out.println("[Debug - SkyWars] La configuración del kit " + kit.getName() + " fue actualizada correctamente");

        KitController.removeEditing(player);
        KitController.removeCreating(player);
        KitLoad.load();

        player.sendMessage(ChatColor.GREEN + "¡Configuración cargada correctamente!");
    }

    public static ArrayList<Kit> getKits() {
        return kits_global;
    }

    public static void setKits(ArrayList<Kit> kits_global) {
        KitLoad.kits_global = kits_global;
    }
}
