import java.io.File;
import java.io.IOException;

import io.qase.api.QaseApi;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

public class Test2 {

    static {
        System.setProperty("qase.enable", "true");
        System.setProperty("qase.api.token", "9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
        System.setProperty("qase.project.code", "TP1");
        System.setProperty("qase.run.id", "2");
    }

    @Test
    public void test1() throws IOException {
        File f1 = new File("1.txt");
        FileUtils.write(f1, "1111111111", "UTF-8");
        File f2 = new File("2.txt");
        FileUtils.write(f1, "2222222222", "UTF-8");
        QaseApi client = new QaseApi("9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
//        client.testRunResults().create()
    }
}
