package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Base class for all {@code Word} tests.
 * 
 * @author Alexander Mattes
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractTestWord {

	/**
	 * A random number generator.
	 */
	protected static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
	
	/**
	 * The number of iterations that are used for tests that include random values.
	 */
	protected static final int ITERATIONS = 100000;
	
	/**
	 * The (arbitrary) {@code Word} sizes to be used.
	 */
	protected static final int[] SIZES = {32, 64, 128, 256};
	
	/**
	 * The long value with all bits set to {@code 0};
	 */
	protected static final long ALL_BITS_0 = 0L;

	/**
	 * The long value with all bits set to {@code 1};
	 */
	protected static final long ALL_BITS_1 = -1L;
	
	/**
	 * The 2-bit pattern {@code 00} that is used to create pre-defined bit sequences with patterns.
	 */
	protected static final String VALUE00 = "00";

	/**
	 * The 2-bit pattern {@code 01} that is used to create pre-defined bit sequences with patterns.
	 */
	protected static final String VALUE01 = "01";

	/**
	 * The 2-bit pattern {@code 10} that is used to create pre-defined bit sequences with patterns.
	 */
	protected static final String VALUE10 = "10";

	/**
	 * The 2-bit pattern {@code 11} that is used to create pre-defined bit sequences with patterns.
	 */
	protected static final String VALUE11 = "11";
		
	/**
	 * A list of 2-bit patterns that are used to create pre-defined bit sequences with patterns.
	 */
	protected static final String[] VALUES = new String[] {
		VALUE00,
		VALUE01,
		VALUE10,
		VALUE11
	};

	/**
	 * Asserts that the given {@code Word} has the given {@code size} and {@code value}.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final byte value, final Word word) {
		byte expected = (byte) ((Byte.SIZE <= size) ? value: value & ((byte) Math.pow(2, size) - 1));
		
		assertEquals(size, word.getSize());
		assertEquals(expected, word.toByte());
	}
	
	/**
	 * Asserts that the given {@code Word} has the given size and {@code String} value.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final String value, final Word word) {
		assertEquals(size, word.getSize());
		assertEquals(value, word.toString());
	}
	
	/**
	 * Asserts that the given {@code Word} has the given size and {@code short} value.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final short value, final Word word) {
		short expected = (short) ((Short.SIZE <= size) ? value: value & ((short) Math.pow(2, size) - 1));
		
		assertEquals(size, word.getSize());
		assertEquals(expected, word.toShort());
	}
	
	/**
	 * Asserts that the given {@code Word} has the given size and {@code int} value.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final int value, final Word word) {
		int expected = (int) ((Integer.SIZE <= size) ? value: value & ((int) Math.pow(2, size) - 1));
		
		assertEquals(size, word.getSize());
		assertEquals(expected, word.toInteger());
	}
	
	/**
	 * Asserts that the given {@code Word} has the given size and {@code long} value.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final long value, final Word word) {	
		long expected = (Long.SIZE <= size) ? value : value & ((long) Math.pow(2, size) - 1);
		
		assertEquals(size, word.getSize());		
		assertEquals(expected, word.toLong());
	}
	
	/**
	 * Asserts that the given {@code Word} has the given size and {@code char} value.
	 *
	 * @param size The expected size.
	 * @param value The expected value.
	 * @param word The actual word.
	 */
	protected static void assertWord(final int size, final char value, final Word word) {
		char expected = (char) ((Character.SIZE <= size) ? value : value & ((char) Math.pow(2, size) - 1));

		assertEquals(size, word.getSize());		
		assertEquals(expected, word.toChar());
	}
	
	/**
	 * Returns the string representation of a bit sequence with the given size where all bits are set to {@code 0}. 
	 * 
	 * @param size The size to be used.
	 * @return The string representation of a bit sequence with the given size where all bits are set to {@code 0}. 
	 */
	protected static String getStringZero(final int size) {
		return StringUtils.repeat('0', size);
	}
	
	/**
	 * Returns the string representation of a bit sequence with the given size where only the bits at the given indices
	 * are set to {@code 1}. 
	 *  
	 * @param size The size to be used.
	 * @param indices The indices of the bits that shall be set to {@code 1}.
	 * @return The string representation of a bit sequence with the given size where only the bits at the given indices
	 *         are set to {@code 1}. 
	 */
	protected static String getStringValue(final int size, final int ... indices) {
		final StringBuilder result = new StringBuilder(StringUtils.repeat('0', size));
		
		if (indices != null) {
			for (final int i  : indices) {
				result.setCharAt(size - i - 1, '1');
			}
		}
		
		return result.toString();
	}
	
	/**
	 * Returns the string representation of a bit sequence with the given size where random bits are set to {@code 1}.
	 * 
	 * @param size The size to be used.
	 * @return The string representation of a bit sequence with the given size where random bits are set to {@code 1}.
	 */
	protected static String getStringRandom(final int size) {
		final StringBuilder result = new StringBuilder(getStringZero(size));
		
		for (int i = 0; i < (RANDOM.nextInt(size) + 1); i++) {
			result.setCharAt(RANDOM.nextInt(size), '1');
		}

		return result.toString();
	}

	/**
	 * Returns the string representation of a bit sequesizence with the given size where the upper half is a repetition of
	 * the high pattern and the lower half is a repetition of the low pattern.
	 * 
	 * @param size The size to be used.
	 * @param high The upper half pattern.
	 * @param low The lower half pattern.
	 * @return The string representation of a bit sequence with the given size where the upper half is a repetition of
	 *         the high pattern and the lower half is a repetition of the low pattern.
	 */
	protected static String getStringPattern(final int size, final String high, final String low) {
		return StringUtils.repeat(high, (size / 4)) + StringUtils.repeat(low, (size / 4));
	}	
	
}
