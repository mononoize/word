package de.mononoize.tools.word;

import java.io.Serial;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>A set of bits with a fixed size.</p>
 * 
 * <p>The {@code Word} class includes methods to manipulate (individual) bits with (bitwise) logical operations (i.e.
 * {@code NOT}, {@code AND}, {@code XOR}, and {@code OR}) as well as shift and rotate operations. Moreover, there are 
 * methods to convert a {@code Word} from/to other types (i.e. {@code long}, {@code int}, {@code short}, {@code byte},
 * and {@code char}).</p> 
 * 
 * <p><b>Note:</b> The implementation of The {@code Word} class is not thread-safe. Any (modifying) usage with multiple
 * threads must be synchronized externally.</p>
 *  
 * @author Alexander Mattes
 */
public class Word implements Serializable, Cloneable {
	
	@Serial
	private static final long serialVersionUID = 190554597376876918L;

	/**
	 * The size of one element of the (internal) data storage array. 
	 */
	protected static final int ELEMENT_SIZE = Long.SIZE;
	
	/**
	 * The number of address bits of one element of the (internal) data storage array.
	 */
	protected static final int ELEMENT_ADDRESS_BITS = 6;
	
	/**
	 * The bit mask to calculate {@code (n % ELEMENT_SIZE)} which is equal to {@code (n & (ELEMENT_SIZE - 1))}.
	 */
	protected static final int ELEMENT_ADDRESS_MOD_MASK = ELEMENT_SIZE - 1;
	
	/**
	 * The (internal) data storage element value that has all bits set to {@code 0}.
	 */
	protected static final long ZERO = 0L;
	
	/**
	 * The (internal) data storage element value that has only the least significant bit set to {@code 1}.
	 */
	protected static final long ONE = 1L;
		
	/**
	 * The (internal) data storage element value that has all bits set to {@code 1}.
	 */
	protected static final long MAX = -1L;
	
	/**
	 * The (internal) data storage array.
	 */
	protected final long m_data[];

	/**
	 * The number of bits of this {@code Word}.
	 */
	protected final int m_size;
	
