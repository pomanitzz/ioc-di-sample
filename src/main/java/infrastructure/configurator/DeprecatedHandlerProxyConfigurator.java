package infrastructure.configurator;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {

    @Override
    public Object replaceWithProxyIfNeeded(Object obj, Class clazz) {
        if (clazz.isAnnotationPresent(Deprecated.class)) {
            if (clazz.getInterfaces().length == 0) {
                return Enhancer.create(clazz, (net.sf.cglib.proxy.InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(method, args, obj));
            }
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(method, args, obj));
        } else {
            return obj;
        }
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t) throws Exception {
        System.out.println("***Deprecated***");
        return method.invoke(t, args);
    }
}
