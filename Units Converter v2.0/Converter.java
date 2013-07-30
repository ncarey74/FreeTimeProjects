import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * Performs all the calculations required to convert measurements between units
 * and formatting the final answer.
 * This class has input interpretation methods, high-level conversion methods, 
 * low-level conversion methods, conversion type methods, output format methods, 
 * and the public methods to process the input and to perform the conversion.
 * Input interpretation methods process the input for conversion. High-level
 * conversion methods convert between the original and converted units by
 * utilizing the low-level conversion methods to perform the math of conversion.
 * Conversion type methods determine if the units are imperial, metric, time,
 * digital storage, temperature, length, mass, volume, or area. Output format 
 * methods format the converted amount and converted units for display by the 
 * UI.   
 * There are two main types of units that can be converted between: single 
 * units like inches or kilograms, and composite units like miles per gallon
 * or meters per second.
 * Valid conversions include imperial and metric measurements of length, mass
 * volume, and area; temperatures, digital storages, and times. 
 * The conversion between length, mass, volume, and area units is as follows:
 * 1) The public convert method decides whether the conversion is imperial to 
 *    imperial, imperial to metric, metric to imperial, and metric to metric.
 * 2) The original amount is converted to its base amount (meters, grams, 
 *    liters, square meters for metric units; inches, fluid ounces, pounds for
 *    imperial units).
 * 3) If the conversion is between metric and imperial, the appropriate 
 *    calculation is made from the base amount in one system to the other.
 * 4) The base amount is then converted to desired unit as declared by the user.
 * The conversion between temperature units is as follows:
 * 1) The public convert method calls a method that decides whether the 
 *    conversion is Fahrenheit to Celsius, Celsius to Fahrenheit, Fahrenheit to
 *    Kelvin, Kelvin to Fahrenheit, Celsius to Kelvin, and Kelvin to Celsius.
 * 2) The original amount is converted from the original units to the converted
 *    units in a straightforward fashion using simple math formulas. 
 * The conversion for time and digital storage are as follows given their 
 * similar nature:
 * 1) The original amount is scaled down to the base amount (seconds for time, 
 *    bits for digital storage).
 * 2) The base amount is scaled up to the final converted amount.
 * The conversion between composite units is as follows:
 * 1) Convert the first original unit to the first converted unit.
 * 2) Convert the second original unit to the second converted unit.
 * 3) Divide the first converted unit by the second converted unit.
 * There are three types of error: a negative amount error for all conversion 
 * except for temperature, invalid conversion error for conversion between two
 * different types of units(like a conversion from meters to pounds), and
 * an invalid unit error for unrecognized units.
 * @author Carey Norslien
 */
public class Converter {
   //distance, volume, mass, temperature conversion numbers
   private static final double M_TO_IN = 39.3701;
   private static final double IN_TO_M = 0.0254;
   private static final double M2_TO_IN2 = 1550;
   private static final double IN2_TO_M2 = 0.00064516;
   private static final double L_TO_FLOZ = 35.1951;
   private static final double FLOZ_TO_L = 0.0284131;
   private static final double G_TO_LB = 0.00220462;
   private static final double LB_TO_G = 453.592;
   private static final double C_TO_F_MULTIPLY = 9.0/5.0; 
   private static final short C_TO_F_ADD = 32; 
   private static final double F_TO_C_MULTIPLY = 5.0/9.0;
   private static final short F_TO_C_SUB = 32;
   private static final double KEL_OFFSET = 273.15;
   
   //metric scale factors
   private static final double YOTTA = Math.pow(10, 24);
   private static final double ZETTA = Math.pow(10, 21);
   private static final double EXA = Math.pow(10, 18);
   private static final double PETA = Math.pow(10, 15);
   private static final double TERA = Math.pow(10, 12);
   private static final double GIGA = Math.pow(10, 9);
   private static final double MEGA = Math.pow(10, 6);
   private static final short KILO = 1000;
   private static final short HECTO = 100;
   private static final short DEKA = 10;
   private static final double DECI = Math.pow(10, -1);
   private static final double CENTI = Math.pow(10, -2);
   private static final double MILLI = Math.pow(10, -3);
   private static final double MICRO = Math.pow(10, -6);
   private static final double NANO = Math.pow(10, -9);
   private static final double PICO = Math.pow(10, -12);
   private static final double FEMTO = Math.pow(10, -15);
   private static final double ATTO = Math.pow(10, -18);
   private static final double ZEPTO = Math.pow(10, -21);
   private static final double YOCTO = Math.pow(10, -24); 
   
   //metric area factors from square meters
   private static final double KM2 = Math.pow(10, 6);
   private static final short HECTARE = 10000;
   
   //imperial are factors from square inch
   private static final double ACRE = 6272640;
   private static final BigInteger MI2 = new BigInteger("4014489600");
   private static final short YRD2 = 1296;
   private static final short FT2 = 144;
   
   //imperial distance factors from inches
   private static final short FEET = 12;
   private static final short YARD = 36;
   private static final int MILE = 63360;
   private static final double THOU = 0.001;
   private static final short CHAIN = 792;
   private static final short FURLONG = 7920;
   
   //imperial mass factors from pounds
   private static final double GRAIN = 1.0/7000.0;
   private static final double OUNCE = 0.0625;
   private static final short STONE = 14;
   private static final short TON = 2000;  
   
   //imperial volume factors from fluid ounces
   private static final short PINT = 20;
   private static final short QUART = 40;
   private static final short GALLON = 160;
   
   //factors for time from seconds
   private static final double YEAR = 31557600;
   private static final double MONTH = 2628000;
   private static final int WEEK = 604800;
   private static final int DAY = 86400;
   private static final short HOUR = 3600;
   private static final short MINUTE = 60;
   private static final double MILLISEC = 0.001;
   private static final double MICROSEC = Math.pow(10, -6);
   private static final double NANOSEC = Math.pow(10, -9);
   
   //factors for digital storage from bytes
   private static final double BIT = 0.125;
   private static final short KILOBYTE = 1024;
   private static final double MEGABYTE = Math.pow(2, 20);
   private static final double GIGABYTE = Math.pow(2, 30);
   private static final double TERABYTE = Math.pow(2, 40);
   private static final double PETABYTE = Math.pow(2, 50);
   
   //other constants
   private static final double ROUND = 0.0009;
   private static final short ABBREV = 3;
   private static final short METABBR = 2;
   private static final short NEG_AMT = -1;
   private static final short INVAL_CONV = -2;
   private static final short INVAL_UNIT = -3;
   private static final String NEG_AMT_MSG = 
           "Invalid! Negative lengths, masses, and volumes are not allowed.";
   private static final String INVAL_CONV_MSG = 
           "Invalid! Conversion is between two different types of measurement.";
   private static final String INVAL_UNIT_MSG = 
           "Invalid! Units are not recognized.";
   
   //global variables
   private boolean abbreviated;
   private double origAmt;
   private static String oUnits1, oUnits2, cUnits1, cUnits2, oMetScale1, 
                         oMetBase1, oMetScale2, oMetBase2, cMetScale1, 
                         cMetBase1, cMetScale2, cMetBase2, scale, base;                                 
   private static final Set<String> imperialUnits = new HashSet<>();
   private static final Set<String> volumeUnits = new HashSet<>();
   private static final Set<String> massUnits = new HashSet<>();
   private static final Set<String> lengthUnits = new HashSet<>();
   private static final Set<String> areaUnits = new HashSet<>();
   private static final Set<String> timeUnits = new HashSet<>();                
   
