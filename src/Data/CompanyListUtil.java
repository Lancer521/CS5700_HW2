package Data;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ty on 9/30/2016 at 10:41 AM.
 *
 */
public class CompanyListUtil {

    List<String> companyList;
    boolean isInitialized = false;

    public List<String> getList(){
        return getList("C:\\Users\\Ty\\Documents\\School\\Fall_2016\\CS5700\\HW2\\src\\CompanyList.csv");
    }

    public List<String> getList(String filename){
        if(isInitialized){
            return companyList;
        }
        isInitialized = true;
        return companyList = getDataFromCSV(filename);
    }


    private List<String> getDataFromCSV(String fileName) {
        String line;
        String[] array;
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                array = line.split(",");
                list.add(array[0]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return list;
    }

}
