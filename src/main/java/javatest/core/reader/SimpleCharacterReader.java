package javatest.core.reader;

import javatest.core.exception.EndOfStreamException;

/**
 * Simple implementation of {@link CharacterReader} used for Question 1
 *
 * @author Formpipe Software Limited
 */
public class SimpleCharacterReader implements CharacterReader {

    private static final String CONTENT = "It was the best of times, it was the worst of times,\n" +
            "it was the age of wisdom, it was the age of foolishness,\n" +
            "it was the epoch of belief, it was the epoch of incredulity,\n" +
            "it was the season of Light, it was the season of Darkness,\n" +
            "it was the spring of hope, it was the winter of despair,\n" +
            "we had everything before us, we had nothing before us,\n" +
            "we were all going direct to Heaven, we were all going direct\n" +
            "the other way--in short, the period was so far like the present\n" +
            "period, that some of its noisiest authorities insisted on its\n" +
            "being received, for good or for evil, in the superlative degree\n" +
            "of comparison only.\n" +
            "\n" +
            "There were a king with a large jaw and a queen with a plain face,\n" +
            "on the throne of England; there were a king with a large jaw and\n" +
            "a queen with a fair face, on the throne of France.  In both\n" +
            "countries it was clearer than crystal to the lords of the State\n" +
            "preserves of loaves and fishes, that things in general were\n" +
            "settled for ever";

    private int position = 0;

    public char getNextChar() {

        if(position >= CONTENT.length())
            throw new EndOfStreamException();

        return CONTENT.charAt(position++);
    }
}
