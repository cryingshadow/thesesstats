package thesesstats;

import java.io.*;

public record ResultFile(String[] nameParts, String title) {

    public static ResultFile create(final File resultFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {
            reader.readLine();
            reader.readLine();
            final String[] nameParts = reader.readLine().split(" ");
            final String title = reader.readLine();
            return new ResultFile(nameParts, title);
        }
    }

}
