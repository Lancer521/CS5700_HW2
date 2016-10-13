package Display;

import Data.Portfolio;
import Data.Stock;

import java.util.ArrayList;

/**
 * Created by Ty on 10/8/2016 at 11:33 AM.
 * <p>
 * Returns a list of new Display objects as specified by parameters
 */
public class DisplayFactory {

    public static ArrayList<Display> createDisplays(boolean basicText, boolean openingRB, boolean priceRange, Portfolio portfolio, boolean stockPrice, Stock priceStock, boolean stockVolume, Stock volumeStock) {
        ArrayList<Display> displays = new ArrayList<>();
        if (basicText) {
            displays.add(new PortfolioStockDisplay(openingRB));
        }
        if (priceRange) {
            displays.add(new PriceRangeDisplay(portfolio));
        }
        if (stockPrice) {
            displays.add(new IndividualStockPriceDisplay(priceStock));
        }
        if (stockVolume) {
            displays.add(new IndividualStockVolumeDisplay(volumeStock));
        }
        return displays;
    }
}