   /**
    * Initializes a Converter object.
    */
   public Converter() {
      abbreviated = false;
      origAmt = 0;
      oUnits1 = oUnits2 = cUnits1 = cUnits2 = oMetScale1 = oMetBase1 = 
      oMetScale2 = oMetBase2 = cMetScale1 = cMetBase1 = cMetScale2 = 
      cMetBase2 = scale = base = "";
   } 
   
   /**
    * Adds the units to their respective unit hash sets.
    */
   static {
      imperialUnits.add("inches");
      imperialUnits.add("feet");
      imperialUnits.add("yards");
      imperialUnits.add("miles");
      imperialUnits.add("thous");
      imperialUnits.add("chains");
      imperialUnits.add("furlongs");
      imperialUnits.add("pounds");
      imperialUnits.add("grains");
      imperialUnits.add("ounces");
      imperialUnits.add("tons");
      imperialUnits.add("fluid ounces");
      imperialUnits.add("pints");
      imperialUnits.add("quarts");
      imperialUnits.add("gallons");
      imperialUnits.add("acres");
      imperialUnits.add("square miles");
      imperialUnits.add("square feet");
      imperialUnits.add("square yards");
      imperialUnits.add("square inches");
      
      areaUnits.add("acres");
      areaUnits.add("square miles");
      areaUnits.add("square feet");
      areaUnits.add("square yards");
      areaUnits.add("square inches");
      
      lengthUnits.add("inches");      
      lengthUnits.add("feet");
      lengthUnits.add("yards");
      lengthUnits.add("miles");
      lengthUnits.add("thous");
      lengthUnits.add("chains");
      lengthUnits.add("furlongs");
      
      massUnits.add("pounds");
      massUnits.add("grains");
      massUnits.add("ounces");
      massUnits.add("tons");
      
      volumeUnits.add("fluid ounces");
      volumeUnits.add("pints");
      volumeUnits.add("quarts");
      volumeUnits.add("gallons");
      
      timeUnits.add("years");
      timeUnits.add("months");
      timeUnits.add("weeks");
      timeUnits.add("days");
      timeUnits.add("hours");
      timeUnits.add("months");
      timeUnits.add("seconds");
      timeUnits.add("milliseconds");
      timeUnits.add("microseconds");
      timeUnits.add("nanoseconds");
   }
   
   /**
    * Processes the input.
    * The input is a conversion statement containing the original amount, the
    * original units, the keyword "to", and the units to convert to, like "25 
    * meters to centimeters". This methods breaks down the input into parts that
    * can be manipulated
    * @param origInput the conversion statement
    */
   public void processInput(String origInput) {
      String[] splits = origInput.split(" to ");
      
      try {
         processOrigAmt(splits[0]);
         processConvUnits(splits[1]);
      }
      catch (ArrayIndexOutOfBoundsException e) {
         origAmt = -1;
      }
   }
   
   /**
    * Determines which high-level conversion method to use to convert the units.
    * @return the answer for the UI to display.
    */
   public String convert(){
      double answer1 = 0;
      double answer2 = 0;
      
      if (isByte(oUnits1) && isByte(cUnits1)) 
         answer1 = convertedByte(oUnits1, cUnits1, 0);
      if (isTime(oUnits1) && isTime(cUnits1))
         answer1 = convertedTime(oUnits1, cUnits1, 0);
      if (isTemperature(oUnits1) && isTemperature(cUnits1))
         answer1 = convertedTemperature(oUnits1, cUnits1, 0);
      if (isImperial(oUnits1) && isImperial(cUnits1))
         answer1 = convertedImpToImp(oUnits1, cUnits1, 0);
      if (isImperial(oUnits1) && isMetric(cUnits1))
         answer1 = convertedImpToMet(oUnits1, cMetScale1, cMetBase1, 0);
      if (isMetric(oUnits1) && isImperial(cUnits1))
         answer1 = convertedMetToImp(oMetScale1, oMetBase1, cUnits1, 0);
      if (isMetric(oUnits1) && isMetric(cUnits1))
         answer1 = convertedMetToMet(oMetScale1, oMetBase1, cMetScale1, 
                                     cMetBase1, 0);
      
      if (isByte(oUnits2) && isByte(cUnits2)) 
         answer2 = convertedByte(oUnits2, cUnits2, 1);
      if (isTime(oUnits2) && isTime(cUnits2))
         answer2 = convertedTime(oUnits2, cUnits2, 1);
      if (isTemperature(oUnits2) && isTemperature(cUnits2))
         answer2 = convertedTemperature(oUnits2, cUnits2, 1);
      if (isImperial(oUnits2) && isImperial(cUnits2))
         answer2 = convertedImpToImp(oUnits2, cUnits2, 1);
      if (isImperial(oUnits2) && isMetric(cUnits2))
         answer2 = convertedImpToMet(oUnits2, cMetScale2, cMetBase2, 1);
      if (isMetric(oUnits2) && isImperial(cUnits2))
         answer2 = convertedMetToImp(oMetScale2, oMetBase2, cUnits2, 1);
      if (isMetric(oUnits2) && isMetric(cUnits2))
         answer2 = convertedMetToMet(oMetScale2, oMetBase2, cMetScale2, 
                                     cMetBase2, 1);
      if (!(isImperial(oUnits1) || isMetric(oUnits1) || isTime(oUnits1)) || 
            isByte(oUnits1) || isTemperature(oUnits1)) {
         answer1 = INVAL_UNIT;
      }

      if (isComposite())
         answer1 /= answer2;
      
      return finalAnswer(formatNumber(answer1));
   }
   
   //BEGIN Input Interpretation methods
   /**
    * Processes the text from the original amount text box.
    * This method determines whether the original units are imperial or 
    * metric if the units are length, mass, volume, or area, temperature, 
    * digital storage, or time.
    * There are two types of original amount: single or composite units. If the
    * unit is composite, then the original amount and the first and second
    * original units are determined. Both original units may be more than one 
    * word like "fluid ounces". If the unit is single, then only the original 
    * amount and original unit are determined. This unit may be more than one 
    * word as well.
    * @param origInput the original amount and units in the input
    */
   private void processOrigAmt(String origInput) {
      Scanner amtScanner = new Scanner(origInput);
      String dummy; //dummy variable for "per" and "/"
      
      try {
         if ((origInput.contains("per") || origInput.contains("/")) && 
              amtScanner.hasNext()) {
            origAmt = amtScanner.nextDouble();
            oUnits1 = amtScanner.next();
            dummy = amtScanner.next();  
            while (!(dummy.equals("per") || dummy.equals("/"))) 
               oUnits1 += " " + amtScanner.next();
            oUnits2 = amtScanner.next(); 
            while (amtScanner.hasNext() && !(amtScanner.next().equals("per") || 
                   amtScanner.next().equals("/"))) 
               oUnits2 += " " + amtScanner.next();
            oUnits1 = standardUnit(oUnits1);
            oUnits2 = standardUnit(oUnits2);
         }
         else if ((!origInput.contains("per") || !origInput.contains("/")) && 
                  amtScanner.hasNext()) {
            origAmt = amtScanner.nextDouble();
            oUnits1 = amtScanner.next();
            while (amtScanner.hasNext())
               oUnits1 += " " + amtScanner.next();
            oUnits1 = standardUnit(oUnits1);
         }
      }
      catch (NoSuchElementException e) {
         oUnits1 = "invalid";
      }
      checkOrigMetric();
   }
   
