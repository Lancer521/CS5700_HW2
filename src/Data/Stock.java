package Data;

import Messages.TickerMessage;

import java.util.Observable;

/**
 * Created by Ty on 9/23/2016.
 *
 */
public class Stock extends Observable {
    String symbol;
    double openingPrice;
    double closingPrice;
    double currentPrice;
    double bidPrice;
    double askPrice;
    double volumeSoldToday;
    double tenDayAverageVolume;

    public void update(TickerMessage message){
        symbol = message.getSymbol();
        openingPrice = message.getOpeningPrice();
        closingPrice = message.getPreviousClosingPrice();
        currentPrice = message.getCurrentPrice();
        bidPrice = message.getBidPrice();
        askPrice = message.getAskPrice();
        volumeSoldToday = message.getCurrentVolume();
        tenDayAverageVolume = message.getAverageVolume();
    }
}
