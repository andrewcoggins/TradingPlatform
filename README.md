# TradingPlatform

Trading Platform (TP) is a software platform designed to run and implement trading environments, involving autonomous decision-making agents in a real-time simulation envoronment. It is implemented in Java and intended to be run in the IDE of your choice. Its primary purpose is to serve as a research environment for game theoretical experiments.

Presently, TP provides a robust array of low-level trading scenarios in which user-programmed agents can submit trades or make decisions. These can be combined to run both in sequence and concurrently with an arbitrary number of other trading scenarios, that autonomous agents may participate in both simultaneously and sequentially. At is core, it is also designed to be flexible, so that trading environments can be easily modified and adjusted, and so that new environments can be easily and cleanly developed and deployed. Users may vary in their involvement- they may be interested in using agents and trading environments that have already been developed, or may develop agents and environments of their own. A major goal of the platform is to provide useful abstractions of the technical challenges facing researchers in algorithmic game theory seeking to develop clean research environments for the study of trading scenarios. To our knowledge, no similar platform exists.

A complete developer guide may be found [here](https://github.com/andrewcoggins/TradingPlatform/wiki). A user guide is provided at the bottom of this page.

## Assembly

1. In a directory of your choice, clone the project. 
```
git clone https://github.com/andrewcoggins/TradingPlatform 
```
or clone using the *clone or download* button above. 

2. Open the project in the java IDE of your choice. Be sure to enable maven nature upon creation of your project.
 
3. The project should be ready to use. Note that assembly has been tested in only Eclipse and Intellij IDEA. Please direct any enduring issues to this project's [issue page](https://github.com/andrewcoggins/TradingPlatform/issues).

## Requirements

Java 8 and Java SDK are necessary to run TP. All further requirements are specified in *pom.xml* and will be added upon enabling maven nature. 

## To Use

User guide coming soon. 

You can track bugs [here](https://github.com/andrewcoggins/TradingPlatform/issues). For general inquiries about TP, please reach out to <acoggins38@gmail.com>.

Thanks, and happy trading! 