   /**
    * Processes the text from the convert to units text box.
    * This method determines whether the converted units are imperial or 
    * metric if the units are length, mass, volume, or area, temperature, 
    * digital storage, or time.
    * There are two types of converted units: single or composite units. If the
    * unit is composite, then the first converted unit and the second converted
    * unit are determined. Both converted units may be more than one word like 
    * "fluid ounces". If the unit is single, then only the converted unit is 
    * determined. This unit may be more than one word as well.
    * @param convInput the units to convert to in the input
    */
   private void processConvUnits(String convInput) {
      Scanner unitScanner = new Scanner(convInput);
      String dummy; //dummy variable for "per" and "/"
      
      try {
         if ((convInput.contains("per") || convInput.contains("/")) && 
             unitScanner.hasNext()) {
            cUnits1 = unitScanner.next();
            dummy = unitScanner.next();
            while (!dummy.equals("per") && !dummy.equals("/")) 
               cUnits1 += " " + unitScanner.next();
            cUnits2 = unitScanner.next();
            while (unitScanner.hasNext() && !(unitScanner.next().equals("per") || 
                   unitScanner.next().equals("/"))) 
               cUnits2 += " " + unitScanner.next();
            cUnits1 = standardUnit(cUnits1);
            cUnits2 = standardUnit(cUnits2);
         }
         else if ((!convInput.contains("per") || !convInput.contains("/")) && 
                  unitScanner.hasNext()) {
            cUnits1 = unitScanner.next();
            while (unitScanner.hasNext())
               cUnits1 += " " + unitScanner.next();
            cUnits1 = standardUnit(cUnits1);
         }
      }
      catch (NoSuchElementException e){
         cUnits1 = "invalid";
      }
      checkConvMetric();
   }
   
   /**
    * Sets up variables for computation if the original units are metric.
    * If the unit is metric, two variables are needed to represent the metric,
    * one for the scale(mega-, centi-, etc) and another for the base unit 
    * (meters, liters, grams, square meters).
    */
   private void checkOrigMetric() {
      if (isMetric(oUnits1)) {
         parseMetricUnits(oUnits1);
         oMetScale1 = scale;                     
         oMetBase1 = base;    
         oUnits1 = oMetScale1 + oMetBase1;
         scale = base = "";       //empty temp scale and base vars
      }
      if (isMetric(oUnits2)) { 
         parseMetricUnits(oUnits1);
         oMetScale2 = scale;                     
         oMetBase2 = base;       
         oUnits2 = oMetScale2 + oMetBase2;
         scale = base = "";       //empty temp scale and base vars
      }
   }
   
   /**
    * Sets up variables for computation if the converted units are metric.
    * If the unit is metric, two variables are needed to represent the metric,
    * one for the scale(mega-, centi-, etc) and another for the base unit 
    * (meters, liters, grams, square meters).
    */
   private void checkConvMetric() {
      if (isMetric(cUnits1)) {
         parseMetricUnits(cUnits1);
         cMetScale1 = scale;                     
         cMetBase1 = base;                       
         scale = base = "";       //empty temp scale and base vars
      }
      if (isMetric(cUnits2)) {
         parseMetricUnits(cUnits2);
         cMetScale2 = scale;                     
         cMetBase2 = base;                       
         scale = base = "";       //empty temp scale and base vars
      }
   }
   
   /**
    * Parses metric units into their scale and base for computations.
    * If a unit is not recognized, then the the original amount becomes -3, 
    * effectively activating the invalid conversion error message.
    * The base is either meters, grams, or liters, or square meters. The scale 
    * ranges from yotta- to yocto- .
    * @param metricUnits the metric units to parse
    */
   private void parseMetricUnits(String metricUnits) {
      int lIndex = metricUnits.indexOf("meter");
      int mIndex = metricUnits.indexOf("gram");
      int vIndex = metricUnits.indexOf("liter");
      
      if (metricUnits.length() == METABBR) { //standardize the abbreviation
         scale = prefixCheck(metricUnits.substring(0, 1)); 
         base = abbrevUnit(metricUnits.substring(1, 2));
      }
      else if (metricUnits.length() == 1) {
         base = abbrevUnit(metricUnits);
      }
      else if (metricUnits.equals("hectares")) {
         scale = metricUnits; //set only scale 
      } 
      else if (lIndex != -1) {   //also used for areas
         scale = metricUnits.substring(0, lIndex);
         base = metricUnits.substring(lIndex);
      }
      else if (mIndex != -1) {
         scale = metricUnits.substring(0, mIndex);
         base = metricUnits.substring(mIndex);
      }
      else if (vIndex != -1) {
         scale = metricUnits.substring(0, vIndex);
         base = metricUnits.substring(vIndex);
      } 
      else
         origAmt = -3;
   }
   
   /**
    * Standardizes the units.
    * A standardized unit is plural, spelled out, lowercase, and devoid of 
    * extraneous characters like periods. This method is used for 
    * computation purposes.
    * @param units the units to check if standard or not
    * @return the units in standard form
    */
   private String standardUnit(String units) {
      String standard = units.replace(".", "");
      boolean expCase, temperature;
      
      abbreviated = false;
      standard = abbrevUnit(standard);
      standard = standard.toLowerCase();
      expCase = standard.contains("inches") || standard.contains("feet");
      temperature = standard.equals("fahrenheit") || standard.equals("celsius") || standard.equals("kelvin");
      
      switch (units) {              //exceptional cases
         case "inch": standard = "inches";
                      expCase = true;
            break;
         case "foot": standard = "feet";
                      expCase = true;
            break;
      } 
      if (!standard.endsWith("s") && expCase == false && temperature == false && 
              abbreviated == false) //make the units plural
         standard = units.concat("s");
      
      return standard;
   }
   
   /** 
    * "Unpacks" the abbreviation of a unit if abbreviated. 
    * @param candidate the unit that might be abbreviated
    * @return the "unpacked" abbreviation, the candidate unit if it was not 
    * an abbreviated unit.
    */
   private String abbrevUnit(String candidate) {
      String abbrev;
      
      abbreviated = true;
      switch (candidate) {
         case "in":     abbrev = "inches";
            break;
         case "ft":     abbrev = "feet";
            break;
         case "yd":     abbrev = "yards";
            break;
         case "mi":     abbrev = "miles";
            break;
         case "ch":     abbrev = "chains";
            break;
         case "fur":    abbrev = "furlongs";
            break;
         case "sq mi":  abbrev = "square miles";
            break;
         case "ac":     abbrev = "acres";
            break;
         case "sq yrd": abbrev = "square yards";
            break;
         case "sq ft":  abbrev = "square feet";
            break;
         case "sq in":  abbrev = "square inches";
            break;
         case "ha":     abbrev = "hectares";
            break;
         case "sq m":   abbrev = "square meters";
            break;
         case "sq km":  abbrev = "square kilometers";
            break;
         case "lb":     abbrev = "pounds";
            break;
         case "gr":     abbrev = "grains";
            break;
         case "oz":     abbrev = "ounces";
            break;
         case "fl oz":  abbrev = "fluid ounces";
            break;
         case "pt":     abbrev = "pints";
            break;
         case "qt":     abbrev = "quarts";
            break;
         case "gal":    abbrev = "gallons";
            break;
         case "m":      abbrev = "meters";
            break;
         case "g":      abbrev = "grams";
            break;
         case "l":      abbrev = "liters";
            break;
         case "C":      abbrev = "celsius";
            break;
         case "F":      abbrev = "fahrenheit";
            break;
         case "K":      abbrev = "kelvin";
            break;
         case "PB":     abbrev = "petabytes";
            break;
         case "TB":     abbrev = "terabytes";
            break;
         case "GB":     abbrev = "gigabytes";
            break;
         case "MB":     abbrev = "megabytes";
            break;
         case "kB":     abbrev = "kilobytes";
            break;
         case "b":      abbrev = "bits";
            break;
         case "yrs":    abbrev = "years";
            break;
         case "yr":     abbrev = "years";
            break;
         case "mo":     abbrev = "months";
            break;
         case "mth":    abbrev = "months";
            break;
         case "wk":     abbrev = "weeks";
            break;
         case "hr":     abbrev = "hours";
            break;
         case "min":    abbrev = "minutes";
            break;
         case "d":      abbrev = "days";
            break;
         case "ms":     abbrev = "milliseconds";
            break;
         case "ns":     abbrev = "nanoseconds";
            break;
          case "sec":   abbrev = "seconds";
            break;
         default:       abbrev = candidate;
                        abbreviated = false;
      }
      if (candidate.length() <= ABBREV && !candidate.equals("day")) 
         abbreviated = true;
      //check for day since it's an exception to the abbreviation condition
      return abbrev;
   }
   
