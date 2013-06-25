import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * Performs all the calculations required to convert measurements between units
 * and formatting the final answer.
 * This class has two methods to interpret the input, one to interpret metric
 * units, four methods used by the UI to convert the length, mass, and volume 
 * amounts, two methods to 
 * determine whether the original and converted units are imperial, six methods 
 * to convert imperial amounts to their base imperial unit and back to another 
 * imperial unit, two methods to convert metric amounts to their base metric 
 * unit and back to another metric unit, a method to interpret abbreviations, a 
 * method to convert the units to standardized units for computation purposes, a
 * method to round down the converted amount, a method to assemble the final 
 * answer, and three methods to convert between metric and imperial units once 
 * the original measured amount is converted to its base amount. 
 * The conversion between length, mass, and volume units is as follows:
 * 1) The UI decides whether the conversion is imperial to imperial, imperial to
 *    metric, metric to imperial, and metric to metric.
 * 2) The original amount is converted to its base amount (meters, grams, liters
 *    for metric units; inches, fluid ounces, pounds).
 * 3) If the conversion is between metric and imperial, the appropriate 
 *    calculation is made from the base amount in one system to the other.
 * 4) The base amount is then converted to desired unit as declared by the user.
 * The conversion between temperature units is as follows:
 * 1) The UI decides whether the conversion is Fahrenheit to Celsius, Celsius to
 *    Fahrenheit, Fahrenheit to Kelvin, Kelvin to Fahrenheit, Celsius to Kelvin,
 *    and Kelvin to Celsius.
 * 2) The original amount is converted from the original units to the converted
 *    units in a straightforward fashion using simple math formulas. 
 * @author Carey Norslien
 */
public class Converter {
   //distance, volume, mass, temperature conversion numbers
   private static final double M_TO_IN = 39.3701;
   private static final double IN_TO_M = 0.0254;
   private static final double L_TO_FLOZ = 35.1951;
   private static final double FLOZ_TO_L = 0.0284131;
   private static final double G_TO_LB = 0.00220462;
   private static final double LB_TO_G = 453.592;
   private static final double C_TO_F_MULTIPLY = 9/5; 
   private static final short C_TO_F_ADD = 32; 
   private static final double F_TO_C_MULTIPLY = 5/9;
   private static final short F_TO_C_SUB = 32;
   private static final double KEL_OFFSET = 273.15;
   
   //metric factors
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
   
   //imperial distance factors from inches
   private static final short FEET = 12;
   private static final short YARD = 36;
   private static final int MILE = 63360;
   private static final double THOU = 0.001;
   private static final short CHAIN = 792;
   private static final short FURLONG = 7920;
   
   //imperial mass factors from pounds
   private static final double GRAIN = 1/7000;
   private static final double OUNCE = 0.0625;
   private static final short STONE = 14;
   private static final short TON = 2000;  
   
   //imperial volume factors from fluid ounces
   private static final short PINT = 20;
   private static final short QUART = 40;
   private static final short GALLON = 160;
   
   //other constants
   private static final double ROUND = 0.009;
   private static final short ABBREV = 3;
   private static final short METABBR = 2;
   private static final short FAH_UNIT = 1;
   private static final short CEL_UNIT = 2;
   private static final short KEL_UNIT = 3;
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
   private boolean isOrigImp;
   private boolean isConvImp;
   private int origTempUnit;
   private int convTempUnit;
   private double oAmount;
   private static String oUnits;
   private static String cUnits;
   private static String oMetScale;
   private static String oMetBase;
   private static String cMetScale;
   private static String cMetBase;
   private static String scale;                                
   private static String base;                                 
   private static final Set<String> imperialUnits = new HashSet<>();
   private static final Set<String> volumeUnits = new HashSet<>();
   private static final Set<String> massUnits = new HashSet<>();
   private static final Set<String> lengthUnits = new HashSet<>();
   
