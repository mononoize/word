package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
}
