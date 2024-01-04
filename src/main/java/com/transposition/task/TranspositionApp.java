package com.transposition.task;

import com.transposition.task.service.NoteTransposeService;


public class TranspositionApp {
    private static final int MAX_ARG_NUMBER = 2;
    private static final int ERROR_STATUS = 1;
    private static final int FIRST_ARG = 0;
    private static final int SECOND_ARG = 1;
    private static final NoteTransposeService transposeService = new NoteTransposeService();

    public static void main(String[] args) {
        checkArgs(args);
        var inputFilePath = args[FIRST_ARG];
        var semitones = Integer.parseInt(args[SECOND_ARG]);
        transposeService.writeTransposedNotesToFile(inputFilePath, semitones);
    }

    private static void checkArgs(String[] args) {
        if (args.length != MAX_ARG_NUMBER) {
            System.err.println("Failed: invalid input arguments");
            System.exit(ERROR_STATUS);
        }
    }
}