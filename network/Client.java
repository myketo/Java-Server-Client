package network;

import java.io.*;
import java.net.Socket;

public class Client {
    // saving client id
    static String id;

    public static void main(String[] args) {
        try {
            // start new connection
            Socket socket = new Socket("localhost", 12129);
            System.out.println("Connecting to server, please wait...");

            // send msg to server
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // get msg from server
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // successfully connected message
            System.out.println(br.readLine());

            // get msg from user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // type in your id
            System.out.print("Type in your id: ");
            id = reader.readLine();
            pw.println(id);

            // get a response from the server
            String response = br.readLine();
            System.out.println(response);

            // check if access was denied
            if(response.equals("REFUSED")) return;

            // asking for 3 objects from server
            for(int i = 0; i < 3; i++) {
                // get 'select object' message from server
                System.out.println(br.readLine());

                // select object
                String objectName = reader.readLine();
                pw.println(objectName);

                // get object from server
                ObjectInputStream inputStream = new ObjectInputStream(is);
                Object obj = inputStream.readObject();

                // print out object
                System.out.println("object: " + obj + "\nid: " + id);
            }

            // close connection
            socket.close();
        } catch (Exception e) {
            // throw exception if client couldn't connect to server
            System.err.println("Client exception: " + e);
        }
    }
}