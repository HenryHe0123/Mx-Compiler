package Util.Error;

import Util.Position;

public class SemanticError extends MxError {
    public SemanticError(String msg, Position pos) {
        super("SemanticError: " + msg, pos);
    }
}
