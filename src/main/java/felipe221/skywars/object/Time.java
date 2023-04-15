package felipe221.skywars.object;

public class Time {
    public enum TypeTime{
        DAY("Día"), SUNSET("Atardecer"), NIGHT("Noche");

        private String name;

        TypeTime(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
