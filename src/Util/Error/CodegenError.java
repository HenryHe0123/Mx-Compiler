package Util.Error;

public class CodegenError extends MxError {
    public CodegenError(String msg) {
        super("CodegenError: " + msg, null);
    }
}
