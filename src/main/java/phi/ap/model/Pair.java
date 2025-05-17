package phi.ap.model;

public class Pair<T> {
        private T key;
        private int value;

        public Pair(T key, int value) {
            this.key = key;
            this.value = value;
        }


    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
