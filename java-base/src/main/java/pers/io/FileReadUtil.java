package pers.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @auther ken.ck
 * @date 2021/7/24 21:20
 */
public class FileReadUtil {

    public static String readFile(String filePath) {

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(readFile("text.txt"));
    }

}
