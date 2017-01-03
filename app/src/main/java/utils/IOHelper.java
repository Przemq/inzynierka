package utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by PrzemekMadzia on 2017-01-01.
 */

public class IOHelper {
    public static String readJSONFromFIle(String fileName) {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root,fileName + ".txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  text.toString();
    }
}


