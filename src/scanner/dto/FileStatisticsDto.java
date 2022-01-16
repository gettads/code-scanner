package scanner.dto;

public class FileStatisticsDto {
    private final String fileName;

    private final Integer count;

    public FileStatisticsDto(String fileName, Integer count) {
        this.fileName = fileName;
        this.count = count;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Integer getCount() {
        return this.count;
    }
}
