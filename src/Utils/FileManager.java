package Utils;

import Data.Portfolio;

/**
 * Created by Ty on 9/23/2016 at 6:04 PM.
 *
 * This Utils.FileManager is a strategy, in case different formats are desired when saving or loading files
 */
public abstract class FileManager {

    public abstract void savePortfolio(Portfolio portfolio);

    public abstract Portfolio loadPortfolio();
}
