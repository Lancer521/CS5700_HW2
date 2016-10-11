package Utils;

import Data.Portfolio;

import Data.Stock;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Ty on 10/8/2016 at 1:30 PM.
 *
 */
public class JsonFileManager extends FileManager {

    private Gson gson;
    private static final String FILENAME = "Portfolio.json";

    public JsonFileManager() {
        gson = new Gson();
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        List<String> list = portfolio.values().stream().map(stock -> stock.symbol).collect(Collectors.toList());
        String json = gson.toJson(list);
        try {
            PrintWriter writer = new PrintWriter(FILENAME);
            writer.print(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Portfolio loadPortfolio() {
        String line;
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));

            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        List<String> list = gson.fromJson(result, List.class);
        Portfolio p = new Portfolio();
        for(String string : list){
            Stock s = new Stock();
            s.symbol = string;
            p.put(string, s);
        }
        return p;
    }
}
