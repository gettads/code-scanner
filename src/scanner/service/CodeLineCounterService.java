package scanner.service;

import scanner.dto.FileStatisticsDto;
import scanner.dto.ScannerResultsDto;
import scanner.exception.GeneralScanException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class CodeLineCounterService implements CodeLineCounter{

    static final String INLINE_COMMENT = "//";
    static final String MULTILINE_COMMENT_OPEN = "/*";
    static final String MULTILINE_COMMENT_CLOSE = "*/";

    private final Configurator configurator;

    public CodeLineCounterService(Configurator configurator) {
        this.configurator = configurator;
    }

    @Override
    public ScannerResultsDto scan() throws GeneralScanException {
        Path currentDir = Path.of(this.configurator.getScanPath());

        ScannerResultsDto result = new ScannerResultsDto();

        System.out.println(currentDir + ": scanning is started...");
        this.scanNestedFiles(currentDir, result);

        return result;
    }

    private void scanNestedFiles(Path currentDir, ScannerResultsDto result) throws GeneralScanException {
        try {
            Files.list(currentDir).forEach(child -> {
                if (Files.isRegularFile(child)) {
                    String fullPath = currentDir + "/" + child.getFileName().toString();
                    try {
                        result.addToResult(
                                new FileStatisticsDto(fullPath, this.count(fullPath))
                        );
                    } catch (GeneralScanException e) {
                        e.printStackTrace();
                    }
                }

                if (Files.isDirectory(child) ) {
                    try {
                        scanNestedFiles(child, result);
                    }  catch (GeneralScanException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (AccessDeniedException e) {
            throw new GeneralScanException("Access denied. Check access on " + currentDir.getFileName().toString());
        } catch (IOException e) {
            throw new GeneralScanException(currentDir + ": Unknown IO error.");
        }
    }

    private Integer count(String path) throws GeneralScanException {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            boolean isLineCommented = false;
            Integer count = 0;

            for (String line : lines) {
                if (!isLineCommented && this.lineHasValidCode(line)) {
                    count++;
                }
                isLineCommented = this.isNextLineCommented(isLineCommented, line);
            }

            return count;

        } catch (IOException e) {
            throw new GeneralScanException("IO error: " + e.getMessage());
        }
    }

    private boolean isNextLineCommented(boolean isPreviousLineOpenComment, String currentLine) {
        currentLine = Pattern.compile("\".*\"").matcher(currentLine).replaceAll("\"\"");

        return (
            isPreviousLineOpenComment ||
            (
                currentLine.contains(MULTILINE_COMMENT_OPEN) &&
                    (
                        !currentLine.contains(INLINE_COMMENT) ||
                        currentLine.lastIndexOf(INLINE_COMMENT) > currentLine.lastIndexOf(MULTILINE_COMMENT_OPEN)
                    )
            )
        ) && (
            !currentLine.contains(MULTILINE_COMMENT_CLOSE) ||
            currentLine.lastIndexOf(MULTILINE_COMMENT_CLOSE) < currentLine.lastIndexOf(MULTILINE_COMMENT_OPEN)
        );
    }

    private boolean lineHasValidCode(String line) {
        line = Pattern.compile("\\s*|\t|\r|\n").matcher(line).replaceAll("");
        line = Pattern.compile("\".*\"").matcher(line).replaceAll("\"\"");
        line = Pattern.compile("/\\*.*\\*/").matcher(line).replaceAll("");
        line = Pattern.compile("//.*").matcher(line).replaceAll("");
        line = Pattern.compile("/\\*.*").matcher(line).replaceAll("");

        return !line.isEmpty();
    }
}
