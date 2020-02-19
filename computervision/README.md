This module is specified to deal with the data sent to and from the glasses device.

## Contents of CV Module:
- ComputerVision
- ComputerVisionInAction
- ConstantsCV

## Purpose of Each File:
### 1. computerVision
This file is an interface that defines the functions and classes necessary to deal with both different parts in the app and the glasses device.
- Vision Class:
	it contains each mode of the glasses with the data that will be sent from the glasses to pass it to the next level in the app.
- CVInput Class:
	it is concerned to send a specified mode from the app to the glasses. 
<p> Also, it contains an activate function to activate the portal that is resbonsible for the connection (fun activate()) between the glasses and the app.<p>

### 2- ComputerVisionInAction
Implementing the interface computerVision with some additional functions to be able to operate on the data that is coming from the glasses in JSON format and send it to/from the glasses.
- cvTokenize Function:
	it takes the raw data of the glasses and change it into a json object to get the info needed from each sent data by checking upon a unique token(indicates that the module is reading data) for each mode of the glasses.
- cvEncode Function:
	responsible for changing the mode of the glasses by checking a unique token(indicates that the module sending data).
 
### 3- ConstantsCV:
- only contains the constants used in cvTokenize function in ComputerVisionInAction for a better understanding.

           



 
