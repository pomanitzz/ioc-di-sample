package infrastructure.configurator;

import infrastructure.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object o, ApplicationContext context) throws Exception;
}
