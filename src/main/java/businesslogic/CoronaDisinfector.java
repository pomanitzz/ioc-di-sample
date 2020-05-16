package businesslogic;

import infrastructure.annotation.InjectByType;

public class CoronaDisinfector {

    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman;

    public void start(Room room) {
        announcer.announce("Start disinfection");
        System.out.println(policeman.makePeopleLeaveRoom());
        System.out.println(disinfect(room));
        announcer.announce("End disinfection");
    }

    private String disinfect(Room room) {
        return String.format("Disinfect room : %s", room.getName());
    }
}
