package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ty on 9/30/2016 at 10:41 AM.
 *
 */
public class CompanyListUtil {

    List<String> companyList;
    Map<String, String> companyMap;
    boolean isInitialized = false;

    public List<String> getList(){
        if(isInitialized){
            return companyList;
        }
        isInitialized = true;
        return getList("C:\\Users\\Ty\\Documents\\School\\Fall_2016\\CS5700\\HW2\\src\\CompanyList.csv");
    }

    public List<String> getList(String filename){
        parseDataFromCSV(filename);
        return companyList;
    }

    public Map<String, String> getMap(){
        if(isInitialized){
            return companyMap;
        }
        isInitialized = true;
        return getMap("C:\\Users\\Ty\\Documents\\School\\Fall_2016\\CS5700\\HW2\\src\\CompanyList.csv");
    }

    public Map<String, String> getMap(String filename){
        parseDataFromCSV(filename);
        return companyMap;
    }

    private void parseDataFromCSV(String fileName) {
        String line;
        String[] array;
        companyMap = new HashMap<>();
        companyList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                array = line.split(",");
                companyMap.put(array[1], array[0]);
                companyList.add(array[1]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
