package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordConstruction extends AbstractTestWord {
	
	@Nested
	@Order(1)
	class TestConstructor extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(0));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_0, new Word(size));
			}
		}
	}

	@Nested
	@Order(2)
	class TestClone extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testContract() { 
			final Word original = new Word(1);
			final Object clone = original.clone();
			
			assertTrue(original != clone);
			assertTrue(original.getClass() == clone.getClass());
			assertTrue(original.equals(clone));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {	
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_0, (Word) new Word(size).clone());
			}
		}

		@Test
		@Order(3)
		public void testResultRandom() { 
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String expected = getStringRandom(size);
					
					assertWord(size, expected, (Word) new Word(size).setValue(expected).clone());
				}
			}
		}
	}
	
		
	@Nested
	@Order(3)
	class TestCompareTo extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).compareTo(null));
		}
		
		@Test
		@Order(2)
		public void testResultEqualZero() {
			for (final int size : SIZES) {
				final Word word1 = new Word(size);				
				final Word word2 = new Word(size);
				
				assertEquals(0, word1.compareTo(word2));
			}
		}
		
		@Test
		@Order(3)
		public void testResultEqualRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					
					final Word word1 = new Word(size).setValue(value);				
					final Word word2 = new Word(size).setValue(value);
					
					assertEquals(0, word1.compareTo(word2));
					
					assertWord(size, value, word1);
					assertWord(size, value, word2);
				}
			}
		}

		@Test
		@Order(4)
		public void testResultSmallerRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value1 = getStringRandom(size);
					final String value2 = getStringSmaller(value1); 
					
					final Word word1 = new Word(size).setValue(value1);				
					final Word word2 = new Word(size).setValue(value2);
					
					assertEquals(1, word1.compareTo(word2));
					assertEquals(-1, word2.compareTo(word1));
					
					assertWord(size, value1, word1);
					assertWord(size, value2, word2);
				}
			}
		}

		@Test
		@Order(5)
		public void testResultBiggerRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value1 = getStringRandom(size);
					final String value2 = getStringBigger(value1); 
					
					final Word word1 = new Word(size).setValue(value1);				
					final Word word2 = new Word(size).setValue(value2);
					
					assertEquals(-1, word1.compareTo(word2));
					assertEquals(1, word2.compareTo(word1));
	
					assertWord(size, value1, word1);
					assertWord(size, value2, word2);
				}
			}
		}
				
		private String getStringSmaller(final String value) {
			final StringBuilder result = new StringBuilder(value);
			
			result.setCharAt(StringUtils.lastIndexOf(value, '1'), '0');
		
			for (int i = 0; i < (RANDOM.nextInt(value.length())); i++) {
				final int index = RANDOM.nextInt(value.length());
				if (result.charAt(index) == '1') { 
					result.setCharAt(index, '0');
				}
			}
			
			return result.toString();
		}
				
		private String getStringBigger(final String value) {
			final StringBuilder result = new StringBuilder(value);
			
			result.setCharAt(StringUtils.lastIndexOf(value, '0'), '1');
		
			for (int i = 0; i < (RANDOM.nextInt(value.length())); i++) {
				final int index = RANDOM.nextInt(value.length());
				if (result.charAt(index) == '0') { 
					result.setCharAt(index, '1');
				}
			}
			
			return result.toString();
		}
		
	}
	
}
