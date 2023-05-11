package felipe221.skywars.object.cosmetics;

public class Time {
    public enum TypeTime{
        DAY("Día", 6000), SUNSET("Atardecer", 12000), NIGHT("Noche", 18000);

        private String name;
        private int ticks;

        TypeTime(String name, int ticks) {
            this.name = name;
            this.ticks = ticks;
        }

        public int getTicks() {
            return ticks;
        }

        public void setTicks(int ticks) {
            this.ticks = ticks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
