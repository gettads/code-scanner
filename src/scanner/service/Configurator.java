package scanner.service;

import java.util.Arrays;

/**
 * Can be improved for using via ENV
 */
public class Configurator {
    static final String SCAN_PATH_DEFAULT = "public";

    private final String path;

    public Configurator(String[] args) {
        this.path = Arrays.stream(args).findAny().isPresent() ? args[0] : SCAN_PATH_DEFAULT;
    }

    public String getScanPath() {
        return this.path;
    }
}
