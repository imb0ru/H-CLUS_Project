package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che rappresenta il client che si connette al server per richiedere la costruzione di un dendrogramma tramite bot Telegram
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String serverIp;
    private final int serverPort;
    private Map<String, ClientSession> userSessions = new HashMap<>();

    public TelegramBot(String botToken, String serverIp, int serverPort) {
        this.botToken = botToken;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    @Override
    public String getBotUsername() {
        return "HCLUS_Bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String receivedMessage = update.getMessage().getText();

            try {
                handleMessage(chatId, receivedMessage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String chatId, String receivedMessage) throws IOException, ClassNotFoundException {
        ClientSession session = getSession(chatId);

        if (session.state == null) {
            session.state = "START";
        }

        switch (session.state) {
            case "START":
                if (receivedMessage.equals("/start")) {
                    sendMessage(chatId, "Benvenuto! Io sono H-CLUS_Bot, il tuo assistente per la costruzione di dendrogrammi. Utilizzo il metodo gerarchico agglomerativo per costruire il dendrogramma.");
                    sendMessage(chatId, "Sei connesso al socket " + InetAddress.getByName(serverIp) + ":" + serverPort);
                    session.out.writeObject(0);
                    sendMessage(chatId, "Inserisci il nome della tabella del database:");
                    session.state = "LOAD_DATA";
                }
                break;
            case "MENU":
                if (receivedMessage.equals("1")) {
                    session.out.writeObject(2);
                    sendMessage(chatId, "Inserire il nome dell'archivio (comprensivo di estensione):");
                    session.state = "LOAD_FILE";
                } else if (receivedMessage.equals("2")) {
                    session.out.writeObject(1);
                    sendMessage(chatId, "Introdurre la profondità del dendrogramma:");
                    session.state = "ENTER_DEPTH";
                } else {
                    sendMessage(chatId, "Scelta non valida. Scegli una opzione:\n1. Carica Dendrogramma da File\n2. Apprendi Dendrogramma da Database");
                    session.state = "MENU";
                }
                break;
            case "LOAD_FILE":
                handleLoadDendrogramFromFile(chatId, receivedMessage);
                break;
            case "LOAD_DATA":
                handleLoadData(chatId, receivedMessage);
                break;
            case "ENTER_DEPTH":
                handleDepth(chatId, receivedMessage);
                break;
            case "ENTER_DISTANCE":
                handleDistance(chatId, receivedMessage);
                break;
            case "SAVE_FILE":
                handleSaveFile(chatId, receivedMessage);
                break;
            default:
                sendMessage(chatId, "Comando non valido.");
                break;
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleLoadDendrogramFromFile(String chatId, String fileName) throws IOException, ClassNotFoundException {
        ClientSession session = getSession(chatId);
        session.out.writeObject(fileName);
        String risposta = (String) (session.in.readObject());
        if (risposta.equals("OK")) {
            sendMessage(chatId, (String) session.in.readObject());
            session.state = "START";
        } else {
            sendMessage(chatId, risposta);
            sendMessage(chatId, "Inserire il nome dell'archivio (comprensivo di estensione):");
            session.state = "LOAD_FILE";
        }
    }

    private void handleLoadData(String chatId, String tableName) throws IOException, ClassNotFoundException {
        ClientSession session = getSession(chatId);
        session.out.writeObject(tableName);
        String risposta = (String) (session.in.readObject());
        if (risposta.equals("OK")) {
            sendMessage(chatId, "Scegli una opzione:\n1. Carica Dendrogramma da File\n2. Apprendi Dendrogramma da Database");
            session.state = "MENU";
        } else {
            sendMessage(chatId, risposta);
            sendMessage(chatId, "Nome tabella:");
            session.state = "LOAD_DATA";
        }
    }

    private void handleDepth(String chatId, String depthStr) throws IOException, ClassNotFoundException {
        int depth = Integer.parseInt(depthStr);
        ClientSession session = getSession(chatId);
        session.out.writeObject(depth);
        sendMessage(chatId, "Distanza: single-link (1), average-link (2):");
        session.state = "ENTER_DISTANCE";
    }

    private void handleDistance(String chatId, String distanceStr) throws IOException, ClassNotFoundException {
        int distance = Integer.parseInt(distanceStr);
        ClientSession session = getSession(chatId);
        session.out.writeObject(distance);
        String risposta = (String) (session.in.readObject());
        if (risposta.equals("OK")) {
            sendMessage(chatId, (String) session.in.readObject());
            sendMessage(chatId, "Inserire il nome dell'archivio (comprensivo di estensione):");
            session.state = "SAVE_FILE";
        } else {
            sendMessage(chatId, risposta);
            sendMessage(chatId, "Introdurre la profondità del dendrogramma:");
            session.state = "ENTER_DEPTH";
        }
    }

    private void handleSaveFile(String chatId, String fileName) throws IOException, ClassNotFoundException {
        ClientSession session = getSession(chatId);
        session.out.writeObject(fileName);
        String risposta = (String) (session.in.readObject());
        if (risposta.equals("OK")) {
            sendMessage(chatId, (String) session.in.readObject());
        } else {
            sendMessage(chatId, risposta);
        }
        session.state = "START";
    }

    private ClientSession getSession(String chatId) throws IOException {
        if (!userSessions.containsKey(chatId)) {
            ClientSession session = new ClientSession(serverIp, serverPort);
            userSessions.put(chatId, session);
        }
        return userSessions.get(chatId);
    }

    static class ClientSession {
        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
        String state;

        public ClientSession(String serverIp, int serverPort) throws IOException {
            this.socket = new Socket(InetAddress.getByName(serverIp), serverPort);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.state = null;
        }
    }
}
