package app.ccb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {

    public String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        StringBuilder sb = new StringBuilder();
        String currentLine = "";
        while ((currentLine = bufferedReader.readLine()) != null) {
            sb.append(currentLine).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
