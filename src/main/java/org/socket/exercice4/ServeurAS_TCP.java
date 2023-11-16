package org.socket.exercice4;
import org.socket.LA;
import org.socket.ListeAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurAS_TCP implements Runnable {

    private LA listeAuth;

    public ServeurAS_TCP(LA listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void run() {
        try {

            // Création d'un socket serveur sur le port 28414
            ServerSocket serverSocket = new ServerSocket(28414);

            System.out.println("Serveur AS en attente de connexion...");

            while (true) {
                // Attente de connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté: " + clientSocket);

                // Création des streams d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Lecture de la requête du client
                String request = in.readLine();
                System.out.println("Requête reçue: " + request);

                // Traitement de la requête
                String[] parts = request.split(" ");
                String command = parts[0];
                String response = "ERROR Unknown command";
                System.out.println(command);

                if ("CHK".equals(command)) {
                    String login = parts[1];
                    String passwd = parts[2];
                    if (listeAuth.tester(login, passwd)) {
                        response = "GOOD";
                    } else {
                        response = "BAD";
                    }
                } else if ("ADD".equals(command)) {
                    String login = parts[1];
                    String passwd = parts[2];
                    if (listeAuth.creer(login, passwd)) {
                        response = "DONE";
                    } else {
                        response = "ERROR User already exists";
                    }
                } // Vous pouvez ajouter les autres commandes ici
                // Envoi de la réponse au client
                out.println(response);
                System.out.println("Réponse envoyée: " + response);

                // Fermeture des ressources
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
