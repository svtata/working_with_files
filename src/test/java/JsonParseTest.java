import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonParseTest {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void jsonTest() throws IOException {
        File file = new File("src/test/resources/student.json");
        JsonNode student = mapper.readTree(file);
        assertThat((student.findValue("age").asInt())).isEqualTo(21);
        assertThat((student.get("name").asText())).isEqualTo("John");
        JsonNode array = student.get("subjects");
        assertThat((array.get(0)).asText()).isEqualTo("math");
    }
}

