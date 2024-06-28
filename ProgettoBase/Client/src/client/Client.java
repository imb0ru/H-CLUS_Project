package client;

import utils.Keyboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe che rappresenta il client
 * che si connette al server
 * per richiedere la costruzione di un dendrogramma
 */
public class Client {
    /** Stream di output per inviare oggetti al server */
    private ObjectOutputStream out;
    /** Stream di input per ricevere oggetti dal server */
    private ObjectInputStream in ;
    /** Socket per la connessione al server */
    private Socket socket;

    /**
     * Costruttore del client
     * @param ip indirizzo ip del server
     * @param port porta del server
     * @throws IOException
     */
    public Client(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        try {
            loadDataOnServer();
            int scelta = menu();
            if(scelta==1)
                loadDedrogramFromFileOnServer();
            else
                mineDendrogramOnServer();

            closeConnection();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo per la scelta dell'operazione da eseguire
     * @return la scelta dell'utente
     */
    private int menu(){
        int answer;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Dendrogramma da File");
            System.out.println("(2) Apprendi Dendrogramma da Database");
            System.out.print("Risposta:");
            answer= Keyboard.readInt();

            if(answer<=0 || answer>2)
                System.out.println("Scelta non valida");
        }
        while(answer<=0 || answer>2);
        return answer;
    }

    /**
     * Metodo per caricare i dati sul server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadDataOnServer() throws IOException, ClassNotFoundException {
        boolean flag=false;
        do {
            System.out.println("Nome tabella:");
            String tableName=Keyboard.readString();
            out.writeObject(0);
            out.writeObject(tableName);
            String risposta=(String)(in.readObject());
            if(risposta.equals("OK"))
                flag=true;
            else System.out.println(risposta);

        }while(!flag);
    }

    /**
     * Metodo per caricare il dendrogramma da file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadDedrogramFromFileOnServer() throws IOException, ClassNotFoundException {
        System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
        String fileName=Keyboard.readString();

        out.writeObject(2);
        out.writeObject(fileName);
        String risposta= (String) (in.readObject());
        if(risposta.equals("OK"))
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }

    /**
     * Metodo per apprendere il dendrogramma dal database
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void mineDendrogramOnServer() throws IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.println("Introdurre la profondita'  del dendrogramma");
        int depth=Keyboard.readInt();
        out.writeObject(depth);
        int dType=-1;
        do {
            System.out.println("Distanza: single-link (1), average-link (2):");
            dType=Keyboard.readInt();
            if(dType<=0 || dType>2)
                System.out.println("Scelta non valida");
        }while (dType<=0 || dType>2);
        out.writeObject(dType);

        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")) {
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
            System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
            String fileName=Keyboard.readString();
            out.writeObject(fileName);
        }
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }

    /**
     * Metodo per chiudere la connessione
     */
    public void closeConnection() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
