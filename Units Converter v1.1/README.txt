This Java program is an expansion on my previous unit converting program. This one is more powerful, it is able to convert not only metric to imperial, but imperial to imperial, imperial to metric, and metric to metric as well. So now you can convert nearly any measure of length, mass, or volume to another form. Note that I'm using the imperial system, which is slightly different from the US system. 

This version merely lays the groundwork for a proper converting application, so it is incomplete. I have many additions and changes planned for the coming weeks.

Changes from v1.0:
-Number format more natural. If a number rounds down to xx.x0, then the final number is xx.x. Numbers are also grouped by the thousands and separated commas. 
-Supports abbreviations for lengths, masses, volumes, and temperatures.
-Remove support for stones.
-Added support for Fahrenheit, Celsius, Kelvin.
-Cleaned up programming style by moving errant leading braces after private void someMethod() { and if (this) {.
-Fixed up sloppy commenting.
-Added picometers and fixed the constants of the metric scale below pico (10 X -12).