   /**
    * "Unpacks" the scale of metric unit when it's abbreviated.
    * @param candidate the letter that represents the scale
    * @return the "unpacked" prefix for an abbreviated metric amount.
    */
   private String prefixCheck(String candidate) {
      String prefix = "";
      
      switch (candidate) {
         case "T": prefix = "tera";
            break;
         case "G": prefix = "giga";
            break;
         case "M": prefix = "mega";
            break;
         case "k": prefix = "kilo";
            break;
         case "h": prefix = "hecto";
            break;    
         case "d": prefix = "deci";
            break;
         case "c": prefix = "centi";
            break;
         case "m": prefix = "milli";
            break;
         case "u": prefix = "micro";
            break;
         case "n": prefix = "nano";
            break;
         case "p": prefix = "pico";
            break;      
      }
      return prefix;
   }
   //END Input Interpretation methods
   
   //BEGIN High-Level Conversion methods 
   /**
    * Converts an imperial amount to another imperial amount for lengths, areas,
    * masses, and volumes.
    * @param oUnits the original imperial units
    * @param cUnits the imperial units to convert to
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return The converted imperial amount if both units are the same type
    * (length, mass, volume, area) and positive. Returns an error number
    * otherwise.
    */
   private double convertedImpToImp(String oUnits, String cUnits, int flag) {
      double convAmt = origAmt;
      double answer;
      
      if (flag == 1)
         convAmt = 1;
      if (convAmt < 0)
         answer = NEG_AMT;
      else if (isLength(oUnits, cUnits)) 
         answer = convertedImpLen(baseImpLen(convAmt, oUnits), cUnits);
      else if (isArea(oUnits, cUnits)) 
         answer = convertedImpArea(baseImpArea(convAmt, oUnits), cUnits);
      else if (isMass(oUnits, cUnits)) 
         answer = convertedImpMass(baseImpMass(convAmt, oUnits), cUnits);
      else if (isVolume(oUnits, cUnits)) 
         answer = convertedImpVol(baseImpVol(convAmt, oUnits), cUnits);
      else  
         answer = INVAL_CONV;
      
      return answer;
   }
   
