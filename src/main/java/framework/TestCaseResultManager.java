package framework;

import java.util.HashMap;
import java.util.Map;

import framework.models.NewTestCaseResultRequest;
import io.qase.api.QaseApiClient;
import io.qase.api.inner.GsonObjectMapper;
import io.qase.api.models.v1.testrunresults.TestRunResult;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public class TestCaseResultManager {

    private final QaseApiClient client;

    public TestCaseResultManager() {
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        unirestInstance.config()
                       .setObjectMapper(new GsonObjectMapper())
                       .addShutdownHook(true)
                       .setDefaultHeader("Token", "9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
        client = new QaseApiClient(unirestInstance, "https://api.qase.io/v1");
    }

    public void createTestCaseResult(NewTestCaseResultRequest testResult) {

        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", "TP1");
        routeParams.put("run_id", "3");

        client.post(TestRunResult.class, "/result/{code}/{run_id}", routeParams, testResult).getHash();
    }
}