   /**
    * Initializes a Converter object.
    */
   public Converter() {
      isOrigImp = false;
      isConvImp = false;
      origTempUnit = 0;
      convTempUnit = 0;
      oAmount = 0;
      oUnits = "";
      cUnits = "";
      oMetScale = "";
      oMetBase = "";
      cMetScale = "";
      cMetBase = "";
      scale = "";
      base = "";
   } 
   
   /**
    * Adds the units to their respective unit hast sets.
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
   }
   
   /**
    * Processes the text from the original amount text box.
    * This method also determines whether the original units are imperial or 
    * metric if the units are length, mass, of volume, or if they are 
    * temperature units.
    */
   public void processOrigAmt(String origInput) {
      Scanner amtScanner = new Scanner(origInput);
      
      if (amtScanner.hasNext()) {
         oAmount = amtScanner.nextDouble();
         //standardizes units for computation purposes 
         oUnits = standardUnit(amtScanner.next());
      }
      else                          //empty text field 
         oUnits = "invalid";
      if (!isImperial(oUnits) && !oUnits.equals("fahrenheit") && 
          !oUnits.equals("celsius") && !oUnits.equals("kelvin")) {
         //original units are metric
         isOrigImp = false;
         parseMetricUnits(oUnits);
         oMetScale = scale;                     
         oMetBase = base;                       
         scale = base = null;       //reset temp scale and base vars
      }
      else if (isImperial(oUnits))
         isOrigImp = true;
      else if (oUnits.equals("fahrenheit")) 
         origTempUnit = FAH_UNIT;
      else if (oUnits.equals("celsius"))
         origTempUnit = CEL_UNIT;
      else if (oUnits.equals("kelvin"))
         origTempUnit = KEL_UNIT;
   }
   
   /**
    * Processes the text from the convert to units text box.
    * This method also determines whether the converted units are imperial or 
    * metric if the units are length, mass, of volume, or if they are 
    * temperature units.
    */
   public void processConvUnits(String convInput) {
      Scanner unitScanner = new Scanner(convInput);
      
      if (unitScanner.hasNext()) { 
         //standardizes units for computation purposes
         cUnits = standardUnit(unitScanner.next());
      }
      else                          //empty text field
         cUnits = "invalid";
      if (!isImperial(cUnits) && !cUnits.equals("fahrenheit") && 
          !cUnits.equals("celsius") && !cUnits.equals("kelvin")) {
         //converted units are metric
         isConvImp = false;
         parseMetricUnits(cUnits);
         cMetScale = scale;
         cMetBase = base;
         scale = base = null;       //reset temp scale and base vars
      }
      else if (isImperial(cUnits))
         isConvImp = true;
      else if (cUnits.equals("fahrenheit")) 
         convTempUnit = FAH_UNIT;
      else if (cUnits.equals("celsius"))
         convTempUnit = CEL_UNIT;
      else if (cUnits.equals("kelvin"))
         convTempUnit = KEL_UNIT;
   }
   
   /**
    * Converts an imperial amount to another imperial amount.
    * @return The converted amount and units if both units are the same type
    * (length, mass, volume, temperature) and positive. Returns an error message
    * otherwise.
    */
   public String convertedImpToImp() {
      double answer = 0;
      
      if (isLength(oUnits, cUnits)) 
         answer = convertedImpLen(baseImpLen(oAmount, oUnits), cUnits);
      else if (isMass(oUnits, cUnits)) 
         answer = convertedImpMass(baseImpMass(oAmount, oUnits), cUnits);
      else if (isVolume(oUnits, cUnits)) 
         answer = convertedImpVol(baseImpVol(oAmount, oUnits), cUnits);
      else if (answer < 0)          //negative length, mass, volume amount
         answer = NEG_AMT;
      else                          //invalid conversion
         answer = INVAL_CONV;
      
      return finalAnswer(formatNumber(answer), cUnits);
   }
   
