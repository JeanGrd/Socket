package org.socket.exercice8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurLogger implements Runnable {

    @Override
    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(3244);
            System.out.println("Serveur Log AS en attente de connexion...");

            while (true) {
                // Attente de connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté: " + clientSocket);

                // Création des streams d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("Requête reçue: " + request);
                    System.out.println("Log JSON bien traité");

                }

                in.close();
                clientSocket.close();
                System.out.println("Client déconnecté: " + clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
