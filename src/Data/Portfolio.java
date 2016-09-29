package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Utils.FileManager;

/**
 * Created by Ty on 9/23/2016.
 */
public class Portfolio extends HashMap<String, Stock> {

    FileManager fileManager;

    public Portfolio newInstance(String fileName){
        Portfolio portfolio = new Portfolio();

        String data = getDataFromCSV(fileName);
        String[] parsedData = data.split(",");
        for(int index = 0; index < parsedData.length; index+=2){
            Stock stock = new Stock();
            stock.symbol = parsedData[index];
            stock.companyName = parsedData[index+1];
            portfolio.put(parsedData[index], stock);
        }
        return portfolio;
    }

    public void update(){

    }

    private String getDataFromCSV(String fileName) {
        String line;
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                if(line.contains("\n")){
                    line = line.replace("\n", "");
                }
                result += line;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
}
