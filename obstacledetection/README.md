#Obstacle Detection Module

This module is responsible for the interaction with the device that the user will hold for interacting
with the outer world (in this case it is a cane).

##How this module works

Mainly, this module is all about an interface called "ObstacleDetector" drawing the lines that anyone
who's interested to listen anything from "the cane". On the other side, this module also has a class
"ObstacleDetectorImpl" that represents what the cane actually needs to send to whom it may concern.
The "ObstacleDetectorImpl" class implements the interface I have just mentioned, it also has its own
tokenizer and encoder to deal with any type of data sent or received.

##Tasks list
- [x] ObstacleDetection interface.
- [x] ObstacleDetection interface implementation.
- [ ] Unit test for the tokenizer and the encoder.
- [x] Adding README file.
