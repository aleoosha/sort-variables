package org.SortVariables;

class FloatHandler extends LineHandler {

    public FloatHandler(OutputProcessedFile outputFile) {
        super(outputFile);
    }

    @Override
    protected boolean canHandle(String line) {
        return line.trim().matches("^-?\\d*\\.\\d+([eE][+-]?\\d+)?$|^-?\\d+[eE][+-]?\\d+$");
    }
}