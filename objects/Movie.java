package objects;

import java.io.Serializable;

public class Movie implements Serializable {
    public String genre;

    public Movie(String genre){
        this.genre = genre;
    }
}
