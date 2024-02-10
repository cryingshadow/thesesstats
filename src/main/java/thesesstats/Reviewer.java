package thesesstats;

public enum Reviewer {

    ALL("Betreuer"), FIRST("Erstbetreuer"), SECOND("Zweitbetreuer");

    public final String title;

    private Reviewer(final String title) {
        this.title = title;
    }

}
