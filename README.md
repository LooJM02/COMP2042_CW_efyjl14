# Brick Breaker Game

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


# Changes Made And Additions
1. Classes were separated into respective packages based on the MVC pattern. 
2. build in gradlew version
3. added an Info button in the HomeMenu
4. changed the background images in the HomeMenu
5. added 2 new levels
6. player is able to move faster by pressing SHIFT+A or SHIFT+D
7. added a Home Menu button in the Pause Menu page
8. changed the colours of the bricks to beautify the game
9. made the sizes of the pages the same
10. added timer to the game to let player know how long they're playing the game


