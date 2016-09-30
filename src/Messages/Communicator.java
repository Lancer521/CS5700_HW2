package Messages;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.Executors;

import Data.Stock;
import Data.Portfolio;

/**
 * Created by Ty on 9/27/2016 at 5:43 PM.
 * Adapted from Dr. Clyde's sample code
 */
public class Communicator {

    private DatagramSocket socket;
    private byte[] buffer = new byte[42];
    private InetAddress socketAddress;
    private static final int PORT = 12099;
    private boolean isMonitoring;
    private Portfolio portfolio;

    public void addTestData() {
        portfolio = new Portfolio();
        portfolio.put("GOOGL", new Stock());
        portfolio.put("AMZN", new Stock());
        portfolio.put("PIH", new Stock());
        portfolio.put("FLWS", new Stock());
        portfolio.put("FCCY", new Stock());
        portfolio.put("SRCE", new Stock());
    }


    public void beginTransfer() {
        try {
            System.out.println("Transfer has begun");
            socketAddress = InetAddress.getLocalHost();
            socket = new DatagramSocket();
            Runnable task = () -> {
                isMonitoring = true;
                monitor();
            };
            Thread thread = new Thread(task);
            thread.start();
//            Executors.newSingleThreadExecutor().submit(task);
            System.out.println("TESTING");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void endTransfer() {
        isMonitoring = false;
        socket.close();
        System.out.println("Transfer ended");
    }

    private void monitor() {

        if (portfolio == null) return;

        StreamStocksMessage startMessage = new StreamStocksMessage();
        for (Map.Entry<String, Stock> entry : portfolio.entrySet()) {
            startMessage.Add(entry.getKey());
        }
        Send(startMessage);

        while (isMonitoring) {
            TickerMessage message = receiveTicker(1000);
            if (message == null) {
                System.out.println("The TickerMessage came back NULL");
            } else {
                portfolio.update(message);
            }
        }
    }

    private void Send(StreamStocksMessage message) {
        if (message == null) return;

        byte[] bytesToSend = message.Encode();
        DatagramPacket packet = new DatagramPacket(bytesToSend, bytesToSend.length, socketAddress, PORT);

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
            e.printStackTrace();
        }
        return packet;
    }
}
