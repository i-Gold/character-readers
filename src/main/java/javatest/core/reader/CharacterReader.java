package javatest.core.reader;

/**
 * Interface contract for Character Readers.
 *
 * @author Formpipe Software Limited
 */
public interface CharacterReader {

    /**
     * @return the next character in the stream
     * @throws javatest.core.exception.EndOfStreamException if there are no more characters
     */
    char getNextChar();
}
