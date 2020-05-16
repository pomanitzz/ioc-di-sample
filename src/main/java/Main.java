import businesslogic.CoronaDisinfector;
import businesslogic.Policeman;
import businesslogic.PolicemanImpl;
import businesslogic.Room;
import infrastructure.Application;
import infrastructure.ApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = Application.run("", Map.of(Policeman.class, PolicemanImpl.class));
        CoronaDisinfector coronaDisinfector = context.getObject(CoronaDisinfector.class);
        coronaDisinfector.start(new Room("living room"));
    }
}
