import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

/**
 * AOC Day4
 * Passport check
 * 
 * @author Jeff Fitzgerald
 * @version 12/04/2020
 */

public class AOCDay4 {
    public static class PassportItem{
        String key;
        String value;

        PassportItem(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

    public static boolean validatePassport(HashMap hashMap) {
        //cid is optional
        if(hashMap.containsKey("byr") &&
           hashMap.containsKey("iyr") &&
           hashMap.containsKey("eyr") &&
           hashMap.containsKey("hgt") &&
           hashMap.containsKey("hcl") &&
           hashMap.containsKey("ecl") &&
           hashMap.containsKey("pid"))  // &&
           // hashMap.containsKey("cid"))
        {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Reads the input file of passports.
     * 
     * @param filename name of file to read
     * @return an array of input values
     */
    public static Integer readFile(String fileName) {

        Scanner inFile = null;
        String oneLine;
        HashMap<String, String> passport = new HashMap<>();
        int validPassports = 0;

        try {
            inFile = new Scanner(new File(fileName));
            
            while (inFile.hasNext()) {
                oneLine = inFile.nextLine();
                    String[] tokens = oneLine.split("[ \n]");
                    if(oneLine.isEmpty()){
                        if(validatePassport(passport)){
                            validPassports++;
                        }
                        passport.clear();
                    } else {
                        for(int i = 0; i < tokens.length; i++){
                                String[] tokens2 = tokens[i].split("[:]");
                                passport.put(tokens2[0], tokens2[1]);
                        }
                    }
            }
            if(validatePassport(passport)){
                validPassports++;
            }

    } catch (IOException e) {
            System.out.println("Error reading input file: " + fileName);
            System.exit(0);

        } finally {
            if (inFile != null) {
                inFile.close();
            }
        }
    
        return validPassports;
    }
      
    public static void main(String[] args) {

        String fileName = "AOCDay4Input.txt"; //ShortList //ShortListPart2
        Integer validPassports = readFile(fileName);
        System.out.println("Number of valid passports: " + validPassports);
    }
}