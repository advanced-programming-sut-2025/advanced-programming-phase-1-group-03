package phi.ap.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        if (success != result.success) return false;
        return result.data.equals(data);
    }
}
