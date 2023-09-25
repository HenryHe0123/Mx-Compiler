import Util.Error.MxError;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LocalJudge {
    /* INSTRUCTION:
     * To use this local judge, you need to enable assertions for your IDE.
     * For IntelliJ IDEA, go to Run -> Edit Configurations -> VM options, and add "-ea" to the text box.
     * While judging, the program will generate some temporary files under tmpFolder, which will be auto-cleared after test.
     * Please make sure that tmpFolder is useless before judging, preventing from deleting your files unexpectedly.
     * Be free to turn off any tests you want to skip and change the configuration below.
     *
     * REQUIREMENTS:
     * You may need to install libc6-dev-i386 ("sudo apt-get install libc6-dev-i386") to run clang-15 -m32 for IR test
     * and install ravel (https://github.com/Engineev/ravel) to run riscv32 assembly for Assembly test.
     *
     * REMARKS:
     * In some cases, codegen/t20.mx may fail IR test for unknown reasons (maybe related to the mismatch of target platform).
     * You can use ravel to test this testcase separately, or try using phi instead of alloca for logic expression.
     */
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    /* --------------------------------------------  configuration  --------------------------------------------- */
    private static final String semanticFolderName = "testcases/sema";
    private static final String codeGenFolderPrefix = "testcases/codegen/";
    private static final String optimFolderName = "testcases/optim"; //optim2 or optim-new are also available
    private static final String tmpFolderName = ".tmp";
    private static final String tmpFilePath = tmpFolderName + "/test";
    private static final String builtinPathPrefix = "builtin/builtin";
    private static final String clang = "clang-15";
    private static final boolean showSemanticDetail = false;
    private static final boolean showIRDetail = true;
    private static final boolean showAsmDetail = true;
    private static final boolean showOptDetail = true;

    public static void main(String[] args) throws Exception {
        testSemantic(true);
        testIR(false);
        testAssembly(true);
        testAssemblyForOptimize(true);
    }

    /* ------------------------------------- Assembly For Optimize ------------------------------------------- */

    public static void testAssemblyForOptimize(boolean on) { //only for assembly correctness
        if (!on) {
            System.out.println(RESET + "Skip assembly test for optimize.");
            return;
        }
        System.out.println(RESET + "Start testing assembly for optimize...");
        initTmpFolder();
        var failList = new ArrayList<>();
        File[] files = new File(optimFolderName).listFiles();
        if (files != null) {
            for (File file : files) {
                if (!testAssemblyForSingleFileOfOpt(file))
                    failList.add(file.getName());
            }
        } else {
            System.err.println("optim folder not found");
            cleanUp();
            return;
        }
        if (failList.isEmpty()) {
            System.out.println(GREEN + "All assembly test for optimize passed!");
        } else {
            System.out.println(RED + "Some assembly test for optimize failed:");
            failList.forEach(filename -> System.out.println(RED + filename));
        }
        cleanUp();
    }

    private static boolean testAssemblyForSingleFileOfOpt(File file) {
        String fileName = file.getAbsolutePath();
        if (!fileName.endsWith(".mx")) return true;
        if (showOptDetail) System.out.println
                (YELLOW + fileName.substring(fileName.indexOf("optim")) + ": ");
        if (checkAssembly(fileName)) {
            if (showOptDetail) System.out.println(GREEN + "Pass!");
            return true;
        } else {
            if (showOptDetail) System.out.println(RED + "Fail!");
            return false;
        }
    }

    /* ---------------------------------------------  Assembly  ---------------------------------------------- */

    public static void testAssembly(boolean on) {
        if (!on) {
            System.out.println(RESET + "Skip assembly test.");
            return;
        }
        System.out.println(RESET + "Start testing assembly...");
        initTmpFolder();
        String fileName;
        var failList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(codeGenFolderPrefix + "judgelist.txt"))) {
            while ((fileName = reader.readLine()) != null) {
                //fileName = fileName.substring(2);
                if (showAsmDetail) System.out.println(YELLOW + fileName + ": ");
                if (checkAssembly(codeGenFolderPrefix + fileName)) {
                    if (showAsmDetail) System.out.println(GREEN + "Pass!");
                } else {
                    failList.add(fileName);
                    if (showAsmDetail) System.out.println(RED + "Fail!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (failList.isEmpty()) {
            System.out.println(GREEN + "All assembly test passed!");
        } else {
            System.out.println(RED + "Some assembly test failed:");
            failList.forEach(filename -> System.out.println(RED + filename));
        }
        cleanUp();
    }

    private static boolean checkAssembly(String mxFileName) {
        try {
            //compile to assembly
            final String asmFileName = tmpFilePath + ".s";
            InputStream compilerInput = new FileInputStream(mxFileName);
            try (PrintStream compilerOutput = new PrintStream(new FileOutputStream(asmFileName))) {
                Compiler.compile(compilerInput, null, compilerOutput);
            } catch (MxError er) {
                System.err.println(er.getText());
                System.err.println("compile error");
                return false;
            }

            //get input and standard output & exitCode
            final String input = getContentFromFile(mxFileName, "=== input ===");
            final String output = getContentFromFile(mxFileName, "=== output ===");
            int exitCode = getExitCodeFromFile(mxFileName, "ExitCode:");

            //write input to tmp.in
            final String inFileName = tmpFilePath + ".in";
            writeStringToFile(input, inFileName);

            //create tmp.out & tmp-ravel.out
            final String ravelOutFileName = tmpFilePath + "-ravel.out";
            final String outFileName = tmpFilePath + ".out";
            File file = new File(outFileName);
            assert file.exists() || file.createNewFile() : "create tmp.out failed";

            //redirect process output to tmp-ravel.out
            ProcessBuilder processBuilder = new ProcessBuilder("wsl", "/usr/local/opt/bin/ravel",
                    "--input-file=" + inFileName, "--output-file=" + outFileName,
                    asmFileName, builtinPathPrefix + ".s");

            processBuilder.redirectOutput(new File(ravelOutFileName));
            processBuilder.redirectErrorStream(true);

            //run ravel
            Process process = processBuilder.start();
            int processExitCode = process.waitFor();
            if (processExitCode != 0) {
                if (showAsmDetail) {
                    String ravelOutput = Files.readString(Paths.get(ravelOutFileName));
                    System.err.println("ravel error");
                    System.err.println(ravelOutput);
                }
                return false;
            }

            //check
            int ravelExitCode = getExitCodeFromFile(ravelOutFileName, "exit code:");
            if (ravelExitCode != exitCode || outputNotSame(output, outFileName)) {
                if (showAsmDetail) System.err.println("running results wrong");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("other error occurred");
            e.printStackTrace();
            return false;
        }
    }

    /* ------------------------------------------------  IR  ------------------------------------------------- */

    public static void testIR(boolean on) {
        if (!on) {
            System.out.println(RESET + "Skip IR test.");
            return;
        }
        System.out.println(RESET + "Start testing IR...");
        initTmpFolder();
        String fileName;
        var failList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(codeGenFolderPrefix + "judgelist.txt"))) {
            while ((fileName = reader.readLine()) != null) {
                //fileName = fileName.substring(2);
                if (showIRDetail) System.out.println(YELLOW + fileName + ": ");
                if (checkIR(fileName)) {
                    if (showIRDetail) System.out.println(GREEN + "Pass!");
                } else {
                    failList.add(fileName);
                    if (showIRDetail) System.out.println(RED + "Fail!");
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
            assert folder.mkdir() : "create tmp folder failed";
        } else { //clear previous files
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    assert file.delete() : "delete previous file in tmp folder failed";
                }
            }
        }
    }

    private static void cleanUp() {
        File folder = new File(tmpFolderName);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                assert file.delete() : "delete tmp file failed";
            }
        }
        assert folder.delete() : "delete tmp folder failed";
    }

    private static boolean checkIR(String fileName) {
        try {
            //compile to llvm ir
            final String mxFileName = codeGenFolderPrefix + fileName;
            final String llFileName = tmpFilePath + ".ll";
            InputStream compilerInput = new FileInputStream(mxFileName);
            try (PrintStream compilerOutput = new PrintStream(new FileOutputStream(llFileName))) {
                Compiler.compile(compilerInput, compilerOutput, null);
            } catch (MxError er) {
                if (showIRDetail) {
                    System.err.println(er.getText());
                    System.err.println("compile error");
                }
                return false;
            }

            //build executable file by clang
            final String exeFileName = tmpFilePath;
            ProcessBuilder processBuilder = new ProcessBuilder("wsl",
                    clang, builtinPathPrefix + ".ll", llFileName, "-o", exeFileName, "-m32");
            Process process = processBuilder.start();
            int processExitCode = process.waitFor();
            if (processExitCode != 0) {
                if (showIRDetail) {
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
            int exitCode = getExitCodeFromFile(mxFileName, "ExitCode:");

            //write input to tmp.in
            final String inFileName = tmpFilePath + ".in";
            writeStringToFile(input, inFileName);

            //redirect process input and output
            final String outFileName = tmpFilePath + ".out";
            processBuilder = new ProcessBuilder("wsl", exeFileName);
            processBuilder.redirectInput(new File(inFileName));
            processBuilder.redirectOutput(new File(outFileName));

            //run executable file
            process = processBuilder.start();
            processExitCode = process.waitFor();

            //check exitCode
            if (processExitCode != exitCode || outputNotSame(output, outFileName)) {
                if (showAsmDetail) System.err.println("running results wrong");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("other error occurred");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean outputNotSame(String output, String outFileName) throws IOException {
        String out = Files.readString(Paths.get(outFileName)).trim();
        return !out.equals(output);
    }

    public static void writeStringToFile(String input, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(input);
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

    private static int getExitCodeFromFile(String fileName, String marker) {
        int exitCode = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(marker)) {
                    exitCode = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exitCode;
    }

    /* ---------------------------------------------  Semantic  ---------------------------------------------- */

    private static ArrayList<String> failListForSemantic = new ArrayList<>();

    public static void testSemantic(boolean on) {
        if (!on) {
            System.out.println(RESET + "Skip semantic test.");
            return;
        }
        System.out.println(RESET + "Start testing semantic...");
        failListForSemantic.clear();
        if (traverseFolderForSemantic(new File(semanticFolderName))) {
            System.out.println(GREEN + "All semantic test passed!");
        } else {
            System.out.println(RED + "Some semantic test failed:");
            failListForSemantic.forEach(filename -> System.out.println(RED + filename));
        }
    }

    private static boolean traverseFolderForSemantic(File folder) {
        if (folder.isDirectory()) {
            boolean success = true;
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!traverseFolderForSemantic(file)) success = false;
                }
            }
            return success;
        } else {
            String fileName = folder.getAbsolutePath();
            String shortName = fileName.substring(fileName.indexOf("sema\\") + 5);
            if (showSemanticDetail) System.out.println
                    (YELLOW + shortName + ": ");
            if (checkSemantic(fileName)) {
                if (showSemanticDetail) System.out.println(GREEN + "Pass!");
                return true;
            } else {
                if (showSemanticDetail) System.out.println(RED + "Fail!");
                failListForSemantic.add(shortName);
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
            Compiler.compile(input, null, null);
        } catch (Exception er) {
            return !success;
        }
        return success;
    }
}

