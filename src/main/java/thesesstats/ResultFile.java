package thesesstats;

import java.io.*;
import java.nio.charset.*;

public record ResultFile(String[] nameParts, String title, String otherReviewer) {

    public static ResultFile create(final File resultFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(resultFile, Charset.forName("UTF-8")))) {
            reader.readLine();
            reader.readLine();
            final String[] nameParts = reader.readLine().split(" ");
            final String title = reader.readLine();
            final String otherReviewer = reader.readLine();
            return new ResultFile(nameParts, title, otherReviewer);
        }
    }

}
