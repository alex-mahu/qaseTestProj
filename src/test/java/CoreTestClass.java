import java.util.Arrays;

import org.testng.annotations.BeforeMethod;

public class CoreTestClass {

    protected String dataProviderOptions;

    @BeforeMethod
    public void getDataProviderOptions(Object[] dataProviderParameters) {
        if (dataProviderParameters.length > 0) {
            dataProviderOptions = Arrays.toString(dataProviderParameters);
        }
    }
}
