package com.transposition.task.service;

import java.util.List;

public class NoteValidationService {

    private final int HIGH_OCTAVE_RANGE = 5;
    private final int LOW_OCTAVE_RANGE = -3;
    private final int HIGH_NOTE_RANGE = 12;
    private final int LOW_NOTE_RANGE = 1;
    private static final int FIRST_ARG = 0;
    private static final int SECOND_ARG = 1;

    // Validates if a musical note is within the specified piano keyboard range
    public boolean isValidNote(List<Integer> note) {
        var octave = note.get(FIRST_ARG);
        var noteNumber = note.get(SECOND_ARG);
        // Check if the octave is within the valid range (-3 to 5)
        if (octave >= LOW_OCTAVE_RANGE && octave <= HIGH_OCTAVE_RANGE) {
           return isValidNoteNumber(noteNumber);
        } else {
            return false; // Invalid octave
        }
    }

    // Check if the note number is within the valid range (1 to 12)
    private boolean isValidNoteNumber(int noteNumber) {
        if (noteNumber >= LOW_NOTE_RANGE && noteNumber <= HIGH_NOTE_RANGE) {
            return true; // Valid note
        } else {
            return false; // Invalid note number
        }
    }
}