   /**
    * Converts an imperial amount to a metric amount.
    * @return The converted amount and units if both units are the same type
    * (length, mass, volume, temperature) and positive. Returns an error message
    * otherwise.
    */
   public String convertedImpToMet() {
      double temp;
      double answer = 0;
      
      if (isLength(oUnits,cMetBase)) {
         temp = impMetLen(baseImpLen(oAmount, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isMass(oUnits,cMetBase)) {
         temp = impMetMass(baseImpMass(oAmount, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isVolume(oUnits,cMetBase)) {
         temp = impMetVol(baseImpVol(oAmount, oUnits), 0);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (answer < 0)          //negative length, mass, volume amount
         answer = NEG_AMT;
      else                          //invalid conversion
         answer = INVAL_CONV;
      
      return finalAnswer(formatNumber(answer), cMetScale + cMetBase);
   }
   
   /**
    * Converts a metric amount to an imperial amount
    * @return The converted amount and units if both units are the same type
    * (length, mass, volume, temperature) and positive. Returns an error message
    * otherwise.
    */
   public String convertedMetToImp() {
      double temp;
      double answer = 0;
      
      if (isLength(oMetBase,cUnits)) {
         temp = impMetLen(scaledDownMetric(oAmount, oMetScale), 1);
         answer = convertedImpLen(temp, cUnits);
      }
      else if (isMass(oMetBase,cUnits)) {
         temp = impMetMass(scaledDownMetric(oAmount, oMetScale), 1);
         answer = convertedImpMass(temp, cUnits);
      }
      else if (isVolume(oMetBase,cUnits)) {
         temp = impMetVol(scaledDownMetric(oAmount, oMetScale), 1);
         answer = convertedImpVol(temp, cUnits);
      }
      else if (answer < 0)          //negative length, mass, volume amount
         answer = NEG_AMT;
      else                          //invalid conversion
         answer = INVAL_CONV;
      
      return finalAnswer(formatNumber(answer), cUnits);
   }
   
   /**
    * Converts a metric amount to another metric amount.
    * @return The converted amount and units if both units are the same type
    * (length, mass, volume, temperature) and positive. Returns an error message
    * otherwise.
    */
   public String convertedMetToMet() {
      double answer = 0;
      
      if ((isLength(oMetBase,cMetBase)) || (isMass(oMetBase, cMetBase)) || 
              (isVolume(oMetBase, cMetBase))) 
         answer = scaledUpMetric(scaledDownMetric(oAmount, oMetScale), cMetScale);
      else if (oAmount == -1.0)
         answer = INVAL_UNIT;
      else if (answer < 0)          //negative amount
         answer = NEG_AMT;
      else                          //invalid conversion
         answer = INVAL_CONV;
      
      return finalAnswer(formatNumber(answer), cMetScale + cMetBase);  
   } 
   
   /**
    * Converts a Fahrenheit temperature to Celsius temperature and vice versa,
    * also assembles the final answer.
    * @param flag 0 if from Fahrenheit to Celsius, 1 if from Celsius to 
    * Fahrenheit
    * @return the Fahrenheit temperature in Celsius or the Celsius temperature 
    * in Fahrenheit
    */
   public String convertedFahCel(int flag) {
      double answer;
      
      if (flag == 0) 
         answer = convertedFahCel(oAmount, 0);
      else
         answer = convertedFahCel(oAmount, 1);
      
      return finalAnswer(formatNumber(answer), cUnits);     
   }
   
   /**
    * Converts a Fahrenheit temperature to Kelvin temperature and vice versa,
    * also assembles the final answer.
    * @param flag 0 if from Fahrenheit to Kelvin, 1 if from Kelvin to 
    * Fahrenheit
    * @return the Fahrenheit temperature in Kelvin or the Kelvin temperature 
    * in Fahrenheit
    */
   public String convertedFahKel(int flag) {
      double answer;
      
      if (flag == 0) 
         answer = convertedFahKel(oAmount, 0);
      else
         answer = convertedFahKel(oAmount, 1);
      
      return finalAnswer(formatNumber(answer), cUnits);      
   }
   
   /**
    * Converts a Celsius temperature to Kelvin temperature and vice versa,
    * also assembles the final answer.
    * @param flag 0 if from Celsius to Kelvin, 1 if from Kelvin to 
    * Celsius
    * @return the Celsius temperature in Kelvin or the Kelvin temperature 
    * in Celsius
    */
   public String convertedCelKel(int flag) {
      double answer;
      
      if (flag == 0) 
         answer = convertedCelKel(oAmount, 0);
      else
         answer = convertedCelKel(oAmount, 1);
      
      return finalAnswer(formatNumber(answer), cUnits);     
   }
   
   /**
    * Determines if the original units are imperial.
    * @return true if the original units are imperial, false otherwise
    */
   public boolean isOrigImperial() {  
      return isOrigImp; 
   }
   
   /**
    * Determines if the converted units are imperial.
    * @return true if the converted units are imperial, false otherwise
    */
   public boolean isConvImperial() {  
      return isConvImp; 
   }
   
   /**
    * Determines the original temperature unit.
    * @return 1 if the original temperature unit is Fahrenheit, 2 if Celsius, 3 
    * if Kelvin
    */
   public int origTempUnit() {
      return origTempUnit;
   }
   
   /**
    * Determines the converted temperature unit.
    * @return 1 if the converted temperature unit is Fahrenheit, 2 if Celsius, 3 
    * if Kelvin
    */
   public int convTempUnit() {
      return convTempUnit;
   }
   
   /**
    * Parses metric units into their scale and base for computations.
    * If a unit is not recognized, then the the original amount becomes -1, 
    * effectively activating the invalid conversion error message.
    * The base is either meters, grams, or liters. The scale ranges from yotta-
    * to yocto- .
    * @param metricUnits the metric units to parse
    */
   private void parseMetricUnits(String metricUnits) {
      int lIndex = metricUnits.indexOf("meter");
      int mIndex = metricUnits.indexOf("gram");
      int vIndex = metricUnits.indexOf("liter");
      
      try {
         if (metricUnits.length() == METABBR) { 
            //standardize the abbreviation
            scale = prefixCheck(metricUnits.substring(0, 1)); 
            base = abbrevUnit(metricUnits.substring(1, 2));
         }
         else if (metricUnits.length() == 1) {
            base = abbrevUnit(metricUnits);
         } 
         else if (lIndex != -1) {
            scale = metricUnits.substring(0, lIndex);
            base = metricUnits.substring(lIndex);
         }
         else if (mIndex != -1) {
            scale = metricUnits.substring(0, mIndex);
            base = metricUnits.substring(mIndex);
         }
         else { //vIndex != -1 
            scale = metricUnits.substring(0, vIndex);
            base = metricUnits.substring(vIndex);
         } 
      }
      catch (StringIndexOutOfBoundsException e){ //unit not recognized
         oAmount = -1;
      }
   }
   
   /**
    * Converts the original imperial distance into inches.
    * Note that inches are the base imperial distance units. Negative distances
    * are not allowed.
    * @param origAmt original distance value
    * @param origUnit original distance unit
    * @return original distance value in inches, -1 if the answer is negative
    */
   private static double baseImpLen(double origAmt, String origUnit) {
      double inches = 0;  
      
      switch (origUnit) {
         case "inches": inches = origAmt;
            break;
         case "feet": inches = origAmt * FEET;
            break;
         case "yards": inches = origAmt * YARD;
            break;
         case "miles": inches = origAmt * MILE;
            break;
         case "thous": inches = origAmt * THOU;
            break;
         case "chains": inches = origAmt * CHAIN;
            break;
         case "furlongs": inches = origAmt * FURLONG;
            break;
      }    
      if (origAmt < 0)
         inches = -1;
      
      return inches;
   }
   
   /**
    * Converts the original imperial mass into pounds.
    * Note that pounds are the base imperial mass units. Negative masses are 
    * not allowed.
    * @param origAmt original imperial mass value
    * @param origUnit original imperial mass units
    * @return original imperial mass value in pounds, -1 if the amount is 
    * negative
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
         case "tons": pounds = origAmt * TON;
            break;
      }  
      if (origAmt < 0)
         pounds = -1;
      
      return pounds;
   }
   
   /**
    * Converts the original imperial volume into fluid ounces.
    * Note that fluid ounces are the base imperial volume units. Negative 
    * volumes are not allowed.
    * @param origAmt original imperial volume value
    * @param origUnit original imperial volume units
    * @return original imperial volume in fluid ounces, -1 if the amount is 
    * negative
    */
   private static double baseImpVol(double origAmt, String origUnit) {
      double floz = 0;
      
      switch (origUnit) {
         case "fluid ounces": floz = origAmt;
            break;
         case "pints": floz = origAmt * PINT;
            break;
         case "quarts": floz = origAmt * QUART;
            break;
         case "gallons": floz = origAmt * GALLON;
            break;
      }   
      if (origAmt < 0)
         floz = -1;
      
      return floz;
   }
   
   /**
    * Converts inches to the converted imperial distance.
    * Note that inches are the base imperial distance units
    * @param inches the distance in inches
    * @param convertUnit the converted imperial distance units
    * @return the converted distance in imperial units
    */
   private static double convertedImpLen(double inches, String convertUnit) {
      double convertImpLen = 0;
      
      switch (convertUnit) {
         case "inches": convertImpLen = inches;
            break;
         case "feet": convertImpLen = inches / FEET;
            break;
         case "yards": convertImpLen = inches / YARD;
            break;
         case "miles": convertImpLen = inches / MILE;
            break;
         case "thous": convertImpLen = inches / THOU;
            break;
         case "chains": convertImpLen = inches / CHAIN;
            break;
         case "furlongs": convertImpLen = inches / FURLONG;
            break;
      }
      return convertImpLen;
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
         case "tons": convertImpMass = pounds / TON;
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
         case "pints": convertImpVol = floz / PINT;
            break;
         case "quarts": convertImpVol = floz / QUART;
            break;
         case "gallons": convertImpVol = floz / GALLON;
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
    * @return original metric amount in the base metric amount, -1 if the 
    * original amount was negative
    */
   private static double scaledDownMetric(double origMet, String fromScale){
      double baseMetric = 0;
      
      switch (fromScale) {
         case "":       baseMetric = origMet;            //meters, grams, liters
            break;
         case "yotta":  baseMetric = origMet * YOTTA;
            break;
         case "zetta":  baseMetric = origMet * ZETTA;
            break;
         case "exa":    baseMetric = origMet * EXA;
            break;
         case "peta":   baseMetric = origMet * PETA;
            break;
         case "tera":   baseMetric = origMet * TERA;
            break;
         case "giga":   baseMetric = origMet * GIGA;
            break;
         case "mega":   baseMetric = origMet * MEGA;
            break;
         case "kilo":   baseMetric = origMet * KILO;
            break;
         case "hecto":  baseMetric = origMet * HECTO;
            break;
         case "deka":   baseMetric = origMet * DEKA;
            break;
         case "deci":   baseMetric = origMet * DECI;
            break;
         case "centi":  baseMetric = origMet * CENTI;
            break;
         case "milli":  baseMetric = origMet * MILLI;
            break;
         case "micro":  baseMetric = origMet * MICRO;
            break;
         case "nano":   baseMetric = origMet * NANO;
            break;
         case "pico":   baseMetric = origMet * PICO;
            break;
         case "femto":  baseMetric = origMet * FEMTO;
            break;
         case "atto":   baseMetric = origMet * ATTO;
            break;
         case "zepto":  baseMetric = origMet * ZEPTO;
            break;
         case "yocto":  baseMetric = origMet * YOCTO;
            break;
      }
      if (origMet < 0)
         baseMetric = -1;
      
      return baseMetric;
   }
   
   /**
    * Scales up the base metric amount to the final conversion amount.
    * For example, meters scales up to kilometers, liters to megaliters, etc.
    * @param baseAmount amount in basic metric units (meters, liters, grams)
    * @param toScale the final conversion unit(zettameters, dekaliters, etc.)
    * @return final conversion amount, -1 if the original amount was negative
    */
   private static double scaledUpMetric(double baseMetric, String toScale) {
      double upScale = 0.0;
      
      switch (toScale) {
         case "":       upScale = baseMetric;            //meters, grams, liters
            break;
         case "yotta":  upScale = baseMetric/ YOTTA;
            break;
         case "zetta":  upScale = baseMetric / ZETTA;
            break;
         case "exa":    upScale = baseMetric / EXA;
            break;
         case "peta":   upScale = baseMetric / PETA;
            break;
         case "tera":   upScale = baseMetric / TERA;
            break;
         case "giga":   upScale = baseMetric / GIGA;
            break;
         case "mega":   upScale = baseMetric / MEGA;
            break;
         case "kilo":   upScale = baseMetric / KILO;
            break;
         case "hecto":  upScale = baseMetric / HECTO;
            break;
         case "deka":   upScale = baseMetric / DEKA;
            break;
         case "deci":   upScale = baseMetric / DECI;
            break;
         case "centi":  upScale = baseMetric / CENTI;
            break;
         case "milli":  upScale = baseMetric / MILLI;
            break;
         case "micro":  upScale = baseMetric / MICRO;
            break;
         case "nano":   upScale = baseMetric / NANO;
            break;
         case "pico":   upScale = baseMetric / PICO;
            break;
         case "femto":  upScale = baseMetric / FEMTO;
            break;
         case "atto":   upScale = baseMetric / ATTO;
            break;
         case "zepto":  upScale = baseMetric / ZEPTO;
            break;
         case "yocto":  upScale = baseMetric / YOCTO;
            break;
      }      
      if (baseMetric < 0)
         upScale = -1;
      
      return upScale;
   }
   
   /**
    * Standardizes the units.
    * A standardized unit is plural and spelled out. This method is useful for 
    * computation purposes.
    * @param units the units to check if plural or not
    * @return the units in plural form
    */
   private String standardUnit(String units) {
   
      String standard = units.toLowerCase();
      boolean expCase = units.equals("fluid ounces") || units.equals("inches") 
                        || units.equals("feet");
      boolean temperature = units.equals("fahrenheit") || 
                            units.equals("celsius") || units.equals("kelvin");
      
      switch (units) {                             //the exceptional cases
         case "fluid":  standard = "fluid ounces";
                        expCase = true;
            break;
         case "inch":   standard = "inches";
                        expCase = true;
            break;
         case "foot":   standard = "feet";
                        expCase = true;
            break;
      }
      if (units.length() <= ABBREV) 
         standard = abbrevUnit(units);   
      else if (!units.endsWith("s") && expCase == false && temperature == false)
         standard = units.concat("s");
      
      return standard;
   }
   
   /**
    * Determines if the unit is an abbreviation of a unit.
    * @param candidate the unit that might be abbreviated
    * @return the "unpacked" abbreviation, the candidate variable if it was not 
    * an abbreviated unit.
    */
   private String abbrevUnit(String candidate) {
      String abbrev;
      
      switch (candidate) {
         case "in":  abbrev = "inches";
            break;
         case "ft":  abbrev = "feet";
            break;
         case "yd":  abbrev = "yards";
            break;
         case "mi":  abbrev = "miles";
            break;
         case "ch":  abbrev = "chains";
            break;
         case "fur": abbrev = "furlongs";
            break;
         case "lb":  abbrev = "pounds";
            break;
         case "gr":  abbrev = "grains";
            break;
         case "oz":  abbrev = "ounces";
            break;
         case "fl":  abbrev = "fluid ounces";
            break;
         case "pt":  abbrev = "pints";
            break;
         case "qt":  abbrev = "quarts";
            break;
         case "gal": abbrev = "gallons";
            break;
         case "m":   abbrev = "meters";
            break;
         case "g":   abbrev = "grams";
            break;
         case "l":   abbrev = "liters";
            break;
         case "C":   abbrev = "celsius";
            break;
         case "F":   abbrev = "fahrenheit";
            break;
         case "K":   abbrev = "kelvin";
            break;
         default:    abbrev = candidate;
      }
      return abbrev;
   }
   
   /**
    * Determines the scale of metric unit when it's abbreviated.
    * @param candidate
    * @return the "unpacked" prefix for an abbreviated metric amount.
    */
   private String prefixCheck(String candidate) {
      String prefix = null;
      
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
   
   /**
    * Formats the number.
    * A formated number is in the form of xx,xxx.xx. Therefore, the number will
    * be rounded and will have commas if they need to be.
    * Rounding conditions:
    * 1) Rounds to a whole number when the decimal number is close enough to an 
    *    integer, in this case, +xx.009. 
    * 2) Leaves unrounded if it is a miniscule amount, in this case, if the 
    *    number is less than 0.009.
    * 3) Rounds to three decimal places. May be one or two decimal places if 
    *    the last digit(s) is zero.
    * @param conversion the number to possibly round
    * @return fAnswer the final answer (may or may not be rounded)
    */
   private static String formatNumber(double conversion) {
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
    * Produces the final answer.
    * Note: If the final answer is 1 unit, the final units are in singular form. 
    * Otherwise, the final units are unchanged and in plural form.
    * @param ansAmt the converted amount
    * @param ansUnits the converted units
    * @return the converted amount and units
    */
   private String finalAnswer(String ansAmt, String ansUnits) {
      boolean single = ansAmt.equals("1"); //is converted amount 1 unit?
      String fAnswer;
      
      if (single && ansUnits.equals("feet")) 
         ansUnits = "foot";
      else if (single && ansUnits.equals("inches")) 
         ansUnits = "inch";
      else if (single) {
         int i = ansUnits.indexOf("s");
         ansUnits = ansUnits.substring(0, i);
      }
  
      switch (ansAmt) {
         case "-1": fAnswer = NEG_AMT_MSG;
            break;
         case "-2": fAnswer = INVAL_CONV_MSG;
            break;
         case "-3": fAnswer = INVAL_UNIT_MSG;
            break;
         default:   fAnswer = ansAmt + " " + ansUnits;
      }
      return fAnswer;
   }
   
   /**
    * Determines if the conversion is between lengths.
    * @param unit1 first possible length unit
    * @param unit2 second possible length unit
    * @return true if the conversion is between lengths, false otherwise
    */
   private boolean isLength(String unit1, String unit2) {  
      return (lengthUnits.contains(unit1) || unit1.equals("meters")) &&
              (lengthUnits.contains(unit2) || unit2.equals("meters")); 
   }
   
   /**
    * Determines if the conversion is between masses.
    * @param unit1 first possible mass unit
    * @param unit2 second possible mass unit
    * @return true if the conversion is between masses, false otherwise
    */
   private boolean isMass(String unit1, String unit2) {  
      return (massUnits.contains(unit1) || unit1.equals("grams")) &&
              (massUnits.contains(unit2) || unit2.equals("grams")); 
   }
   
   /**
    * Determines if the conversion is between volumes.
    * @param unit1 first possible volume unit
    * @param unit2 second possible volume unit
    * @return true if the conversion is between volumes, false otherwise
    */
   private boolean isVolume(String unit1, String unit2) {  
      return (volumeUnits.contains(unit1) || unit1.equals("liters")) &&
              (volumeUnits.contains(unit2) || unit2.equals("liters"));
   } 
   
   /**
    * Converts imperial lengths to metric lengths and vice versa.
    * After the imperial length amount has been converted to inches, it is
    * converted to meters. Likewise, after the base metric length amount has 
    * been converted to meters, it is converted to inches.
    * @param length the length in inches or meters
    * @param flag 0 if converting inches to meters, 1 if converting meters
    * to inches
    * @return inch amount in meters or meters amount in inches.
    */  
   private static double impMetLen(double length, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = length * IN_TO_M;  
      else
         answer = length * M_TO_IN;
      
      return answer;  
   }
   
   /**
    * Converts imperial volumes to metric volumes and vice versa.
    * After the imperial volume amount has been converted to fluid ounces, it is
    * converted to liters. Likewise, after the base metric volume amount has 
    * been converted to liters, it is converted to fluid ounces.
    * @param volume the volume in fluid ounces or liters
    * @param flag 0 if converting fluid ounces to liters, 1 if converting liters
    * to fluid ounces
    * @return fluid ounce amount in liters or liter amount in fluid ounces.
    */   
   private static double impMetVol(double volume, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = volume * FLOZ_TO_L;  
      else
         answer = volume * L_TO_FLOZ;
      
      return answer;   
   }

   /**
    * Converts imperial masses to metric masses and vice versa.
    * After the imperial mass amount has been converted to pounds, it is
    * converted to grams. Likewise, after the base metric mass amount has been 
    * converted to grams, it is converted to pounds.
    * @param mass the mass in pounds or grams
    * @param flag 0 if converting pounds to grams, 1 if converting grams to 
    * pounds
    * @return pound amount in grams or gram amount in pounds
    */   
   private static double impMetMass(double mass, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = mass * LB_TO_G;  
      else
         answer = mass * G_TO_LB;
      
      return answer;  
   }
   
   /**
    * Converts Fahrenheit amount to Celsius amount and vice-versa.
    * Uses a flag variable to determine the conversion to perform. 
    * @param temp temperature in Fahrenheit or Celsius
    * @param flag flag variable, 0 for Fahrenheit to Celsius, 1 for Celsius
    * to Fahrenheit
    * @return temperature in Kelvin if the original temperature was Fahrenheit,
    * Fahrenheit if the original temperature was Kelvin.
    */
   private static double convertedFahCel(double temp, int flag) {
      double answer;
      
      if (flag == 0) 
         answer = (temp - F_TO_C_SUB) * F_TO_C_MULTIPLY;
      else
         answer = (temp * C_TO_F_MULTIPLY) + C_TO_F_ADD;
      
      return answer;
   }
   
   /**
    * Converts Fahrenheit amount to Kelvin amount and vice-versa.
    * Uses a flag variable to determine the conversion to perform. 
    * @param temp temperature in Fahrenheit or Kelvin
    * @param flag flag variable, 0 for Fahrenheit to Kelvin, 1 for Kelvin 
    * to Fahrenheit
    * @return temperature in Kelvin if the original temperature was Fahrenheit,
    * Fahrenheit if the original temperature was Kelvin.
    */
   private static double convertedFahKel(double temp, int flag) {
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
    * Converts Celsius amount to Kelvin amount and vice versa.
    * @param temp temperature in Celsius or Kelvin
    * @param flag flag variable, 0 for Celsius to Kelvin, 1 for Kelvin to 
    * Celsius
    * @return temperature in Kelvin if the original temperature was Celsius,
    * Celsius if the original temperature was Kelvin.
    */
   private static double convertedCelKel(double temp, int flag) {
      double answer;
      
      if (flag == 0)
         answer = temp + KEL_OFFSET;
      else 
         answer = temp - KEL_OFFSET;
      
      return answer;
   }
   
   /**
    * Determines if a unit is imperial.
    * @param candidate the unit that might be imperial
    * @return true if the unit is an imperial unit, false otherwise
    */
   private boolean isImperial(String candidate) {
      return imperialUnits.contains(candidate);
   }
}