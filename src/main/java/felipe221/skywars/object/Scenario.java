package felipe221.skywars.object;

import java.lang.reflect.Type;
import java.util.Random;

public class Scenario {
    public enum TypeScenario{
        NORMAL("Normal"),
        ANTIKB("Anti KB"),
        LUCKY("Lucky"),
        SPEED("Velocidad"),
        SCAFFOLD("Scaffold"),
        TORMENTA("Tormenta");

        private String name;

        TypeScenario(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static TypeScenario getRandomScenario(){
        Random rand = new Random();

        int scenario = rand.nextInt(TypeScenario.values().length);
        return TypeScenario.values()[scenario];
    }
}
