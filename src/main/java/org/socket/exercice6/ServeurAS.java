package org.socket.exercice6;

import org.socket.LA;
import org.socket.ListeAuth_2;
import org.socket.exercice8.ServeurAS_TCP;
import org.socket.exercice8.ServeurAS_UDP;

public class ServeurAS {

    public static void main(String[] args) {

        LA listeAuth = new ListeAuth_2("target/hello");

        Thread udpThread = new Thread(new ServeurAS_UDP(listeAuth));
        Thread tcpThread = new Thread(new ServeurAS_TCP(listeAuth));

        udpThread.start();
        tcpThread.start();
    }
}
