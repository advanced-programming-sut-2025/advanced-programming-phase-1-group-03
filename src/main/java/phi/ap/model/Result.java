package phi.ap.model;

public class Result<T> {
    public boolean success;
    public T data;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
