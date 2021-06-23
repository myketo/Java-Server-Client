package objects;

import java.io.Serializable;

public class Pizza implements Serializable {
    public String type;

    public Pizza(String type){
        this.type = type;
    }
}