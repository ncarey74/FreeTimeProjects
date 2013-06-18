/**
 * This class converts metric measurements to imperial measurements.
 * The amount and the units are captured by the interface. The amount is 
 * converted from metric to imperial using the convertAmount() method. The units
 * are changed from metric to imperial using the convertUnits() method. The 
 * converted amount and units are returned to the interface for display. 
 * @author Carey Norslien
 */
public class Converter 
{
   private static final double KILOMETERS_TO_MILES = 0.62137;//km to mi
   private static final double LITERS_TO_GALLONS = .2642;    //L to gal
   private static final double KILOGRAMS_TO_POUNDS = 2.2046; //kg to lb   
   private static final double CELSIUS_TO_FAHRENHEIT_MULTIPLY = 9.0/5.0;   //multiply C by nine fifths
   private static final short CELSIUS_TO_FAHRENHEIT_ADD = 32;   //add 32 to finish converting C to F
   
   private float amount;
   private static double conversion = 0;
   private String units;
   
   /**
    * Initializes a Converter object.
    */
   public Converter()
   {
      amount = 0;
      conversion = 0;
      units = " ";
   } 
   
   /**
    * Converts metric measurements to imperial.
    * Metric measurements are easily computed with simple math and 
    * constants. Note that only celsius can have negative values. 
    * @param amount the value of the metric measurement
    * @param units the unit of the metric measurement
    * @return the converted measurement
    */
   public static double convertAmount(double amount, String units)
   {
      boolean positive = amount > 0;
      
      if (units.equals("celsius"))
         conversion = amount * CELSIUS_TO_FAHRENHEIT_MULTIPLY + 
                   CELSIUS_TO_FAHRENHEIT_ADD;
      else if (units.equals("kilograms") && positive)
         conversion = amount * KILOGRAMS_TO_POUNDS;
      else if (units.equals("kilometers") && positive) 
         conversion = amount * KILOMETERS_TO_MILES;
      else if (units.equals("liters") && positive)
         conversion = amount * LITERS_TO_GALLONS;
      else if (!positive)
         conversion = -1;
      
      return conversion;
   }
   
   /**
    * Converts from metric units to imperial units
    * @param units metric unit
    * @return imperial units
    */
   public static String convertUnits(String units)
   {
      switch (units) 
      {
         case "celsius": units = "fahrenheit";
            break;
         case "kilograms": units = "pounds";
            break;
         case "kilometers": units = "miles";
            break;
         case "liters": units = "gallons";
            break;
      }
      return units;
   }       
}
