package com.eranga.scenarios;

import static com.eranga.common.Constants.GROUP_EMPLOYEE;
import static com.eranga.common.Constants.SCENARIO_EMPLOYEE;
import static com.eranga.feeder.Feeders.FEED_DATA;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.exitBlockOnFail;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.springframework.http.HttpStatus;

public class EmployeeScenario extends Scenario {

    public ScenarioBuilder getScenario() {

        HttpRequestActionBuilder createEmployee = http("create-employee-request")
            .post("/api/employees")
            .header("Content-Type", "application/json")
            .body(StringBody("{ \"empName\": \"${empName}\" }"))
            .check(header("Location").saveAs("location"))
            .check(checkSession(HttpStatus.CREATED.value()));

        HttpRequestActionBuilder readEmployee = http("read-employee-request")
            .get("/api/employees-x/99")//FIXME set incorrect URL on purpose to trigger 404
            .header("Content-Type", "application/json")
            .check(checkSession(HttpStatus.OK.value()));

        return scenario(SCENARIO_EMPLOYEE).group(GROUP_EMPLOYEE).on(exitBlockOnFail(
            exec(create(createEmployee)).exec(read(readEmployee))
        ));

    }

    private ChainBuilder create(HttpRequestActionBuilder httpRequestActionBuilder) {
        return exec(feed(FEED_DATA),exec(httpRequestActionBuilder).exitHereIfFailed().exec(supplyReconcile(HttpStatus.CREATED.value())));
    }

    private ChainBuilder read(HttpRequestActionBuilder httpRequestActionBuilder) {
        return exec(feed(FEED_DATA),exec(httpRequestActionBuilder).exitHereIfFailed().exec(supplyReconcile(HttpStatus.OK.value())));
    }

}
