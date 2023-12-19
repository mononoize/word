package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordTypeWord extends AbstractTestWord {

	@Nested
	@Order(1)
	class TestOf extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> Word.of((Word) null));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {
			for (final int size : SIZES) {
				assertWord(size, ALL_BITS_0, Word.of(new Word(size)));
			}
		}
			
		@Test
		@Order(3)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) { 
					final long randomValue = RANDOM.nextLong();
					assertWord(size, randomValue, Word.of(new Word(size).setValue(randomValue)));
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
			assertThrows(NullPointerException.class, () -> new Word(1).setValue((Word) null));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue(new Word(2)));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(new Word(1)));
		}
		
		@Test
		@Order(2)
		public void testResultZero() {
			for (final int size : SIZES) {	
				assertWord(size, ALL_BITS_0, new Word(size).setValue(new Word(size).setValue(ALL_BITS_0)));
			}
		}
			
		@Test
		@Order(3)
		public void testResultRandom() {
			for (final int size : SIZES) {	
				for (int i = 0; i < ITERATIONS; i++) {
					final long randomValue = RANDOM.nextLong();
					assertWord(size, randomValue, new Word(size).setValue(new Word(size).setValue(randomValue)));
				}
			}
		}
		
	}
	
}
