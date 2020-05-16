package infrastructure;

import infrastructure.annotation.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ObjectFactory objectFactory;
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private final Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) throws Exception {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<? extends T> impl = type;
        if (type.isInterface()) {
            impl = config.getImplClass(type);
        }
        T t = objectFactory.createObject(impl);
        if (impl.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Config getConfig() {
        return config;
    }
}
