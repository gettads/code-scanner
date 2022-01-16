package scanner;

import scanner.exception.GeneralScanException;
import scanner.service.CodeLineCounterService;
import scanner.service.Configurator;
import scanner.service.ScannerReportService;

import java.util.Arrays;

public class Scanner {

    public static void main(String[] args) {
        try {
            new ScannerReportService().printReport(
                    new CodeLineCounterService(new Configurator(args)).scan()
            );
        } catch (GeneralScanException exception) {
            System.out.println(exception.getMessage() + Arrays.toString(exception.getStackTrace()));
        }
    }
}
