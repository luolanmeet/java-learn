package pers.regular.limit;

/**
 * 限制次数
 * @auther ken.ck
 * @date 2023/8/9 15:53
 */
public class CountedCharSequence implements CharSequence {

    private final CharSequence sequence;
    private long count;

    public CountedCharSequence(CharSequence sequence, long maxCount) {
        this.sequence = sequence;
        this.count = maxCount;
    }

    @Override
    public String toString() {return sequence.toString();}

    @Override
    public int length() {return sequence.length();}

    @Override
    public char charAt(int index) {
        if (count <= 0) {
            throw new IllegalStateException("Regex match timeout");
        }
        count--;
        return sequence.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return sequence.subSequence(start, end);
    }

}
