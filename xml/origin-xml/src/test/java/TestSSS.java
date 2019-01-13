import java.util.Arrays;

public class TestSSS {

    private int[] nums = new int[1000001];

    /** Initialize your data structure here. */
    public TestSSS() {
        Arrays.fill(nums, -1);
    }

    public void add(int key) {
        nums[key] = 1;
    }

    public void remove(int key) {
        nums[key] = 0;
    }

    /**
     * Returns true if this set did not already contain the specified element
     */
    public boolean contains(int key) {
        return nums[key] == 1;
    }

}
