package infrastructure.configurator;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object obj, Class clazz);
}
