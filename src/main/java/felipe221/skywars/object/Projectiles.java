package felipe221.skywars.object;

public class Projectiles {
    public enum TypeProjectiles{
        NORMAL("Normales"), TP("TP"), EXPLOSIVE("Explosivos");

        private String name;

        TypeProjectiles(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum Trails{
        NONE("Ninguno");

        private String name;

        Trails(String name) {
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
