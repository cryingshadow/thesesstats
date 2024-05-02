package thesesstats;

import java.io.*;

public enum ThesisType {

    ALL("Abschluss- und Praxisarbeiten"),
    ALL_BUT_PA("Abschlussarbeiten"),
    BA("Bachelorarbeiten"),
    MA("Masterarbeiten"),
    PA("Praxisarbeiten");

    public static ThesisType fromFile(final File resultFile) {
        switch (resultFile.getAbsoluteFile().getParentFile().getParentFile().getName()) {
        case "Master":
            return MA;
        case "Bachelor":
            return BA;
        case "Praxisarbeiten":
            return PA;
        default:
            throw new IllegalArgumentException("File is not located in the required structure!");
        }
    }

    public final String title;

    private ThesisType(final String title) {
        this.title = title;
    }

}
