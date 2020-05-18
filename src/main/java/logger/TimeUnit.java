package logger;

public enum TimeUnit {
    SEC("s", 1000000000.0),
    MILI("ms", 1000000.0),
    MICRO("ys", 1000.0),
    NANO("ns", 1.0);

    public final String key;
    public final double value;

    TimeUnit(String key, double value) {
        this.key = key;
        this.value = value;
    }
}
