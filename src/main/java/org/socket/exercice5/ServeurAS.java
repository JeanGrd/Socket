package org.socket.exercice5;

import org.socket.ListeAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurAS {

    public static void main(String[] args) {
        try {
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

                // Gestion des requêtes du client
                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("Requête reçue: " + request);

                    // Traitement de la requête
                    String[] parts = request.split(" ");
                    String command = parts[0];
                    String response = "ERROR Unknown command";

                    String login = parts[1];
                    String passwd = parts[2];

                    if ("CHK".equals(command)) {
                        if (listeAuth.tester(login, passwd)) {
                            response = "GOOD";
                        } else {
                            response = "BAD";
                        }
                    } else if ("ADD".equals(command)) {
                        if (listeAuth.creer(login, passwd)) {
                            response = "DONE";
                        } else {
                            response = "ERROR User already exists";
                        }
                    } else if ("DEL".equals(command)) {
                        if (listeAuth.supprimer(login, passwd)) {
                            response = "DONE";
                        } else {
                            response = "ERROR User not exists";
                        }
                    } else if ("MOD".equals(command)) {
                        if (listeAuth.mettreAJour(login, passwd)) {
                            response = "DONE";
                        } else {
                            response = "ERROR User not exists";
                        }
                    }

                    // Envoi de la réponse au client
                    out.println(response);
                    System.out.println("Réponse envoyée: " + response);
                }

                // Fermeture des ressources pour ce client
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Client déconnecté: " + clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
