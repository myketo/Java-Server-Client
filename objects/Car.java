package objects;

import java.io.Serializable;

public class Car implements Serializable {
    public String brand;

    public Car(String brand){
        this.brand = brand;
    }
}
