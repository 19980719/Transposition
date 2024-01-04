package com.transposition.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NoteTransposeService {

    private final int HIGH_RANGE = 12;
    private final int LOW_RANGE = 1;
    private static final int FIRST_ARG = 0;
    private static final int SECOND_ARG = 1;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NoteValidationService validationService = new NoteValidationService();

    // Reads the input JSON file, transposes the notes, validates, and writes the result to an output JSON file
    public void writeTransposedNotesToFile(String inputFilePath, int semitones, String outputFilePath) {
        try {
            var notes = readNotesFromFile(inputFilePath);
            var transposedNotes = getTransposedNotes(notes, semitones);
            if (transposedNotes.stream().allMatch(validationService::isValidNote)) {
                writeToFile(transposedNotes, outputFilePath);
            } else {
                System.err.println("Transposed notes fall out of the keyboard range");
            }
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    //Read notes from input file
    private List<List<Integer>> readNotesFromFile(String inputFilePath) throws IOException {
        var jsonContent = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        return objectMapper.readValue(jsonContent, List.class);
    }

    // Transposes a collection of notes by the specified number of semitones
    private List<List<Integer>> getTransposedNotes(List<List<Integer>> notes, int semitones) {
        var transposedNotes = new ArrayList<List<Integer>>();
        // Iterate over each note
        for (List<Integer> note : notes) {
            var transposeOctave = note.get(FIRST_ARG);
            var transposeNote = note.get(SECOND_ARG) + semitones;
            // Adjust the new note and octave to stay within valid musical ranges
            var transposedNote = getAdjustedByRange(transposeOctave, transposeNote);
            // Add the transposed note to the result collection
            transposedNotes.add(transposedNote);

        }
        return transposedNotes;
    }

    // Adjust the new note and octave to stay within valid musical ranges
    private List<Integer> getAdjustedByRange(int transposeOctave, int transposeNote) {
        while (transposeNote > HIGH_RANGE) {
            transposeOctave++;
            transposeNote -= HIGH_RANGE;
        }
        while (transposeNote < LOW_RANGE) {
            transposeOctave--;
            transposeNote += HIGH_RANGE;
        }
        return List.of(transposeOctave, transposeNote);
    }

    //Write to output file
    private void writeToFile(List<List<Integer>> transposedNotes, String outputFilePath) throws IOException {
        var transposedJson = objectMapper.writeValueAsString(transposedNotes);
        Files.write(Paths.get(outputFilePath), transposedJson.getBytes());
        System.out.println("Transposition completed successfully. Result saved by path: " + outputFilePath);
    }

}
