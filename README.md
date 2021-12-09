# Brick_Destroy
This is a simple arcade video game.
Player's goal is to destroy a wall with a small ball.
The game has  very simple commmand:

SPACE - Start/Pause the game 

A - move the player LEFT

D - move the player RIGHT 

SHIFT+A - move the player faster to the LEFT

SHIFT+D - move the player faster to the RIGHT

ESC - Enter/Exit the pause menu 

ALT+SHITF+F1 - open console 

The game automatically pause if the frame loses focus.
# Gradle Run

Pre-requisite : Java 8 to Java 15
Note: Gradle is not compatible with Java versions after Java 15.

A build file is added as it automatically downloads and configures the dependencies and other libraries used. Gradle Wrapper allows us to run the build file without installing Gradle. When we invoke "gradlew", it downloads and builds the Gradle version specified. In order to run the application from the command line, the following steps can be followed:

  1. Open command prompt and navigate to the folder where the file exists by copying the path and typing: cd <path>
  2. Run the application using the command "gradlew run".

 > gradlew run


