package felipe221.skywars.object;

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
        NONE("Ninguno");

        private String name;

        KillEffect(String name) {
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
