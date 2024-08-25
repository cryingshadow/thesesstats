package thesesstats;

import java.io.*;
import java.nio.charset.*;

public record ResultFile(Integer points, String grade, String[] nameParts, String title, String otherReviewer) {

    public static ResultFile create(final File resultFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(resultFile, Charset.forName("UTF-8")))) {
            final String pointsString = reader.readLine();
            final Integer points =
                pointsString == null || pointsString.isBlank() ? null : Integer.parseInt(pointsString);
            final String grade = reader.readLine();
            final String namePartsString = reader.readLine();
            final String[] nameParts = namePartsString == null ? new String[] {} : namePartsString.split(" ");
            final String title = reader.readLine();
            final String otherReviewer = reader.readLine();
            return new ResultFile(points, grade, nameParts, title, otherReviewer);
        }
    }

    public void write(final BufferedWriter writer) throws IOException {
        if (this.points != null) {
            writer.write(String.valueOf(this.points));
        }
        writer.write("\n");
        if (this.grade != null) {
            writer.write(this.grade);
        }
        writer.write("\n");
        writer.write(String.join(" ", this.nameParts));
        writer.write("\n");
        if (this.title != null) {
            writer.write(this.title);
        }
        writer.write("\n");
        if (this.otherReviewer != null) {
            writer.write(this.otherReviewer);
        }
        writer.write("\n");
    }

    public ResultFile setOtherReviewer(final String otherReviewer) {
        return new ResultFile(this.points, this.grade, this.nameParts, this.title, otherReviewer);
    }

}
