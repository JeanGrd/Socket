package org.socket.exercice3;

import org.socket.ListeAuth;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServeurAS {

    public static final int PORT = 28414;
    public static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Serveur AS en attente de connexion...");
            ListeAuth listeAuth = new ListeAuth();

            while (true) {
                byte[] receiveData = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Client connecté: " + clientAddress.getHostAddress() + ":" + clientPort);
                System.out.println("Requête reçue: " + message);

                String[] parts = message.split(" ");

                String login = parts[1];
                String password = parts[2];
                if ("CHK".equals(parts[0])) {
                    boolean result = listeAuth.tester(login, password);
                    byte[] sendData = (result ? "GOOD" : "BAD").getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    serverSocket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