   /**
    * Converts an imperial amount to a metric amount for lengths, areas, masses, 
    * and volumes.
    * @param oUnits the original imperial unit
    * @param cMetScale the scale of the metric unit to convert to
    * @param cMetBase the base of the metric unit to convert to
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return The converted metric amount if both units are the same type
    * (length, mass, volume, area) and positive. Returns an error number
    * otherwise.
    */
   private double convertedImpToMet(String oUnits, String cMetScale, 
                                    String cMetBase, int flag) {
      double convAmt = origAmt;
      double temp;
      double answer;
      
      if (flag == 1)
         convAmt = 1;
      if (convAmt < 0)          
         answer = NEG_AMT;
      else if (isLength(oUnits, cMetBase)) {
         temp = impMetLen(baseImpLen(convAmt, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isArea(oUnits, cMetBase)) {
         temp = impMetArea(baseImpArea(convAmt, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isMass(oUnits, cMetBase)) {
         temp = impMetMass(baseImpMass(convAmt, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isVolume(oUnits, cMetBase)) {
         temp = impMetVol(baseImpVol(convAmt, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else                          
         answer = INVAL_CONV;
      
      return answer;
   }
   
   /**
    * Converts a metric amount to an imperial amount for lengths, areas, masses, 
    * and volumes.
    * @param oMetScale the scale of the original metric unit
    * @param oMetBase the base of the original metric unit
    * @param cUnits the imperial unit to convert to
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return The converted imperial amount if both units are the same type
    * (length, mass, volume, area) and positive. Returns an error number
    * otherwise
    */
   private double convertedMetToImp(String oMetScale, String oMetBase, 
                                    String cUnits, int flag) {
      double convAmt = origAmt;
      double temp;
      double answer;
      
      if (flag == 1)
         convAmt = 1;
      if (convAmt < 0)        
         answer = NEG_AMT;
      else if (isLength(oMetBase, cUnits)) {
         temp = impMetLen(scaledDownMetric(convAmt, oMetScale), 1);
         answer = convertedImpLen(temp, cUnits);
      }
      else if (isArea(oMetBase, cUnits)) {
         temp = impMetArea(scaledDownMetric(convAmt, oMetScale), 1);
         answer = convertedImpArea(temp, cUnits);
      }
      else if (isMass(oMetBase, cUnits)) {
         temp = impMetMass(scaledDownMetric(convAmt, oMetScale), 1);
         answer = convertedImpMass(temp, cUnits);
      }
      else if (isVolume(oMetBase, cUnits)) {
         temp = impMetVol(scaledDownMetric(convAmt, oMetScale), 1);
         answer = convertedImpVol(temp, cUnits);
      }
      else                
         answer = INVAL_CONV;
      
      return answer;
   }
   
   /**
    * Converts a metric amount to another metric amount.
    * @param oMetScale the scale of the original metric unit
    * @param oMetBase the base of the original metric unit
    * @param cMetScale the scale of the metric unit to convert to
    * @param oMetBase the base of the metric unit to convert to
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return The converted metric amount if both units are the same type
    * (length, mass, volume, area) and positive. Returns an error number
    * otherwise.
    */
   private double convertedMetToMet(String oMetScale, String oMetBase, 
                                    String cMetScale, String cMetBase, 
                                    int flag) {
      double convAmt = origAmt;
      double answer;
      
      if (flag == 1)
         convAmt = 1;
      if (convAmt < 0) 
         answer = NEG_AMT;
      else if ((isLength(oMetBase,cMetBase)) || (isMass(oMetBase, cMetBase)) || 
              (isVolume(oMetBase, cMetBase)) || (isArea(oMetBase, cMetBase))) 
         answer = scaledUpMetric(scaledDownMetric(convAmt, oMetScale), 
                                 cMetScale);
      else                          
         answer = INVAL_CONV;
      
      return answer;
   } 
   
   /**
    * Converts digital storage amounts.
    * Negative storage amounts are not allowed.
    * @param oUnits the original storage unit
    * @param cUnits the converted storage unit
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return the converted storage amount
    */
   private double convertedByte(String oUnits, String cUnits, int flag) {
      double convAmt = origAmt;
      double convertedByte;
      
      if (flag == 1)
         convAmt = 1;
      
      convertedByte = scaledUpByte(scaledDownByte(convAmt, oUnits), cUnits);
      
      if (convAmt < 0) 
         convertedByte = NEG_AMT;
      
      return convertedByte;
   }
   
   /**
    * Converts time amounts.
    * Negative times are not allowed.
    * @param oUnits the original time unit
    * @param cUnits the converted time unit
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return the converted time amount
    */
   private double convertedTime(String oUnits, String cUnits, int flag) {
      double convAmt = origAmt;
      double convertedTime;
      
      if (flag == 1)
         convAmt = 1;
      
      convertedTime = scaledUpTime(scaledDownTime(convAmt, oUnits), cUnits);
      
      if (convAmt < 0) 
         convertedTime = NEG_AMT;
      
      return convertedTime;
   }
   
   /**
    * Converts temperature amounts.
    * Negative temperatures are allowed.
    * @param oUnits the original temperature unit
    * @param cUnits the converted temperature unit
    * @param flag numerator or denominator of the composite unit. 0 if the unit
    * is the numerator or if the unit is not composite, 1 if the unit is the 
    * denominator.
    * @return 
    */
   private double convertedTemperature(String oUnits, String cUnits, int flag) {
      double convAmt = origAmt;
      double answer = 0;
      
      if (flag == 1)
         convAmt = 1;
      if (oUnits.equals("fahrenheit") && cUnits.equals("celsius"))
         answer = convertedFahCel(convAmt, 0);
      else if (oUnits.equals("celsius") && cUnits.equals("fahrenheit")) 
         answer = convertedFahCel(convAmt, 1);
      else if (oUnits.equals("fahrenheit") && cUnits.equals("kelvin")) 
         answer = convertedFahKel(convAmt, 0);
      else if (oUnits.equals("kelvin") && cUnits.equals("fahrenheit")) 
         answer = convertedFahKel(convAmt, 1);
      else if (oUnits.equals("celsius") && cUnits.equals("kelvin"))
         answer = convertedCelKel(convAmt, 0);
      else if (oUnits.equals("kelvin") &&  cUnits.equals("celsius"))
         answer = convertedCelKel(convAmt, 1);
      
      return answer;
   }
   //END High-Level Conversion methods 
   
   //BEGIN Low-Level Conversion Methods
   /**
    * Converts the original imperial distance into inches.
    * Note that inches are the base imperial distance units. Negative distances
    * are not allowed.
    * @param origAmt original length value
    * @param origUnit original length unit
    * @return original distance value in inches
    */
   private static double baseImpLen(double origAmt, String origUnit) {
      double inches = 0;  
      
      switch (origUnit) {
         case "inches":    inches = origAmt;
            break;
         case "feet":      inches = origAmt * FEET;
            break;
         case "yards":     inches = origAmt * YARD;
            break;
         case "miles":     inches = origAmt * MILE;
            break;
         case "thous":     inches = origAmt * THOU;
            break;
         case "chains":    inches = origAmt * CHAIN;
            break;
         case "furlongs":  inches = origAmt * FURLONG;
            break;
      }    
      return inches;
   }
   
   /**
    * Converts the original imperial area to square inches.
    * Note that inches are the base imperial distance units. Negative areas
    * are not allowed.
    * @param origAmt original area value
    * @param origUnit original area units
    * @return original area in square inches
    */
   private static double baseImpArea(double origAmt, String origUnit) {
      double squareIn = 0;
      
      switch (origUnit) {
         case "square inches":   squareIn = origAmt;
            break;
         case "square feet":     squareIn = origAmt * FT2;
            break;
         case "square yards":    squareIn = origAmt * YRD2;
            break;
         case "acres":           squareIn = origAmt * ACRE;
            break;
         case "square miles":    squareIn = origAmt * MI2.doubleValue();
            break;
      }
      
      return squareIn;
   }
   
   /**
    * Converts the original imperial mass into pounds.
    * Note that pounds are the base imperial mass units. Negative masses are 
    * not allowed.
    * @param origAmt original imperial mass value
    * @param origUnit original imperial mass units
    * @return original imperial mass value in pounds
    */
   private static double baseImpMass(double origAmt, String origUnit) {
      double pounds = 0;     
      
      switch (origUnit) {
         case "pounds": pounds = origAmt;
            break;
         case "grains": pounds = origAmt * GRAIN;
            break;
         case "ounces": pounds = origAmt * OUNCE;
            break;
         case "stones": pounds = origAmt * STONE;
            break;
         case "tons":   pounds = origAmt * TON;
            break;
      }  
      
      return pounds;
   }
   
   /**
    * Converts the original imperial volume into fluid ounces.
    * Note that fluid ounces are the base imperial volume units. Negative 
    * volumes are not allowed.
    * @param origAmt original imperial volume value
    * @param origUnit original imperial volume units
    * @return original imperial volume in fluid ounces
    */
   private static double baseImpVol(double origAmt, String origUnit) {
      double floz = 0;
      
      switch (origUnit) {
         case "fluid ounces": floz = origAmt;
            break;
         case "pints":        floz = origAmt * PINT;
            break;
         case "quarts":       floz = origAmt * QUART;
            break;
         case "gallons":      floz = origAmt * GALLON;
            break;
      }   
      
      return floz;
   }
   
   /**
    * Converts inches to the converted imperial length.
    * Note that inches are the base imperial length units.
    * @param inches the length in inches
    * @param convertUnit the converted imperial length units
    * @return the converted length in imperial units
    */
   private static double convertedImpLen(double inches, String convertUnit) {
      double convertImpLen = 0;
      
      switch (convertUnit) {
         case "inches":    convertImpLen = inches;
            break;
         case "feet":      convertImpLen = inches / FEET;
            break;
         case "yards":     convertImpLen = inches / YARD;
            break;
         case "miles":     convertImpLen = inches / MILE;
            break;
         case "thous":     convertImpLen = inches / THOU;
            break;
         case "chains":    convertImpLen = inches / CHAIN;
            break;
         case "furlongs":  convertImpLen = inches / FURLONG;
            break;
      }
      return convertImpLen;
   }
   
   /**
    * Converts square inches to the converted imperial area.
    * Note that inches are the base imperial area units.
    * @param squareIn the area in square inches
    * @param convertUnit the converted imperial distance units
    * @return the converted area in imperial units
    */
   private static double convertedImpArea(double squareIn, String convertUnit) {
      double convertImpArea = 0;
      
      switch (convertUnit) {
         case "square inches":   convertImpArea = squareIn;
            break;
         case "square feet":     convertImpArea = squareIn / FT2;
            break;
         case "square yards":    convertImpArea = squareIn / YRD2;
            break;
         case "acres":           convertImpArea = squareIn / ACRE;
            break;
         case "square miles":    convertImpArea = squareIn / MI2.doubleValue();
            break;
      }
      return convertImpArea;
   }
   
   /**
    * Converts pounds to the converted imperial mass.
    * Note that pounds are the base imperial mass units. 
    * @param pounds the mass in pounds
    * @param convertUnit the converted imperial mass units
    * @return the converted mass in imperial units
    */
   private static double convertedImpMass(double pounds, String convertUnit) {
      double convertImpMass = 0;
      
      switch (convertUnit) {
         case "pounds": convertImpMass = pounds;
            break;
         case "grains": convertImpMass = pounds / GRAIN;
            break;
         case "ounces": convertImpMass = pounds / OUNCE;
            break;
         case "stones": convertImpMass = pounds / STONE;
            break;
         case "tons":   convertImpMass = pounds / TON;
            break;
      }
      return convertImpMass;
   }
   
   /**
    * Converts fluid ounces to the converted imperial volume.
    * Note that fluid ounces are the base imperial volume units.
    * @param floz the volume in fluid ounces
    * @param convertUnit the converted imperial volume units
    * @return the converted volume in imperial units
    */
   private static double convertedImpVol(double floz, String convertUnit) {
      double convertImpVol = 0;
      
      switch (convertUnit) {
         case "fluid ounces": convertImpVol = floz;
            break;
         case "pints":        convertImpVol = floz / PINT;
            break;
         case "quarts":       convertImpVol = floz / QUART;
            break;
         case "gallons":      convertImpVol = floz / GALLON;
            break;
      }
      return convertImpVol;
   }
   
   /**
    * Scales the original metric amount to the base metric amount.
    * For example, the amount in yottameters becomes the amount in meters, 
    * kilogram amount to gram amount, etc.
    * @param origMet original metric amount
    * @param fromScale original metric scale (kilo-, yotto-)
    * @return original metric amount in the base metric amount
    */
   private static double scaledDownMetric(double origMet, String fromScale){
      double baseMetric = 0;
      
      switch (fromScale) {
         case "":             baseMetric = origMet;      //meters, grams, liters
            break;
         case "square ":      baseMetric = origMet;
            break;
         case "hectares":      baseMetric = origMet * HECTARE;
            break;
         case "square kilo":  baseMetric = origMet * KM2;
            break;
         case "yotta":        baseMetric = origMet * YOTTA;
            break;
         case "zetta":        baseMetric = origMet * ZETTA;
            break;
         case "exa":          baseMetric = origMet * EXA;
            break;
         case "peta":         baseMetric = origMet * PETA;
            break;
         case "tera":         baseMetric = origMet * TERA;
            break;
         case "giga":         baseMetric = origMet * GIGA;
            break;
         case "mega":         baseMetric = origMet * MEGA;
            break;
         case "kilo":         baseMetric = origMet * KILO;
            break;
         case "hecto":        baseMetric = origMet * HECTO;
            break;
         case "deka":         baseMetric = origMet * DEKA;
            break;
         case "deci":         baseMetric = origMet * DECI;
            break;
         case "centi":        baseMetric = origMet * CENTI;
            break;
         case "milli":        baseMetric = origMet * MILLI;
            break;
         case "micro":        baseMetric = origMet * MICRO;
            break;
         case "nano":         baseMetric = origMet * NANO;
            break;
         case "pico":         baseMetric = origMet * PICO;
            break;
         case "femto":        baseMetric = origMet * FEMTO;
            break;
         case "atto":         baseMetric = origMet * ATTO;
            break;
         case "zepto":        baseMetric = origMet * ZEPTO;
            break;
         case "yocto":        baseMetric = origMet * YOCTO;
            break;
      }
      
      return baseMetric;
   }
   
   /**
    * Scales up the base metric amount to the final conversion amount.
    * For example, meters scales up to kilometers, liters to megaliters, etc.
    * @param baseAmount amount in basic metric units (meters, liters, grams)
    * @param toScale the final conversion unit(zettameters, dekaliters, etc.)
    * @return the final conversion amount
    */
   private static double scaledUpMetric(double baseMetric, String toScale) {
      double upScale = 0;
      
      switch (toScale) {
         case "":             upScale = baseMetric;         //meters, grams, liters
            break;
         case "square ":      upScale = baseMetric;
            break;
         case "hectares":      upScale = baseMetric / HECTARE;
            break;
         case "square kilo":  upScale = baseMetric / KM2;
            break;
         case "yotta":        upScale = baseMetric / YOTTA;
            break;
         case "zetta":        upScale = baseMetric / ZETTA;
            break;
         case "exa":          upScale = baseMetric / EXA;
            break;
         case "peta":         upScale = baseMetric / PETA;
            break;
         case "tera":         upScale = baseMetric / TERA;
            break;
         case "giga":         upScale = baseMetric / GIGA;
            break;
         case "mega":         upScale = baseMetric / MEGA;
            break;
         case "kilo":         upScale = baseMetric / KILO;
            break;
         case "hecto":        upScale = baseMetric / HECTO;
            break;
         case "deka":         upScale = baseMetric / DEKA;
            break;
         case "deci":         upScale = baseMetric / DECI;
            break;
         case "centi":        upScale = baseMetric / CENTI;
            break;
         case "milli":        upScale = baseMetric / MILLI;
            break;
         case "micro":        upScale = baseMetric / MICRO;
            break;
         case "nano":         upScale = baseMetric / NANO;
            break;
         case "pico":         upScale = baseMetric / PICO;
            break;
         case "femto":        upScale = baseMetric / FEMTO;
            break;
         case "atto":         upScale = baseMetric / ATTO;
            break;
         case "zepto":        upScale = baseMetric / ZEPTO;
            break;
         case "yocto":        upScale = baseMetric / YOCTO;
            break;
      }
      return upScale;
   }
   
   /**
    * Scales the original time amount to seconds.
    * @param origTime original time amount
    * @param fromTime original time unit
    * @return original time amount in seconds
    */
   private static double scaledDownTime(double origTime, String fromTime){
      double downScale = 0;
      
      switch (fromTime) {
         case "seconds":      downScale = origTime;
            break;
         case "years":        downScale = origTime * YEAR;
            break;
         case "months":       downScale = origTime * MONTH;
            break;
         case "weeks":        downScale = origTime * WEEK;
            break;
         case "days":         downScale = origTime * DAY;
            break;
         case "hours":        downScale = origTime * HOUR;
            break;
         case "minutes":      downScale = origTime * MINUTE;
            break;
         case "milliseconds":  downScale = origTime * MILLISEC;
            break;
         case "microseconds":  downScale = origTime * MICROSEC;
            break;
         case "nanoseconds":   downScale = origTime * NANOSEC;
            break;
      }
      return downScale;
   }
   
   /**
    * Scales up the seconds to the final conversion amount.
    * @param seconds amount in seconds
    * @param toTime the final conversion unit
    * @return the final conversion amount
    */
   private static double scaledUpTime(double seconds, String toTime){
      double upScale = 0;
      
      switch (toTime) {
         case "seconds":      upScale = seconds;
            break;
         case "years":        upScale = seconds / YEAR;
            break;
         case "months":       upScale = seconds / MONTH;
            break;
         case "weeks":        upScale = seconds / WEEK;
            break;
         case "days":         upScale = seconds / DAY;
            break;
         case "hours":        upScale = seconds / HOUR;
            break;
         case "minutes":      upScale = seconds / MINUTE;
            break;
         case "milliseconds": upScale = seconds / MILLISEC;
            break;
         case "microseconds": upScale = seconds / MICROSEC;
            break;
         case "nanoseconds":  upScale = seconds / NANOSEC;
            break;
      }
      
      return upScale;
   }
   
   /**
    * Scales the original byte amount to the base byte amount.
    * For example, the amount in gigabytes becomes the amount in bytes.
    * @param origByte original byte amount like 27, 256, etc
    * @param fromScale original byte scale (terabyte, bit, etc)
    * @return original byte amount in the base byte amount
    */
   private static double scaledDownByte(double origByte, String fromScale) {
      double baseByte = 0;
      
      switch (fromScale) {
         case "bytes":     baseByte = origByte;
            break;
         case "bits":      baseByte = origByte * BIT;
            break;
         case "kilobytes": baseByte = origByte * KILOBYTE;
            break;
         case "megabytes": baseByte = origByte * MEGABYTE;
            break;
         case "gigabytes": baseByte = origByte * GIGABYTE;
            break;
         case "terabytes": baseByte = origByte * TERABYTE;
            break;
         case "petabytes": baseByte = origByte * PETABYTE;
            break;
      }
      return baseByte;
   }
   
   /**
    * Scales up the base byte amount to the final conversion amount.
    * For example, megabytes scales up to gigabytes, etc.
    * @param baseByte amount in bytes
    * @param toScale the final conversion unit(petabytes, kilobytes, etc)
    * @return final conversion amount
    */
   private static double scaledUpByte(double baseByte, String toScale) {
      double upScale = 0;
      
      switch (toScale) {
         case "bytes":     upScale = baseByte;
            break;
         case "bits":      upScale = baseByte / BIT;
            break;
         case "kilobytes": upScale = baseByte / KILOBYTE;
            break;
         case "megabytes": upScale = baseByte / MEGABYTE;
            break;
         case "gigabytes": upScale = baseByte / GIGABYTE;
            break;
         case "terabytes": upScale = baseByte / TERABYTE;
            break;
         case "petabytes": upScale = baseByte / PETABYTE;
            break;
      }
      return upScale;
   }
   
   /**
    * Converts imperial lengths to metric lengths and vice versa.
    * After the imperial length amount has been converted to inches, it is
    * converted to meters. Likewise, after the metric length amount has 
    * been converted to meters, it is converted to inches.
    * @param length the length in inches or meters
    * @param flag 0 if converting inches to meters, 1 if converting meters
    * to inches
    * @return inch amount in meters or meter amount in inches.
    */  
   private static double impMetLen(double length, int flag) {
      double answer = 0;
      
      if (flag == 0) 
         answer = length * IN_TO_M;  
      else if (flag == 1)
         answer = length * M_TO_IN;
      
      return answer;  
   }
   
   /**
    * Converts imperial areas to metric areas and vice versa.
    * After the imperial area amount has been converted to square inches, it is
    * converted to square meters. Likewise, after the metric area amount 
    * has been converted to square meters, it is converted to square inches.
    * @param length the area in square inches or square meters
    * @param flag 0 if converting square inches to square meters, 1 if 
    * converting square meters to square inches
    * @return square inch amount in square meters or square meter amount in 
    * square inches.
    */
   private static double impMetArea(double area, int flag) {
      double answer = 0;
      
      if (flag == 0) 
         answer = area * IN2_TO_M2;  
      else if (flag == 1)
         answer = area * M2_TO_IN2;
      
      return answer;
   }
   
   /**
    * Converts imperial volumes to metric volumes and vice versa.
    * After the imperial volume amount has been converted to fluid ounces, it is
    * converted to liters. Likewise, after the metric volume amount has 
    * been converted to liters, it is converted to fluid ounces.
    * @param volume the volume in fluid ounces or liters
    * @param flag 0 if converting fluid ounces to liters, 1 if converting liters
    * to fluid ounces
    * @return fluid ounce amount in liters or liter amount in fluid ounces.
    */   
   private static double impMetVol(double volume, int flag) {
      double answer = 0;
      
      if (flag == 0) 
         answer = volume * FLOZ_TO_L;  
      else if (flag == 1)
         answer = volume * L_TO_FLOZ;
      
      return answer;   
   }

   /**
    * Converts imperial masses to metric masses and vice versa.
    * After the imperial mass amount has been converted to pounds, it is
    * converted to grams. Likewise, after the metric mass amount has been 
    * converted to grams, it is converted to pounds.
    * @param mass the mass in pounds or grams
    * @param flag 0 if converting pounds to grams, 1 if converting grams to 
    * pounds
    * @return pound amount in grams or gram amount in pounds
    */   
   private static double impMetMass(double mass, int flag) {
      double answer = 0;
      
      if (flag == 0) 
         answer = mass * LB_TO_G;  
      else if (flag == 1)
         answer = mass * G_TO_LB;
      
      return answer;  
   }
   
   /**
    * Converts a Fahrenheit temperature to Celsius temperature and vice versa.
    * @param temp temperature amount to convert, is either Fahrenhiet or Celsius
    * @param flag 0 if from Fahrenheit to Celsius, 1 if from Celsius to 
    * Fahrenheit
    * @return the Fahrenheit temperature in Celsius or the Celsius temperature 
    * in Fahrenheit
    */
   private double convertedFahCel(double temp, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = (temp - F_TO_C_SUB) * F_TO_C_MULTIPLY;
      else
         answer = (temp * C_TO_F_MULTIPLY) + C_TO_F_ADD;
      
      return answer;   
   }
   
   /**
    * Converts a Fahrenheit temperature to Kelvin temperature and vice versa,
    * also assembles the final answer.
    * @param temp temperature amount to convert, is either Fahrenheit or Kelvin
    * @param flag 0 if from Fahrenheit to Kelvin, 1 if from Kelvin to 
    * Fahrenheit
    * @return the Fahrenheit temperature in Kelvin or the Kelvin temperature 
    * in Fahrenheit
    */
   private double convertedFahKel(double temp, int flag) {
      double answer;
      
      if (flag == 0) {
         double celsius = convertedFahCel(temp, 0);
         answer = celsius + KEL_OFFSET;
      }
      else
         answer = convertedFahCel(temp - KEL_OFFSET, 1);
      
      return answer;      
   }
   
   /**
    * Converts a Celsius temperature to Kelvin temperature and vice versa,
    * also assembles the final answer.
    * @param temp temperature amount to convert, is either Celius or Kelvin
    * @param flag 0 if from Celsius to Kelvin, 1 if from Kelvin to 
    * Celsius
    * @return the Celsius temperature in Kelvin or the Kelvin temperature 
    * in Celsius
    */
   private double convertedCelKel(double temp, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = temp + KEL_OFFSET;
      else
         answer = temp - KEL_OFFSET;
      
      return answer; 
   }
   //END Low-Level Conversion methods
   
   //BEGIN Conversion Type methods
   /**
    * Determines if the conversion is between lengths.
    * @param unit1 first possible length unit
    * @param unit2 second possible length unit
    * @return true if the conversion is between lengths, false otherwise
    */
   private boolean isLength(String unit1, String unit2) {  
      return (lengthUnits.contains(unit1) || unit1.contains("meters")) &&
              (lengthUnits.contains(unit2) || unit2.contains("meters")); 
   }
   
   /**
    * Determines if the conversion is between areas.
    * Checks if a unit is empty as a workaround for how hectares are currently
    * implemented.
    * @param unit1 first possible area unit
    * @param unit2 second possible area unit
    * @return true if the conversion is between areas, false otherwise
    */
   private boolean isArea(String unit1, String unit2) {
      return (areaUnits.contains(unit1) || unit1.equals("hectares") || 
              unit1.contains("meters") || unit1.isEmpty()) && 
              (areaUnits.contains(unit2) || unit2.equals("hectares") || 
              unit2.contains("meters") || unit2.isEmpty());
   }
   
   /**
    * Determines if the conversion is between masses.
    * @param unit1 first possible mass unit
    * @param unit2 second possible mass unit
    * @return true if the conversion is between masses, false otherwise
    */
   private boolean isMass(String unit1, String unit2) {  
      return (massUnits.contains(unit1) || unit1.contains("grams")) &&
              (massUnits.contains(unit2) || unit2.contains("grams")); 
   }
   
   /**
    * Determines if the conversion is between volumes.
    * @param unit1 first possible volume unit
    * @param unit2 second possible volume unit
    * @return true if the conversion is between volumes, false otherwise
    */
   private boolean isVolume(String unit1, String unit2) {  
      return (volumeUnits.contains(unit1) || unit1.contains("liters")) &&
              (volumeUnits.contains(unit2) || unit2.contains("liters"));
   } 
   
   /**
    * Determines if the unit is for digital storage.
    * @param candidate possible digital storage unit
    * @return true if the unit is for digital storage, false otherwise.
    */
   private boolean isByte(String candidate) {
      return candidate.contains("bit") || candidate.contains("byte") && 
             !candidate.isEmpty();
   }
   
   /**
    * Determines if the unit is for time.
    * @param candidate possible time unit
    * @return true if the unit is for time, false otherwise. 
    */
   private boolean isTime(String candidate) {
      return timeUnits.contains(candidate) && !candidate.isEmpty();
   }
   
   /**
    * Determines if the unit is for temperature.
    * @param candidate is for temperature
    * @return true if the unit is for temperature, false otherwise.
    */
   private boolean isTemperature(String candidate) {
      
      return candidate.equals("fahrenheit") || candidate.equals("celsius") || 
              candidate.equals("kelvin") && !candidate.isEmpty();
   }
   
   /**
    * Determines if a unit is imperial and not empty.
    * @param candidate the unit that might be imperial
    * @return true if the unit is an imperial unit, false otherwise
    */
   private boolean isImperial(String candidate) {
      return imperialUnits.contains(candidate) && !candidate.isEmpty();
   }
   
   /**
    * Determines if a unit is metric and not empty.
    * A unit is metric if 
    * 1) it contains meters, liters, or grams OR
    * 2) it is an abbreviation that ends in m, g, or l
    * 3) it is hectares
    * AND the unit is not empty.
    * @param candidate the unit that might be metric
    * @return true if the unit is a metric unit, false otherwise
    */
   private boolean isMetric(String candidate) {
      return (((candidate.contains("meters") || candidate.contains("liters") ||
              candidate.contains("grams")) || (candidate.length() == METABBR && 
             (candidate.endsWith("m") ||  candidate.contains("g") || 
              candidate.contains("l")))) || candidate.equals("hectares")) && 
              !candidate.isEmpty();
   }
   
   /**
    * Determines whether the conversion is between two composite units.
    * @return true if the conversion is between two composite units, false
    * otherwise
    */
   private boolean isComposite() {
      return !oUnits2.isEmpty() && !cUnits2.isEmpty();
   }
   //END Conversion Type methods
   
   //BEGIN Output Format methods
   /**
    * Formats the number.
    * A formated number is in the form of xx,xxx.xx. Therefore, the number will
    * be rounded and will have commas if they need to be.
    * Rounding conditions:
    * 1) Rounds to a whole number when the decimal number is close enough to an 
    *    integer, in this case, +xx.0009. 
    * 2) Leaves unrounded if it is a miniscule amount, in this case, if the 
    *    number is less than 0.0009.
    * 3) Rounds to three decimal places. May be one or two decimal places if 
    *    the last digit(s) is zero.
    * @param conversion the number to possibly round
    * @return fAnswer the final answer (may or may not be rounded)
    */
   private String formatNumber(double conversion) {
      Double iAnswer = conversion;
      String fAnswer;
      String a = Double.toString(conversion);                     
      //get digits right of decimal point ( .xxxx ) for comparisons
      double decimals = Double.parseDouble(a.substring(a.indexOf(".")));
      
      if (iAnswer.intValue() != 0 && decimals < ROUND)            //condition 1
         fAnswer = String.format("%,d%n", iAnswer.intValue()).trim();          
      else if (iAnswer.intValue() == 0 && decimals < ROUND)       //condition 2
         fAnswer = Double.toString(iAnswer);                      
      else {                                                      //condition 3
         DecimalFormat myFormatter = new DecimalFormat("###,###.###");
         fAnswer = myFormatter.format(iAnswer);
      }
      return fAnswer;
   } 
   
   /**
    * Assembles the final answer.
    * The converted amount and units are turned into an output string.
    * The first set of if statements apply to conversion that end up as one,
    * like "100 cm to m".
    * The second set of if statement are for determining the output of the units.
    * If the unit is composite, the second unit must become singular
    * @param ansAmt the converted amount
    * @param ansUnits the converted units
    * @return the converted amount and units
    */
   private String finalAnswer(String ansAmt) {
      boolean single = ansAmt.equals("1"); //is converted amount = 1 unit?
      String fAnswer;
      String ansUnits = "";
      
      if (single && cUnits1.equals("feet") ) 
         cUnits1 = "foot";
      if (single && cUnits2.equals("feet"))
         cUnits2 = "foot";
      if (single && (cUnits1.equals("inches"))) 
         cUnits1 = "inch";
      if (single && cUnits2.equals("inches"))
         cUnits2 = "inch";
      if (single) {
         int i = ansUnits.indexOf("s");
         ansUnits = ansUnits.substring(0, i);
      }
        
      //single non-metric unit
      if (!isMetric(cUnits1) && !isComposite()) 
         ansUnits = cUnits1;
      //single metric unit
      else if (isMetric(cUnits1) && !isComposite()) 
         ansUnits = cMetScale1 + cMetBase1;
      //composite non-metric/non-metric unit
      else if (!isMetric(cUnits1) && !isMetric(cUnits2) && isComposite())
         ansUnits = cUnits1 + " per " + 
                   (cUnits2.substring(0, cUnits2.length() - 1));
      //composite non-metric/metric unit
      else if (!isMetric(cUnits1) && isMetric(cUnits2) && isComposite())
         ansUnits = cUnits1 + " per " + cMetScale2 + 
                 (cMetBase2.substring(0, cMetBase2.length() - 1));
      //composite metric/non-metric unit
      else if (isMetric(cUnits1) && isMetric(cUnits2) && isComposite())
         ansUnits = cMetScale1 + cMetBase1 + " per " + 
                   (cUnits2.substring(0, cUnits2.length() - 1));
      //composite metric/metric unit
      else if (isMetric(cUnits1) && isMetric(cUnits2) && isComposite())
         ansUnits = cMetScale1 + cMetBase1 + " per " + cMetScale2 + 
                    (cMetBase2.substring(0, cMetBase2.length() - 1));
      
      switch (ansAmt) {
         case "-1": fAnswer = NEG_AMT_MSG;
            break;
         case "-2": fAnswer = INVAL_CONV_MSG;
            break;
         case "-3": fAnswer = INVAL_UNIT_MSG;
            break;
         default:   fAnswer = ansAmt + " " + ansUnits;
            break;
      }
      
      return fAnswer;
   }
   //END Output Format methods
}