package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.Kills;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KillsLoad {
    private static HashMap<String, ArrayList<Kills>> listTematicas = new HashMap<>();

    public static void load(){
        ConfigurationSection listTemas = Main.getConfigManager().getConfig("messages.yml").getConfigurationSection("Kill-Messages");
        ArrayList<Kills> listKill = new ArrayList<>();

        for (Map.Entry<String, Object> entry : listTemas.getValues(false).entrySet()) {
            String nameTematica = entry.getKey();

            //BOW MESSAGES
            ConfigurationSection listBow = Main.getConfigManager().getConfig("messages.yml").getConfigurationSection("Kill-Messages." + entry.getKey() + ".Bow");
            ArrayList<String> listMessageBow = new ArrayList<>();

            for (Map.Entry<String, Object> a : listBow.getValues(false).entrySet()) {
                String id = a.getKey();
                String message = Main.getConfigManager().getConfig("messages.yml").getString("Kill-Messages." + entry.getKey() + ".Bow." + id);

                listMessageBow.add(message);
            }

            listKill.add(new Kills(Kills.TypeKill.BOW, listMessageBow));

            //SWORD MESSAGES
            ConfigurationSection listSword = Main.getConfigManager().getConfig("messages.yml").getConfigurationSection("Kill-Messages." + entry.getKey() + ".Sword");
            ArrayList<String> listMessageSword = new ArrayList<>();

            for (Map.Entry<String, Object> a : listSword.getValues(false).entrySet()) {
                String id = a.getKey();
                String message = Main.getConfigManager().getConfig("messages.yml").getString("Kill-Messages." + entry.getKey() + ".Sword." + id);

                listMessageSword.add(message);
            }

            listKill.add(new Kills(Kills.TypeKill.SWORD, listMessageSword));

            //void MESSAGES
            ConfigurationSection listVoid = Main.getConfigManager().getConfig("messages.yml").getConfigurationSection("Kill-Messages." + entry.getKey() + ".Void");
            ArrayList<String> listMessageVoid = new ArrayList<>();

            for (Map.Entry<String, Object> a : listVoid.getValues(false).entrySet()) {
                String id = a.getKey();
                String message = Main.getConfigManager().getConfig("messages.yml").getString("Kill-Messages." + entry.getKey() + ".Void." + id);

                listMessageVoid.add(message);
            }

            listKill.add(new Kills(Kills.TypeKill.VOID, listMessageVoid));

            listTematicas.put(nameTematica, listKill);
        }
    }

    public static String getRandomMessageByTypeKill(String tematica, Kills.TypeKill typeKill){
        Random rand = new Random();
        ArrayList<Kills> killsArrayList = listTematicas.get(tematica);
        Kills finalKills = null;

        for (Kills kills : killsArrayList){
            if (kills.getTypeKill() == typeKill){
                finalKills = kills;
            }
        }

        int index = rand.nextInt(finalKills.getMsgList().size());
        return (String) finalKills.getMsgList().toArray()[index];
    }
}
