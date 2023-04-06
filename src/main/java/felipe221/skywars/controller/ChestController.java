package felipe221.skywars.controller;

import felipe221.skywars.load.ChestLoad;
import felipe221.skywars.object.Chest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;

public class ChestController implements Listener {
    private static HashMap<Player, Chest.TypeChest> editing = new HashMap<Player, Chest.TypeChest>();

    //config set in inventory
    public static void set(Player player){
        player.getInventory().clear();

        ItemStack chest_basic = new ItemStack(Material.CHEST);
        ItemMeta chest_basic_meta = chest_basic.getItemMeta();
        chest_basic_meta.setDisplayName(ChatColor.GREEN+ "Cofre básico " + ChatColor.GRAY + " (Click derecho)");
        chest_basic.setItemMeta(chest_basic_meta);

        ItemStack chest_normal = new ItemStack(Material.CHEST);
        ItemMeta chest_normal_meta = chest_normal.getItemMeta();
        chest_normal_meta.setDisplayName(ChatColor.GOLD+ "Cofre normal " + ChatColor.GRAY + " (Click derecho)");
        chest_normal.setItemMeta(chest_normal_meta);

        ItemStack chest_op = new ItemStack(Material.CHEST);
        ItemMeta chest_op_meta = chest_op.getItemMeta();
        chest_op_meta.setDisplayName(ChatColor.RED + "Cofre OP " + ChatColor.GRAY + " (Click derecho)");
        chest_op.setItemMeta(chest_op_meta);
        
        player.getInventory().addItem(chest_basic, chest_normal, chest_op);
        player.updateInventory();
    }

    @EventHandler
    public void onPlayerClickSlot(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (Objects.requireNonNull(e.getItem()).getType() != Material.CHEST) {
            return;
        }

        if (!e.getItem().hasItemMeta()){
            return;
        }

        if (!editing.isEmpty()){
            player.sendMessage(ChatColor.RED + "Un usuario ya se encuentra editando los cofres ¡Espera que termine y podrás!");

            return;
        }

        if (Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName().contains("básico")){
            ChestLoad.fromConfig(player, Chest.TypeChest.BASICO);

            return;
        }

        if (Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName().contains("normal")){
            ChestLoad.fromConfig(player, Chest.TypeChest.NORMAL);

            return;
        }

        if (Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName().contains("OP")){
            ChestLoad.fromConfig(player, Chest.TypeChest.OP);

            return;
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

        Chest.TypeChest chest = editing.get(edit);

        assert edit != null;
        if (player.getUniqueId() == edit.getUniqueId()){
            ChestLoad.sendToConfig(chest, e.getInventory());
        }else{
            player.sendMessage("ERROR AL CERRAR COFRE");
        }
    }
}
