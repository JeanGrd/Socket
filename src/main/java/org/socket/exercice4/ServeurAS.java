package org.socket.exercice4;

import org.socket.LA;
import org.socket.ListeAuth;
import org.socket.ListeAuth_2;
import org.socket.exercice7.ServeurAS_TCP;
import org.socket.exercice7.ServeurAS_UDP;

public class ServeurAS {

    public static void main(String[] args) {

        LA listeAuth = new ListeAuth_2("target/hello");

        Thread udpThread = new Thread(new ServeurAS_UDP(listeAuth));
        Thread tcpThread = new Thread(new ServeurAS_TCP(listeAuth));

        udpThread.start();
        tcpThread.start();
    }
}
