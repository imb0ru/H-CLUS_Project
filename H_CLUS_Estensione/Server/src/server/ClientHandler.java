package server;

import clustering.HierachicalClusterMiner;
import clustering.InvalidClustersNumberException;
import clustering.InvalidDepthException;
import data.Data;
import data.InvalidSizeException;
import data.NoDataException;
import distance.AverageLinkDistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.telegram.telegrambots.meta.TelegramBotsApi;

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Data data;
    TelegramBotsApi bot;

    public ClientHandler(Socket socket, TelegramBotsApi bot) throws IOException {
        this.clientSocket = socket;
        this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        this.bot = bot;
        this.start();
    }

    public void run() {
        try {
            while(true) {
                Object request = this.in.readObject();
                if (request instanceof Integer) {
                    int requestType = (Integer)request;
                    switch (requestType) {
                        case 0:
                            this.handleLoadData();
                            break;
                        case 1:
                            this.handleClustering();
                            break;
                        case 2:
                            this.handleLoadDendrogramFromFile();
                            break;
                        default:
                            this.out.writeObject("Tipo di richiesta non valido");
                    }
                } else {
                    this.out.writeObject("Tipo di richiesta non valido");
                }
            }
        } catch (IOException var12) {
            System.out.println("Disconnessione client: " + String.valueOf(this.clientSocket));
        } catch (ClassNotFoundException var13) {
            ClassNotFoundException e = var13;
            System.out.println(e.getMessage());
        } finally {
            try {
                this.clientSocket.close();
                this.out.close();
                this.in.close();
            } catch (IOException var11) {
                System.err.println("Errore nella chiusura del socket o degli ObjectStream");
            }

        }

    }

    private void handleLoadData() throws IOException, ClassNotFoundException {
        String tableName = (String)this.in.readObject();

        try {
            this.data = new Data(tableName);
            this.out.writeObject("OK");
        } catch (NoDataException var3) {
            NoDataException e = var3;
            this.out.writeObject(e.getMessage());
        }

    }

    private void handleClustering() throws IOException, ClassNotFoundException {
        if (this.data == null) {
            this.out.writeObject("Dati non caricati");
        } else {
            int depth = (Integer)this.in.readObject();
            int distanceType = (Integer)this.in.readObject();

            try {
                HierachicalClusterMiner clustering = new HierachicalClusterMiner(depth);
                ClusterDistance distance = distanceType == 1 ? new SingleLinkDistance() : new AverageLinkDistance();
                clustering.mine(this.data, (ClusterDistance)distance);
                this.out.writeObject("OK");
                this.out.writeObject(clustering.toString(this.data));
                String fileName = (String)this.in.readObject();
                clustering.salva(fileName);
                this.out.writeObject("OK");
                this.out.writeObject("Salvataggio effettuato con successo!");
            } catch (InvalidClustersNumberException | IOException | InvalidDepthException | InvalidSizeException var6) {
                Exception e = var6;
                this.out.writeObject(e.getMessage());
            }

        }
    }

    private void handleLoadDendrogramFromFile() throws IOException, ClassNotFoundException {
        String fileName = (String)this.in.readObject();

        try {
            HierachicalClusterMiner clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(fileName);
            if (this.data == null) {
                this.out.writeObject("Dati non caricati");
                return;
            }

            if (clustering.getDepth() > this.data.getNumberOfExample()) {
                this.out.writeObject("Numero di esempi maggiore della profondità del dendrogramma!");
            } else {
                this.out.writeObject("OK");
                this.out.writeObject(clustering.toString(this.data));
            }
        } catch (FileNotFoundException var3) {
            FileNotFoundException e = var3;
            this.out.writeObject("File non trovato: " + e.getMessage());
        } catch (ClassNotFoundException | InvalidDepthException | IOException var4) {
            Exception e = var4;
            this.out.writeObject(e.getMessage());
        }

    }
}
