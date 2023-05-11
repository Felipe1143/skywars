package felipe221.skywars.controller;

import felipe221.skywars.load.SignLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.iSign;
import felipe221.skywars.object.iStats;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class SignController implements Listener {
    @EventHandler
    public void onPlayerDelete(BlockBreakEvent e){
        Player player = e.getPlayer();

        if (player.hasPermission("skywars.admin") || player.hasPermission("skywars.config")) {
            if (e.getBlock().getType().name().contains("SIGN")) {
                iSign sign = iSign.getSignBySignLocation(e.getBlock().getLocation());

                if (sign != null) {
                    sign.delete();

                    player.sendMessage(ChatColor.GREEN + "¡Cartel removido correctamente!");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        if (e.getClickedBlock() == null){
            return;
        }

        if (!e.getClickedBlock().getType().name().contains("SIGN")) {
            return;
        }


        iSign sign = iSign.getSignBySignLocation(e.getClickedBlock().getLocation());

        if (sign != null) {
            ArenaController.join(player, sign.getArena());
        }
    }

    @EventHandler
    public void onPlayerSetSign(SignChangeEvent e){
        Player player = e.getPlayer();
        Sign sign = (Sign) e.getBlock().getState();

        if (player.hasPermission("skywars.admin") || player.hasPermission("skywars.config")) {
            if (e.getLine(0).equalsIgnoreCase("[SKYWARS]")) {
                try {
                    Block d = e.getBlock().getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
                }catch (ClassCastException b){
                    player.sendMessage(ChatColor.RED + "¡Este cartel debe estar pegado en un bloque!");

                    e.setCancelled(true);

                    return;
                }


                String arena = e.getLine(1);

                if (Arena.getByName(arena) != null) {
                    Arena arenaMap = Arena.getByName(arena);

                    iSign sign1 = new iSign(arenaMap, sign.getLocation());
                    sign1.update();
                    sign1.send();

                    player.sendMessage(ChatColor.GREEN + "¡Cartel agregado correctamente!");
                } else {
                    player.sendMessage(ChatColor.RED + "¡No existe una arena con ese nombre!");
                }
            }
        }
    }
}
