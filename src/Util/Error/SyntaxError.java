package Util.Error;

import Util.Position;

public class SyntaxError extends MxError {
    public SyntaxError(String msg, Position pos) {
        super("SyntaxError: " + msg, pos);
    }
}
