package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordOperationsLogic extends AbstractTestWord {

	@Nested
	@Order(1)
	class TestNot extends AbstractTestWord {
		
		private static final String[][] TRUTH_TABLE_NOT = new String[][] {
			{VALUE00, VALUE11},
			{VALUE01, VALUE10},
			{VALUE10, VALUE01},
			{VALUE11, VALUE00}
		};
		
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String[] high : TRUTH_TABLE_NOT) {
					for (final String[] low : TRUTH_TABLE_NOT) {	
						final Word operator = Word.of(getStringPattern(size, high[0], low[0]));
						final Word expected = Word.of(getStringPattern(size, high[1], low[1]));
										
						assertEquals(expected, operator.not());
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(2)
	class TestAnd extends AbstractTestWord {
	
		private static final String[][] TRUTH_TABLE_AND = new String[][] {
			{VALUE00, VALUE00, VALUE00},
			{VALUE00, VALUE01, VALUE00},
			{VALUE00, VALUE10, VALUE00},
			{VALUE00, VALUE11, VALUE00},
			
			{VALUE01, VALUE00, VALUE00},	
			{VALUE01, VALUE01, VALUE01},
			{VALUE01, VALUE10, VALUE00},		
			{VALUE01, VALUE11, VALUE01},
			
			{VALUE10, VALUE00, VALUE00},
			{VALUE10, VALUE01, VALUE00},
			{VALUE10, VALUE10, VALUE10},
			{VALUE10, VALUE11, VALUE10},
			
			{VALUE11, VALUE00, VALUE00},
			{VALUE11, VALUE01, VALUE01},
			{VALUE11, VALUE10, VALUE10},
			{VALUE11, VALUE11, VALUE11}		
		};
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).and(null));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).and(new Word(2)));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).and(new Word(1)));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String[] high : TRUTH_TABLE_AND) {
					for (final String[] low : TRUTH_TABLE_AND) {
						final Word operator01 = Word.of(getStringPattern(size, high[0], low[0]));
						final Word operator02 = Word.of(getStringPattern(size, high[1], low[1]));
						final Word expected = Word.of(getStringPattern(size, high[2], low[2]));
										
						assertEquals(expected, operator01.and(operator02));
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(3)
	class TestXOR extends AbstractTestWord {
	
		private static final String[][] TRUTH_TABLE_XOR = new String[][] {
			{VALUE00, VALUE00, VALUE00},
			{VALUE00, VALUE01, VALUE01},
			{VALUE00, VALUE10, VALUE10},
			{VALUE00, VALUE11, VALUE11},
			
			{VALUE01, VALUE00, VALUE01},
			{VALUE01, VALUE01, VALUE00},
			{VALUE01, VALUE10, VALUE11},		
			{VALUE01, VALUE11, VALUE10},
			
			{VALUE10, VALUE00, VALUE10},
			{VALUE10, VALUE01, VALUE11},
			{VALUE10, VALUE10, VALUE00},
			{VALUE10, VALUE11, VALUE01},
			
			{VALUE11, VALUE00, VALUE11},
			{VALUE11, VALUE01, VALUE10},
			{VALUE11, VALUE10, VALUE01},
			{VALUE11, VALUE11, VALUE00}		
		};
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).xor(null));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).xor(new Word(2)));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).xor(new Word(1)));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String[] high : TRUTH_TABLE_XOR) {
					for (final String[] low : TRUTH_TABLE_XOR) {
						final Word operator01 = Word.of(getStringPattern(size, high[0], low[0]));
						final Word operator02 = Word.of(getStringPattern(size, high[1], low[1]));
						final Word expected = Word.of(getStringPattern(size, high[2], low[2]));
										
						assertEquals(expected, operator01.xor(operator02));
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(4)
	class TestOR extends AbstractTestWord {
	
		private static final String[][] TRUTH_TABLE_OR = new String[][] {
			{VALUE00, VALUE00, VALUE00},
			{VALUE00, VALUE01, VALUE01},
			{VALUE00, VALUE10, VALUE10},
			{VALUE00, VALUE11, VALUE11},
			
			{VALUE01, VALUE00, VALUE01},	
			{VALUE01, VALUE01, VALUE01},
			{VALUE01, VALUE10, VALUE11},		
			{VALUE01, VALUE11, VALUE11},
			
			{VALUE10, VALUE00, VALUE10},
			{VALUE10, VALUE01, VALUE11},
			{VALUE10, VALUE10, VALUE10},
			{VALUE10, VALUE11, VALUE11},
			
			{VALUE11, VALUE00, VALUE11},
			{VALUE11, VALUE01, VALUE11},
			{VALUE11, VALUE10, VALUE11},
			{VALUE11, VALUE11, VALUE11}		
		};
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).or(null));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).or(new Word(2)));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).or(new Word(1)));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String[] high : TRUTH_TABLE_OR) {
					for (final String[] low : TRUTH_TABLE_OR) {
						final Word operator01 = Word.of(getStringPattern(size, high[0], low[0]));
						final Word operator02 = Word.of(getStringPattern(size, high[1], low[1]));
						final Word expected = Word.of(getStringPattern(size, high[2], low[2]));
										
						assertEquals(expected, operator01.or(operator02));
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(5)
	class TestSHLByOne extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						final String expected = getStringPattern(size, high, low);
						
						for (int i = 1; i <= size; i++) {			
							assertWord(size, shl(expected, i), operator.shl());
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(6)
	class TestSHLByN extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).shl(-1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						String expected = getStringPattern(size, high, low);
						
						for (int i = 0, j = 1, x = 0; i <= size; x = i, i += j, j = x) {
							assertWord(size, (expected = shl(expected, i)), operator.shl(i));
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(7)
	class TestSHRByOne extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						final String expected = getStringPattern(size, high, low);
						
						for (int i = 1; i <= size; i++) {
							assertWord(size, shr(expected, i), operator.shr());
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(8)
	class TestSHRByN extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).shr(-1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						String expected = getStringPattern(size, high, low);
					
						for (int i = 0, j = 1, x = 0; i <= size; x = i, i += j, j = x) {
							assertWord(size, (expected = shr(expected, i)), operator.shr(i));
						}
					}
				}
			}
		}
		
	}
		
	@Nested
	@Order(9)
	class TestROLByOne extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						final String expected = getStringPattern(size, high, low);
						
						for (int i = 1; i <= size; i++) {
							assertWord(size, rol(expected, i), operator.rol());
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(10)
	class TestROLByN extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).rol(-1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						String expected = getStringPattern(size, high, low);
					
						for (int i = 0, j = 1, x = 0; i <= size; x = i, i += j, j = x) {
							assertWord(size, (expected = rol(expected, i)), operator.rol(i));
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(11)
	class TestRORByOne extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						final String expected = getStringPattern(size, high, low);
					
						for (int i = 1; i <= size; i++) {
							assertWord(size, ror(expected, i), operator.ror());
						}
					}
				}
			}
		}
		
	}
	
	@Nested
	@Order(12)
	class TestRORByN extends AbstractTestWord {
	
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(IllegalArgumentException.class, () -> new Word(1).ror(-1));
		}
		
		@Test
		@Order(2)
		public void testResult() {
			for (final int size : SIZES) {
				for (final String high : VALUES) {
					for (final String low : VALUES) {
						final Word operator = Word.of(getStringPattern(size, high, low));
						String expected = getStringPattern(size, high, low);
						
						for (int i = 0, j = 1, x = 0; i <= size; x = i, i += j, j = x) {
							assertWord(size, (expected = ror(expected, i)), operator.ror(i));
						}
					}
				}
			}
		}
	
	}

	private static String shl(final String value, final int count) {
		final String part1 = StringUtils.truncate(value, count, value.length());
		final String part2 = StringUtils.repeat("0", count);
		
		return StringUtils.truncate((part1 + part2), value.length());
	}
	
	private static String shr(final String value, final int count) {
		final String part1 = StringUtils.repeat("0", count);
		final String part2 = StringUtils.truncate(value, 0, Math.max(0, value.length() - count));
		
		return StringUtils.truncate((part1 + part2), value.length());
	}
	
	private static String rol(final String value, final int count) {
		final String part1 = StringUtils.truncate(value, count, value.length());
		final String part2 = StringUtils.truncate(value, 0, count);

		return StringUtils.truncate((part1 + part2), value.length());
	}
	
	private static String ror(final String value, final int count) {
		final String part1 = StringUtils.truncate(value, Math.max(0, value.length() - count), value.length());
		final String part2 = StringUtils.truncate(value, 0, Math.max(0, value.length() - count));
		
		return StringUtils.truncate((part1 + part2), value.length());
	}
	
}
