/**
 * Used to track elapsed milliseconds using System time
 * @author Owen Reid
 */


 class SimpleTimer {

    private long markedMilliseconds = System.currentTimeMillis();

    public SimpleTimer() {
    }

    public void mark() {
        markedMilliseconds = System.currentTimeMillis();
    }

    public long elapsedMilliseconds() {
        return System.currentTimeMillis() -  markedMilliseconds;
    }
}
