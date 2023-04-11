package felipe221.skywars.controller;

import felipe221.skywars.load.ChestLoad;
import felipe221.skywars.menus.ConfigMenu;
import felipe221.skywars.object.Chests;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ChestController implements Listener {
    private static HashMap<Player, Chests.TypeChest> editing = new HashMap<Player, Chests.TypeChest>();

    //config set in inventory
    public static void set(Player player){
        player.getInventory().clear();

        ItemStack leave = new ItemStack(Material.BARRIER);
        ItemMeta leave_meta = leave.getItemMeta();
        leave_meta.setDisplayName(ChatColor.RED+ "Salir" + ChatColor.GRAY + " (Click derecho)");
        leave.setItemMeta(leave_meta);

        ItemStack chest_basic = new ItemStack(Material.CHEST);
        ItemMeta chest_basic_meta = chest_basic.getItemMeta();
        chest_basic_meta.setDisplayName(ChatColor.GREEN+ "Cofre básico" + ChatColor.GRAY + " (Click derecho)");
        chest_basic.setItemMeta(chest_basic_meta);

        ItemStack chest_normal = new ItemStack(Material.CHEST);
        ItemMeta chest_normal_meta = chest_normal.getItemMeta();
        chest_normal_meta.setDisplayName(ChatColor.GOLD+ "Cofre normal" + ChatColor.GRAY + " (Click derecho)");
        chest_normal.setItemMeta(chest_normal_meta);

        ItemStack chest_op = new ItemStack(Material.CHEST);
        ItemMeta chest_op_meta = chest_op.getItemMeta();
        chest_op_meta.setDisplayName(ChatColor.RED + "Cofre OP" + ChatColor.GRAY + " (Click derecho)");
        chest_op.setItemMeta(chest_op_meta);
        
        player.getInventory().addItem(chest_basic, chest_normal, chest_op);
        player.getInventory().setItem(8, leave);

        player.updateInventory();

        player.sendMessage(ChatColor.GREEN + "¡Abre el cofre que deseas configurar y pon los items dentro!");
    }

    @EventHandler
    public void onPlayerClickSlot(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (e.getItem() == null){
            return;
        }

        if (e.getItem().getType() == Material.BARRIER){
            if (BukkitUtil.stripcolor(e.getItem().getItemMeta().getDisplayName()).equals("Salir (Click derecho)")) {
                player.sendMessage(ChatColor.GREEN + "¡Configuración terminada exitosamente!");
                player.getInventory().clear();
                ConfigMenu.openConfigMenu(player);
            }
        }

        if (e.getItem().getType() != Material.CHEST) {
            return;
        }

        if (!e.getItem().hasItemMeta()){
            return;
        }

        if (e.getItem().getItemMeta().getDisplayName().contains("básico")){
            if (!editing.isEmpty()){
                player.sendMessage(ChatColor.RED + "Un usuario ya se encuentra editando los cofres ¡Espera que termine y podrás!");

                return;
            }

            player.openInventory(ChestLoad.fromConfig(player, Chests.TypeChest.BASICO));
            addEdit(player, Chests.TypeChest.BASICO);

            return;
        }

        if (e.getItem().getItemMeta().getDisplayName().contains("normal")){
            if (!editing.isEmpty()){
                player.sendMessage(ChatColor.RED + "Un usuario ya se encuentra editando los cofres ¡Espera que termine y podrás!");

                return;
            }

            player.openInventory(ChestLoad.fromConfig(player, Chests.TypeChest.NORMAL));
            addEdit(player, Chests.TypeChest.NORMAL);

            return;
        }

        if (e.getItem().getItemMeta().getDisplayName().contains("OP")){
            if (!editing.isEmpty()){
                player.sendMessage(ChatColor.RED + "Un usuario ya se encuentra editando los cofres ¡Espera que termine y podrás!");

                return;
            }

            player.openInventory(ChestLoad.fromConfig(player, Chests.TypeChest.OP));
            addEdit(player, Chests.TypeChest.OP);

            return;
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

        if (e.getCurrentItem().getType().toString().toUpperCase().contains("GLASS_PANE")){
            e.setCancelled(true);
        }
    }

    //enviar a config al cerrar cofre
    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e){
        if (editing.isEmpty()){
            return;
        }

        Player player = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        Player edit = null;

        for (Player players : editing.keySet()){
            edit = players;
        }

        Chests.TypeChest chest = editing.get(edit);

        assert edit != null;
        if (player.getUniqueId() == edit.getUniqueId()){
            ChestLoad.sendToConfig(chest, e.getInventory());

            removeEdit(player);
            ChestLoad.load();

            player.sendMessage(ChatColor.GREEN + "¡Configuración cargada correctamente!");
        }
    }

    public static void addEdit(Player player, Chests.TypeChest chest){
        editing.put(player, chest);
    }

    public static void removeEdit(Player player){
        editing.remove(player);
    }

    public static boolean isEditing(Player player){
        if (editing.isEmpty()){
            return false;
        }

        if (editing.containsKey(player)){
            return true;
        }

        return false;
    }
}
