package Util.Error;

import Util.Position;

abstract public class Error extends RuntimeException {
    private final Position pos;
    private final String message;

    public Error(String msg, Position pos) {
        this.pos = pos;
        this.message = msg;
    }

    public String toString() {
        return message + ": " + (pos == null ? "" : pos.toString());
    }
}
