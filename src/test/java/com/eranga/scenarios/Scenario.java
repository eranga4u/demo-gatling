package com.eranga.scenarios;

import static com.eranga.common.Constants.RESPONSE_HTTP_STATUS_CODE;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.eranga.reconcile.ReconcileResult;
import io.gatling.javaapi.core.CheckBuilder;
import io.gatling.javaapi.core.Session;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scenario {

    private final ReconcileResult reconcileResult = ReconcileResult.getInstance();

    public static List<CheckBuilder> checkSession(int expectedStatus) {

        return List.of(
            status().saveAs(RESPONSE_HTTP_STATUS_CODE),
            status().is(expectedStatus)
        );

    }

    public Function<Session, Session> supplyReconcile(int expectedStatus) {
        return session -> {
            log.info("session status={}", session.isFailed());//FIXME status is never true even though api throw 404 http code
            String statusCode = session.getString(RESPONSE_HTTP_STATUS_CODE);
            String id = session.getString("correlationId");

            reconcileResult.addRequest(id, statusCode);

            if (statusCode == null) {
                log.info("Found null");
                reconcileResult.setKo(id, null);
            } else {
                if (String.valueOf(expectedStatus).contains(statusCode)) {
                    log.info("found match = {}", statusCode);
                    reconcileResult.setOk(id, statusCode);
                } else {
                    //FIXME never reach to this block even though api throw 404 http code
                    reconcileResult.setKo(id, statusCode);
                    log.info("not match = {}", statusCode);
                }
            }
            return session;
        };

    }
}
