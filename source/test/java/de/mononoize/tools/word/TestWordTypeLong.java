package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordTypeLong extends AbstractTestWord {
	
	private static final int SIZE = Long.SIZE;
	
	private static final long ZERO_VALUE = (long) 0;
	
	private static final long MIN_VALUE = Long.MIN_VALUE;
	
	private static final long MAX_VALUE = Long.MAX_VALUE;
	
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
				final long randomValue = (long) RANDOM.nextLong();
				
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
					final long randomValue = (long) RANDOM.nextLong();
					
					assertWord(size, randomValue, new Word(size).setValue(randomValue));
				}
			}
		}
		
	}
	
	@Nested
	@Order(3)
	class TestToLong extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				assertEquals(ZERO_VALUE, new Word(size).toLong());
			}
		}
			
		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final long randomValue = (long) RANDOM.nextLong();
					
					assertEquals(randomValue, new Word(size).setValue(randomValue).toLong());
				}
			}
		}
		
	}
	
	@Nested
	@Order(4)
	class TestToString extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {			
			assertEquals(getStringZero(SIZE), Word.toString(ZERO_VALUE));
		}
			
		@Test
		@Order(2)
		public void testResultHardcoded() {
			for (int i = 0; i < ITERATIONS; i++) {
				final String randomValue = getStringRandom(SIZE);
				
				assertEquals(randomValue, Word.toString(Word.of(randomValue).toLong()));
			}
		}
		
	}
	
}
