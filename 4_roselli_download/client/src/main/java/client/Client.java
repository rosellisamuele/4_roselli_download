package client;

import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {


    protected static String host = "127.0.0.1";
    protected static int port = 6789;
    protected static Socket socket;
    //

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        socket = new Socket(host, port);
        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
        DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

        //Ricezione da parte del server della lista di immagini scaricabili
        String message = (String)reader.readObject();
        System.out.println(message);
              
        int select = userInput();
        outStream.write(select);
        
        String filename = "";
        switch(select){
            case 0: 
                filename = "bluesmurfcat.jpg";
                break;
            case 1:
                filename = "johnpork.jpg";
                break;
            case 2:
                filename = "pandatrueno.jpg";
                break;
            case 3:
                filename = "scenic.jpg";
                break;
            case 4:
                filename = "forzaviola.jpg";
                break;
            default:
                System.exit(1);
        }
        
        //Apro stream verso il file da scaricare
        FileOutputStream writer = new FileOutputStream("images\\"+filename);
        
        
        //leggo i byte dal socket e li scrivo sul file
        byte[] buffer = new byte[1024];
        int lengthRead;

            while ((lengthRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, lengthRead);
            }
            System.out.println("File ricevuto.");
        
        writer.close();
        reader.close();
        socket.close();
    }
    

    public static int userInput(){
        Scanner scan = new Scanner(System.in);
        int input = scan.nextInt();
        scan.close();
        return input;
    }
}