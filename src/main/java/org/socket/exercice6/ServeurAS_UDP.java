package org.socket.exercice6;

import org.socket.LA;
import org.socket.ListeAuth;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServeurAS_UDP implements Runnable {

    public static final int PORT = 28414;
    public static final int BUFFER_SIZE = 1024;
    private LA listeAuth;

    public ServeurAS_UDP(LA listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Serveur AS en attente de connexion...");

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
                if ("CHK".equals(parts[0])) {
                    String login = parts[1];
                    String password = parts[2];

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
