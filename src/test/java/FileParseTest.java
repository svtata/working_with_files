import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    ClassLoader classloader = FileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        Selenide.open("http://the-internet.herokuapp.com/download");
        File file = $("a[href*='document.pdf']").download();
        PDF pdf = new PDF(file);
        assertThat(pdf.numberOfPages).isEqualTo(4);
    }

    @Test
    void xslTest() throws Exception {
        Selenide.open("http://romashka2008.ru/");
        File file = $(".top-menu__items a[href*='/f/prajs_ot_0109.xls']").download();
        XLS xsl = new XLS(file);
        assertThat(xsl.excel.getSheetAt(0).
                getRow(22).
                getCell(2).
                getStringCellValue()).isEqualToIgnoringWhitespace("Бумага для цветной печати");
        //Находим в excel конкретный лист, строку, ячейку, индекс будет -1
    }


    @Test
    void csvTest() throws Exception {
        try(InputStream is = classloader.getResourceAsStream("456.csv")){
            CSVReader csvReader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv.get(0)).contains("number", "name");
        }
    }
}