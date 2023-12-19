package de.mononoize.tools.word;

import java.io.Serial;

/**
 * <p>An immutable set of bits with a fixed size.</p>
 * 
 * <p>See: {@link Word}.</p>
 *
 * @author Alexander Mattes
  */
public final class ImmutableWord extends Word {

	@Serial
	private static final long serialVersionUID = -9212570806820029658L;

	/**
	 * Constructs a new {@code ImmutableWord} with the given number of bits. Initially, all bits are set to {@code 0}.
	 * 
	 * @param size The number of bits to be used.
	 */
	private ImmutableWord(int size) {
		super(size);
	}

	@Override
	public Word setValue(final Word that) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final String value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final byte value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final short value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final int value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final long value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word setValue(final char value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word set(final int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word set(final int startIndex, final int endIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Word set() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word clear(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Word clear(final int startIndex, final int endIndex) {
		throw new UnsupportedOperationException();
	}
		
	@Override
	public Word clear() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word flip(final int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word flip(final int startIndex, final int endIndex) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word flip() {
		throw new UnsupportedOperationException();
	}
	
	public Word not() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word and(final Word that) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word xor(final Word that) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word or(final Word that) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word shl() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word shl(final int steps) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word shr() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Word shr(final int steps) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word rol() {	
		throw new UnsupportedOperationException();
	}	
	
	@Override
	public Word rol(final int steps) {
		throw new UnsupportedOperationException();
	}
		
	@Override
	public Word ror() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Word ror(final int steps) {
		throw new UnsupportedOperationException();
	}
	
}
