import java.io.*;
import java.nio.file.*;
import java.util.*;

import Util.Error.Error;

public class LocalJudge {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    private static final String semanticFolderName = "testcases/sema";
    private static final String codeGenFolderPathPrefix = "testcases/codegen/";
    private static final String tmpFolderName = ".tmp";
    private static final String tmpFilePath = tmpFolderName + "/test";
    private static final String builtinPath = "builtin/builtin.ll";

    public static void main(String[] args) throws Exception {
        testSemantic(false);
        testIR(true);
    }

    public static void testIR(boolean showDetail) {
        System.out.println(RESET + "Start testing IR...");
        initTmpFolder();
        String fileName;
        var failList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(codeGenFolderPathPrefix + "judgelist.txt"))) {
            while ((fileName = reader.readLine()) != null) {
                fileName = fileName.substring(2);
                if (showDetail) System.out.println(YELLOW + fileName + ": ");
                if (checkIR(fileName, showDetail)) {
                    if (showDetail) System.out.println(GREEN + "Pass!");
                } else {
                    failList.add(fileName);
                    if (showDetail) System.out.println(RED + "Fail!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (failList.isEmpty()) {
            System.out.println(GREEN + "All IR test passed!");
        } else {
            System.out.println(RED + "Some IR test failed:");
            failList.forEach(filename -> System.out.println(RED + filename));
        }
        cleanUp();
    }

    private static void initTmpFolder() {
        File folder = new File(tmpFolderName);
        if (!folder.exists()) {
            assert folder.mkdir();
        } else { //clear previous files
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    assert file.delete();
                }
            }
        }
    }

    private static void cleanUp() {
        File folder = new File(tmpFolderName);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                assert file.delete();
            }
        }
        assert folder.delete();
    }

    private static boolean checkIR(String fileName, boolean showDetail) {
        try {
            //compile to llvm ir
            final String mxFileName = codeGenFolderPathPrefix + fileName;
            final String llFileName = tmpFilePath + ".ll";
            InputStream compilerInput = new FileInputStream(mxFileName);
            try (PrintStream compilerOutput = new PrintStream(new FileOutputStream(llFileName))) {
                Compiler.compile(compilerInput, compilerOutput);
            } catch (Error er) {
                if (showDetail) {
                    System.err.println(er.getText());
                    System.err.println("compile error");
                }
                return false;
            }

            //build executable file by clang
            final String exeFileName = tmpFilePath;
            ProcessBuilder processBuilder = new ProcessBuilder("wsl", "clang-15", builtinPath, llFileName, "-o", exeFileName, "-m32");
            Process process = processBuilder.start();
            int processExitCode = process.waitFor();
            if (processExitCode != 0) {
                if (showDetail) {
                    InputStream errorStream = process.getErrorStream();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.err.println(line);
                        }
                    }
                    System.err.println("clang error");
                }
                return false;
            }

            //get input and standard output & exitCode
            final String input = getContentFromFile(mxFileName, "=== input ===");
            final String output = getContentFromFile(mxFileName, "=== output ===");
            int exitCode = getExitCodeFromFile(mxFileName);

            //write input to tmp.in
            final String inFileName = tmpFilePath + ".in";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inFileName))) {
                writer.write(input);
            }

            //redirect process input and output
            final String outFileName = tmpFilePath + ".out";
            processBuilder = new ProcessBuilder("wsl", exeFileName);
            processBuilder.redirectInput(new File(inFileName));
            processBuilder.redirectOutput(new File(outFileName));

            //run executable file
            process = processBuilder.start();
            processExitCode = process.waitFor();

            //check exitCode
            if (processExitCode != exitCode) {
                if (showDetail) System.err.println("exit code not match");
                return false;
            }

            //check output
            String processOutput = Files.readString(Paths.get(outFileName)).trim();
            if (!processOutput.equals(output)) {
                if (showDetail) System.err.println("output not match");
                return false;
            }

            return true;
        } catch (Exception err) {
            System.err.println("other error occurred");
            return false;
        }
    }

    private static String getContentFromFile(String fileName, String startMarker) {
        StringBuilder content = new StringBuilder();
        boolean startReading = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (startReading) {
                    if (line.equals("=== end ===")) break;
                    content.append(line).append("\n");
                } else if (line.equals(startMarker)) {
                    startReading = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
    }

    private static int getExitCodeFromFile(String fileName) {
        int exitCode = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ExitCode:")) {
                    exitCode = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exitCode;
    }

    //----------------------------------- Semantic ----------------------------------------

    public static void testSemantic(boolean showDetail) {
        System.out.println(RESET + "Start testing semantic...");
        if (traverseFolderForSemantic(new File(semanticFolderName), showDetail)) {
            System.out.println(GREEN + "All semantic tests passed!");
        } else {
            System.out.println(RED + "Some semantic tests failed!");
        }
    }

    private static boolean traverseFolderForSemantic(File folder, boolean showDetail) {
        if (folder.isDirectory()) {
            boolean success = true;
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!traverseFolderForSemantic(file, showDetail)) success = false;
                }
            }
            return success;
        } else {
            String fileName = folder.getAbsolutePath();
            if (showDetail) System.out.println
                    (YELLOW + fileName.substring(folder.getAbsolutePath().indexOf("sema\\") + 5) + ": ");
            if (checkSemantic(fileName)) {
                if (showDetail) System.out.println(GREEN + "Pass!");
                return true;
            } else {
                if (showDetail) System.out.println(RED + "Fail!");
                return false;
            }
        }
    }

    private static boolean checkSemantic(String fileName) {
        boolean success = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber == 6) {
                    String result = line.substring(line.indexOf(":") + 2);
                    success = result.equals("Success");
                    break; // find the answer in line 6 and exit the loop
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream input = new FileInputStream(fileName);
            Compiler.compile(input, null);
        } catch (Exception er) {
            return !success;
        }
        return success;
    }
}

