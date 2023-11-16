package org.socket.exercice6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager {

    public static void main(String[] args) throws Exception {
        // Création d'un socket client vers le serveur AS
        Socket socket = new Socket("localhost", 28414);

        // Création des streams d'entrée et de sortie
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Envoi d'une requête de vérification au serveur
        out.println("CHK Toto Toto");

        // Réception et affichage de la réponse
        String response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Envoi d'une requête de vérification au serveur
        out.println("ADD Anoushka Palea");

        // Réception et affichage de la réponse
        response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Envoi d'une requête de vérification au serveur
        out.println("DEL Anoushka Palea");

        // Réception et affichage de la réponse
        response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Envoi d'une requête de vérification au serveur
        out.println("MOD Toto Titi");


        // Réception et affichage de la réponse
        response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Envoi d'une requête de vérification au serveur
        out.println("CHK Toto Toto");

        // Réception et affichage de la réponse
        response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Envoi d'une requête de vérification au serveur
        out.println("CHK Toto Titi");

        // Réception et affichage de la réponse
        response = in.readLine();
        System.out.println("Réponse du serveur: " + response);

        // Fermeture des ressources
        out.close();
        in.close();
        socket.close();
    }
}
