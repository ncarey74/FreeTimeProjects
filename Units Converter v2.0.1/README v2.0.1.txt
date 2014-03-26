This is the 2.0.1 version of my Unit Converter. The major difference on the user side is the more simplified method of inputting a conversion. Now the user can just type "25 miles to feet" and press convert. In addition, the user can convert between composite units as well, such as miles per gallon or meters per second. As for the code itself, I hope this version follows proper programming practice and common OOP design concepts better than the previous versions. 

This version largely resembles what I want from a unit converting program. However; there are still some things to be changed and added in the coming weeks.

Changes from v1.1 to v2.0.0
-Simplified UI. The entire conversion input is now typed into one text box.
-Now supports composite units like miles per hour, miles per gallon, etc.
-Abbreviations with periods (ft., mi., etc) are supported. 
-Added support for digital storage, area, time.
-Moved all the conditions from the UI class to the Converter class for lower coupling.
-Reduced the prevalence of the global variables by using additional parameters in the conversion methods.
-Reduced the redundancy of the temperature conversion methods by moving the logic of the child method inside the parent  method.

Changes from v2.0.0 to v2.0.1
-bug fix for time conversions, minutes now supported

