   /**
    * This is a code fragment, so it will not run. I decided to upload only
	* the parts of the Converter class that has been changed or added.
	*/
   
   /**
    * Processes the input.
    * The input is a conversion statement containing the original amount, the
    * original units, the keyword "to", and the units to convert to, like "25 
    * meters to centimeters". This methods breaks down the input into parts that
    * can be manipulated
    * @param statement the conversion statement
    */
   public void processInput(String statement) {
      if (statement.contains(" to ")){
         String[] splits = statement.split(" to ");

         try {
            processOrigAmt(splits[0]); 
            processConvUnits(splits[1]);
         }
         catch (ArrayIndexOutOfBoundsException e) {
            origAmt = -1;
         }
      }
      else if (statement.contains(" in normal time")){
         Scanner scanner = new Scanner(statement);
         origAmt = scanner.nextDouble();
         oUnits1 = standardUnit(scanner.next());
         normalTimeMethod = true;
      }
   }
   
   /**
    * Determines which high-level conversion method to use to convert the units.
    * @return the answer for the UI to display.
    */
   public String convert(){
      double answer1 = 0;
      double answer2 = 0;
      String normalTime = null;
      String finalAnswer;
      
      if (normalTimeMethod == true)
         normalTime = normalTime(oUnits1);
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
      if (normalTimeMethod == true)
         finalAnswer = normalTime;
      else
         finalAnswer = finalAnswer(formatNumber(answer1));
      
      return finalAnswer;
   }
   
   /**
    * Converts a large time amount into a normal time amount.
    * A normal time amount is in days, hours, minutes, seconds.
    * For example, 72 minutes in normal time is 1 hour, 12 minutes
    * @param oUnits the original units of the large time amount
    * @return normal time amount
    */
   private String normalTime(String oUnits){
      double time = scaledDownTime(origAmt, oUnits);
      double years = 0;
      double months = 0;
      double weeks = 0;
      double days = 0;
      double hours = 0;
      double minutes = 0;
      double seconds;

      while (time >= YEAR){
         time -= YEAR;
         years++;
      }
      while (time >= MONTH){
         time -= MONTH;
         months++;
      }
      while (time >= WEEK){
         time -= WEEK;
         weeks++;
      }
      while (time >= DAY) {
         time -= DAY;
         days++;
      }
      while (time >= HOUR) {
         time -= HOUR;
         hours++;
      }
      while (time >= MINUTE) {
         time -= MINUTE;
         minutes++;
      }
      seconds = time;
      
      return printNormalTime(years, months, weeks, days, hours, minutes, 
                             seconds);
   }
   
   /**
    * Prints the normal time.
    * @param years the years of the normal time
    * @param months the months of the normal time
    * @param weeks the weeks of the normal time
    * @param days the days of the normal time
    * @param hours the hours of the normal time
    * @param minutes the minutes of the normal time
    * @param seconds the seconds of the normal time
    * @return the normal time
    */
   private String printNormalTime(double years, double months, double weeks, 
                                  double days, double hours, double minutes,
                                  double seconds) {
      String yearTime, monthTime, weekTime, dayTime, hourTime, minuteTime, 
             secondTime;
      
      if (years == 0)
         yearTime = "";
      else if (years == 1)
         yearTime = formatNumber(years) + " year, ";
      else 
         yearTime = formatNumber(years) + " years, ";
      
      if (months == 0)
         monthTime = "";
      else if (months == 1)
         monthTime = formatNumber(months) + " month, ";
      else 
         monthTime = formatNumber(months) + " months, ";
      
      if (weeks == 0)
         weekTime = "";
      else if (weeks == 1)
         weekTime = formatNumber(weeks) + " week, ";
      else 
         weekTime = formatNumber(weeks) + " weeks, ";
      
      if (days == 0)
         dayTime = "";
      else if (days == 1)
         dayTime = formatNumber(days) + " day, ";
      else 
         dayTime = formatNumber(days) + " days, ";
      
      if (hours == 0)
         hourTime = "";
      else if (hours == 1)
         hourTime = formatNumber(hours) + " hour, ";
      else
         hourTime = formatNumber(hours) + " hours, ";
      
      if (minutes == 0)
         minuteTime = "";
      else if (minutes == 1)
         minuteTime = formatNumber(minutes) + " minute, ";
      else 
         minuteTime = formatNumber(minutes) + " minutes, ";
      
      if (seconds == 0)
         secondTime = "";
      else if (seconds == 1)
         secondTime = formatNumber(seconds) + " second, ";
      else 
         secondTime = formatNumber(seconds) + " seconds, ";
      
      String normalTime = yearTime + monthTime + weekTime + dayTime + hourTime + minuteTime +
             secondTime;
      return normalTime.substring(0, normalTime.length() - TRIM);
   }