	/**
	 * Constructs a new {@code Word} with the given number of bits. Initially, all bits are set to {@code 0}.
	 * 
	 * @param size The number of bits to be used.
	 */
	public Word(final int size) {
		Validate.isTrue(size > 0, "The size must be at least 1.");
		
		this.m_size = size;
		this.m_data = new long[((this.m_size - 1) >>> ELEMENT_ADDRESS_BITS) + 1];
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code Word} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code Word} value.
	 */
	public static Word of(final Word value) {
		Validate.notNull(value, "The value must not be null.");
		
		return new Word(value.getSize()).setValue(value);
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code String} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code String} value.
	 */
	public static Word of(final String value) {
		Validate.notNull(value, "The string must not be null.");
		Validate.notEmpty(value, "The string must not be empty.");
		Validate.isTrue(StringUtils.containsOnly(value, '0', '1'), "The value contains invalid characters.");
		
		return new Word(value.length()).setValue(value);
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code byte} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code byte} value.
	 */
	public static Word of(final byte value) {
		return new Word(Byte.SIZE).setValue(value);
	}
		
	/**
	 * Returns a new {@code Word} using the given {@code short} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code short} value.
	 */
	public static Word of(final short value) {
		return new Word(Short.SIZE).setValue(value);
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code integer} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code integer} value.
	 */
	public static Word of(final int value) {
		return new Word(Integer.SIZE).setValue(value);
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code long} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code long} value.
	 */
	public static Word of(final long value) {
		return new Word(Long.SIZE).setValue(value);
	}
	
	/**
	 * Returns a new {@code Word} using the given {@code char} value.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code char} value.
	 */
	public static Word of(final char value) {
		return new Word(Character.SIZE).setValue(value);
	}
		
	@Override
	public int hashCode() {
		return new HashCodeBuilder() //
				.append(this.m_size) //
				.append(this.m_data) //
				.build();
	}
	
	@Override
    public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}
		
		if (this == object) {
			return true;
		}
		
		if (this.getClass() != object.getClass()) {
			return false;
		}
		
		final Word that = (Word) object;
	
		return new EqualsBuilder() //
				.append(this.m_size, that.m_size) //
				.append(this.m_data, that.m_data) //
				.isEquals();		
    }
	
	@Override
	public Object clone() {
		return Word.of(this);
	}
	
	@Override
	public String toString() {
		return toString(this.m_size, this.m_data);	
	}
	
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 8} bits. 
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final byte value) {
		return toString(Byte.SIZE, value);
	}
	
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 16} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final short value) {
		return toString(Short.SIZE, value);
	}
		
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 32} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final int value) {
		return toString(Integer.SIZE, value);
	}
		
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 64} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final long value) {
		return toString(Long.SIZE, value);
	}
	
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code 16} bits.
	 * 
	 * @param value The value to be used.
	 * @return A string representation of the given value.
	 */
	public static String toString(final char value) {
		return toString(Character.SIZE, value);
	}
	
	/**
	 * Returns a string representation of the given value in base 2. The string is filled with extra leading {@code 0}s
	 * to a total size of {@code size} bits.
	 * 
	 * @param size The number of bits of the value.
	 * @param values The values to be used.
	 * @return A string representation of the given value.
	 */
	private static String toString(final int size, final long ... values) {
		final StringBuilder result = new StringBuilder();
		
		for (int i = (values.length - 1); i >= 0; i--) {
			result.append(StringUtils.leftPad(Long.toBinaryString(values[i]), ELEMENT_SIZE, '0'));
		}
		
		return StringUtils.right(result.toString(), size);
	}
	
	/**
	 * Returns the lowest {@code 8} bits as a {@code byte}.
	 * 
	 * @return The lowest {@code 8} bits as a {@code byte}.
	 */
	public byte toByte() {
		return (byte) this.m_data[0];	
	}
	
	/**
	 * Returns the lowest {@code 16} bits as a {@code short}.
	 * 
	 * @return The lowest {@code 16} bits as a {@code short}.
	 */
	public short toShort() {
		return (short) this.m_data[0];	
	}
	
	/**
	 * Returns the lowest {@code 32} bits as an {@code integer}.
	 * 
	 * @return The lowest {@code 32} bits as an {@code integer}.
	 */
	public int toInteger() {
		return (int) this.m_data[0];	
	}
	
	/**
	 * Returns the lowest {@code 64} bits as a {@code long}.
	 * 
	 * @return The lowest {@code 64} bits as a {@code long}.
	 */
	public long toLong() {
		return this.m_data[0];	
	}
	
	/**
	 * Returns the lowest {@code 16} bits as a {@code char}.
	 * 
	 * @return The lowest {@code 16} bits as a {@code char}.
	 */
	public char toChar() {
		return (char) this.m_data[0];	
	}
	
	/**
	 * Returns the number of bits of this {@code Word}.
	 * 
	 * @return The number of bits of this {@code Word}.
	 */
	public int getSize() {
		return this.m_size;
	}

	/**
	 * Sets the value.
	 * 
	 * @param that The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final Word that) {
		Validate.notNull(that, "The word must not be null.");
		Validate.isTrue(this.m_size == that.m_size, "Different word sizes not allowed.");
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = that.m_data[i];
		}
		
		return this;
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final String value) {
		Validate.notNull(value, "The string must not be null.");
		Validate.notEmpty(value, "The string must not be empty.");
		Validate.isTrue(StringUtils.containsOnly(value, '0', '1'), "The value contains invalid characters.");
		Validate.isTrue(this.m_size == value.length(), "Different word sizes not allowed.");
		
		this.clear();
		
		for (int i = 0; i < this.m_size; i++) {
			if (value.charAt((this.m_size - 1 - i)) == '1') {
				this.set(i);	
			}
		}
		
		return this;		
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final byte value) {
		return this.setValue(Byte.SIZE, value);
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final short value) {
		return this.setValue(Short.SIZE, value);
	}
		
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final int value) {
		return this.setValue(Integer.SIZE, value);
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final long value) {
		return this.setValue(Long.SIZE, value);
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final char value) {
		return this.setValue(Character.SIZE, value);
	}

	/**
	 * Sets the value.
	 * 
	 * @param size The number of bits of the value.
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	private Word setValue(final int size, final long value) {
		this.clear();

		for (int i = 0; i < Math.min(this.m_size, size); i++) {
			this.m_data[i >>> ELEMENT_ADDRESS_BITS] |= (value & (ONE << i));
		}
		
		return this;
	}
	
	/**
	 * Returns the bit at the given index.
	 * 
	 * @param index The index of the bit to be returned.
	 * @return The bit at the given index.
	 */
	public long get(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index, "Index out of bounds.");

		return ((this.m_data[index >>> ELEMENT_ADDRESS_BITS] & (ONE << (index & ELEMENT_ADDRESS_MOD_MASK))) == 0) ? ZERO : ONE;
	}

	/**
	 * Returns the highest bit.
	 * 
	 * @return The highest bit.
	 */
	public long get() {
		return this.get(this.m_size - 1);
	}
	
	/**
	 * Returns the bit at the given index as a {@code boolean}.
	 * 
	 * @param index The index of the bit to be returned.
	 * @return The bit at the given index as a {@code boolean}.
	 */
	public boolean getBoolean(final int index) {
		return (this.get(index) == 1);
	}
		
	/**
	 * Returns the highest bit as a {@code boolean}.
	 * 
	 * @return The highest bit as a {@code boolean}.
	 */
	public boolean getBoolean() {
		return (this.get() == 1);
	}
	
	/**
	 * Sets the given bit to {@code 1}.
	 * 
	 * @param index The index of the bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word set(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index, "Index out of bounds.");
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] |= (ONE << (index & ELEMENT_ADDRESS_MOD_MASK));

		return this;
	}
	
	/**
	 * Sets the given bits to {@code 1}. This method sets all bits from {@code startIndex} to {@code endIndex}
	 * (inclusive) to {@code 1}. 
	 *  
	 * @param startIndex The index of the first bit to be set.
	 * @param endIndex The index of the last bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word set(final int startIndex, final int endIndex) {
		Validate.inclusiveBetween(0, (this.m_size - 1), startIndex, "Start index out of bounds.");
		Validate.inclusiveBetween(0, (this.m_size - 1), endIndex, "End index out of bounds.");
		Validate.isTrue(startIndex <= endIndex, "Start index must be before end index.");
		
		final int startWordIndex = startIndex >>> ELEMENT_ADDRESS_BITS;
		final int endWordIndex = endIndex >>> ELEMENT_ADDRESS_BITS;
		
		final long startWordMask = MAX << startIndex;
		final long endWordMask = MAX >>> (-endIndex - 1);
						
		if (startWordIndex == endWordIndex) {
			this.m_data[startWordIndex] |= (startWordMask & endWordMask);
		} else {
			this.m_data[startWordIndex] |= startWordMask;
			for(int i = (startWordIndex + 1); i < endWordIndex; i++) {
				this.m_data[i] = MAX;
			}
			this.m_data[endWordIndex] |= endWordMask;
		}
		
		return this;
	}

	/**
	 * Sets all bits to {@code 1}.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word set() {
		return this.set(0, (this.m_size - 1));
	}
	
	/**
	 * Sets the given bit to {@code 0}.
	 * 
	 * @param index The index of the bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word clear(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index , "Index out of bounds.");
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] &= ~(ONE << (index & ELEMENT_ADDRESS_MOD_MASK));

		return this;
	}

	/**
	 * Sets the given bits to {@code 0}. This method sets all bits from {@code startIndex} to {@code endIndex}
	 * (inclusive) to {@code 0}. 
	 *  
	 * @param startIndex The index of the first bit to be set.
	 * @param endIndex The index of the last bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word clear(final int startIndex, final int endIndex) {
		Validate.inclusiveBetween(0, (this.m_size - 1), startIndex, "Start index out of bounds.");
		Validate.inclusiveBetween(0, (this.m_size - 1), endIndex, "End index out of bounds.");
		Validate.isTrue(startIndex <= endIndex, "Start index must be before than end index.");
		
		final int startWordIndex = startIndex >>> ELEMENT_ADDRESS_BITS;
		final int endWordIndex = endIndex >>> ELEMENT_ADDRESS_BITS;
		
		final long startWordMask = MAX << startIndex;
		final long endWordMask = MAX >>> (-endIndex - 1);
						
		if (startWordIndex == endWordIndex) {
			this.m_data[startWordIndex] &= (startWordMask & endWordMask);
		} else {
			this.m_data[startWordIndex] &= startWordMask;
			for(int i = (startWordIndex + 1); i < endWordIndex; i++) {
				this.m_data[i] = ZERO;
			}
			this.m_data[endWordIndex] &= endWordMask;
		}
	
		return this;
	}
		
	/**
	 * Sets all bits to {@code 0}.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word clear() {
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = ZERO;
		}
		
		return this;
	}
	
	/**
	 * Flips the given bit. This method inverts the bit:
	 * <ul>
	 * <li>{@code 0} is changed to {@code 1}</li>
	 * <li>{@code 1} is changed to {@code 0}</li>  
	 * </ul>
	 * 
	 * @param index The index of the bit to be flipped.
	 * @return A reference to this {@code Word}.
	 */
	public Word flip(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index, "Index out of bounds.");
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] ^= (ONE << (index & ELEMENT_ADDRESS_MOD_MASK));
		
		return this;
	}
	
	/**
	 * Flips the given bits. This method inverts all bits from {@code startIndex} to {@code endIndex} (inclusive):
	 * <ul>
	 * <li>{@code 0} is changed to {@code 1}</li>
	 * <li>{@code 1} is changed to {@code 0}</li>  
	 * </ul>
	 *  
	 * @param startIndex The index of the first bit to be set.
	 * @param endIndex The index of the last bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word flip(final int startIndex, final int endIndex) {
		Validate.inclusiveBetween(0, (this.m_size - 1), startIndex, "Start index out of bounds.");
		Validate.inclusiveBetween(0, (this.m_size - 1), endIndex, "End index out of bounds.");
		Validate.isTrue(startIndex <= endIndex, "Start index must be before than end index.");
				
		final int startWordIndex = startIndex >>> ELEMENT_ADDRESS_BITS;
		final int endWordIndex = endIndex >>> ELEMENT_ADDRESS_BITS;
		
		final long startWordMask = MAX << startIndex;
		final long endWordMask = MAX >>> (-endIndex - 1);
						
		if (startWordIndex == endWordIndex) {
			this.m_data[startWordIndex] ^= (startWordMask & endWordMask);
		} else {
			this.m_data[startWordIndex] ^= startWordMask;
			for(int i = (startWordIndex + 1); i < endWordIndex; i++) {
				this.m_data[i] ^= MAX;
			}
			this.m_data[endWordIndex] ^= endWordMask;
		}
		
		return this;
	}
	
	/**
	 * Flips all bits. This method inverts all bits:
	 * <ul>
	 * <li>{@code 0} is changed to {@code 1}</li>
	 * <li>{@code 1} is changed to {@code 0}</li>  
	 * </ul>
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word flip() {
		return this.flip(0, (this.m_size - 1));
	}
	
	/**
	 * Executes a logical negation operation on this {@code Word}.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word not() {
		return this.flip();
	}
	
	/**
	 * Executes a logical conjunction operation on this {@code Word}.
	 * 
	 * @param that The word to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word and(final Word that) {
		Validate.notNull(that, "Null not allowed.");
		Validate.isTrue(this.m_size == that.m_size, "Different size Words not allowed.");
		
		if (this == that) {
			return this;
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] &= that.m_data[i];
		}

		return this;
	}
	
	/**
	 * Executes a logical exclusive disjunction operation on this {@code Word}.
	 * 
	 * @param that The word to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word xor(final Word that) {
		Validate.notNull(that, "Null not allowed.");
		Validate.isTrue(this.m_size == that.m_size, "Different size Words not allowed.");
		
		if (this == that) {
			return this.clear();
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] ^= that.m_data[i];
		}

		return this;
	}
	
	/**
	 * Executes a logical inclusive disjunction operation on this {@code Word}.
	 * 
	 * @param that The word to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word or(final Word that) {
		Validate.notNull(that, "Null not allowed.");
		Validate.isTrue(this.m_size == that.m_size, "Different size Words not allowed.");
		
		if (this == that) {
			return this;
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] |= that.m_data[i];
		}

		return this;
	}
	
	/**
	 * Executes a logical shift-left operation on this {@code Word} by one step.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word shl() {
		for (int i = (this.m_data.length - 1); i > 0; i--) {
			this.m_data[i] <<= 1;
			this.m_data[i] |= (this.m_data[i - 1] >>> (ELEMENT_SIZE - 1));
		}
		this.m_data[0] <<= 1;
		
		return this;
	}
	
	/**
	 * Executes a logical shift-left operation on this {@code Word} by the given steps.
	 * 
	 * @param steps The shift-left steps to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word shl(final int steps) {
		Validate.isTrue(steps >= 0, "Negative steps not allowed.");
		
		for (int i = 0; i < steps; i++) {
			this.shl();
		}
		
		return this;
	}
	
	/**
	 * Executes a logical shift-right operation on this {@code Word} by one step.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word shr() {
		for (int i = 0; i < (this.m_data.length - 1); i++) {
			this.m_data[i] >>>= 1;
			this.m_data[i] |= (this.m_data[i + 1] << (ELEMENT_SIZE - 1));
		}
		this.m_data[this.m_data.length - 1] >>>= 1;
		
		return this;
	}

	/**
	 * Executes a logical shift-right operation on this {@code Word} by the given steps.
	 * 
	 * @param steps The shift-right steps to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word shr(final int steps) {
		Validate.isTrue(steps >= 0, "Negative steps not allowed.");
		
		for (int i = 0; i < steps; i++) {
			this.shr();
		}
		
		return this;
	}
	
	/**
	 * Executes a logical rotate-left operation on this {@code Word} by one step.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word rol() {	
		final long carry = this.get(this.m_size - 1);
		this.shl();
		if (carry > 0) {
			this.set(0);
		} else {
			this.clear(0);
		}

		return this;
	}	
	
	/**
	 * Executes a logical rotate-left operation on this {@code Word} by the given steps.
	 * 
	 * @param steps The rotate-left steps to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word rol(final int steps) {
		Validate.isTrue(steps >= 0, "Negative steps not allowed.");
		
		for (int i = 0; i < steps; i++) {
			this.rol();
		}
		
		return this;
	}
		
	/**
	 * Executes a logical rotate-right operation on this {@code Word} by one step.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word ror() {
		final long carry = this.get(0);
		this.shr();
		if (carry > 0) {
			this.set(this.m_size - 1);
		} else {
			this.clear(this.m_size- 1);
		}
		
		return this;
	}
	
	/**
	 * Executes a logical rotate-right operation on this {@code Word} by the given steps.
	 * 
	 * @param steps The rotate-right steps to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word ror(final int steps) {
		Validate.isTrue(steps >= 0, "Negative steps not allowed.");
		
		for (int i = 0; i < steps; i++) {
			this.ror();
		}
		
		return this;
	}
	
}
