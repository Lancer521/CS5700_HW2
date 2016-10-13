package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ty on 9/30/2016 at 10:41 AM.
 *
 */
public class CompanyListUtil {

    private static Map<String, String> companyMap;
    private static boolean isInitialized = false;

    public static Map<String, String> getMap(){
        if(isInitialized){
            return companyMap;
        }
        isInitialized = true;
        return getMap("C:\\Users\\Ty\\Documents\\School\\Fall_2016\\CS5700\\HW2\\src\\CompanyList.csv");
    }

    public static Map<String, String> getMap(String filename){
        parseDataFromCSV(filename);
        return companyMap;
    }

    private static void parseDataFromCSV(String fileName) {
        String line;
        String[] array;
        companyMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                array = line.split(",");
                companyMap.put(array[1], array[0]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
