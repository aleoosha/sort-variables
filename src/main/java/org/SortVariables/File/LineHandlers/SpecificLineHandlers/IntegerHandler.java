package org.SortVariables;

class IntegerHandler extends LineHandler {

    public IntegerHandler(OutputProcessedFile outputFile) {
        super(outputFile);
    }

    @Override
    protected boolean canHandle(String line) {
        return line.trim().matches("^-?\\d+$");
    }
}