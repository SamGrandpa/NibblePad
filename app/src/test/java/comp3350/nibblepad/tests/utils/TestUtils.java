package comp3350.nibblepad.tests.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestUtils {
    private static final String DB_SRC = "src/main/assets/db/DB.script";

    public static File getTmpDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(Paths.get(DB_SRC), Paths.get(target.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        return target;
    }
}
