package infrastructure;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String package2Scan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(package2Scan);
        this.ifc2ImplClass = new HashMap<>(ifc2ImplClass);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> subTypesOfIfc = scanner.getSubTypesOf(ifc);
            if (subTypesOfIfc.size() != 1) {
                throw new RuntimeException("Must be one implementation");
            }
            return subTypesOfIfc.iterator().next();
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
