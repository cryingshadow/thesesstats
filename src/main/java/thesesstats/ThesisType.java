package thesesstats;

public enum ThesisType {

    ALL("Abschluss- und Praxisarbeiten"),
    ALL_BUT_PA("Abschlussarbeiten"),
    BA("Bachelorarbeiten"),
    MA("Masterarbeiten"),
    PA("Praxisarbeiten");

    public final String title;

    private ThesisType(final String title) {
        this.title = title;
    }

}
