import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * Performs all the calculations required to convert measurements between units
 * and formating the final answer.
 * This class has two methods to interpret the input, one to interpret metric
 * units, four methods used by the UI to convert the amounts, two methods to 
 * determine whether the original and converted units are imperial, six methods 
 * to convert imperial amounts to their base imperial unit and back to another 
 * imperial unit, two methods to convert metric amounts to their base metric 
 * unit and back to another metric unit, a method to convert the units to 
 * standardized units for computation purposes, a method to round down the 
 * converted amount, a method to assemble the final answer, and several methods 
 * to convert between metric and imperial units once the original measured 
 * amount is converted to its base amount. 
 * The conversion between units is as follows:
 * 1) The UI decides whether the conversion is imperial to imperial, imperial to
 *    metric, metric to imperial, and metric to metric.
 * 2) The original amount is converted to its base amount (meters, grams, liters
 *    for metric units; inches, fluid ounces, pounds).
 * 3) If the conversion is between metric and imperial, the appropriate 
 *    calculation is made from the base amount in one system to the other.
 * 4) The base amount is then converted to desired unit as declared by the user.
 * @author Carey Norslien
 */
public class Converter {
   //distance, volume, mass conversion numbers
   private static final double M_TO_IN = 39.3701;
   private static final double IN_TO_M = 0.0254;
   private static final double L_TO_FLOZ = 35.1951;
   private static final double FLOZ_TO_L = 0.0284131;
   private static final double G_TO_LB = 0.00220462;
   private static final double LB_TO_G = 453.592;
   private static final double C_TO_F_MULTIPLY = 9.0/5.0; 
   private static final short C_TO_F_ADD = 32; 
   private static final double F_TO_C_MULTIPLY = 5.0/9.0;
   private static final short F_TO_C_SUB = 32;
   
   //metric factors
   private static final double YOTTA = Math.pow(10, 24);
   private static final double ZETTA = Math.pow(10, 21);
   private static final double EXA = Math.pow(10, 18);
   private static final double PETA = Math.pow(10, 15);
   private static final double TERA = Math.pow(10, 12);
   private static final double GIGA = Math.pow(10, 9);
   private static final double MEGA = Math.pow(10, 6);
   private static final double KILO = 1000.0;
   private static final double HECTO = 100.0;
   private static final double DEKA = 10.0;
   private static final double DECI = Math.pow(10, -1);
   private static final double CENTI = Math.pow(10, -2);
   private static final double MILLI = Math.pow(10, -3);
   private static final double MICRO = Math.pow(10, -6);
   private static final double NANO = Math.pow(10, -9);
   private static final double FEMTO = Math.pow(10, -12);
   private static final double ATTO = Math.pow(10, -18);
   private static final double ZEPTO = Math.pow(10, -21);
   private static final double YOCTO = Math.pow(10, -24); 
   
   //imperial distance factors from inches
   private static final double FEET = 12.0;
   private static final double YARD = 36.0;
   private static final double MILE = 63360.0;
   private static final double THOU = 0.001;
   private static final double CHAIN = 792.0;
   private static final double FURLONG = 7920.0;
   
   //imperial mass factors from pounds
   private static final double GRAIN = 1.0/7000.0;
   private static final double OUNCE = 0.0625;
   private static final double STONE = 14.0;
   private static final double TON = 2000.0;  
   
   //imperial volume factors from fluid ounces
   private static final double PINT = 20.0;
   private static final double QUART = 40.0;
   private static final double GALLON = 160.0;
   
