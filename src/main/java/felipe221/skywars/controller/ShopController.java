package felipe221.skywars.controller;

import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.User;
import org.bukkit.entity.Player;

public class ShopController {
    public enum TypeShop{
        TRAIL("skywars.trail."),
        TEMATICA("skywars.tematica."),
        KILL_EFFECT("skywars.kill."),
        WIN_EFFECT("skywars.win."),
        CAGE_TYPE("skywars.type."),
        CAGE_MATERIAL("skywars.material.");

        private String permission;

        TypeShop(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

    //TODO
    public static void shopItem(Player player, TypeShop typeShop, String objectShop, String name, int price){
        User user = User.getUser(player);

        if (user.getCoins() <= price){
            //msg no tiene plata

            return;
        }

        if (player.hasPermission(typeShop.getPermission() + objectShop)){
            //msg ya tiene permiso

            return;
        }

        user.setCoins(user.getCoins() - price);
        //msg compra exitosa
        //set permission
    }
}
