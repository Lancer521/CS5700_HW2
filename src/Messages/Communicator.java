package Messages;

import java.io.IOException;
import java.net.*;

import Data.Portfolio;

/**
 * Created by Ty on 9/27/2016 at 5:43 PM.
 * Adapted from Dr. Clyde's sample code
 */
public class Communicator {

    private DatagramSocket socket;
    private byte[] buffer = new byte[42];
    private SocketAddress socketAddress;
    private static final String SERVER_IP = "52.37.77.101";
    private static final int PORT = 12099;
    private boolean isMonitoring;
    private Portfolio portfolio;

    public Communicator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public boolean isMonitoring() {
        return isMonitoring;
    }

    public void updatePortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void beginTransfer() {
        try {
            System.out.println("Transfer started");
            socketAddress = new InetSocketAddress(SERVER_IP, PORT);
            socket = new DatagramSocket();
            Runnable task = () -> {
                isMonitoring = true;
                monitor();
            };
            Thread thread = new Thread(task);
            thread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void endTransfer() {
        isMonitoring = false;
        StreamStocksMessage endMessage = new StreamStocksMessage();
        send(endMessage);
        socket.close();
        System.out.println("Transfer ended");
    }

    private void monitor() {

        if (portfolio == null) return;

        StreamStocksMessage startMessage = new StreamStocksMessage();
        portfolio.keySet().forEach(startMessage::Add);
        send(startMessage);

        while (isMonitoring) {
            TickerMessage message = receiveTicker(1000);
            if (message == null) {
                System.out.println("The TickerMessage came back NULL");
            } else {
                portfolio.update(message);
            }
        }
    }

    private void send(StreamStocksMessage message) {
        if (message == null) return;

        byte[] bytesToSend = message.Encode();
        DatagramPacket packet = new DatagramPacket(bytesToSend, bytesToSend.length, socketAddress);

        try {
            socket.send(packet);
        } catch (IOException ioe) {
            // TODO: handle the error. For example, you may want to Stop the communicator and log the error
            ioe.printStackTrace();
        }
    }

    private TickerMessage receiveTicker(int timeout) {
        TickerMessage message = null;

        DatagramPacket packet = receiveBytes(timeout);
        if (packet == null) return null;
        byte[] receivedBytes = packet.getData();
        if (receivedBytes != null && receivedBytes.length > 0) {
            try {
                message = TickerMessage.Decode(receivedBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    private DatagramPacket receiveBytes(int timeout) {

        DatagramPacket packet = null;
        try {
            socket.setSoTimeout(timeout);
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return packet;
    }
}
