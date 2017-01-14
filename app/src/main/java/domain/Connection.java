package domain;

/**
 * Created by PrzemekMadzia on 2016-12-15.
 */

public class Connection {
    private int from;
    private  int to;

    public Connection(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
