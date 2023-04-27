package felipe221.skywars.controller;

import felipe221.skywars.load.SignLoad;
import felipe221.skywars.load.TopLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignController implements Listener {
    @EventHandler
    public void onPlayerSetSign(SignChangeEvent e){
        Player player = e.getPlayer();
        Sign sign = (Sign) e.getBlock();

        if (player.hasPermission("skywars.admin") || player.hasPermission("skywars.config")) {
            if (e.getLine(0).equalsIgnoreCase("[SKYWARS]")) {
                if (sign.getBlock().getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace()) == null){
                    player.sendMessage(ChatColor.RED + "¡Este cartel debe estar pegado en un bloque!");

                    e.setCancelled(true);

                    return;
                }

                if (e.getLine(1).equalsIgnoreCase("[TOP]")){
                    TopLoad.TypeTop top = TopLoad.TypeTop.valueOf(e.getLine(2));

                    if (top != null){
                        if (BukkitUtil.isNumeric(e.getLine(3))){
                            int valueTop = Integer.parseInt(e.getLine(3));

                            if (valueTop <= 10){
                                SignLoad.sendConfig((Sign) e.getBlock(), null, top, valueTop);
                                SignLoad.Signs.TOP.addSign((Sign) e.getBlock(), top.name());
                                SignLoad.Signs.TOP.update();

                                player.sendMessage(ChatColor.GREEN + "¡Cartel agregado correctamente!");
                            }else{
                                player.sendMessage(ChatColor.RED + "¡El valor del top no puede ser mayor a 10!");
                            }
                        }else{
                            player.sendMessage(ChatColor.RED + "¡Porfavor ubique la posición del top que desea mostrar en la última linea!");
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "¡No existe este tipo de top! (KILLS_SOLO - WINS_SOLO - KILLS_TEAM - WINS_TEAM - LEVEL)");
                    }
                }else {
                    String arena = e.getLine(1);

                    if (Arena.getByName(arena) != null) {
                        Arena arenaMap = Arena.getByName(arena);

                        SignLoad.sendConfig((Sign) e.getBlock(), arenaMap, null, 0);
                        SignLoad.Signs.ARENA.addSign((Sign) e.getBlock(), arena);
                        SignLoad.Signs.ARENA.update();

                        player.sendMessage(ChatColor.GREEN + "¡Cartel agregado correctamente!");
                    } else {
                        player.sendMessage(ChatColor.RED + "¡No existe una arena con ese nombre!");
                    }
                }
            }
        }
    }
}
