package com.car.baselib.net.http;

public class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

    public ResponseThrowable(int code, String message) {
        this.code = code;
        this.message = message;
    }
}