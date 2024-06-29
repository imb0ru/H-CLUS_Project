## MANUALE UTENTE H-CLUS

# INDICE

---

[1.Introduzione](#1-introduzione)
- [1.2 Obiettivi](#12-obiettivi-del-progetto)

[2. Istruzioni per l'installazione](#2-Istruzioni-per-l'installazione)

[3. Istruzioni per l'uso](#3-istruzioni-per-l'uso)

[4. Modello UML](#4-modello-uml)

[5. Riepilogo dei test](#5-riepilogo-dei-test)

[6. Java doc](#6-Javadoc)

[7. Contatti](#7-Contatti)

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

---

# **3. Istruzioni per l'uso**

---
### Schermata di avvio del programma:

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Avvio.jpeg" alt="">
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
    <img src="./Istruzioni d'uso foto/Tabella inesistente.jpeg" alt="">
</p>
Il programma ci dirà che la tabella non è stata trovata e ci dara la possibilità di inserire un'altro nome.

---
### Inserimento nome tabella:
Inserimento del nome della tabella (nel nostro caso exampletab):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Inserimento tabella.jpeg" alt="">
</p>
Dopo aver inserito il nome della tabella ci compare a video una scelta.

---
### Scelta diversa da 1 o 2:
Inserimento di una scelta diversa da 1 o 2 (nel nostro caso 3):
<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Scelta diversa.jpeg" alt="">
</p>

Come notiamo, inserendo una scelta diversa da 1 o 2 ci stamperà a video un messaggio di errore.
Con scritto "Scelta non valida e andrà a chiederci di nuovo la scelta poichè non è stata valida.

---
### Scelta 1: 

Se inseriamo 1 avremo scelto il caricamento del dendrogramma da file: 

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Scelta 1.jpeg" alt="">
</p>
Ci verrà richiesto di inserire il nome dell'archivio con la relativa estensione

---
### Inserimento nome file errato:
Inserimento del nome del file (nel nostro caso error, un file che non esiste):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/File error.jpeg" alt="">
</p>
Il programma ci dirà che il file non è stato trovare e terminerà l'esecuzione.

---
### Inserimento file corretto:
Inserimento del nome del file (nel nostro caso example.txt):
<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/File corretto.jpeg" alt="">
</p>
Il programma ci darà a video il dendrogramma e terminerà l'esecuzione.

---
### Scelta 2:
Se inseriamo 2 avremo scelto l'opzione apprendi dendrogramma dal database:

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Scelta 2.jpeg" alt="">
</p>

Ci comparirà successivamente l'inserimento della profondità del dendrogramma desiderata

---
### Inserimento profondità errato:
Inserimento di una profondità errata (nel nostro caso 0):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/prof errata.jpeg" alt="">
</p>

il programam ci chiederà comunque le opzioni per calcolare la distanza ma una volta inserita ci dirà che la profondità è errata e il programma termina l'esecuzione.

---
### Inserimento profondità corretto:
Se inseriamo una profondità corretta (da 1 a 5):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/prof corretta.jpeg" alt="">
</p>

Ci verrà chiesto il tipo di distanza desiderata.

---
### Inserimento distanza errata:
Se inseriamo una scelta errata (nel nostro caso 3):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Distanza errata.jpeg" alt="">
</p>

In questo caso ci dirà che la scelta non è valida e ci chiederà di nuovo la scelta.

---
### Inserimento distanza 1:
Se inseriamo una scelta corretta (1 o 2), in questo caso 1:

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Scelta 1d.jpeg" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso example.txt):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Nome archivios.jpeg" alt="">
</p>

Il programma termina.

---
### Inserimento distanza 2:
Se inseriamo una scelta corretta (1 o 2), in questo caso 2:

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Scelta 2d.jpeg" alt="">
</p>

Ci darà a video il dendrogramma e ci chiederà il nome dell'archivio in cui salvarlo.

---
### Inserimento nome file:
Inserimento del nome del file (nel nostro caso examplea.txt):

<p style="text-align: center;">
    <img src="./Istruzioni d'uso foto/Nome archivio2.jpeg" alt="">
</p>
Il programma termina.

---

# 4. Modello UML

---

# **5. Riepilogo dei test**

---

# 6. JavaDoc

---

# 7. Contatti

Per ulteriori informazioni, contattare:

- **Ferrara Marco**: m.ferrara62@studenti.uniba.it;
- **Appice Lorenzo**: l.appice@studenti.uniba.it;
- **De giglio Pietro**: p.degiglio5@studenti.uniba.it;

---