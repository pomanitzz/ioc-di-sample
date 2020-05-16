package businesslogic;

import infrastructure.annotation.InjectProperty;
import infrastructure.annotation.Singleton;

@Singleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty
    private String alcohol;

    @InjectProperty("cucumber")
    private String meal;

    @Override
    public String recommend() {
        return String.format("To protect from covid-19 drink: %s1 and eat: %s2", alcohol, meal);
    }
}
