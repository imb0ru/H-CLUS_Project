package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce il server
 */
public class MultiServer {
    /** La porta del server */
    private final int PORT;
    /** Singleton */
    private static MultiServer singleton = null;

    /**
     * Costruttore di classe, inizializza la porta e invoca run()
     *
     * @param port che indica la porta alla quale connettersi
     */
    private MultiServer(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Metodo che serve per creare un istanza un nuovo Server, specificando
     * la porta in modo tale da rendere Singleton la classe Server.
     * Crea un nuovo server solo 1 volta, quelle successive non potrò più farlo.
     *
     * @param port che indica la porta sulla quale avviare il server
     */
    public static void instanceMultiServer(int port){
        if(singleton == null)
            singleton = new MultiServer(port);
    }

    /**
     * Crea un oggetto istanza della classe ServerSocket che pone in attesa di
     * richiesta di connessioni da parte del client. A ogni nuova richiesta di
     * connessione si crea un istanza ServerOneClient sfruttando cosi il MultiThreading
     */
    private void run() {
        try {
            ServerSocket s = new ServerSocket(PORT);
            try (s) {
                System.out.println("Started: " + s);
                while (true) {
                    Socket socket = s.accept();
                    System.out.println("Connessione client: " + socket);

                    try {
                        new ServerOneClient(socket);
                    } catch (IOException e) {
                        System.out.println("Errore nella creazione del socket: " + socket);
                        socket.close();
                    }
                }
            }
            // ServerSocket, serve se ci fanno un kill del thread dall'esterno
        } catch (IOException e) {
            System.out.println("Errore...");
        }
    }
}

