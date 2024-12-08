# Getting Started

### Issue 
 http status code is always null for the 404 error scenario.


### Reproduce steps
* Please run 
	* com/eranga/Application.java (running on port 8080)
* Then run : 
	* mvn gatling:test


### Actual result
Never reached this [Scenario.java#L29](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L29) block even though the API throws a 404 http code.

### Expected result
if a 404 error happens then reach to this [Scenario.java#L29](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L29) and execute the rest of the logic.
