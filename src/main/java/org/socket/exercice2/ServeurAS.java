package org.socket.exercice2;

import org.socket.ListeAuth;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServeurAS {

    public static void main(String[] args) throws Exception {
        // Initialisation du service de liste d'authentification
        ListeAuth listeAuth = new ListeAuth();

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
            }

            // Vous pouvez ajouter les autres commandes ici
            // Envoi de la réponse au client
            out.println(response);
            System.out.println("Réponse envoyée: " + response);

            // Fermeture des ressources
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
