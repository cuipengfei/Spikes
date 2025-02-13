package cpf.gatling_jdbc;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ExampleSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("https://www.baidu.com")
            .header("Content-Type", "application/json")
            .header("Accept-Encoding", "gzip")
            .check(status().is(200));

    ScenarioBuilder scn = scenario("Root end point calls")
            .exec(http("root end point").get("/").body(StringBody("{}")));

    {
        setUp(scn.injectOpen(constantUsersPerSec(1).during(Duration.ofSeconds(10))))
                .protocols(httpProtocol);
    }
}
