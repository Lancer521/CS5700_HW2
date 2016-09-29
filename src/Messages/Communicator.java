package Messages;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Map;

import Data.Stock;
import Data.Portfolio;

/**
 * Created by Ty on 9/27/2016 at 5:43 PM.
 * Adapted from Dr. Clyde's sample code
 */
public class Communicator {

    private DatagramSocket socket;
    InetSocketAddress socketAddress;
    private boolean isMonitoring;
    private Portfolio portfolio;

    public void addTestData(){
        portfolio = new Portfolio();
        portfolio.put("GOOGL", new Stock());
        portfolio.put("AMZN", new Stock());
    }


    public void beginTransfer(){
        try {
            socketAddress = new InetSocketAddress("127.0.0.1", 12099);
            socket = new DatagramSocket(socketAddress);
            StreamStocksMessage startMessage = new StreamStocksMessage();
            for(Map.Entry<String, Stock> entry : portfolio.entrySet()){
                startMessage.Add(entry.getKey());
            }
            Send(startMessage);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void endTransfer(){
        //DO STUFF FIRST
        //THEN:
        socket.close();
    }

    private void Send(StreamStocksMessage message)
    {
        if (message == null) return;

        byte[] bytesToSend = message.Encode();
        DatagramPacket packet = new DatagramPacket(bytesToSend, bytesToSend.length, socketAddress);

        try
        {
            socket.send(packet);
        }
        catch (IOException ioe)
        {
            // TODO: handle the error. For example, you may want to Stop the communicator and log the error
        }
    }
}
