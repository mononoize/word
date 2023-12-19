package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordTypeString extends AbstractTestWord {
	

	@Nested
	@Order(1)
	class TestOf extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> Word.of((String) null));
			assertThrows(IllegalArgumentException.class, () -> Word.of(""));
			assertThrows(IllegalArgumentException.class, () -> Word.of(" "));
			assertThrows(IllegalArgumentException.class, () -> Word.of("a"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("!"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("0a"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("a0"));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {
			for (final int size : SIZES) {	
				final String zeroValue = getStringZero(size);
				assertWord(size, zeroValue, Word.of(zeroValue));
			}
		}
			
		@Test
		@Order(3)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) { 
					final String randomValue = getStringRandom(size);
					assertWord(size, randomValue, Word.of(randomValue));
				}
			}
		}
		
	}
	
	@Nested
	@Order(2)
	class TestSetValue extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).setValue((String) null));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue(""));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue(" "));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue("a"));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue("!"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0a"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("a0"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0"));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {
			for (final int size : SIZES) {	
				final String zeroValue = getStringZero(size);
				assertWord(size, zeroValue, new Word(size).setValue(zeroValue));
			}
		}
			
		@Test
		@Order(3)
		public void testResultRandom() {
			for (final int size : SIZES) {	
				for (int i = 0; i < ITERATIONS; i++) { 
					final String randomValue = getStringRandom(size);
					assertWord(size, randomValue,new Word(size).setValue(randomValue));
				}
			}
		}
		
	}
	
}
