package thesesstats;

import java.io.*;
import java.nio.file.*;

public record Thesis(String title, String name) {

    public static Thesis fromFile(final File resultFile) {
        try {
            return new Thesis(
                Files.lines(resultFile.toPath()).skip(3).findFirst().get(),
                Files.lines(resultFile.toPath()).skip(2).findFirst().get()
            );
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
