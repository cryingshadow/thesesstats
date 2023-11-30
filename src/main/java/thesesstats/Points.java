package thesesstats;

public class Points {

    public int bachelorFirst;

    public int bachelorSecondLong;

    public int bachelorSecondShort;

    public int masterFirst;

    public int masterSecondLong;

    public int masterSecondShort;

    public int practicalCheck;

    public int practicalThesesFirst;

    public int practicalThesesSecondLong;

    public int practicalThesesSecondShort;

    public int sum() {
        return this.bachelorFirst * 3
            + this.bachelorSecondShort
            + this.bachelorSecondLong * 2
            + this.masterFirst * 5
            + this.masterSecondShort
            + this.masterSecondLong * 2
            + this.practicalCheck
            + this.practicalThesesFirst
            + this.practicalThesesSecondShort
            + this.practicalThesesSecondLong;
    }

}
