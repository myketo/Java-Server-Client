package network;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

class ServerClientThread extends Thread {
    Socket socket;  // current users socket
    final int MAX_CLIENTS = 5;  // max amount of clients
    HashMap<String, Object> objects;    // map containing objects to request by user

    // assigning values in constructor
    public ServerClientThread(Socket socket, HashMap<String, Object> objects){
        this.socket = socket;
        this.objects = objects;
    }

    public void run(){
        try{
            // Sending 'successfully connected' message
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("Successfully connected to server!");

            // getting id from the user
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String userId = br.readLine();

            // check if too many clients connected and deny access if so
            if((Thread.activeCount()-2) > MAX_CLIENTS){
                pw.println("Connection Status: REFUSED");
                System.out.println("Client left. Active clients: " + (Thread.activeCount()-3));
                return;
            }

            // otherwise print "OK" and display new user id on server
            pw.println("Connection Status: OK");
            System.out.println("User id: " + userId);

            // let user ask for 3 objects
            for(int i = 0; i < 3; i++){
                pw.println("What object do you want to get? ");
                String objectName = br.readLine();

                // get object from map if it exists
                ObjectOutputStream outputStream = new ObjectOutputStream(os);
                Object selectedObject = objects.get(objectName);

                // if it doesn't exist then take random object
                if(selectedObject == null){
                    Object[] values = objects.values().toArray();
                    Random rand = new Random();
                    selectedObject = values[rand.nextInt(values.length)];
                }

                // send selected object to client
                outputStream.writeObject(selectedObject);

                // write to server console
                System.out.println("Sent object " + selectedObject + " to user " + userId);
            }

            // communicate that client left and display active users
            System.out.println("Client left. Active clients: " + (Thread.activeCount()-3));
            socket.close();
        }catch (Exception e) {
            // display exception communicate that client left and display active users
            System.err.println("Server exception: " + e);
            System.out.println("Client left. Active clients: " + (Thread.activeCount()-3));
        }
    }
}