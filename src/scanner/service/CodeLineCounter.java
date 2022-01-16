package scanner.service;

import scanner.dto.ScannerResultsDto;
import scanner.exception.GeneralScanException;

public interface CodeLineCounter {
    public ScannerResultsDto scan() throws GeneralScanException;
}
