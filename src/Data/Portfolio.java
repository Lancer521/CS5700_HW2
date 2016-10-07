package Data;

import java.util.HashMap;

import Messages.TickerMessage;

/**
 * Created by Ty on 9/23/2016.
 *
 */
public class Portfolio extends HashMap<String, Stock> {

    public void update(TickerMessage message){
        Stock stock = this.get(message.getSymbol());
        if(stock != null){
            stock.update(message);
        }
    }

    public void add(String symbol){
        Stock stock = new Stock();
        stock.symbol = symbol;
        this.put(symbol, stock);
    }
}
