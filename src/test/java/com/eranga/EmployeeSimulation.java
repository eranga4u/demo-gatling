package com.eranga;

import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;

import com.eranga.scenarios.EmployeeScenario;
import io.gatling.javaapi.core.OpenInjectionStep.RampRate.RampRateOpenInjectionStep;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeSimulation extends Simulation {

    private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = setupProtocolForSimulation();

    public EmployeeSimulation() {
        EmployeeScenario employeeScenario = new EmployeeScenario();

        setUp(employeeScenario.getScenario().injectOpen(postEndpointInjectionProfile())
            .protocols(HTTP_PROTOCOL_BUILDER)).assertions(global().responseTime()
            .max()
            .lte(10000), global().successfulRequests()
            .percent()
            .gt(90d));
    }

    private RampRateOpenInjectionStep postEndpointInjectionProfile() {
        int totalDesiredUserCount = 20; //200
        double userRampUpPerInterval = 10; //50
        double rampUpIntervalSeconds = 10; // 30

        int totalRampUptimeSeconds = 0; //120
        int steadyStateDurationSeconds = 120; // 300
        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
            .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    }

    private static HttpProtocolBuilder setupProtocolForSimulation() {
        return HttpDsl.http.baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .maxConnectionsPerHost(10)
            .userAgentHeader("Gatling/Performance Test");
    }


}
