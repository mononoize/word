package de.mononoize.tools.word;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordTypeInteger extends AbstractTestWord {
	
	private static final int SIZE = Integer.SIZE;
	
	private static final int ZERO_VALUE = (int) 0;
	
	private static final int MIN_VALUE = Integer.MIN_VALUE;
	
	private static final int MAX_VALUE = Integer.MAX_VALUE;
	
	@Nested
	@Order(1)
	class TestOf extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testResultZero() {
			assertWord(SIZE, ZERO_VALUE, Word.of(ZERO_VALUE));
		}
			
		@Test
		@Order(2)
		public void testResultRandom() {
			for (int i = 0; i < ITERATIONS; i++) { 
				final int randomValue = (int) RANDOM.nextLong();
				assertWord(SIZE, randomValue, Word.of(randomValue));
			}
		}
		
	}
	
	@Nested
	@Order(2)
	class TestSetValue extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertWord(SIZE, ZERO_VALUE, new Word(SIZE).setValue(ZERO_VALUE));
			assertWord(SIZE, MIN_VALUE, new Word(SIZE).setValue(MIN_VALUE));
			assertWord(SIZE, MAX_VALUE, new Word(SIZE).setValue(MAX_VALUE));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {
			for (final int size : SIZES) {	
				assertWord(size, ZERO_VALUE, new Word(size).setValue(ZERO_VALUE));
			}
		}
			
		@Test
		@Order(3)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final int randomValue = (int) RANDOM.nextLong();
					assertWord(size, randomValue, new Word(size).setValue(randomValue));
				}
			}
		}
		
	}
	
}
