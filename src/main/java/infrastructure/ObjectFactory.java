package infrastructure;

import infrastructure.configurator.ObjectConfigurator;
import infrastructure.configurator.ProxyConfigurator;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) throws Exception {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    public <T> T createObject(Class<T> implClass) throws Exception {
        T t = create(implClass);

        configure(t);

        invokeInit(implClass, t);

        t = wrappWithProxyIfNeeded(implClass, t);

        return t;
    }

    private <T> T create(Class<T> implClass) throws Exception {
        return implClass.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T t) throws Exception {
        for (ObjectConfigurator configurator : configurators) {
            configurator.configure(t, context);
        }
    }

    private <T> void invokeInit(Class<T> implClazz, T t) throws Exception {
        for (Method method : implClazz.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> T wrappWithProxyIfNeeded(Class<T> implClass, T t) {
        for (ProxyConfigurator p : proxyConfigurators) {
            t = (T) p.replaceWithProxyIfNeeded(t, implClass);
        }
        return t;
    }
}
