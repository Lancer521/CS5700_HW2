package Utils;

import Data.Portfolio;

import com.google.gson.Gson;


/**
 * Created by Ty on 10/8/2016 at 1:30 PM.
 *
 */
public class JsonFileManager extends FileManager {

    Gson gson;

    public JsonFileManager() {
        gson = new Gson();
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        String json = gson.toJson(portfolio.values().toArray());


    }

    @Override
    public Portfolio loadPortfolio(String filepath) {
        return null;
    }
}
