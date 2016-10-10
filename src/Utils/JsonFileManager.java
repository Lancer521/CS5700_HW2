package Utils;

import Data.Portfolio;

import com.google.gson.Gson;

import java.io.*;


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
        String json = gson.toJson(portfolio.values().toArray());
        try {
            PrintWriter writer = new PrintWriter(FILENAME);
            writer.print(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Portfolio loadPortfolio(String fileName) {
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
        return gson.fromJson(result, Portfolio.class);
    }
}
