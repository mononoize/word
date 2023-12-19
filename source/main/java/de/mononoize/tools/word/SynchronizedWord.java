package de.mononoize.tools.word;

import java.io.Serial;
import java.util.stream.LongStream;

/**
 * <p>An synchronized set of bits with a fixed size.</p>
 * 
 * <p>See: {@link Word}.</p>
 *
 * @author Alexander Mattes
  */
public class SynchronizedWord extends Word {
	
	@Serial
	private static final long serialVersionUID = 9031820144374535420L;
	
	/**
	 * The mutex object.
	 */
	private final Object mutex = new Object();
	
	/**
	 * Constructs a new {@code SynchronizedWord} with the given number of bits. Initially, all bits are set to {@code 0}.
	 * 
	 * @param size The number of bits to be used.
	 */
	public SynchronizedWord(final int size) {
		super(size);
	}
	
	@Override
	public int hashCode() {
		synchronized(mutex) {
			return super.hashCode();
		}
	}

	@Override
    public boolean equals(final Object object) {
		synchronized(mutex) {
			return super.equals(object);
		}
	}

	@Override
	public Object clone() {
		synchronized(mutex) {
			return super.clone();
		}
	}

	@Override
	public int compareTo(final Word that) {
		synchronized(mutex) {
			return super.compareTo(that);
		}
	}

	@Override
	public String toString() {
		synchronized(mutex) {
			return super.toString();
		}
	}

	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 8} bits. 
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final byte value) {
		return Word.toString(value);
	}

	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 16} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final short value) {
		return Word.toString(value);
	}

	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 32} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final int value) {
		return Word.toString(value);
	}

	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 64} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final long value) {
		return Word.toString(value);
	}

	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 16} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final char value) {
		return Word.toString(value);
	}

	@Override
	public byte toByte() {
		synchronized(mutex) {
			return super.toByte();
		}
	}

	@Override
	public short toShort() {
		synchronized(mutex) {
			return super.toShort();
		}
	}

	@Override
	public int toInteger() {
		synchronized(mutex) {
			return super.toInteger();
		}
	}

	@Override
	public long toLong() {
		synchronized(mutex) {
			return super.toLong();
		}
	}

	@Override
	public char toChar() {
		synchronized(mutex) {
			return super.toChar();
		}
	}

	@Override
	public int getSize() {
		synchronized(mutex) {
			return super.getSize();
		}
	}

	@Override
	public Word setValue(final Word that) {
		synchronized(mutex) {
			return super.setValue(that);
		}
	}

	@Override
	public Word setValue(final String value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public Word setValue(final byte value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public Word setValue(final short value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public Word setValue(final int value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public Word setValue(final long value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public Word setValue(final char value) {
		synchronized(mutex) {
			return super.setValue(value);
		}
	}

	@Override
	public long get(final int index) {
		synchronized(mutex) {
			return super.get(index);
		}
	}

	@Override
	public long get() {
		synchronized(mutex) {
			return super.get();
		}
	}

	@Override
	public boolean getBoolean(final int index) {
		synchronized(mutex) {
			return super.getBoolean(index);
		}
	}

	@Override
	public boolean getBoolean() {
		synchronized(mutex) {
			return super.getBoolean();
		}
	}

	@Override
	public Word set(final int index) {
		synchronized(mutex) {
			return super.set(index);
		}
	}

	@Override
	public Word set(final int startIndex, final int endIndex) {
		synchronized(mutex) {
			return super.set(startIndex, endIndex);
		}
	}

	@Override
	public Word set() {
		synchronized(mutex) {
			return super.set();
		}
	}

	@Override
	public Word clear(final int index) {
		synchronized(mutex) {
			return super.clear(index);
		}
	}

	@Override
	public Word clear(final int startIndex, final int endIndex) {
		synchronized(mutex) {
			return super.clear(startIndex, endIndex);
		}
	}

	@Override
	public Word clear() {
		synchronized(mutex) {
			return super.clear();
		}
	}

	@Override
	public Word flip(final int index) {
		synchronized(mutex) {
			return super.flip(index);
		}
	}

	@Override
	public Word flip(final int startIndex, final int endIndex) {
		synchronized(mutex) {
			return super.flip(startIndex, endIndex);
		}
	}

	@Override
	public Word flip() {
		synchronized(mutex) {
			return super.flip();
		}
	}

	@Override
	public int findFirstOne() {
		synchronized(mutex) {
			return super.findFirstOne();
		}
	}

	@Override
	public int findLastOne() {
		synchronized(mutex) {
			return super.findLastOne();
		}
	}

	@Override
	public int findFirstZero() {
		synchronized(mutex) {
			return super.findFirstZero();
		}
	}

	@Override
	public int findLastZero() {
		synchronized(mutex) {
			return super.findLastZero();
		}
	}

	@Override
    public int countOnes() {
		synchronized(mutex) {
			return super.countOnes();
		}
	}

	@Override
    public int countZeros() {
		synchronized(mutex) {
			return super.countZeros();
		}
	}

	@Override
    public int countLeadingOnes() {
		synchronized(mutex) {
			return super.countLeadingOnes();
		}
	}

	@Override
    public int countLeadingZeros() {
		synchronized(mutex) {
			return super.countLeadingZeros();
		}
	}

	@Override
    public int countTrailingOnes() {
		synchronized(mutex) {
			return super.countTrailingOnes();
		}
	}

	@Override
    public int countTrailingZeros() {
		synchronized(mutex) {
			return super.countTrailingZeros();
		}
	}

	@Override
	public Word not() {
		synchronized(mutex) {
			return super.not();
		}
	}

	@Override
	public Word and(final Word that) {
		synchronized(mutex) {
			return super.and(that);
		}
	}

	@Override
	public Word xor(final Word that) {
		synchronized(mutex) {
			return super.xor(that);
		}
	}

	@Override
	public Word or(final Word that) {
		synchronized(mutex) {
			return super.or(that);
		}
	}

	@Override
	public Word shl() {
		synchronized(mutex) {
			return super.shl();
		}
	}

	@Override
	public Word shl(final int steps) {
		synchronized(mutex) {
			return super.shl(steps);
		}
	}

	@Override
	public Word shr() {
		synchronized(mutex) {
			return super.shr();
		}
	}

	@Override
	public Word shr(final int steps) {
		synchronized(mutex) {
			return super.shr(steps);
		}
	}

	@Override
	public Word rol() {
		synchronized(mutex) {
			return super.rol();
		}
	}

	@Override	
	public Word rol(final int steps) {
		synchronized(mutex) {
			return super.rol(steps);
		}
	}

	@Override
	public Word ror() {
		synchronized(mutex) {
			return super.ror();
		}
	}

	@Override
	public Word ror(final int steps) {
		synchronized(mutex) {
			return super.ror(steps);
		}
	}

	@Override
	public LongStream stream() {
		synchronized(mutex) {
			return super.stream();
		}
	}

}
