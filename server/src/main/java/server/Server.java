package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    protected static String[] filenames = {
        "bluesmurfcat.jpg",
        "johnpork.jpg",
        "pandatrueno.jpg",
        "scenic.jpg",
        "forzaviola.jpg"
    };

    protected static ObjectOutputStream writer;
    protected static BufferedReader clientIn;

    public static void main(String[] args) throws IOException {
        
        ServerSocket server = new ServerSocket(6789);
        System.out.println("Server in ascolto sulla porta 6789...");

        
        Socket socket = server.accept();
        System.out.println("Connessione accettata.");

        
        writer = new ObjectOutputStream(socket.getOutputStream());
        clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.writeObject(list());

        int option = clientIn.read();



/* 
        if(option > -1 && option < 5){
            downloadFile(filenames[option]);
            System.out.println("File scaricato.");
        }else{
            System.out.println("Scelta non valida.");
        }
*/
        downloadFile(filenames[option]);
        System.out.println("File scaricato.");
            
        //chiusura stream, socket e server
        writer.close();
        socket.close();
        server.close();
    }

    public static void downloadFile(String filename) throws FileNotFoundException, IOException{
        //leggo i byte dal file e li scrivo sul socket
        FileInputStream reader = new FileInputStream("images\\"+filename);
        byte[] buffer = new byte[1024];
        int lengthRead;
        while ((lengthRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, lengthRead);
                //writer.flush();
        }

        
        
        File file = new File(filename);

        FileOutputStream toFile;

            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            try{
                toFile = new FileOutputStream(file);
                toFile.close();
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

        reader.close();
    }
    public static String list(){
        return "Benvenuto! Per iniziare, seleziona un file da scaricare: \n"+
        "[0]. bluesmurfcat.jpg, "+'\n'+
        "[1]. johnpork.jpg, "+'\n'+
        "[2]. pandatrueno.jpg, "+'\n'+
        "[3]. scenic.jpg, "+'\n'+
        "[4]. forzaviola.jpg, "+'\n'+
        "Seleziona: ";
    }

 

    
}
