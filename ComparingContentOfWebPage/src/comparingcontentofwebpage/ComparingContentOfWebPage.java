package comparingcontentofwebpage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ComparingContentOfWebPage {

    public static void main(String[] args) {
        int flag;
        String line, fileName;
        StringBuilder sourceCodeOfNewURL, sourceCodeOfOldURL;
        BufferedReader bufferedReader;
        ArrayList<Boolean> equal;
        ArrayList<String> newURL, oldURL;

        flag = 0;
        line = null;
        fileName = "C:\\Users\\ponalvin\\Downloads\\accounts-smcweb-new.html";
        equal = new ArrayList<>();
        newURL = new ArrayList<>();
        oldURL = new ArrayList<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));

            while ((line = bufferedReader.readLine()) != null) {
                if (flag == 0) {
                    extractURL(newURL, line);
                    flag = 1;
                } else {
                    extractURL(oldURL, line);
                    flag = 0;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        for (int i = 0; i < newURL.size(); i++) {
            sourceCodeOfNewURL = getSourceCodeOfURL(newURL.get(i));
            sourceCodeOfOldURL = getSourceCodeOfURL(oldURL.get(i));
            equal.add(sourceCodeOfNewURL.toString().equals(sourceCodeOfOldURL.toString()));
        }
        
        for (int i = 0; i < equal.size(); i++) {
            if (equal.get(i) == false) {
                System.out.println(newURL.get(i) + "  doesn't equals " + oldURL.get(i));
            }
            
        }

    }

    public static void extractURL(ArrayList<String> location, String content) {
        int start, end;

        start = 0;
        end = 0;

        if (content.contains("\"")) {
            start = content.indexOf("\"") + 1;
            end = content.lastIndexOf("\"");
            location.add(content.substring(start, end));
        }
    }

    public static StringBuilder getSourceCodeOfURL(String URL) {
        String line;
        StringBuilder sourceCodeOfWebPage;
        BufferedReader bufferedReader;

        sourceCodeOfWebPage = null;

        try {
            sourceCodeOfWebPage = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(new URL(URL).openConnection().getInputStream(), "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                sourceCodeOfWebPage.append(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            // System.out.println(ex);
            sourceCodeOfWebPage.append(ex.toString().subSequence(0, ex.toString().indexOf("L") + 1));
        } finally {
            return sourceCodeOfWebPage;
        }
    }
}
