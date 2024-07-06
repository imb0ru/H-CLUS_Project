package bot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String serverIp;
    private final int serverPort;
    private final Map<String, ClientSession> userSessions = new HashMap<>();  //con final non rischiamo di reassegnarla per sbaglio

    public TelegramBot(String botToken, String serverIp, int serverPort) {
        this.botToken = botToken;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public String getBotUsername() {
        return "HCLUS_Bot";
    }

    public String getBotToken() {
        return this.botToken;
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String receivedMessage = update.getMessage().getText();

            try {
                this.handleMessage(chatId, receivedMessage);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleMessage(String chatId, String receivedMessage) throws IOException, ClassNotFoundException {
        ClientSession session = this.getSession(chatId);
        if (session.state == null) {
            session.state = "START";
        }

        switch (session.state) {
            case "START":
                if (receivedMessage.equals("/start")) {
                    this.sendMessage(chatId, "Benvenuto! Io sono H-CLUS_Bot, il tuo assistente per la costruzione di dendrogrammi. Utilizzo il metodo gerarchico agglomerativo per costruire il dendrogramma.");
                    String var10002 = String.valueOf(InetAddress.getByName(this.serverIp));
                    this.sendMessage(chatId, "Sei connesso al socket " + var10002 + ":" + this.serverPort);
                    session.out.writeObject(0);
                    this.sendMessage(chatId, "Inserisci il nome della tabella del database da caricare,scegliere uno tra i seguenti:\n- exampletab;");
                    session.state = "LOAD_DATA";
                }
                break;
            case "MENU":
                if (receivedMessage.equals("1")) {
                    session.out.writeObject(2);
                    this.sendMessage(chatId, "Inserisci il nome dell'archivio con estensione: \nnomefile.(txt, csv, json, xml,dat, bin, ser)");
                    session.state = "LOAD_FILE";
                } else if (receivedMessage.equals("2")) {
                    session.out.writeObject(1);
                    this.sendMessage(chatId, "Inserisci la profondità del dendrogramma (da 1 a 5):");
                    session.state = "ENTER_DEPTH";
                } else {
                    this.sendMessage(chatId, "Scelta non valida. Scegli una opzione:\n1. Carica Dendrogramma da File\n2. Apprendi Dendrogramma da Database");
                    session.state = "MENU";
                }
                break;
            case "LOAD_FILE":
                this.handleLoadDendrogramFromFile(chatId, receivedMessage);
                break;
            case "LOAD_DATA":
                this.handleLoadData(chatId, receivedMessage);
                break;
            case "ENTER_DEPTH":
                this.handleDepth(chatId, receivedMessage);
                break;
            case "ENTER_DISTANCE":
                this.handleDistance(chatId, receivedMessage);
                break;
            case "SAVE_FILE":
                this.handleSaveFile(chatId, receivedMessage);
                break;
            default:
                this.sendMessage(chatId, "Comando non valido.");
        }

    }


    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void handleLoadDendrogramFromFile(String chatId, String fileName) throws IOException, ClassNotFoundException {
        ClientSession session = this.getSession(chatId);
        session.out.writeObject(fileName);
        String risposta = (String) session.in.readObject();
        if (risposta.equals("OK")) {
            this.sendMessage(chatId, (String) session.in.readObject());
            session.state = "START";
        } else {
            this.sendMessage(chatId, risposta);
            this.sendMessage(chatId, "Inserisci il nome dell'archivio con estensione: \nnomefile.(txt, csv, json, xml,dat, bin, ser)");
            session.out.writeObject(2);
            session.state = "LOAD_FILE";
        }

    }

    private void handleLoadData(String chatId, String tableName) throws IOException, ClassNotFoundException {
        ClientSession session = this.getSession(chatId);
        session.out.writeObject(tableName);
        String risposta = (String) session.in.readObject();
        if (risposta.equals("OK")) {
            this.sendMessage(chatId, "Scegli una opzione:\n1. Carica Dendrogramma da File\n2. Apprendi Dendrogramma da Database");
            session.state = "MENU";
        } else {
            this.sendMessage(chatId, risposta);
            this.sendMessage(chatId, "Inserisci il nome della tabella del database da caricare,scegliere uno tra i seguenti:\n- exampletab;");
            session.out.writeObject(0);
            session.state = "LOAD_DATA";
        }

    }

    private void handleDepth(String chatId, String depthStr) throws IOException {
        int depth = Integer.parseInt(depthStr);
        ClientSession session = this.getSession(chatId);
        session.out.writeObject(depth);
        this.sendMessage(chatId, "Scegli il tipo di distanza:\n1. single-link\n2. average-link");
        session.state = "ENTER_DISTANCE";
    }

    private void handleDistance(String chatId, String distanceStr) throws IOException, ClassNotFoundException {
        int distance;
        try {
            distance = Integer.parseInt(distanceStr);
        } catch (NumberFormatException e) {
            this.sendMessage(chatId, "Scelta non valida. Scegli una opzione:\n1. Single-link\n2. Average-link");
            return;
        }
        if (distance != 1 && distance != 2) {
            this.sendMessage(chatId, "Scelta non valida. Scegli una opzione:\n1. Single-link\n2. Average-link");
            return;
        }
        ClientSession session = this.getSession(chatId);
        session.out.writeObject(distance);
        String risposta = (String) session.in.readObject();
        if (risposta.equals("OK")) {
            this.sendMessage(chatId, (String) session.in.readObject());
            this.sendMessage(chatId, "Inserisci il nome dell'archivio con estensione: \nnomefile.(txt, csv, json, xml,dat, bin, ser)");
            session.state = "SAVE_FILE";
        } else {
            this.sendMessage(chatId, risposta);
            session.out.writeObject(1);
            this.sendMessage(chatId, "Inserisci la profondità del dendrogramma (da 1 a 5):");
            session.state = "ENTER_DEPTH";
        }
    }

    private void handleSaveFile(String chatId, String fileName) throws IOException, ClassNotFoundException {
        ClientSession session = this.getSession(chatId);
        session.out.writeObject(fileName);
        String risposta = (String) session.in.readObject();
        if (risposta.equals("OK")) {
            this.sendMessage(chatId, (String) session.in.readObject());
            this.sendMessage(chatId, "Il mio lavoro qui è finito, digita /start per iniziare una nuova sessione.");
            session.state = "START";
        } else {
            this.sendMessage(chatId, risposta);
            this.sendMessage(chatId, "Inserisci il nome dell'archivio con estensione: \nnomefile.(txt, csv, json, xml,dat, bin, ser):");
            session.state = "SAVE_FILE";
        }

    }

    private boolean isValidFileName(String fileName) {
        String regex = "^[\\w,\\s-]+\\.(txt|csv|json|xml|dat|bin|ser)$";
        return Pattern.matches(regex, fileName);
    }

    private ClientSession getSession(String chatId) throws IOException {
        if (!this.userSessions.containsKey(chatId)) {
            ClientSession session = new ClientSession(this.serverIp, this.serverPort);
            this.userSessions.put(chatId, session);
        }

        return this.userSessions.get(chatId);
    }

    static class ClientSession {
        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
        String state;

        public ClientSession(String serverIp, int serverPort) throws IOException {
            this.socket = new Socket(InetAddress.getByName(serverIp), serverPort);
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());
            this.state = null;
        }
    }
}
