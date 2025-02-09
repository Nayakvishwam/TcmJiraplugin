package tcmapp.entity;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface TestCaseEntity extends Entity {
    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);
}
