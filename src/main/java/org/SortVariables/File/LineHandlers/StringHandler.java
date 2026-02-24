package org.SortVariables;

class StringHandler extends LineHandler {

    public StringHandler(ProcessedFile outputFile) {
        super(outputFile);
    }

    @Override
    protected boolean canHandle(String line) {
        return true;
    }
}