package pers.regular.limit;

/**
 * 限制执行时间
 * @auther ken.ck
 * @date 2023/8/9 15:52
 */
public class TimedCharSequence implements CharSequence {

    private final CharSequence sequence;
    private final long timestamp;

    public TimedCharSequence(CharSequence sequence, long milliseconds) {
        this.sequence = sequence;
        this.timestamp = System.currentTimeMillis() + milliseconds;
    }

    @Override
    public String toString() {return sequence.toString();}

    @Override
    public int length() {return sequence.length();}

    @Override
    public char charAt(int index) {
        if (timestamp < System.currentTimeMillis()) {
            throw new IllegalStateException("Regex match timeout");
        }
        return sequence.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return sequence.subSequence(start, end);
    }

}
