package network;

import objects.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class Server {
    static HashMap<String, Object> objects = new HashMap<>();   // map containing all of the objects

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            // starting the server
            serverSocket = new ServerSocket(12129);
            System.out.println("Server started.");
        } catch (Exception e) {
            // throwing exception and returning on fail
            System.err.println("Create server socket: " + e);
            return;
        }

        // creating objects and putting them into the objects map
        objects.put("car_1", new Car("Mercedes"));
        objects.put("car_2", new Car("Hyundai"));
        objects.put("dog_1", new Dog("Yorkshire Terrier"));
        objects.put("dog_2", new Dog("German Shepherd"));
        objects.put("energydrink_1", new EnergyDrink("white"));
        objects.put("energydrink_2", new EnergyDrink("red"));
        objects.put("movie_1", new Movie("fantasy"));
        objects.put("movie_2", new Movie("comedy"));
        objects.put("pizza_1", new Pizza("HAWAJSKA"));
        objects.put("pizza_2", new Pizza("Pepperoni"));

        // looping all time to serve new clients
        while (true) try {
            // generating random delay time for user serving
            Random rand = new Random();
            int delayInSeconds = 20;    // 20sec
            Thread.sleep(rand.nextInt(delayInSeconds)*1000);

            // creating a new client socket
            Socket socket = serverSocket.accept();

            // increasing current clients amount
            System.out.println("Client joined. Active clients: " + (Thread.activeCount()-1));

            // serving a client
            ServerClientThread sct = new ServerClientThread(socket, objects);
            sct.start();
        } catch (Exception e) {
            // throwing exception on server crash
            System.err.println("Server exception: " + e);
        }
    }
}

