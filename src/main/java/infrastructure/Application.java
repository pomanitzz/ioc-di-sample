package infrastructure;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String package2Scan, Map<Class, Class> ifc2ImplClass) throws Exception {
        Config config = new JavaConfig(package2Scan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setObjectFactory(objectFactory);
        return context;
    }
}
