package Util.Error;

import Util.Position;

abstract public class MxError extends RuntimeException {
    private final Position pos;
    private final String message;

    public MxError(String msg, Position pos) {
        this.pos = pos;
        this.message = msg;
    }

    public String getText() {
        return message + ": " + (pos == null ? "" : pos.toString());
    }
}
