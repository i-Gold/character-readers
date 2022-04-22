package javatest.core.reader;

import javatest.core.exception.EndOfStreamException;

import java.util.Random;

/**
 * Slow implementation of {@link CharacterReader} used for Question 2
 *
 * @author Formpipe Software Limited
 */
public class SlowCharacterReader implements CharacterReader {

    private static final String CONTENT = "Alice was beginning to get very tired of sitting by her sister\n" +
            "on the bank, and of having nothing to do:  once or twice she had\n" +
            "peeped into the book her sister was reading, but it had no\n" +
            "pictures or conversations in it, 'and what is the use of a book,'\n" +
            "thought Alice `without pictures or conversation?'\n" +
            "\n" +
            "  So she was considering in her own mind (as well as she could,\n" +
            "for the hot day made her feel very sleepy and stupid), whether\n" +
            "the pleasure of making a daisy-chain would be worth the trouble\n" +
            "of getting up and picking the daisies, when suddenly a White\n" +
            "Rabbit with pink eyes ran close by her.";

    private int position = 0;
    private Random random = new Random();

    public char getNextChar() {

        try {
            Thread.sleep(random.nextInt(200));
        } catch (InterruptedException e) {/*carry on*/}

        if(position >= CONTENT.length())
            throw new EndOfStreamException();

        return CONTENT.charAt(position++);
    }
}
