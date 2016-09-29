package Messages;

import java.io.IOException;
import java.net.*;
import java.util.Map;

import Data.Stock;
import Data.Portfolio;

/**
 * Created by Ty on 9/27/2016 at 5:43 PM.
 * Adapted from Dr. Clyde's sample code
 */
public class Communicator {

    private DatagramSocket socket;
    private byte[] buffer = new byte[1000];
    private InetAddress socketAddress;
    private static final int PORT = 12099;
    private boolean isMonitoring;
    private Portfolio portfolio;

    public void addTestData() {
        portfolio = new Portfolio();
        portfolio.put("GOOGL", new Stock());
        portfolio.put("AMZN", new Stock());
    }


    public void beginTransfer() {
        try {
            socketAddress = InetAddress.getLocalHost();
            socket = new DatagramSocket();
            StreamStocksMessage startMessage = new StreamStocksMessage();
            for (Map.Entry<String, Stock> entry : portfolio.entrySet()) {
                startMessage.Add(entry.getKey());
            }
            Send(startMessage);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void endTransfer() {
        //DO STUFF FIRST
        //THEN:
        socket.close();
    }

    private void Send(StreamStocksMessage message) {
        if (message == null) return;

        byte[] bytesToSend = message.Encode();
        DatagramPacket packet = new DatagramPacket(bytesToSend, bytesToSend.length, socketAddress, PORT);

        try {
            socket.send(packet);
        } catch (IOException ioe) {
            // TODO: handle the error. For example, you may want to Stop the communicator and log the error
        } finally {
            System.out.println();
        }

    }
}
