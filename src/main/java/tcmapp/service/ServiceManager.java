package tcmapp.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

import tcmapp.servlet.TodoService;
import tcmapp.servlet.TodoServiceImpl;

import javax.inject.Named;

@Scanned
@Named
public class ServiceManager {
    private static ActiveObjects ao;
    private static TodoService todoService;

    public static void setActiveObjects(ActiveObjects activeObjects) {
        ao = activeObjects;
        todoService = new TodoServiceImpl(ao);
    }

    public static ActiveObjects getActiveObjects() {
        if (ao == null) {
            throw new IllegalStateException("ActiveObjects has not been initialized.");
        }
        return ao;
    }

    public static TodoService getTodoService() {
        if (todoService == null) {
            throw new IllegalStateException("TodoService has not been initialized.");
        }
        return todoService;
    }
}
