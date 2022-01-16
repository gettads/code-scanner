package scanner.service;

import scanner.dto.FileStatisticsDto;
import scanner.dto.ScannerResultsDto;

public class ScannerReportService {
    public void printReport(ScannerResultsDto scannerResultsDto) {
        if (scannerResultsDto.getResult().size() == 0) {
            System.out.println("No results for report. Check the directory.");
            return;
        }

        scannerResultsDto.getResult().forEach(
            (FileStatisticsDto statistics) -> {
                System.out.println(
                    "File: "
                    + statistics.getFileName()
                    + " contains "
                    + statistics.getCount()
                    + " lines of code."
                );
            }
        );

        System.out.println("Scan process is done.");
    }
}
