package org.socket.exercice8;
import org.socket.JsonLogger;
import org.socket.LA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurAS_TCPM implements Runnable {

    private LA listeAuth;

    public ServeurAS_TCPM(LA listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(28415);
            System.out.println("Serveur AS en attente de connexion...");

            while (true) {
                // Attente de connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté: " + clientSocket);

                // Création des streams d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("Requête reçue: " + request);

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

                    JsonLogger.log("localhost", 28415,"TCP",command, login, response);

                    out.println(response);
                    System.out.println("Réponse envoyée: " + response);

                }

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
