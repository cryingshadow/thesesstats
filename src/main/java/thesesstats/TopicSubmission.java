package thesesstats;

import java.text.*;
import java.util.*;

public record TopicSubmission(String type, String student, Date due, boolean submitted)
implements Comparable<TopicSubmission> {

    public static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

    @Override
    public int compareTo(final TopicSubmission o) {
        return this.due().compareTo(o.due());
    }

}
