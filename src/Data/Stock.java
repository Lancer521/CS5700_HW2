package Data;

import Messages.TickerMessage;

import java.util.Observable;

/**
 * Created by Ty on 9/23/2016.
 *
 */
public class Stock extends Observable {

    public String symbol;
    public long messageTimestamp;
    public int openingPrice;
    public int closingPrice;
    public int currentPrice;
    public int bidPrice;
    public int askPrice;
    public int volumeSoldToday;
    public int tenDayAverageVolume;

    public boolean isHydrated = false;

    public void update(TickerMessage message){
        symbol = message.getSymbol();
        messageTimestamp = message.getMessageTimestamp();
        openingPrice = message.getOpeningPrice();
        closingPrice = message.getPreviousClosingPrice();
        currentPrice = message.getCurrentPrice();
        bidPrice = message.getBidPrice();
        askPrice = message.getAskPrice();
        volumeSoldToday = message.getCurrentVolume();
        tenDayAverageVolume = message.getAverageVolume();

        isHydrated = true;

        setChanged();
        notifyObservers();
    }
}