   //other constants
   private static final double ROUND = 0.009;
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
   private double oAmount;
   private static String oUnits;
   private static String cUnits;
   private static String oMetScale;
   private static String oMetBase;
   private static String cMetScale;
   private static String cMetBase;
   private static String scale;              //temp variable
   private static String base;               //temp variable
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
      lengthUnits.add("inches");
      imperialUnits.add("feet");
      lengthUnits.add("feet");
      imperialUnits.add("yards");
      lengthUnits.add("yards");
      imperialUnits.add("miles");
      lengthUnits.add("miles");
      imperialUnits.add("thous");
      lengthUnits.add("thous");
      imperialUnits.add("chains");
      lengthUnits.add("chains");
      imperialUnits.add("furlongs");
      lengthUnits.add("furlongs");
      imperialUnits.add("pounds");
      massUnits.add("pounds");
      imperialUnits.add("grains");
      massUnits.add("grains");
      imperialUnits.add("ounces");
      massUnits.add("ounces");
      imperialUnits.add("stones");
      massUnits.add("stones");
      imperialUnits.add("tons");
      massUnits.add("tons");
      imperialUnits.add("fluid ounces");
      volumeUnits.add("fluid ounces");
      imperialUnits.add("pints");
      volumeUnits.add("pints");
      imperialUnits.add("quarts");
      volumeUnits.add("quarts");
      imperialUnits.add("gallons");
      volumeUnits.add("gallons");
   }
   
   /**
    * Processes the text from the original amount text box.
    */
   public void processOrigAmt(String origInput) {
      Scanner amtScanner = new Scanner(origInput);
      if (amtScanner.hasNext()) {
         oAmount = amtScanner.nextDouble();
         oUnits = amtScanner.next().toLowerCase();
         //standardizes units for computation purposes
         oUnits = standardUnit(oUnits); 
      }
      else                                     //empty field case
         oUnits = "invalid";
      if (!isImperial(oUnits) && !oUnits.equals("invalid")) {
         isOrigImp = false;
         parseMetricUnits(oUnits);
         oMetScale = scale;                     
         oMetBase = base;                       
         scale = base = null;                   //reset temp scale and base vars
      }
      else if (!oUnits.equals("invalid"))
         isOrigImp = true;
   }
   
   /**
    * Processes the text from the convert to units text box.
    */
   public void processConvUnits(String convInput) {
      Scanner unitScanner = new Scanner(convInput);
      if (unitScanner.hasNext())
      {
         cUnits = unitScanner.next().toLowerCase(); 
         //standardizes units for computation purposes
         cUnits = standardUnit(cUnits);
      }
      else                                      //empty field case
         cUnits = "invalid";
      if (!isImperial(cUnits) && !cUnits.equals("invalid")) {
         isConvImp = false;
         parseMetricUnits(cUnits);
         cMetScale = scale;
         cMetBase = base;
         scale = base = null;                   //reset temp scale and base vars
      }
      else if (!cUnits.equals("invalid"))
         isConvImp = true;
   }
   
   /**
    * Converts an imperial amount to another imperial amount.
    * @return The converted amount if both units are the same type(length, mass,
    * volume) and positive. Returns an error message otherwise.
    */
   public String convertedImpToImp() {
      double temp;
      double answer = 0;
      if (isLength(oUnits) && isLength(cUnits))
      {
         temp = baseImpLen(oAmount, oUnits);
         answer = convertedImpLen(temp, cUnits);
      }
      else if (isMass(oUnits) && isMass(cUnits))
      {
         temp = baseImpMass(oAmount, oUnits);
         answer = convertedImpMass(temp, cUnits);
      }
      else if (isVolume(oUnits) && isVolume(cUnits))
      {
         temp = baseImpVol(oAmount, oUnits);
         answer = convertedImpVol(temp, cUnits);
      }
      else if (answer < 0) //negative amount
         answer = NEG_AMT;
      else //invalid conversion
         answer = INVAL_CONV;
      String fAnswerAmt = roundedAnswer(answer);
      String fAnswerUnits = cUnits;
      String fAnswer = finalAnswer(fAnswerAmt, fAnswerUnits);
      return fAnswer;
   }
   
   /**
    * Converts an imperial amount to a metric amount.
    * @return The converted amount if both units are the same type(length, mass,
    * volume) and positive. Returns an error message otherwise.
    */
   public String convertedImpToMet() {
      double temp;
      double answer = 0.0;
      if (isLength(oUnits) && isLength(cMetBase))
      {
         temp = baseImpLen(oAmount, oUnits);
         temp = impToMetLen(temp);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isMass(oUnits) && isMass(cMetBase))
      {
         temp = baseImpMass(oAmount, oUnits);
         temp = impToMetMass(temp);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (isVolume(oUnits) && isVolume(cMetBase))
      {
         temp = baseImpVol(oAmount, oUnits);
         temp = impToMetVol(temp);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (answer < 0) //negative amount
         answer = NEG_AMT;
      else //invalid conversion
         answer = INVAL_CONV;
      String fAnswerAmt = roundedAnswer(answer);
      String fAnswerUnits = cMetScale + cMetBase;
      String fAnswer = finalAnswer(fAnswerAmt, fAnswerUnits);
      return fAnswer;
   }
   
   /**
    * Converts a metric amount to an imperial amount
    * @return The converted amount if both units are the same type(length, mass,
    * volume) and positive. Returns an error message otherwise.
    */
   public String convertedMetToImp() {
      double temp;
      double answer = 0.0;
      if (isLength(oMetBase) && isLength(cUnits))
      {
         temp = scaledDownMetric(oAmount, oMetScale);
         temp = metToImpLen(temp);
         answer = convertedImpLen(temp, cUnits);
      }
      else if (isMass(oMetBase) && isMass(cUnits))
      {
         temp = scaledDownMetric(oAmount, oMetScale);
         temp = metToImpMass(temp);
         answer = convertedImpMass(temp, cUnits);
      }
      else if (isVolume(oMetBase) && isVolume(cUnits))
      {
         temp = scaledDownMetric(oAmount, oMetScale);
         temp = metToImpVol(temp);
         answer = convertedImpVol(temp, cUnits);
      }
      else if (answer < 0) //negative length, mass, volume amount
         answer = NEG_AMT;
      else //invalid conversion
         answer = INVAL_CONV;
      String fAnswerAmt = roundedAnswer(answer);
      String fAnswerUnits = cUnits;
      String fAnswer = finalAnswer(fAnswerAmt, fAnswerUnits);
      return fAnswer;
   }
   
   /**
    * Converts a metric amount to another metric amount.
    * @return The converted amount if both units are the same type(length, mass,
    * volume) and positive. Returns an error message otherwise.
    */
   public String convertedMetToMet() {
      double temp;
      double answer = 0;
      if ((isLength(oMetBase) && isLength(cMetBase)) || (isMass(oMetBase) && 
           isMass(cMetBase)) || (isVolume(oMetBase) && isVolume(cMetBase))) {
         temp = scaledDownMetric(oAmount, oMetScale);
         answer = scaledUpMetric(temp, cMetScale);
      }
      else if (oAmount == -1.0)
         answer = INVAL_UNIT;
      else if (answer < 0) //negative amount
         answer = NEG_AMT;
      else //invalid conversion
         answer = INVAL_CONV;
      String fAnswerAmt = roundedAnswer(answer);
      String fAnswerUnits = cMetScale + cMetBase;
      String fAnswer = finalAnswer(fAnswerAmt, fAnswerUnits);
      return fAnswer;  
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
    * Parses metric units into their scale and base for computations.
    * If a unit is not recognized, then the the original amount becomes 1, 
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
         if (lIndex != -1) {
            scale = metricUnits.substring(0, lIndex).toLowerCase();
            base = metricUnits.substring(lIndex).toLowerCase();
         }
         else if (mIndex != -1) {
            scale = metricUnits.substring(0, mIndex).toLowerCase();
            base = metricUnits.substring(mIndex).toLowerCase();
         }
         else {//vIndex != -1 
            scale = metricUnits.substring(0, vIndex).toLowerCase();
            base = metricUnits.substring(vIndex).toLowerCase();
         } 
         if (scale.equals(" "))
            scale = "base";
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
      double inches = 0.0;   
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
      double pounds = 0.0;     
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
      double floz = 0.0;
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
      double convertImpLen = 0.0;
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
      double convertImpMass = 0.0;
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
      double convertImpVol = 0.0;
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
      double baseMetric = 0.0;
      switch (fromScale) {
         case "": baseMetric = origMet;               //meters, grams, liters
            break;
         case "yotta": baseMetric = origMet * YOTTA;
            break;
         case "zetta": baseMetric = origMet * ZETTA;
            break;
         case "exa": baseMetric = origMet * EXA;
            break;
         case "peta": baseMetric = origMet * PETA;
            break;
         case "tera": baseMetric = origMet * TERA;
            break;
         case "giga": baseMetric = origMet * GIGA;
            break;
         case "mega": baseMetric = origMet * MEGA;
            break;
         case "kilo": baseMetric = origMet * KILO;
            break;
         case "hecto": baseMetric = origMet * HECTO;
            break;
         case "deka": baseMetric = origMet * DEKA;
            break;
         case "deci": baseMetric = origMet * DECI;
            break;
         case "centi": baseMetric = origMet * CENTI;
            break;
         case "milli": baseMetric = origMet * MILLI;
            break;
         case "micro": baseMetric = origMet * MICRO;
            break;
         case "nano": baseMetric = origMet * NANO;
            break;
         case "femto": baseMetric = origMet * FEMTO;
            break;
         case "atto": baseMetric = origMet * ATTO;
            break;
         case "zepto": baseMetric = origMet * ZEPTO;
            break;
         case "yocto": baseMetric = origMet * YOCTO;
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
         case "": upScale = baseMetric;               //meters, grams, liters
            break;
         case "yotta": upScale = baseMetric/ YOTTA;
            break;
         case "zetta": upScale = baseMetric / ZETTA;
            break;
         case "exa": upScale = baseMetric / EXA;
            break;
         case "peta": upScale = baseMetric / PETA;
            break;
         case "tera": upScale = baseMetric / TERA;
            break;
         case "giga": upScale = baseMetric / GIGA;
            break;
         case "mega": upScale = baseMetric / MEGA;
            break;
         case "kilo": upScale = baseMetric / KILO;
            break;
         case "hecto": upScale = baseMetric / HECTO;
            break;
         case "deka": upScale = baseMetric / DEKA;
            break;
         case "deci": upScale = baseMetric / DECI;
            break;
         case "centi": upScale = baseMetric / CENTI;
            break;
         case "milli": upScale = baseMetric / MILLI;
            break;
         case "micro": upScale = baseMetric / MICRO;
            break;
         case "nano": upScale = baseMetric / NANO;
            break;
         case "femto": upScale = baseMetric / FEMTO;
            break;
         case "atto": upScale = baseMetric / ATTO;
            break;
         case "zepto": upScale = baseMetric / ZEPTO;
            break;
         case "yocto": upScale = baseMetric / YOCTO;
            break;
      }      
      if (baseMetric < 0)
         upScale = -1;
      return upScale;
   }
   

   
   /**
    * Standardizes the units.
    * A standardized unit is plural. This is useful for computation purposes.
    * @param units the units to check if plural or not
    * @return the units in plural form
    */
   private String standardUnit(String units) {
      switch (units) {
         case "fluid": units = "fluid ounces";
            break;
         case "inch": units = "inches";
            break;
         case "foot": units = "feet";
            break;
      }
      if (!units.endsWith("s") && !units.equals("inches") && 
          !units.equals("feet") && !units.equals("fluid ounces"))
         units = units.concat("s");
      return units;
   }
   
   
   /**
    * Rounds the answer when appropriate.
    * Rounding conditions:
    * 1) Rounds to a whole number when the decimal number is close enough to an 
    * integer, in this case, +xx.009. 
    * 2) Leaves unrounded if it is a miniscule amount, in this case, if the 
    * number is less than 0.009.
    * 3) Rounds to two decimal places otherwise.
    * @param conversion the number to possibly round
    * @return fAnswer the final answer (may or may not be rounded)
    */
   private static String roundedAnswer(double conversion) {
      Double iAnswer = conversion;
      String fAnswer;
      String a = Double.toString(conversion);                     
      //get digits right of decimal point ( .xxxx ) for comparisons
      double d = Double.parseDouble(a.substring(a.indexOf("."))); 
      if (iAnswer.intValue() != 0 && d < ROUND)                   //condition 1
         fAnswer = Integer.toString(iAnswer.intValue());          
      else if (iAnswer.intValue() == 0 && d < ROUND)              //condition 2
         fAnswer = Double.toString(iAnswer);                      
      else                                                        //condition 3
         fAnswer = String.format("%.2f", conversion);             
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
      boolean single = ansAmt.equals("1"); //is converted amount is 1 unit?
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
    * @param units possible length units
    * @return true if the conversion is between lengths, false otherwise
    */
   private boolean isLength(String units) {  
      return (lengthUnits.contains(units) || units.equals("meters")); 
   }
   
   /**
    * Determines if the conversion is between masses.
    * @param units possible mass units
    * @return true if the conversion is between masses, false otherwise
    */
   private boolean isMass(String units) {  
      return (massUnits.contains(units) || units.equals("grams")); 
   }
   
   /**
    * Determines if the conversion is between volumes.
    * @param units possible volume units
    * @return true if the conversion is between volumes, false otherwise
    */
   private boolean isVolume(String units) {  
      return (volumeUnits.contains(units) || units.equals("liters")); 
   } 
       
   /**
    * After the metric distance amount has been converted to meters, it is
    * converted to inches.
    * @return meter amount in inches
    */
   private static double metToImpLen(double meters) {
      return meters * M_TO_IN;
   }
   
   /**
    * After the imperial distance amount has been converted to inches, it is
    * converted to meters.
    * @return inch amount in centimeters
    */
   private static double impToMetLen(double inches) {
      return inches * IN_TO_M;
   }
   
   /**
    * After the metric volume amount has been converted to liters, it is 
    * converted to fluid ounces.
    * @return liter amount in fluid ounces
    */
   private static double metToImpVol(double liters) {
      return liters * L_TO_FLOZ;
   }
   
   /**
    * After the imperial volume amount has been converted to fluid ounces, it is
    * converted to liters.
    * @return fluid ounce amount in liters
    */   
   private static double impToMetVol(double floz) {
      return floz * FLOZ_TO_L;  
   }
   /**
    * After the metric mass amount has been converted to grams, it is 
    * converted to pounds.
    * @return gram amount in pounds
    */   
   private static double metToImpMass(double grams) {
      return grams * G_TO_LB;
   }

   /**
    * After the imperial mass amount has been converted to pounds, it is
    * converted to grams.
    * @return pound amount in grams
    */   
   private static double impToMetMass(double pounds) {
      return pounds * LB_TO_G;  
   }
   
   /**
    * Converts Fahrenheit amount to Celsius amount.
    * @param fahrenheit temperature in Fahrenheit
    * @return temperature in Celsius
    */
   private static double fahToCelAmount(double fahrenheit) {
      return (fahrenheit - F_TO_C_SUB) * F_TO_C_MULTIPLY;
   }
   
   /**
    * Converts Celsius amount to Fahrenheit amount.
    * @param celsius temperature in Celsius
    * @return temperature in Fahrenheit
    */
   private static double celToFahAmount(double celsius) {
      return (celsius * C_TO_F_MULTIPLY) + C_TO_F_ADD;
   }
      
   /**
    * Determines if a unit is imperial.
    * @return true if the unit is a imperial unit, false otherwise
    */
   private boolean isImperial(String unitCandidate) {
      return imperialUnits.contains(unitCandidate);
   }
}