package objects;

import java.io.Serializable;

public class Dog implements Serializable {
    public String breed;

    public Dog(String breed){
        this.breed = breed;
    }
}
