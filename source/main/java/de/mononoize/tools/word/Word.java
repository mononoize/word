package de.mononoize.tools.word;

import java.io.Serial;
import java.io.Serializable;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

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
public class Word implements Serializable, Cloneable, Comparable<Word> {
	
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
	 * A mask to replace {@code x % this.m_size} with {@code x & this.m_sizeModMask}.
	 */
	protected final int m_sizeModMask;
	
	/**
	 * Constructs a new {@code Word} with the given number of bits. Initially, all bits are set to {@code 0}.
	 * 
	 * @param size The number of bits to be used.
	 */
	public Word(final int size) {
		Validate.isTrue(size > 0, "The size must be at least 1.");
		
		this.m_size = size;
		this.m_sizeModMask = this.m_size - 1;
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
	 * Returns a new {@code Word} using the given {@code String} value. The given string must consist of {@code '0'} and
	 * {@code '1'} characters. However, it is allowed to group the individual bits into groups using the {@code ' '} and
	 * {@code '_'} characters.
	 * 
	 * @param value The value to be used.
	 * @return A new {@code Word} using the given {@code String} value.
	 */
	public static Word of(final String value) {
		Validate.notNull(value, "The string must not be null.");
		Validate.notEmpty(value, "The string must not be empty.");
		Validate.isTrue(StringUtils.containsOnly(value, '0', '1', ' ', '_'), "The string contains invalid characters.");
		Validate.isTrue(StringUtils.containsAny(value,  '0', '1'), "The string must include at least one '0' or '1' character");
		
		String temp = value;
		temp = StringUtils.remove(temp, ' ');
		temp = StringUtils.remove(temp, '_');
		
		return new Word(temp.length()).setValue(temp);
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
	public int compareTo(final Word that) {
		Validate.notNull(that, "The word must not be null.");
	
		if (this.equals(that)) {
			return 0;
		} else {
			final long[] temp = new long[this.m_data.length];
			
			for (int i = 0; i < this.m_data.length; i++) {
				temp[i] = this.m_data[i] ^ that.m_data[i];
			}
		
			int result = 0;
			for (int i = (this.m_size - 1); i >= 0; i--) {
				if ((temp[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) {
					result = ((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) ? 1 : -1;
					break;
				}
			}
			return result;
		}
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
	 * Sets the value.  The given string must consist of {@code '0'} and {@code '1'} characters. However, it is allowed
	 * to group the individual bits into groups using the {@code ' '} and {@code '_'} characters.
	 * 
	 * @param value The value to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word setValue(final String value) {
		Validate.notNull(value, "The string must not be null.");
		Validate.notEmpty(value, "The string must not be empty.");
		Validate.isTrue(StringUtils.containsOnly(value, '0', '1', ' ', '_'), "The string contains invalid characters.");
		Validate.isTrue(StringUtils.containsAny(value,  '0', '1'), "The string must include at least one '0' or '1' character");
		
		String temp = value;
		temp = StringUtils.remove(temp, ' ');
		temp = StringUtils.remove(temp, '_');
	
		Validate.isTrue(this.m_size == temp.length(), "Different word sizes not allowed.");
		
		this.clear();
		
		for (int i = 0; i < this.m_size; i++) {
			if (temp.charAt((this.m_size - 1 - i)) == '1') {
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
			return this.getBoolean(index) ? ONE : ZERO;
	}

	/**
	 * Returns the highest bit.
	 * 
	 * @return The highest bit.
	 */
	public long get() {
		return this.getBoolean() ? ONE : ZERO;
	}
	
	/**
	 * Returns the bit at the given index as a {@code boolean}.
	 * 
	 * @param index The index of the bit to be returned.
	 * @return The bit at the given index as a {@code boolean}.
	 */
	public boolean getBoolean(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index, "Index out of bounds.");
		
		return ((this.m_data[index >>> ELEMENT_ADDRESS_BITS] & (ONE << index)) != 0);
	}
		
	/**
	 * Returns the highest bit as a {@code boolean}.
	 * 
	 * @return The highest bit as a {@code boolean}.
	 */
	public boolean getBoolean() {
		return this.getBoolean(this.m_size - 1);
	}
	
	/**
	 * Sets the given bit to {@code 1}.
	 * 
	 * @param index The index of the bit to be set.
	 * @return A reference to this {@code Word}.
	 */
	public Word set(final int index) {
		Validate.inclusiveBetween(0, (this.m_size - 1), index, "Index out of bounds.");
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] |= (ONE << index);

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
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] &= ~(ONE << index);

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
		
		this.m_data[index >>> ELEMENT_ADDRESS_BITS] ^= (ONE << index);
		
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
	 * Returns the index of the least significant bit set or {@code -1} if all bits are {@code 0}.
	 * 
	 * @return The index of the least significant bit set or {@code -1} if all bits are {@code 0}.
	 */
	public int findFirstOne() {
		for (int i = 0; i < this.m_size; i++) {
			if (((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the index of the most significant bit set or {@code -1} if all bits are {@code 0}.
	 * 
	 * @return The index of the most significant bit set or {@code -1} if all bits are {@code 0}.
	 */
	public int findLastOne() {
		for (int i = (this.m_size - 1); i >= 0; i--) {
			if (((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the index of the least significant bit not set or {@code -1} if all bits are {@code 1}.
	 * 
	 * @return The index of the least significant bit not set or {@code -1} if all bits are {@code 1}.
	 */
	public int findFirstZero() {
		for (int i = 0; i < this.m_size; i++) {
			if (((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) == 0)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the index of the most significant bit not set or {@code -1} if all bits are {@code 1}.
	 * 
	 * @return The index of the most significant bit not set or {@code -1} if all bits are {@code 1}.
	 */
	public int findLastZero() {
		for (int i = (this.m_size - 1); i >= 0; i--) {
			if (((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) == 0)) {
				return i;
			}
		}
		
		return -1;
	}
	
    /**
     * Returns the number of bits that are set to {@code 1}.
     *
     * @return The number of bits that are set to {@code 1}.
     */
    public int countOnes() {
    	int result = 0;
    	for (final long element : this.m_data) {
    		result += Long.bitCount(element);
    	}
        return result;
    }
    
    /**
     * Returns the number of bits that are set to {@code 0}.
     *
     * @return The number of bits that are set to {@code 0}.
     */
    public int countZeros() {
    	return (this.m_size - this.countOnes());
    }
	
    /**
     * Returns the number of leading bits that are set to {@code 1}.
     *
     * @return The number of leading bits that are set to {@code 1}.
     */
    public int countLeadingOnes() {
    	return (this.findLastZero() != -1) ? this.m_size - 1 - this.findLastZero() : this.m_size;
    }
    
    /**
     * Returns the number of leading bits that are set to {@code 0}.
     *
     * @return The number of leading bits that are set to {@code 0}.
     */
    public int countLeadingZeros() {
    	return (this.findLastOne() != -1) ? this.m_size - 1 - this.findLastOne() : this.m_size;
    }
    
    /**
     * Returns the number of trailing bits that are set to {@code 1}.
     *
     * @return The number of trailing bits that are set to {@code 1}.
     */
    public int countTrailingOnes() {
    	return (this.findFirstZero() != -1) ? this.findFirstZero() : this.m_size;
    }
    
    /**
     * Returns the number of trailing bits that are set to {@code 0}.
     *
     * @return The number of trailing bits that are set to {@code 0}.
     */
    public int countTrailingZeros() {
    	return (this.findFirstOne() != -1) ? this.findFirstOne() : this.m_size;
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
	 * Executes a logical conjunction operation on this {@code Word} and negates the result.
	 * 
	 * @param that The word to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word nand(final Word that) {
		Validate.notNull(that, "Null not allowed.");
		Validate.isTrue(this.m_size == that.m_size, "Different size Words not allowed.");
		
		if (this == that) {
			return this.not();
		}
		
		return this.and(that).not();
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
	 * Executes a logical inclusive disjunction operation on this {@code Word} and negates the result.
	 * 
	 * @param that The word to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word nor(final Word that) {
		Validate.notNull(that, "Null not allowed.");
		Validate.isTrue(this.m_size == that.m_size, "Different size Words not allowed.");
		
		if (this == that) {
			return this.not();
		}
			
		return this.or(that).not();
	}
	
	/**
	 * Executes a logical shift-left operation on this {@code Word} with an offset of one.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word shl() {
		return this.shl(1);
	}
	
	/**
	 * Executes a logical shift-left operation on this {@code Word}using the given offset.
	 * 
	 * @param offset The offset to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word shl(final int offset) {
		Validate.isTrue(offset >= 0, "A negative offset is not allowed.");

		// There is nothing to do when the offset is zero.
		if (offset == 0) {
			return this;		
		}
		
		// All bits can be set to zero when the offset is equal or greater than the size of this Word.
		if (offset >= this.m_size) {
			return this.clear();
		}
				
		final long[] target = new long[this.m_data.length];
		
		// The following loop iterates over all bits of this Word (i.e. the source array) starting at index 0 (i.e. the
	    // least significant bit). All zeros of the source array are skipped as the result array is already initialized
		// to zero. The control-variable i points to the current bit of the source array whereas the control-variable j
		// points to the current bit of the target array.
				
		final int sourceStart = 0;
		final int sourceEnd = (this.m_size - offset);
		final int targetStart = offset;
				
		for(int i = sourceStart, j = targetStart; i < sourceEnd ; i++, j++) {
			// Skip zeros.
			if ((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) {
				target[j >>> ELEMENT_ADDRESS_BITS] |= (ONE << j);
			}
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = target[i];
		}
		
		return this;
	}
	
	/**
	 * Executes a logical shift-right operation on this {@code Word} with an offset of one.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word shr() {
		return this.shr(1);
	}

	/**
	 * Executes a logical shift-right operation on this {@code Word} using the given offset.
	 * 
	 * @param offset The offset to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word shr(final int offset) {
		Validate.isTrue(offset >= 0, "A negative offset is not allowed.");
		
		// There is nothing to do when the offset is zero.
		if (offset == 0) {
			return this;
		}
		
		// All bits can be set to zero when the offset is equal or greater than the size of this Word.
		if (offset >= this.m_size) {
			return this.clear();
		}
		
		final long[] target = new long[this.m_data.length];
	
		// The following loop iterates over all bits of this Word (i.e. the source array) starting at index 0 (i.e. the
	    // least significant bit). All zeros of the source array are skipped as the result array is already initialized
		// to zero. The control-variable i points to the current bit of the source array whereas the control-variable j
		// points to the current bit of the target array.
		
		final int sourceStart = offset;
		final int sourceEnd = this.m_size;
		final int targetStart = 0;
		
		for(int i = sourceStart, j = targetStart; i < sourceEnd; i++, j++) {
			// Skip zeros.
			if ((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) {
				target[j >>> ELEMENT_ADDRESS_BITS] |= (ONE << j);
			}
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = target[i];
		}
		
		return this;
	}
	
	/**
	 * Executes a logical rotate-left operation on this {@code Word} with an offset of one.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word rol() {	
		return this.rol(1);
	}	
	
	/**
	 * Executes a logical rotate-left operation on this {@code Word} using the given offset.
	 * 
	 * @param offset The offset to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word rol(final int offset) {
		Validate.isTrue(offset >= 0, "A negative offset is not allowed.");
				
		// There is nothing to do when the offset is zero.
		if (offset == 0) {
			return this;		
		}

		// The following loop iterates over all bits of this Word (i.e. the source array) starting at index 0 (i.e. the
	    // least significant bit). All zeros of the source array are skipped as the result array is already initialized
		// to zero. The control-variable i points to the current bit of the source array whereas the control-variable j
		// points to the current bit of the target array.
		//
		// Multiple (unnecessary) rotations of the whole Word are prevented by initializing the control-variable j with
		// the remainder of the division with the Words size.
		
		final long[] target = new long[this.m_data.length];
		
		final int sourceStart = 0;
		final int sourceEnd = this.m_size;
		final int targetStart = (this.m_size + offset) & this.m_sizeModMask;
		
		for(int i = sourceStart, j = targetStart; i <sourceEnd; i++, j = (++j & this.m_sizeModMask)) {
			// Skip zeros.
			if ((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) {
				target[j >>> ELEMENT_ADDRESS_BITS] |= (ONE << j);
			}
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = target[i];
		}
		
		return this;
	}
		
	/**
	 * Executes a logical rotate-right operation on this {@code Word} with an offset of one.
	 * 
	 * @return A reference to this {@code Word}.
	 */
	public Word ror() {
		return this.ror(1);
	}
	
	/**
	 * Executes a logical rotate-right operation on this {@code Word} using the given offset.
	 * 
	 * @param offset The offset to be used.
	 * @return A reference to this {@code Word}.
	 */
	public Word ror(final int offset) {
		Validate.isTrue(offset >= 0, "A negative offset is not allowed.");
		
		// There is nothing to do when the offset is zero.
		if (offset == 0) {
			return this;		
		}

		// The following loop iterates over all bits of this Word (i.e. the source array) starting at index 0 (i.e. the
	    // least significant bit). All zeros of the source array are skipped as the result array is already initialized
		// to zero. The control-variable i points to the current bit of the source array whereas the control-variable j
		// points to the current bit of the target array.
		//
		// Multiple (unnecessary) rotations of the whole Word are prevented by initializing the control-variable j with
		// the remainder of the division with the Words size.
		
		final long[] target = new long[this.m_data.length];
		
		final int sourceStart = 0;
		final int sourceEnd = this.m_size;
		final int targetStart = (this.m_size - offset) & this.m_sizeModMask;
		
		for(int i = sourceStart, j = targetStart; i < sourceEnd; i++, j = (++j & this.m_sizeModMask)) {
			// Skip zeros.
			if ((this.m_data[i >>> ELEMENT_ADDRESS_BITS] & (ONE << i)) != 0) {
				target[j >>> ELEMENT_ADDRESS_BITS] |= (ONE << j);
			}
		}
		
		for (int i = 0; i < this.m_data.length; i++) {
			this.m_data[i] = target[i];
		}
		
		return this;
	}
	
	/**
	 * Returns a spliterator over the bits of this {@code Word}.
	 * 
	 * @return A spliterator over the bits of this {@code Word}.
	 */
	public Spliterator.OfLong spliterator() {
		
		/**
		 * A spliterator over the individual bits of this{@code Word}. 
		 */
		final class WordSpliterator implements Spliterator.OfLong {

			/**
			 *  The upper boundary.
			 */
			private final int m_fence;
			
			/**
			 * The current index.
			 */
			private int m_origin;
				
			/**
			 * Constructs a new {@code WordSpliterator}.
			 * 
			 * @param origin The origin index to be used.
			 * @param fence The fence index to be used.
			 */
			public WordSpliterator(final int origin, final int fence) {
				this.m_origin = origin;
				this.m_fence = fence;
			}

			@Override
			public int characteristics() {
				return Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
			}

			@Override
			public long estimateSize() {
				return (this.m_fence - this.m_origin);
			}
			
			@Override
			public Spliterator.OfLong trySplit() {
				int low = this.m_origin;
				int mid = ((low + this.m_fence) >>> 1) & ~1;

				if (low < mid) {
					this.m_origin = mid;
					return new WordSpliterator(low, mid);
				} else {
					return null;
				}
			}
			
			@Override
			public boolean tryAdvance(final LongConsumer action) {
				Validate.notNull(action, "The action must not be null.");
				
				if (this.m_origin < this.m_fence) {
					action.accept(Word.this.get(this.m_origin++));
					return true;
				} else {
					return false;
				}
			}
			
		}
		
		return new WordSpliterator(0, this.m_size);
	}
	
	/**
	 * Returns a sequential stream of bits of this {@code Word}. The bits are returned from the least significant bit to
	 * the most significant bit.
	 * 
	 * @return A sequential stream of bits of this {@code Word}.
	 */
	public LongStream stream() {
		return StreamSupport.longStream(this.spliterator(), false);
	}

}
