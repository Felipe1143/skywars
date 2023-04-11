package felipe221.skywars.controller;

import felipe221.skywars.Main;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
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
    private Kit kit;
    private Player player;
    private static HashMap<Player, Kit> editing = new HashMap<>();
    private static ArrayList<Player> creating = new ArrayList<>();

    public KitController(){
        //listener
    }

    public KitController(Kit kit) {
        this.kit = kit;
    }

    public KitController(Player player, Kit kit) {
        this.player = player;
        this.kit = kit;
    }

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

        if (edit.getUniqueId() == player.getUniqueId()){
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

        if (e.getCurrentItem().getType() == Material.BARRIER){
            e.setCancelled(true);

            Inventory inv = e.getInventory();
            Player edit = null;

            for (Player players : editing.keySet()) {
                edit = players;
            }

            Kit kit = editing.get(player);

            KitLoad.remove(player, kit);
        }

        if (e.getCurrentItem().getType().toString().toUpperCase().contains("GLASS_PANE")){
            e.setCancelled(true);
        }

    }

    //kit create on chat
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();

        if (creating.contains(player)){
            //if check is exist
            Inventory inventory = Bukkit.createInventory(null, 9*5);

            Kit newKit =  new Kit(msg, msg, new ArrayList<>(), new ArrayList<>(), 0, new ArrayList<>(), "skywars.kit." + msg, new ItemStack(Material.WOODEN_AXE));
            KitLoad.sendToConfig(player, inventory, newKit);

            editing.put(player, newKit);

            player.sendMessage(ChatColor.GREEN + "¡Kit " + msg + " creado correctamente!" );
            player.sendMessage(ChatColor.GREEN + "Abriendo configuración...");

            new BukkitRunnable() {
                @Override
                public void run() {
                    KitLoad.fromConfig(player, newKit);
                }
            }.runTaskLater(Main.getInstance(), 2);

            //open in menu
            //set lore, price, permission in menu
        }
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static HashMap<Player, Kit> getEditing() {
        return editing;
    }

    public static boolean isEditing(Player player){
        if (editing.containsKey(player)){
            return true;
        }

        return false;
    }

    public static void removeEditing(Player player){
        if (editing.containsKey(player)){
            editing.remove(player);
        }
    }

    public static void addEditing(Player player, Kit kit) {
        editing.put(player, kit);
    }


    public static ArrayList<Player> getCreating() {
        return creating;
    }

    public static void addCreating(Player player) {
        creating.add(player);
    }

}
