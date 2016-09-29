package Data;

import java.util.Observable;

/**
 * Created by Ty on 9/23/2016.
 */
public class Stock extends Observable {
    String symbol;
    String companyName;
    double openingPrice;
    double closingPrice;
    double currentPrice;
    double bidPrice;
    double askPrice;
    double volumeSoldToday;
    double tenDayAverageVolume;

    public void update(){
        //set all variables
    }
}
