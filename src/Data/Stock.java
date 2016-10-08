package Data;

import Messages.TickerMessage;
import Utils.HelperUtils;

import java.util.Observable;

/**
 * Created by Ty on 9/23/2016.
 *
 */
public class Stock extends Observable {

    public String symbol;
    public long messageTimestamp;
    public int openingPrice = HelperUtils.NOT_INITIALIZED;
    public int closingPrice = HelperUtils.NOT_INITIALIZED;
    public int currentPrice = HelperUtils.NOT_INITIALIZED;
    public int bidPrice = HelperUtils.NOT_INITIALIZED;
    public int askPrice = HelperUtils.NOT_INITIALIZED;
    public int volumeSoldToday = HelperUtils.NOT_INITIALIZED;
    public int tenDayAverageVolume = HelperUtils.NOT_INITIALIZED;

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
