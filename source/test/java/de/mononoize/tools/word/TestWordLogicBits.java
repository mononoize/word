package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordLogicBits extends AbstractTestWord {

	@Nested
	@Order(1)
	class TestGetLong extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).get(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).get(1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);

					for (int j = 0; j < size; j++) {				
						assertEquals(getValue(value, size, j), word.get(j)); 
					}
				
				}
			}
		}
		
	}
	
	@Nested
	@Order(2)
	class TestGetBoolean extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).getBoolean(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).getBoolean(1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					for (int j = 0; j < size; j++) {						
						assertEquals(getBooleanValue(value, size, j), word.getBoolean(j)); 
					}
					
					assertWord(size, value, word);
				}
			}
		}
		
	}
	
	@Nested
	@Order(3)
	class TestSetAll extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_1, new Word(size).set());
				assertWord(size, ALL_BITS_1, new Word(size).clear().set());
			}
		}
		
	}
	
	@Nested
	@Order(4)
	class TestSetRegion extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(-1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(0, -1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(0, 1));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).set(1, 0));	
		}
		
		@Test
		@Order(2)
		public void testResult() {
			// TODO Implementation
		}
		
	}
	
	@Nested
	@Order(5)
	class TestSetIndex extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).set(1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (int i = 0; i < size; i++) { 
					final String value = getStringValue(size, i);
					assertWord(size, value, new Word(size).set(i));
				}
			}
		}
		
	}
		
	@Nested
	@Order(6)
	class TestClearAll extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_0, new Word(size).clear());
				assertWord(size, ALL_BITS_0, new Word(size).set().clear());
			}
		}
		
	}
	
	@Nested
	@Order(7)
	class TestClearRegion extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(-1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(0, -1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(0, 1));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).clear(1, 0));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			// TODO Implementation
		}
		
	}
	
	@Nested
	@Order(8)
	class TestClearIndex extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).clear(1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			// TODO Implementation
		}
		
	}
	
	@Nested
	@Order(9)
	class TestFlipAll extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_0, new Word(size).set().flip());
				assertWord(size, ALL_BITS_1, new Word(size).flip());
			}
		}
		
	}
	
	@Nested
	@Order(10)
	class TestFlipRegion extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(-1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(1, 0));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(0, -1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(0, 1));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).flip(1, 0));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			// TODO Implementation
		}
		
	}
	
	@Nested
	@Order(11)
	class TestFlipIndex extends AbstractTestWord {

		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(-1));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).flip(1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			// TODO Implementation
		}
		
	}
	
	private static long getValue(final String expected, final int size, final int index) {
		return expected.charAt(size - 1 - index) - '0';
	}
	
	private static boolean getBooleanValue(final String expected, final int size, final int index) {
		return (getValue(expected, size, index) == 1);
	}
	
}
