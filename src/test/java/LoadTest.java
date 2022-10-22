import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoadTest {


    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/selenide/selenide.github.io/blob/master/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            assertThat(new String(fileContent, UTF_8)).contains("JUnit 5");
        }
    }

    @Test
    void uploadTest() {
        Selenide.open("https://demoqa.com/upload-download");
        $("input[type='file']").uploadFromClasspath("123.txt");
        $("#uploadedFilePath").shouldHave(text("123.txt"));
    }
}