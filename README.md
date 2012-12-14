EhCacheSamplePositionEngine
===========================

Version: 0.1
Author: Stephan Grotz
URL: https://github.com/sgrotz/EhCacheSamplePositionEngine

Sample Position Engine, calculating book positions while receiving trades in real time. 

This sample, was created as part of a showcase for a financial institute, to show how easy a complex
implementation of a position engine is done in EhCache/Terracotta. 
The sample is obviously vastly simplified - but should show the basics required to build complex applications. 

To run the application, please make sure you use Terracotta version 3.7.0 - or alternatively, overwrite the 
jar files with a newer release.

Also - the implementation requires a terracotta license key, which you can obtain as a trial from Terracotta.org. 
Simply place the key in the conf folder and/or specify its location through the JVM Argument -Dcom.tc.productkey.path

Have fun :)
