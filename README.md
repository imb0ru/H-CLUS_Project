## MANUALE UTENTE H-CLUS

# INDICE

---

[1.Introduzione](#1-introduzione)
- [1.2 Obiettivi](#12-obiettivi-del-progetto)

[2. Istruzioni per l'installazione](#2-Istruzioni-per-l'installazione)
- [2.1 Configurazione del server H-CLUS](#21-configurazione-del-server-h-clus)
- [2.2 Configurazione del client H-CLUS](#22-configurazione-del-client-h-clus)

[3. Istruzioni per l'uso](#3-istruzioni-per-l'uso)

[4. Modello UML](#4-modello-uml)

[5. Riepilogo dei test](#5-riepilogo-dei-test)

[6. Java doc](#6-javadoc)

[7. Contatti](#7-contatti)

---

# **1. Introduzione**

Il progetto H-CLUS, sviluppato nell'ambito del corso di Metodi Avanzati di Programmazione (Anno Accademico 2023-24), si propone di implementare un sistema client-server per il clustering gerarchico di dati.

## 1.2 Obiettivi del Progetto

L'obiettivo principale del progetto è la realizzazione di un sistema denominato "H-CLUS", il quale include le seguenti componenti:
- **Server**: Modulo responsabile dell'applicazione di algoritmi di data mining per la scoperta di dendrogrammi di cluster di dati utilizzando tecniche di clustering agglomerativo.
- **Client**: Un'applicazione Java che consente agli utenti di accedere ai servizi di scoperta remota offerti dal server e visualizzare i cluster di dati identificati.

---

# **2. Istruzioni per l'installazione**

Per installare il software H-CLUS, è necessario seguire i seguenti passaggi:

1. **Scaricare e Installare Java Development Kit (JDK):**
   - Assicurarsi di avere installato il JDK versione 11 o successiva. È possibile scaricarlo dal sito ufficiale di Oracle o utilizzare un'alternativa come OpenJDK.
     - [Scarica JDK da Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
     - [Scarica OpenJDK](https://openjdk.java.net/install/)

2. **Scaricare e Installare MySQL:**
   - Installare MySQL Community Server e MySQL Workbench. È possibile scaricarlo dal sito ufficiale di MySQL.
     - [Scarica MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
     - [Scarica MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

## **2.1 Configurazione del server H-CLUS**
   - Nella directory principale del progetto, individuare il file `server_setup.bat` (o il nome del file batch fornito) e seguire questi passaggi per eseguirlo:

### Esecuzione del file batch `server_setup.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `server_setup.bat` nella directory del progetto.
  - Fare doppio clic sul file `server_setup.bat` per eseguirlo.

- **Metodo 2: Da terminale**
  - Aprire un terminale o prompt dei comandi.
  - Navigare alla directory dove si trova il file `server_setup.bat`.
  - Eseguire il comando:
    ```sh
    server_setup.bat
    ```

### Descrizione delle funzionalità del file batch

Il file batch eseguirà i seguenti passaggi:

1. **Configurazione delle variabili di progetto:** Imposta variabili per i percorsi dei file e delle directory utilizzate nel progetto.
2. **Esecuzione del file SQL:** Esegue uno script SQL per configurare il database utilizzando MySQL.
3. **Compilazione del server:** Compila i file sorgente Java presenti nella directory `src` e li posiziona nella directory `out`.
4. **Creazione del file JAR:** Crea un file JAR eseguibile che include il server e tutte le dipendenze necessarie.
5. **Generazione della documentazione Javadoc:** Genera la documentazione Javadoc per il progetto.
6. **Esecuzione del server:** Esegue il file JAR del server con la porta specificata.

Durante l'esecuzione, il file batch fornirà messaggi di feedback per indicare lo stato di ciascun passaggio e segnalerà eventuali errori incontrati.

## **2.2 Configurazione del client H-CLUS**
   - Nella directory principale del progetto, individuare il file `client_setup.bat` (o il nome del file batch fornito) e seguire questi passaggi per eseguirlo:

### Esecuzione del file batch `client_setup.bat`

- **Metodo 1: Doppio clic**
  - Individuare il file `client_setup.bat` nella directory del progetto.
  - Fare doppio clic sul file `client_setup.bat` per eseguirlo.

- **Metodo 2: Da terminale**
  - Aprire un terminale o prompt dei comandi.
  - Navigare alla directory dove si trova il file `client_setup.bat`.
  - Eseguire il comando:
    ```sh
    client_setup.bat
    ```

### Descrizione delle funzionalità del file batch

Il file batch eseguirà i seguenti passaggi:

1. **Configurazione delle variabili di progetto:** Imposta variabili per i percorsi dei file e delle directory utilizzate nel progetto.
2. **Compilazione del client:** Compila i file sorgente Java presenti nella directory `src` e li posiziona nella directory `out`.
3. **Creazione del file JAR:** Crea un file JAR eseguibile che include il client e tutte le dipendenze necessarie.
4. **Generazione della documentazione Javadoc:** Genera la documentazione Javadoc per il progetto.
5. **Esecuzione del client:** Esegue il file JAR del client con l'indirizzo IP e la porta specificati.

Durante l'esecuzione, il file batch fornirà messaggi di feedback per indicare lo stato di ciascun passaggio e segnalerà eventuali errori incontrati.

---

# **3. Istruzioni per l'uso**

### Schermata di avvio del programma:

<p style="text-align: center;">
    <img src="./assets/Avvio.jpeg" alt="">
</p>
addr indica l'indirizzo ip a cui si è connessi.

Socket invece ha 3 campi: 
- addr: nome del dispotivo/indirizzo locale
- Port: la porta del server
- localport: la porta locale

Nome tabella: Inserire il nome della tabella su cui si vuole lavorare, in questo caso exampletab.

---
### Inserimento tabella errato:
Inserimento di un nome di tabella errato (nel nostro caso prova):

<p style="text-align: center;">
    <img src="./assets/Tabella inesistente.jpeg" alt="">
</p>
Il programma ci dirà che la tabella non è stata trovata e ci dara la possibilità di inserire un'altro nome.

---
### Inserimento nome tabella:
Inserimento del nome della tabella (nel nostro caso exampletab):

<p style="text-align: center;">
    <img src="./assets/Inserimento tabella.jpeg" alt="">
</p>
Dopo aver inserito il nome della tabella ci compare a video una scelta.

---
### Scelta diversa da 1 o 2:
Inserimento di una scelta diversa da 1 o 2 (nel nostro caso 3):
<p style="text-align: center;">
    <img src="./assets/Scelta diversa.jpeg" alt="">
</p>

Come notiamo, inserendo una scelta diversa da 1 o 2 ci stamperà a video un messaggio di errore.
Con scritto "Scelta non valida e andrà a chiederci di nuovo la scelta poichè non è stata valida.

---
### Scelta 1: 

Se inseriamo 1 avremo scelto il caricamento del dendrogramma da file: 

<p style="text-align: center;">
    <img src="./assets/Scelta 1.jpeg" alt="">
</p>
Ci verrà richiesto di inserire il nome dell'archivio con la relativa estensione

---
### Inserimento nome file errato:
Inserimento del nome del file (nel nostro caso error, un file che non esiste):

<p style="text-align: center;">
    <img src="./assets/File error.jpeg" alt="">
</p>
Il programma ci dirà che il file non è stato trovare e terminerà l'esecuzione.

---
### Inserimento file corretto:
Inserimento del nome del file (nel nostro caso example.txt):
<p style="text-align: center;">
    <img src="./assets/File corretto.jpeg" alt="">
</p>
Il programma ci darà a video il dendrogramma e terminerà l'esecuzione.

---
### Scelta 2:
Se inseriamo 2 avremo scelto l'opzione apprendi dendrogramma dal database:

<p style="text-align: center;">
    <img src="./assets/Scelta 2.jpeg" alt="">
</p>

Ci comparirà successivamente l'inserimento della profondità del dendrogramma desiderata

---
### Inserimento profondità errato:
Inserimento di una profondità errata (nel nostro caso 0):

<p style="text-align: center;">
    <img src="./assets/prof errata.jpeg" alt="">
</p>

il programam ci chiederà comunque le opzioni per calcolare la distanza ma una volta inserita ci dirà che la profondità è errata e il programma termina l'esecuzione.

---
### Inserimento profondità corretto:
Se inseriamo una profondità corretta (da 1 a 5):

<p style="text-align: center;">
    <img src="./assets/prof corretta.jpeg" alt="">
</p>

Ci verrà chiesto il tipo di distanza desiderata.

---
### Inserimento distanza errata:
Se inseriamo una scelta errata (nel nostro caso 3):

<p style="text-align: center;">
    <img src="./assets/Distanza errata.jpeg" alt="">
</p>

In questo caso ci dirà che la scelta non è valida e ci chiederà di nuovo la scelta.

---
### Inserimento distanza 1:
Se inseriamo una scelta corretta (1 o 2), in questo caso 1:

<p style="text-align: center;">
    <img src="./assets/Scelta 1d.jpeg" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso example.txt):

<p style="text-align: center;">
    <img src="./assets/Nome archivios.jpeg" alt="">
</p>

Il programma termina.

---
### Inserimento distanza 2:
Se inseriamo una scelta corretta (1 o 2), in questo caso 2:

<p style="text-align: center;">
    <img src="./assets/Scelta 2d.jpeg" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso examplea.txt):

<p style="text-align: center;">
    <img src="./assets/Nome archivio2.jpeg" alt="">
</p>
Il programma termina.

---

# 4. Modello UML


# **5. Riepilogo dei test**


# 6. JavaDoc

Per accedere alla documentazione JavaDoc del progetto, fare clic sui link sottostanti:

- [JavaDoc del Client](ProgettoBase/Client/client_javadoc/index.html)
- [JavaDoc del Server](ProgettoBase/Server/server_javadoc/index.html)


# 7. Contatti

Per ulteriori informazioni, contattare:

- **Ferrara Marco**: m.ferrara62@studenti.uniba.it;
- **Appice Lorenzo**: l.appice@studenti.uniba.it;
- **De giglio Pietro**: p.degiglio5@studenti.uniba.it;

