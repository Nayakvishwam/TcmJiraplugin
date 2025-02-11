package tcmapp.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import static com.google.common.collect.Lists.newArrayList;

@Scanned
@Named
public class TodoServiceImpl implements TodoService {
    private final ActiveObjects ao;

    @Inject
    public TodoServiceImpl(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public Todo add(String description) {
        final Todo todo = ao.create(Todo.class);
        todo.setDescription(description);
        todo.setComplete(false);
        todo.save();
        return todo;
    }

    @Override
    public List<Todo> all() {
        return newArrayList(ao.find(Todo.class));
    }
}
