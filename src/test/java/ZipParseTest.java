import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipParseTest {
    ClassLoader classLoader = ZipParseTest.class.getClassLoader();

    @Test
    void zipReadPDF() throws Exception {
        ZipFile zip = new ZipFile(Objects.requireNonNull(classLoader.getResource("Desktop.zip")).getFile());
        ZipEntry entry = zip.getEntry("Document.pdf");
        try (InputStream inputStream = zip.getInputStream(entry)) {
            PDF pdf = new PDF(inputStream);
            assertThat(pdf.text).contains("Тестовый документ для проверки содержимого pdf");
        }
    }


    @Test
    void zipReadXSL() throws Exception {
        ZipFile zip = new ZipFile(Objects.requireNonNull(classLoader.getResource("Desktop.zip")).getFile());
        ZipEntry entry = zip.getEntry("Books.xlsx");
        try (InputStream inputStream = zip.getInputStream(entry)) {
            XLS xsl = new XLS(inputStream);
            assertThat(xsl.excel.getSheetAt(0).
                    getRow(0).
                    getCell(2).
                    getStringCellValue()).isEqualToIgnoringWhitespace("Год издания");
        }
    }

    @Test
    void zipReadCSV() throws Exception {
        ZipFile zip = new ZipFile(Objects.requireNonNull(classLoader.getResource("Desktop.zip")).getFile());
        ZipEntry entry = zip.getEntry("Numbers.csv");
        try (InputStream is = zip.getInputStream(entry)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv.get(0)).contains("number", " name");
        }
    }
}
