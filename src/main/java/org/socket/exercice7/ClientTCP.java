package org.socket.exercice7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 28414);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("CHK Toto Toto");
        String response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        out.close();
        in.close();
        socket.close();
    }
}
