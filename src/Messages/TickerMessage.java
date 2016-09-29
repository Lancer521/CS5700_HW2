package Messages;

import Utils.HelperFunctions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by swc on 9/20/16.
 * updated by EL
 */
public class TickerMessage {

    private String symbol;
    private long messageTimestamp;
    private int openingPrice;
    private int previousClosingPrice;
    private int currentPrice;
    private int bidPrice;
    private int askPrice;
    private int currentVolume;
    private int averageVolume;

    public byte[] Encode() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(42).order(ByteOrder.BIG_ENDIAN);

        // Write out Symbol as string, padded spaces to 6 characters, converted to a byte array using ASCII encoding
        byte[] bytes = getPaddedSymbol().getBytes(StandardCharsets.US_ASCII);
        buffer.put(bytes);

        // Write out Timestamp as a long integer, which respents number of 100-nano second units since Jan 1, 0001
        buffer.putLong(getMessageTimestamp());

        // Write out OpeningPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
        buffer.putInt(getOpeningPrice());

        // Write out PreviousClosingPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
        buffer.putInt(getPreviousClosingPrice());

        // Write out CurrentPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
        buffer.putInt(getCurrentPrice());

        // Write out BidPrice, as ang integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
        buffer.putInt(getBidPrice());

        // Write out AskPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
        buffer.putInt(getAskPrice());

        // Write out CurrentVolume, as an integer in Network Standard Byte Order
        buffer.putInt(getCurrentVolume());

        // Write out AverageVolume, as an integer in Network Standard Byte Order
        buffer.putInt(getAverageVolume());

        return buffer.array();
    }


    public static TickerMessage Decode(byte[] bytes) throws IOException {
        TickerMessage message = null;
        if (bytes != null && bytes.length == 42)
        {
            message = new TickerMessage();

            ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);

            // Read in Symbol as string, padded spaces to 6 characters, converted to a byte array using ASCII encoding
            byte[] tmp = new byte[6];
            buffer.get(tmp);
            message.setPaddedSymbol(new String (tmp, 0, 6, StandardCharsets.US_ASCII));

            // Read in Timestamp as a long integer of "ticks" in Network Standard Byte Order
            message.setMessageTimestamp(buffer.getLong());

            // Read in OpeningPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
            message.setOpeningPrice(buffer.getInt());

            // Read in PreviousClosingPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
            message.setPreviousClosingPrice(buffer.getInt());

            // Read in CurrentPrice, as an integer in Network Standard Byte Order.  The integer represents pennies (not dollars)
            message.setCurrentPrice(buffer.getInt());

            // Read in BidPrice, as an integer in Network Standard Byte order.  The integer represents pennies (not dollars)
            message.setBidPrice(buffer.getInt());

            // Read in AskPrice, as an integer in Network Standard Byte order.  The integer represents pennies (not dollars)
            message.setAskPrice(buffer.getInt());

            // Read in CurrentVolume, as an integer in Network Standard Byte Order
            message.setCurrentVolume(buffer.getInt());

            // Read in AverageVolume, as an integer in Network Standard Byte Order
            message.setAverageVolume(buffer.getInt());
        }

        return message;
    }


    public String getPaddedSymbol() {
        String result;
        if (symbol == null || symbol.isEmpty() || symbol.trim().isEmpty())
            result = "";
        else
            result=symbol;
        return HelperFunctions.padRight(result,6);
    }

    public void setPaddedSymbol(String paddedSymbol) {
        symbol = null;
        if(paddedSymbol!=null  && !paddedSymbol.isEmpty() && !paddedSymbol.trim().isEmpty())
            symbol=paddedSymbol.trim().toUpperCase();
    }

    public TickerMessage(String symbol, long messageTimestamp, int openingPrice, int previousClosingPrice, int currentPrice, int bidPrice, int askPrice, int currentVolume, int averageVolume) {
        this.symbol = symbol;
        this.messageTimestamp = messageTimestamp;
        this.openingPrice = openingPrice;
        this.previousClosingPrice = previousClosingPrice;
        this.currentPrice = currentPrice;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.currentVolume = currentVolume;
        this.averageVolume = averageVolume;
        this.messageTimestamp=messageTimestamp;
    }

    public TickerMessage() {
        symbol = "";
        messageTimestamp = 0;
        openingPrice = 0;
        previousClosingPrice = 0;
        currentPrice = 0;
        bidPrice = 0;
        askPrice = 0;
        currentVolume = 0;
        averageVolume = 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public int getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(int openingPrice) {
        this.openingPrice = openingPrice;
    }

    public int getPreviousClosingPrice() {
        return previousClosingPrice;
    }

    public void setPreviousClosingPrice(int previousClosingPrice) {
        this.previousClosingPrice = previousClosingPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(int askPrice) {
        this.askPrice = askPrice;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public int getAverageVolume() {
        return averageVolume;
    }

    public void setAverageVolume(int averageVolume) {
        this.averageVolume = averageVolume;
    }
}
