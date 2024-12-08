# Getting Started

### Issue 
 http status code is always null for 404 error scenario.


### Reproduce steps
* Please run 
	* com/eranga/Application.java (running on port 8080)
* Then run : 
	* mvn gatling:test


### Actual result
Never reach to this [Scenario.java#L31](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L31) block even though api throw 404 http code and KO array has zero record.

### Expected result
if 404 error happend then reach to this [Scenario.java#L31](https://github.com/eranga4u/demo-gatling/blob/develop/src/test/java/com/eranga/scenarios/Scenario.java#L31) and record should save as KO.
