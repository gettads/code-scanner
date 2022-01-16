package scanner.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import scanner.dto.FileStatisticsDto;
import scanner.dto.ScannerResultsDto;
import scanner.exception.GeneralScanException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.swing.UIManager.put;

public class CodeLineCounterServiceTest {

    private final Map<String, Integer> map = new HashMap<>(){
        {
            put("test/public/test.txt", 3);
            put("test/public/folder/Example1.java", 5);
            put("test/public/folder/Example2.java", 5);
            put("test/public/folder/Example3.java", 5);
            put("test/public/folder/Example4.java", 5);
            put("test/public/folder/Example5.java", 6);
        }
    };

    @Test
    public void testScan() throws GeneralScanException {
        String[] testArgs = new String[]{"test/public"};
        ScannerResultsDto scannerResults = new CodeLineCounterService(new Configurator(testArgs)).scan();
        for (FileStatisticsDto dto : scannerResults.getResult()) {
            if (!this.map.containsKey(dto.getFileName())) {
                throw new GeneralScanException("Test data for file does not exist. File name: " + dto.getFileName());
            }

            if (!Objects.equals(this.map.get(dto.getFileName()), dto.getCount())) {
                throw new GeneralScanException("Invalid value of counter. File name: " + dto.getFileName());
            }
        }
    }
}
