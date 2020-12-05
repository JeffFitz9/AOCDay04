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

public class AOCDay4Part2 {

    enum eclValues {amb, blu, brn, gry, grn, hzl, oth};


    public static class PassportItem{
        String key;
        String value;

        PassportItem(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

    public static boolean validatePassport(HashMap hashMap) {
        
        boolean byrValid = false;
        boolean iyrValid = false;
        boolean eyrValid = false;
        boolean hgtValid = false;
        boolean hclValid = false;
        boolean eclValid = false;
        boolean pidValid = false;
        
        //cid is optional
        if(hashMap.containsKey("byr")){
//            System.out.println(hashMap.get("byr"));
            String byr = (hashMap.get("byr").toString());
            if(byr.length() == 4 &&
                Integer.parseInt(byr) >= 1920 &&
                Integer.parseInt(byr) <= 2002){
                byrValid = true;
            }
        }
        if(hashMap.containsKey("iyr")){
//            System.out.println(hashMap.get("iyr"));
            String iyr = (hashMap.get("iyr").toString());
            if(iyr.length() == 4 &&
                Integer.parseInt(iyr) >= 2010 &&
                Integer.parseInt(iyr) <= 2020){
                iyrValid = true;
            }
        }
        if(hashMap.containsKey("eyr")){
            String eyr = (hashMap.get("eyr").toString());

//            System.out.println(hashMap.get("iyr"));
            if(eyr.length() == 4 &&
                Integer.parseInt(eyr) >= 2020 &&
                Integer.parseInt(eyr) <= 2030){
                eyrValid = true;
            }
        }
        if(hashMap.containsKey("hgt")){
            boolean allCharCorrect = true;
            String hgt = (String)hashMap.get("hgt");
            // check inch or cm
            if(hgt.substring(hgt.length()-2).equals("cm") || hgt.substring(hgt.length()-2).equals("in")){
                switch (hgt.substring(hgt.length()-2)) {
                    case "in":
                        if(!hgt.substring(0, hgt.length()-2).chars().allMatch(Character::isDigit)){
                            if(Integer.parseInt(hgt.substring(0, hgt.length()-2)) < 59 || Integer.parseInt(hgt.substring(0, hgt.length()-2)) > 76){
                                allCharCorrect = false;
                            }
                        }
                        break;
                    case "cm":
                        if(!hgt.substring(0, hgt.length()-2).chars().allMatch(Character::isDigit)){
                            if(Integer.parseInt(hgt.substring(0, hgt.length()-2)) < 150 || Integer.parseInt(hgt.substring(0, hgt.length()-2)) > 193){
                                allCharCorrect = false;
                            }
                        }
                        break;
                    default: allCharCorrect = false;
                        break;
                }
            }
            if(allCharCorrect){
                hgtValid = true;
            }
        }
        if(hashMap.containsKey("hcl")){
            boolean allCharCorrectHCL = true;
            String hcl = (String)hashMap.get("hcl");
            if(hcl.length() == 7){
                if(hcl.charAt(0) == '#'){
                    for(int i = 1; i < hcl.length(); i++) {
                        if(!(Character.isDigit(hcl.charAt(i)) || (hcl.charAt(i) >= 'a' && hcl.charAt(i) <= 'f'))){
                            allCharCorrectHCL = false;
                        }
                    }
                } else {allCharCorrectHCL = false;}
            } else {allCharCorrectHCL = false;}
            if(allCharCorrectHCL){
                hclValid = true;
            }
        }
        if(hashMap.containsKey("ecl")){
            String temp = (String)hashMap.get("ecl");
            //System.out.println("ECL: " + temp);
            for (eclValues value : eclValues.values()) { 
                if((value).toString().equals(temp)){
                    eclValid = true;
                }
            } 
        }
        if(hashMap.containsKey("pid")){
            boolean allDigits = true;
            String pid = (String)hashMap.get("pid");
            if(pid.length() == 9){
                for(int i = 0; i < pid.length(); i++) {
                    if(!Character.isDigit(pid.charAt(i))){
                        allDigits = false;
                    }
                }
            } else {allDigits = false;}
            if(allDigits){
                pidValid = true;
            }
        }
        System.out.println("byrValid " + byrValid + " iyrValid " + iyrValid + " eyrValid " + eyrValid + " hgtValid " + hgtValid + " hclValid " + hclValid + " eclValid " + eclValid + " pidValid " + pidValid);

        if(byrValid &&
           iyrValid &&
           eyrValid &&
           hgtValid &&
           hclValid &&
           eclValid &&
           pidValid)  // &&
           // hashMap.containsKey("cid"))

//////////////////////////////////////////
/*
byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
If cm, the number must be at least 150 and at most 193.
If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
pid (Passport ID) - a nine-digit number, including leading zeroes.
cid (Country ID) - ignored, missing or not.
*/
////////////////////////////////////
        {
            System.out.println("Valid");
            return true;
        } else {
            System.out.println("Inalid");
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