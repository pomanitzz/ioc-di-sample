package infrastructure.configurator;

import infrastructure.ApplicationContext;
import infrastructure.annotation.InjectByType;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object o, ApplicationContext context) throws Exception {
        for(Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(o, object);
            }
        }
    }
}
