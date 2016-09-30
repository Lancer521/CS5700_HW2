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

    @Override
    public Stock put(String key, Stock value) {
        if(companyList != null && !companyList.isEmpty()){
            if(companyList.contains(key)) {
                return super.put(key, value);
            } else {
                System.out.println(key + " is not a valid symbol");
            }
        } else {
            System.out.println("The list of symbols is corrupted");
        }
        return null;
    }
    public void update(TickerMessage message){
        Stock stock = this.get(message.getSymbol());
        if(stock != null){
            stock.update(message);
        }
    }
}
