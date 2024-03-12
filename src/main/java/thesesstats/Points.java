package thesesstats;

import java.time.*;
import java.util.*;

public class Points {

    public List<Thesis> bachelorFirst;

    public List<Thesis> bachelorSecondLong;

    public List<Thesis> bachelorSecondShort;

    public List<Thesis> masterFirst;

    public List<Thesis> masterSecondLong;

    public List<Thesis> masterSecondShort;

    public List<LocalDate> practicalCheck;

    public List<Thesis> practicalThesesFirst;

    public List<Thesis> practicalThesesSecondLong;

    public List<Thesis> practicalThesesSecondShort;

    public int sum() {
        return this.bachelorFirst.size() * 3
            + this.bachelorSecondShort.size()
            + this.bachelorSecondLong.size() * 2
            + this.masterFirst.size() * 5
            + this.masterSecondShort.size()
            + this.masterSecondLong.size() * 3
            + this.practicalCheck.size()
            + this.practicalThesesFirst.size() * 2
            + (this.practicalThesesSecondShort.size() + this.practicalThesesSecondLong.size()) / 2;
    }

}
