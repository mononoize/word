package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.StringUtils;
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
	
	@Nested
	@Order(12)
	class TestFindFirstOne extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(-1, word.findFirstOne());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(findFirstOne(size, value), word.findFirstOne());
				}
			}
		}
		
		private static int findFirstOne(final int size, final String value) {
			final int index = StringUtils.lastIndexOf(value, '1');
			if (index == -1) {
				return -1;
			} else {
				return size - 1 - index;
			}
		}
		
	}
	
	@Nested
	@Order(13)
	class TestFindLastOne extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(-1, word.findLastOne());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(findLastOne(size, value), word.findLastOne());
				}
			}
		}
		
		private static int findLastOne(final int size, final String value) {	
			final int index = StringUtils.indexOf(value, '1');
			if (index == -1) {
				return -1;
			} else {
				return size - 1 - index;
			}
		}
		
	}
	

	@Nested
	@Order(14)
	class TestFindFirstZero extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(0, word.findFirstZero());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(findFirstZero(size, value), word.findFirstZero());
				}
			}
		}
		
		private static int findFirstZero(final int size, final String value) {
			final int index = StringUtils.lastIndexOf(value, '0');
			if (index == -1) {
				return -1;
			} else {
				return size - 1 - index;
			}
		}
		
	}
	
	@Nested
	@Order(15)
	class TestFindLastZero extends AbstractTestWord {

		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals((size - 1), word.findLastZero());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(findLastZero(size, value), word.findLastZero());
				}
			}
		}
		
		private static int findLastZero(final int size, final String value) {	
			final int index = StringUtils.indexOf(value, '0');
			if (index == -1) {
				return -1;
			} else {
				return size - 1 - index;
			}
		}
		
	}
		
	@Nested
	@Order(16)
	class TestCountOnes extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(0, word.countOnes());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(StringUtils.countMatches(value, '1'), word.countOnes());
				}
			}
		}

	}
	
	@Nested
	@Order(17)
	class TestCountZeros extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(size, word.countZeros());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(StringUtils.countMatches(value, '0'), word.countZeros());
				}
			}
		}

	}
	
	@Nested
	@Order(18)
	class TestCountLeadingOnes extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(0, word.countLeadingOnes());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(StringUtils.indexOf(value, '0'), word.countLeadingOnes());
				}
			}
		}

	}
	
	@Nested
	@Order(19)
	class TestCountLeadingZeros extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(size, word.countLeadingZeros());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(StringUtils.indexOf(value, '1'), word.countLeadingZeros());
				}
			}
		}

	}
	
	@Nested
	@Order(20)
	class TestCountTrailingOnes extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(0, word.countTrailingOnes());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(size - 1 - StringUtils.lastIndexOf(value, '0'), word.countTrailingOnes());
				}
			}
		}

	}
	
	@Nested
	@Order(21)
	class TestCountTrailingZeros extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testResultZero() {
			for (final int size : SIZES) {
				final Word word = new Word(size);
				
				assertEquals(size, word.countTrailingZeros());
			}
		}

		@Test
		@Order(2)
		public void testResultRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final Word word = Word.of(value);
					
					assertEquals(size - 1 - StringUtils.lastIndexOf(value, '1'), word.countTrailingZeros());
				}
			}
		}

	}
	
	private static long getValue(final String expected, final int size, final int index) {
		return expected.charAt(size - 1 - index) - '0';
	}
	
	private static boolean getBooleanValue(final String expected, final int size, final int index) {
		return (getValue(expected, size, index) == 1);
	}
	
}
