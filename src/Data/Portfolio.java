package Data;

import java.util.HashMap;
import java.util.List;

import Messages.TickerMessage;
import Utils.FileManager;

/**
 * Created by Ty on 9/23/2016.
 *
 */
public class Portfolio extends HashMap<String, Stock> {

    private FileManager fileManager;
    private List<String> companyList;

    public Portfolio(){
        CompanyListUtil companyListUtil = new CompanyListUtil();
        companyList = companyListUtil.getList();
    }

    public void update(TickerMessage message){
        Stock stock = this.get(message.getSymbol());
        if(stock != null){
            stock.update(message);
        }
    }
}
