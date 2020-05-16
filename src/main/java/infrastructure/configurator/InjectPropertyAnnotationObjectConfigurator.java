package infrastructure.configurator;

import infrastructure.ApplicationContext;
import infrastructure.annotation.InjectProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private Map<String, String> properties;

    public InjectPropertyAnnotationObjectConfigurator() throws Exception {
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        properties = lines.map(l -> l.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    public void configure(Object o, ApplicationContext context) throws Exception {
        Class<?> aClass = o.getClass();
        for(Field field : aClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value = annotation.value().isEmpty() ? properties.get(field.getName()) : properties.get(annotation.value());
                field.setAccessible(true);
                field.set(o, value);
            }
        }
    }
}
