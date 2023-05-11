package felipe221.skywars.controller;

import felipe221.skywars.Main;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class KitController implements Listener {
    private static HashMap<Player, Kit> editing = new HashMap<>();
    //CREATE, PRICE, LORE, NAME, PERMISSION, ITEM_MENU
    private static HashMap<Player, Object> creating = new HashMap<>();

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if (editing.isEmpty()) {
            return;
        }

        Player player = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        Player edit = null;

        for (Player players : editing.keySet()) {
            edit = players;
        }

        Kit kit = editing.get(player);

        //prevent send to config on editing price, name...
        if (edit.getUniqueId() == player.getUniqueId() && !creating.containsKey(player)){
           KitLoad.sendToConfig(player, inv, kit);
        }
    }

    //prevent move panels
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (player == null){
            return;
        }

        if (!isEditing(player)){
            return;
        }

        if (e.getCurrentItem() == null){
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR){
            return;
        }

        if (e.getSlot() == 31 || e.getSlot() == 32){
            e.setCancelled(true);
        }

        Inventory inv = e.getInventory();
        Player edit = null;

        for (Player players : editing.keySet()) {
            edit = players;
        }

        Kit kit = editing.get(player);

        if (e.getCurrentItem().getType() == Material.BARRIER){
            e.setCancelled(true);

            //no more kits prevent
            if ((KitLoad.getKits().size()-1) == 0){
                player.sendMessage(ChatColor.RED + "¡Si eliminas este kit no se podrá seleccionar ninguno para jugar!");
                player.sendMessage(ChatColor.RED + "Porfavor, crea otro y luego podras eliminarlo");

                return;
            }else {
                KitLoad.remove(player, kit);
            }
        }

        if (e.getCurrentItem().getType() == Material.DIAMOND && BukkitUtil.stripcolor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Cambiar precio")){
            e.setCancelled(true);

            setTypeCreating(player, "PRICE");
            edit.sendMessage(ChatColor.GREEN + "Escribe en el chat el costo del kit: ");
            player.closeInventory();

            return;
        }

        if (e.getCurrentItem().getType() == Material.SLIME_BALL && BukkitUtil.stripcolor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Volver al menú de kits")){
            e.setCancelled(true);
            player.closeInventory();

            KitLoad.fromConfigList(player);

            return;
        }

        if (e.getCurrentItem().getType().toString().toUpperCase().contains("GLASS_PANE")){
            e.setCancelled(true);

            return;
        }

    }

    //kit create on chat
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();

        if (!creating.containsKey(player)){
            return;
        }

        if (getTypeCreating(player).equals("CREATE")){
            //if check is exist
            Inventory inventory = Bukkit.createInventory(player, 9*5, "Kit");

            Kit newKit =  new Kit(msg, msg, new ArrayList<>(), new ArrayList<>(), 0, new ArrayList<>(), new ItemStack(Material.WOODEN_AXE));
            KitLoad.sendToConfig(player, inventory, newKit);

            editing.put(player, newKit);
            creating.remove(player);

            player.sendMessage(ChatColor.GREEN + "¡Kit " + msg + " creado correctamente!" );
            player.sendMessage(ChatColor.GREEN + "Abriendo configuración...");

            new BukkitRunnable() {
                @Override
                public void run() {
                    KitLoad.fromConfig(player, newKit);
                }
            }.runTaskLater(Main.getInstance(), 2);

            return;
        }

        if (getTypeCreating(player).equals("PRICE")){
            if (isNumeric(msg)){
                int price = Integer.parseInt(msg);
                Kit kit = editing.get(player);
                kit.setPrice(price);
                creating.remove(player);

                if (editing.containsKey(player)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            KitLoad.fromConfig(player, kit);
                        }
                    }.runTaskLater(Main.getInstance(), 2);
                }

                player.sendMessage(ChatColor.GREEN + "¡Configuración cambiada correctamente!");
            }else{
                player.sendMessage(ChatColor.RED + "¡Porfavor, escribe un precio en números!");
            }
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static HashMap<Player, Kit> getEditing() {
        return editing;
    }

    public static boolean isEditing(Player player){
        return editing.containsKey(player);
    }

    public static void removeEditing(Player player){
        if (editing.containsKey(player)){
            editing.remove(player);
        }
    }

    public static void removeCreating(Player player){
        if (creating.containsKey(player)){
            creating.remove(player);
        }
    }

    public static void addEditing(Player player, Kit kit) {
        editing.put(player, kit);
    }


    public static Object getTypeCreating(Player player) {
        return creating.get(player);
    }

    public static boolean isCreating(Player player) {
        return creating.containsKey(player);
    }

    public static void setTypeCreating(Player player, Object type) {
        creating.put(player, type);
    }

}
