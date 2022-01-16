package scanner.dto;

import java.util.ArrayList;
import java.util.List;

public class ScannerResultsDto {

    private final List<FileStatisticsDto> result;

    public ScannerResultsDto() {
        this.result = new ArrayList<>();
    }

    public List<FileStatisticsDto> getResult() {
        return this.result;
    }

    public void addToResult(FileStatisticsDto statisticsDto) {
        this.result.add(statisticsDto);
    }
}
