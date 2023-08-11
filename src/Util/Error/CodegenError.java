package Util.Error;

public class CodegenError extends Error {
    public CodegenError(String msg) {
        super("CodegenError: " + msg, null);
    }
}
