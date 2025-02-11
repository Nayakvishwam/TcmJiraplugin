package tcmapp.servlet;

import net.java.ao.Entity;

public interface User extends Entity {
    String getName();

    void setName(String name);
}
