# TradingPlatform

O: Introduction
---------------

### i. What is the Trading Platform? 

The Trading Platform is a software platform designed to run a flexible array of trading enviroments. Within a trading environment, Trading Platform hosts any number of trading agents, that bid in markets and receive utility outcomes. The Trading Platform is very flexible; it was the designed with the goal of being able to run any game-theoretical scenario that can be specified as a sequence of markets, each formally described using only a small set of rules. In addition, Trading Platform was designed with real-time use in mind: when a trading environment is running, an arbitrary number of people on different computers can submit agents to trade in that environment. 

### ii. Why should anyone care about the Trading Platform? 

The basic idea of a game where agents independently attempt to maximize utility is a useful abstraction of virtually any scenario that involves intelligent agents behaving in a self-interested manner. Games are a broadly applicable idea within many subfields of computer science and economics.  
Trading Platform attempts to capture the core components of a game to subject the game to study. Specifically, Trading Platform is useful in the study of how Agents can and should behave within a game, and the study of how mechanisms can and should be designed to achieve certain goals from the playing of the game itself. There is no limit to the different kinds of games that may be played, or the different behaviors an agent may exhibit. There are a rich variety of real-world applications: to name a profitable few, spectrum auction agent and mechanism design, internet advertising, auctions for emissions licenses, auctions for copyright, auctions for real-estate, auctions for roses. These are limited by the imagination. 
Trading Platform provides a clean, formal and efficient environment for such study- it is designed with the hope that users will develop agent and mechanism designs, with hopes of studying their properties and providing insights into meaningful real-world problems. 


### iii. Who should use the Trading Platform? 

The primary audience of Trading Platform is students and researchers in Game Theory and Algorithmic Game Theory, hoping to design trading environments. Trading Platform has also been used as a tool for teaching, to positive and often very interesting results- it has been used for labs, homeworks, and term projects in an undergraduate and graduate course in Algorithmic Game Theory at Brown University. 

I: What the Trading Platform Simulates
--------------------------------------

The Trading Platform simulates games. On the highest level, these games require autonomous agents to submit actions that affect utility outcomes according to a predefined set of rules. The most readily useful interpretation of this process is of agents submitting trades in markets; however, the platform is not strictly limited to simulated auctions that involve bidding on goods. It can be used for one-sided auctions, two-sided markets, or simple games that map actions directly to outcomes, like the cake cutting game. While this abstractly describes what the platform simulates, these situations can be used to represent scenarios where google stock, paintings, flowers, etc. are bought and sold. 
All Trading Platform games share common characteristics: they consist of Agents that submit actions, which are interpreted by a center and mapped to utility outcomes. The behavior of the center that receives agent actions is dictated by a set of Rules. These Rules run in series to form a Simulation. 
Hopefully, the Simulation and its contingent Agents correspond to a scenario of interest. 

II: Broad Structural Overview
-----------------------------

![alt text](https://github.com/andrewcoggins/TradingPlatform/blob/master/images/platform_overview.jpg "Trading Platform Overview")

The Platform uses a network Client/Server model, where one server supports any number of clients. Within the platform, this model is specialized to one market/auction supporting many participants/bidders. The server side of the platform runs the game, meaning it maintains information about the game's current state, sends agents relevant information, and prompts them to respond (e.g., to send their bids). The agent/client side of this platform is responsible for receiving the server’s messages and replying, just as a bidder would do in an auction. This general means of communication serves as the basis for Trading Platform Simulations. 

III: Structure of Markets within a Simulation
---------------------------------------------

The description above implies that in a simulation run on the Platform, an agent may bid in only one Market. However, there are many cases of interest where a simulation may involve agents bidding in multiple markets, often simultaneously. Trading Platform is structured to support these simulations. 

![alt text](https://github.com/andrewcoggins/TradingPlatform/blob/master/images/SingleSimulation.jpg "Trading Platform Simulation")

The sequence of markets to be run in a Simulation is specified by the user and is stored in the MarketManager. The Platform runs an arbitrary number of individual markets (i.e. Second Price Auctions or Prediction Markets) at any one time slot. Once every market at a time slot is complete and closed to trading with Agents, a new time slot will begin with a new set of Markets. This structure allows for flexibility in Trading Simulations- for instance, a simulation with two price-discovery rounds (each modeled as an independent Market) followed by a single settlement round. 

As in the above example, it is oftentimes the case that the Market(s) at some time slot t are dependent on some information from Market(s) at time slot t - 1. The Platform's MarketManager handles this by opening markets at time t with some PrevStateInfo gathered from markets at time t - 1 or with some initial condition if t = 0. This allows for the state of a Market to be dependent on a history of previous Markets. Note that the content of PrevStateInfo is flexible- the information that is transferred from one Market timeslot to another is up to the Market designer. 

![alt text](https://github.com/andrewcoggins/TradingPlatform/blob/master/images/simulation.jpg "Multiple Simulations")

Once a sequence is specified within a Simulation, the Simulation may be run repeatedly. This may be useful for capturing averaged performance measures of agents participating in the Simulation. 



IV: Assembly
------------

1.     Enter Eclipse workspace directory in Terminal and clone the git repository: git clone https://github.com/andrewcoggins/TradingPlatform
2.     In eclipse: file->import->Existing projects into workspace-> Import the TradingPlatform project into eclipse.
3.     Build path->configure build path->source->add folder: src/main/java, src/test/java, remove old source folder
4.     enable maven nature if not already done. You may need to select maven->update project for eclipse to recognize some libraries
5.     Use platform

