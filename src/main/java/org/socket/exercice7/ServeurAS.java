package org.socket.exercice7;
import org.socket.LA;
import org.socket.ListeAuth_2;

public class ServeurAS {

    public static void main(String[] args) {

        LA listeAuth = new ListeAuth_2("target/hello");

        Thread udpThread = new Thread(new ServeurAS_UDP(listeAuth));
        Thread tcpThread = new Thread(new ServeurAS_TCP(listeAuth));
        Thread tcpmThread = new Thread(new ServeurAS_TCPM(listeAuth));

        udpThread.start();
        tcpThread.start();
        tcpmThread.start();
    }
}
