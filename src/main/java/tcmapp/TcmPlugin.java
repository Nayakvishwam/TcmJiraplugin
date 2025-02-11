package tcmapp;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import tcmapp.service.ServiceManager;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Scanned
@Named
public class TcmPlugin {
    private static final Logger log = LoggerFactory.getLogger(TcmPlugin.class);

    @Inject
    public TcmPlugin(@ComponentImport ActiveObjects activeObjects) {
        log.info("Initializing TcmPlugin and setting ActiveObjects in ServiceManager...");
        ServiceManager.setActiveObjects(activeObjects);
        log.info("ActiveObjects successfully set in ServiceManager.");
    }
}
