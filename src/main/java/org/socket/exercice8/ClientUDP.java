package org.socket.exercice8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {

    public static final int PORT = 28414;
    public static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");

            String login = "Toto";
            String password = "Toto";
            String message = "CHK " + login + " " + password;

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, PORT);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Réponse du serveur: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
