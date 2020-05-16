package pers.chainOfResponsibility.handler;

/**
 * 责任链 + 建造者
 */
public abstract class Handler {

    protected Handler chain;

    private void next(Handler handler) {
        this.chain = handler;
    }

    public abstract void doHandle(String username, String password);

    public static class Builder {
        private Handler head;
        private Handler tail;

        public Builder addHandler(Handler handler) {

            if (head == null) {
                this.head = this.tail = handler;
                return this;
            }

            this.tail.next(handler);
            tail = handler;
            return this;
        }

        public Handler build() {
            return head;
        }
    }

}
