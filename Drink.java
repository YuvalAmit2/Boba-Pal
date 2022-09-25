import java.util.ArrayList;

public class Drink {
   public String teaType;
   public String milk;
   public String toppings;
   public String flavor;
   public String temperature;
   public String consistency;
   public String fruit;
   public String misc;
   
   String description;
   int matchPercent;
   
   public Drink(String description, int matchPercent) {
      this.description = description;
      this.matchPercent = matchPercent;
   }
   
   public String getDescription() {
      return description;
   }

   public int getMatchPercent() {
      return matchPercent;
   }
   
   public String getNiceString() {
      return description + ", " + matchPercent + "%";
   }
}
