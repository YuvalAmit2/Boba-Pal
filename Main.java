import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
   Drink myDrink;
   static File drinkDatabase = new File(System.getProperty("user.dir") + "//BobaJSON.txt");
   static int databaseLength = 0;
   public static void main(String[] args) throws Exception {
      System.out.println("Hi there! What would you like in your boba? (enter \"done\" when you're done!)");
      Scanner userInput = new Scanner(System.in);
      String keyword = userInput.nextLine();
      ArrayList<String> description = new ArrayList<String>();
      while (!keyword.equals("done")){
         description.add(keyword.toLowerCase());
         keyword = userInput.nextLine();
      }


      Scanner input = new Scanner(drinkDatabase);
      while (input.hasNextLine()) {
         databaseLength++;
         input.nextLine();
      }
      
      //System.out.println(databaseLength);
      ArrayList<Drink> bobas = findBoba(description);
      sort(bobas);
      
      for (int i = 0; i < bobas.size(); i++) {
         System.out.println(bobas.get(i).getNiceString());
         System.out.println("-------");
      }
   
   }

   
   public static ArrayList<Drink> findBoba(ArrayList<String> keys) throws IOException {
      ArrayList<Drink> retList = new ArrayList<Drink>();
      for (int i = 1; i <= databaseLength; i += 4) {
         int hits = 0;
         String line = Files.readAllLines(drinkDatabase.toPath()).get(i);
         //System.out.print(line);
         for (int j = 0; j < keys.size(); j++) {
            if (line.toLowerCase().contains(keys.get(j))) {
               hits++;
            }
         }
         //System.out.println(line);
         if (hits > 0) {
            //System.out.println("Hits: " + hits +", # of keys: " + keys.size());
            int matchPercent = (int)(((Double.valueOf(hits)/Double.valueOf(keys.size())) * 100) + 0.5);
            retList.add(new Drink(formatBoba(i), matchPercent));
            //System.out.println(line);
         }
      }
      return retList;
   }
   
   public static String formatBoba(int line) throws IOException{
      String teaName = Files.readAllLines(drinkDatabase.toPath()).get(line);
      String price = Files.readAllLines(drinkDatabase.toPath()).get(line);
      String storeName = Files.readAllLines(drinkDatabase.toPath()).get(line + 1);
      
      int storeCol = storeName.indexOf(':');
      //System.out.println(teaName);
      teaName = splitFirst(teaName).get(0);
      price = splitFirst(price).get(1);

      storeName = storeName.substring(storeCol + 2, storeName.length() - 1);
      String retString = teaName + " from " + storeName + " for " + price;
      return retString;
   }

   public static ArrayList<String> splitFirst(String s) {
      int slashes = 0;
      ArrayList<String> sections = new ArrayList<String>();
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == '\\') {
             slashes += 1;
         }
     }
      //System.out.println(slashes);
     int col = s.indexOf(':');
     int firSlash = s.indexOf('\\');

     if (slashes == 1) {
         sections.add(s.substring(col + 2, firSlash));
         sections.add(s.substring((firSlash + 2), s.length() - 2));
     }

     if (slashes == 2) {
        //System.out.println(s.substring(firSlash+1));
         int secSlash = s.substring(firSlash + 1).indexOf('\\') + s.substring(0, firSlash+1).length();
         //System.out.println(firSlash);
         //System.out.println(secSlash);
         sections.add(s.substring(col + 2, firSlash));
         sections.add(s.substring((secSlash + 2), s.length() - 2));
     }
      return sections;
   }

   //I'm so tired bro I'm doing bubble sort and there's nothing you can do about it
   public static void sort(ArrayList<Drink> list) {
      
      int n = list.size();
      for (int i = 0; i < n - 1; i++) {
         for (int j = 0; j < n - i - 1; j++) {
            if (list.get(j).getMatchPercent() < list.get(j + 1).getMatchPercent()) {
                Collections.swap(list, j, j+1);
            }
         }
      }
       
      //Collections.sort(list.Cast<String>().ToList());
   }   
}
