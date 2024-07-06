package server;

import bot.TelegramBot;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Gestisce il server che accetta connessioni dai client e avvia istanze di ServerOneClient.
 * Configura anche e registra un bot Telegram.
 */
public class MultiServer {
    private final int PORT;
    private static MultiServer singleton = null;
    private static TelegramBotsApi bot;


    /**
     * Costruttore privato per inizializzare il server e avviare il ciclo di accettazione delle connessioni.
     *
     * @param port La porta sulla quale il server ascolta le connessioni dei client.
     */
    private MultiServer(int port) {
        this.PORT = port;
        this.run();
    }

    /**
     * Crea un'istanza singleton di  MultiServer se non esiste già.
     * Configura il bot Telegram e registra un'istanza di TelegramBot.
     *
     * @param token Il token per autenticare il bot su Telegram.
     * @param port  La porta sulla quale il server ascolta le connessioni dei client.
     */
    public static void instanceMultiServer(String token, int port) {
        if (singleton == null) {
            try {
                bot = new TelegramBotsApi(DefaultBotSession.class);
                bot.registerBot(new TelegramBot(token, "localhost", port));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            singleton = new MultiServer(port);
        }

    }

    /**
     * Avvia il server e accetta le connessioni dai client.
     * Per ogni connessione, crea un'istanza di ServerOneClient per gestirla.
     */
    private void run() {
        try {
            ServerSocket s = new ServerSocket(this.PORT);
            try {
                System.out.println("Started: " + s);

                while(true) {
                    Socket socket = s.accept();
                    System.out.println("Connessione client: " + socket);

                    try {
                        new ServerOneClient(socket, bot);
                    } catch (IOException e) {
                        System.out.println("Errore nella creazione del socket: " + socket);
                        socket.close();
                    }
                }
            } catch (Throwable e) {
                if (s != null) {
                    try {
                        s.close();
                    } catch (Throwable e1) {
                        e.addSuppressed(e1);
                    }
                }

                throw e;
            }
        } catch (IOException var8) {
            System.out.println("Errore...");
        }
    }
}
