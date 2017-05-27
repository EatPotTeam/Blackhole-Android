package com.blackhole.blackhole.framework;

/**
 * Author: perqin
 * Date  : 5/27/17
 */

public final class RxResult<T> {
    public static <T> RxResult<T> error(Throwable error) {
        if (error == null) throw new NullPointerException("error == null");
        return new RxResult<>(null, error);
    }

    public static <T> RxResult<T> result(T result) {
        if (result == null) throw new NullPointerException("result == null");
        return new RxResult<>(result, null);
    }

    private final T result;
    private final Throwable error;

    private RxResult(T result, Throwable error) {
        this.result = result;
        this.error = error;
    }

    public T result() {
        return result;
    }

    public Throwable error() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }
}
