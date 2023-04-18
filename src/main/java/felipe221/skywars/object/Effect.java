package felipe221.skywars.object;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;

public class Effect {
    public enum WinEffect{
        NONE("Ninguno");

        private String name;

        WinEffect(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum KillEffect{
        NONE("Ninguno"),
        COLD("Nieve");

        private String name;
        private Location location;

        KillEffect(String name) {
            this.name = name;
            this.location = null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public KillEffect setLocation(Location location){
            this.location = location;
            return this;
        }

        public Location getLocation() {
            return location;
        }

        public void play(){
            if (this == COLD){
                Location location = this.location.add(0,1,0);

                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LIGHT_BLUE_STAINED_GLASS, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, location, 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.BLUE_STAINED_GLASS, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, location, 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.ICE, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, location, 20.0D);

                ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 0.1F, 30, location, 20.0D);
            }
        }
    }
}
