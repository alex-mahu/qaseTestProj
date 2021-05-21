package framework;

import io.qase.api.inner.GsonObjectMapper;
import io.qase.api.models.v1.testrunresults.NewTestRunResults;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import kong.unirest.json.JSONObject;

public class TestCaseResultManager {

    private final UnirestInstance client;

    public TestCaseResultManager() {
        client = Unirest.spawnInstance();
        client.config()
              .setObjectMapper(new GsonObjectMapper())
              .addShutdownHook(true)
              .setDefaultHeader("Token", "9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
    }

    public void createTestCaseResult(Class<NewTestRunResults> responseClass, Object payload) {

        HttpResponse<JsonNode> jsonNodeHttpResponse = client.request("POST", "https://api.qase.io/v1/result/TP1/3")
                                                            .header("Content-Type", "application/json")
                                                            .body(payload)
                                                            .asJson();
        JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

        final NewTestRunResults result = client.config().getObjectMapper().readValue(jsonObject.get("result").toString(), responseClass);
    }
}
