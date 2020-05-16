package businesslogic;

import infrastructure.annotation.InjectByType;

import javax.annotation.PostConstruct;

@Deprecated
public class PolicemanImpl implements Policeman {

    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init() {
        System.out.println("PolicemanImpl.init");
    }

    @Override
    public String makePeopleLeaveRoom() {
        System.out.println(recommendator.recommend());
        return "Go away";
    }
}
