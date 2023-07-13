# Template for handling Request/Response

Base template to make a Burp Suite extension that can process Request/Response + some helper functions.

**Warning**: this is a personal project aim to learn about Burp Suite Montoya API so expect some spaghetti. 

## How to use:
- Clone/Download this project.
- Add this project to your IDE of choice.
- Download and install gradle
- Make change to the logic.
- Run `gradler shadowJar`
- Result jar file is in `./build/libs/<EXT_NAME>-<VERSION>-all.jar` 

## TODO:
- Improve Helper class to support json data.