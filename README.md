# Getting Started

### Issue 
 http status code is always null for the 404 error scenario.


### Reproduce steps
* Please run 
	* com/eranga/Application.java (running on port 8080)
* Then run : 
	* mvn gatling:test


### Actual result
1. Never reached this [Scenario.java#L29](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L29) block even though the API throws a 404 http code.
2. The statusCode [Scenario.java#L35](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L35) is always null for the 404 error scenario. 

### Expected result
1. if a 404 error happens then reach this [Scenario.java#L29](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L29) and execute the rest of the logic.
2. The statusCode [Scenario.java#L35](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L35) should not be null for the 404 error scenario. 
