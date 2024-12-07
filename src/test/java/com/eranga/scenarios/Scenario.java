package com.eranga.scenarios;

import static com.eranga.common.Constants.RESPONSE_HTTP_STATUS_CODE;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.CheckBuilder;
import io.gatling.javaapi.core.Session;
import java.util.List;
import java.util.function.Function;

public class Scenario {

    public static List<CheckBuilder> checkSession(int expectedStatus) {

        return List.of(
            status().saveAs(RESPONSE_HTTP_STATUS_CODE),
            status().is(expectedStatus)
        );

    }

    public Function<Session, Session> supplyReconcile(int expectedStatus) {
        return session -> {
            System.out.println("session status = " + session.isFailed()); //FIXME status is never true even though api throw 404 http code
            String statusCode = session.getString(RESPONSE_HTTP_STATUS_CODE);
            System.out.println("RESPONSE_HTTP_STATUS_CODE " + statusCode);

            if (statusCode == null) {
                System.out.println("Found null");
            } else {
                if (String.valueOf(expectedStatus).contains(statusCode)) {
                    System.out.println("found match - " + statusCode);
                } else {
                    System.out.println("not match - " + statusCode); //FIXME never reach to this block even though api throw 404 http code
                }
            }
            return session;
        };

    }
}
