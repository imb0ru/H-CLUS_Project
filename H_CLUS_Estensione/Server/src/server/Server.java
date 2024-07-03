package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import bot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Classe che gestisce il server
 */
public class Server {
    /** La porta del server */
    private final int PORT;
    /** Singleton */
    private static Server singleton = null;
    /** Telegram Bot */
    private static TelegramBotsApi bot;

    /**
     * Costruttore di classe, inizializza la porta e il bot e invoca run()
     *
     * @param port che indica la porta alla quale connettersi
     */
    private Server(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Metodo che serve per creare un istanza un nuovo Server, specificando
     * la porta e il token del bot telegram in modo tale da rendere Singleton la classe Server.
     * Crea un nuovo server solo 1 volta, quelle successive non potrò più farlo.
     *
     * @param port che indica la porta sulla quale avviare il server
     */
    public static void instanceMultiServer(String token, int port){
        if(singleton == null) {
            try {
                 bot = new TelegramBotsApi(DefaultBotSession.class);
                 bot.registerBot(new TelegramBot(token, "localhost", port));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            singleton = new Server(port);
        }
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
                        new ClientHandler(socket, bot);
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

