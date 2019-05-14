package Compiler.DotPrinter;

public class DotPrinter {

    private String inputFile;
    private String outputFile;
    private int processCode;

    public DotPrinter(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void exec() throws Exception {
        String cmd = getCommand("dot", "-Teps", getAbsolutePath(inputFile), getAbsolutePath(outputFile));
        Process process = Runtime.getRuntime().exec(cmd);
        this.processCode = process.waitFor();
    }

    String getCommand(String program, String format, String inputFile, String outputFile) {
        return program + " " + format + " " + inputFile + " -o " + outputFile;
    }

    // Constructs path relative from directory 'P4/src/'
    private String getAbsolutePath(String filename) {
        return System.getProperty("user.dir") + "/src/" + filename;
    }

    public int getProcessCode() {
        return processCode;
    }
}
