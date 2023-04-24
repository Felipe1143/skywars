package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.Effect;
import org.bukkit.Material;

public class EffectLoad {
    public static void load(){
        for (Effect.KillEffect effects : Effect.KillEffect.values()){
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Kill-Effect." + effects.name() + ".Price");
            String name = Main.getConfigManager().getConfig("config.yml").getString("Kill-Effect." + effects.name() + ".Name");
            Material material = Material.valueOf(Main.getConfigManager().getConfig("config.yml").getString("Kill-Effect." + effects.name() + ".ID"));

            if (name == null){
                System.out.println("[Debug - SkyWars - ERROR] No se pudo encontrar el nombre del efecto de asesinato " + effects.name() + " en config.yml");
                System.out.println("[Debug - SkyWars - ERROR] Cancelando carga de efectos...");

                return;
            }

            effects.setName(name);
            effects.setPrice(price);
            effects.setMaterial(material);
        }

        for (Effect.WinEffect effects : Effect.WinEffect.values()){
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Win-Effect." + effects.name() + ".Price");
            String name = Main.getConfigManager().getConfig("config.yml").getString("Win-Effect." + effects.name() + ".Name");
            Material material = Material.valueOf(Main.getConfigManager().getConfig("config.yml").getString("Win-Effect." + effects.name() + ".ID"));

            if (name == null){
                System.out.println("[Debug - SkyWars - ERROR] No se pudo encontrar el nombre del efecto de victoria " + effects.name() + " en config.yml");
                System.out.println("[Debug - SkyWars - ERROR] Cancelando carga de efectos...");

                return;
            }

            effects.setName(name);
            effects.setPrice(price);
            effects.setMaterial(material);
        }

        for (Effect.Trail effects : Effect.Trail.values()){
            int price = Main.getConfigManager().getConfig("config.yml").getInt("Trails." + effects.name() + ".Price");
            String name = Main.getConfigManager().getConfig("config.yml").getString("Trails." + effects.name() + ".Name");
            Material material = Material.valueOf(Main.getConfigManager().getConfig("config.yml").getString("Trails." + effects.name() + ".ID"));

            if (name == null){
                System.out.println("[Debug - SkyWars - ERROR] No se pudo encontrar el nombre del trail " + effects.name() + " en config.yml");
                System.out.println("[Debug - SkyWars - ERROR] Cancelando carga de efectos...");

                return;
            }

            effects.setName(name);
            effects.setPrice(price);
            effects.setMaterial(material);
        }
    }
}
