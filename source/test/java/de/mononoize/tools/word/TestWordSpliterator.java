package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Spliterator;
import java.util.function.LongConsumer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestWordSpliterator extends AbstractTestWord {
	
	@Nested
	@Order(1)
	class TestCharacteristics extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testConfiguration() {
			final Spliterator.OfLong spliterator = new Word(1).spliterator();
			
			assertFalse(spliterator.hasCharacteristics(Spliterator.CONCURRENT));
			assertFalse(spliterator.hasCharacteristics(Spliterator.DISTINCT));
			assertFalse(spliterator.hasCharacteristics(Spliterator.IMMUTABLE));
			assertFalse(spliterator.hasCharacteristics(Spliterator.SORTED));
			
			assertTrue(spliterator.hasCharacteristics(Spliterator.NONNULL));
			assertTrue(spliterator.hasCharacteristics(Spliterator.ORDERED));
			assertTrue(spliterator.hasCharacteristics(Spliterator.SIZED));
			assertTrue(spliterator.hasCharacteristics(Spliterator.SUBSIZED));
		}
		
	}
		
	@Nested
	@Order(2)
	class TestTrySplit extends AbstractTestWord {	
		
		@Test
		@Order(1)
		public void testUneven() {
			final Spliterator.OfLong spliterator1 = new Word(7).spliterator();
			
			assertEquals(7, spliterator1.getExactSizeIfKnown());
			assertEquals(7, spliterator1.estimateSize());
			
			final Spliterator.OfLong spliterator2 = spliterator1.trySplit();
			
			assertEquals(5, spliterator1.getExactSizeIfKnown());
			assertEquals(5, spliterator1.estimateSize());			
			assertEquals(2, spliterator2.getExactSizeIfKnown());
			assertEquals(2, spliterator2.estimateSize());
			
			final Spliterator.OfLong spliterator3 = spliterator2.trySplit();
			
			assertEquals(5, spliterator1.getExactSizeIfKnown());
			assertEquals(5, spliterator1.estimateSize());			
			assertEquals(2, spliterator2.getExactSizeIfKnown());
			assertEquals(2, spliterator2.estimateSize());
			assertEquals(null, spliterator3);			
		}
		
		@Test
		@Order(2)
		public void testEven() {
			final Spliterator.OfLong spliterator1 = new Word(8).spliterator();
			
			assertEquals(8, spliterator1.getExactSizeIfKnown());
			assertEquals(8, spliterator1.estimateSize());
			
			final Spliterator.OfLong spliterator2 = spliterator1.trySplit();
			
			assertEquals(4, spliterator1.getExactSizeIfKnown());
			assertEquals(4, spliterator1.estimateSize());			
			assertEquals(4, spliterator2.getExactSizeIfKnown());
			assertEquals(4, spliterator2.estimateSize());
			
			final Spliterator.OfLong spliterator3 = spliterator2.trySplit();
			
			assertEquals(4, spliterator1.getExactSizeIfKnown());
			assertEquals(4, spliterator1.estimateSize());			
			assertEquals(2, spliterator2.getExactSizeIfKnown());
			assertEquals(2, spliterator2.estimateSize());
			assertEquals(2, spliterator3.getExactSizeIfKnown());
			assertEquals(2, spliterator3.estimateSize());
			
			final Spliterator.OfLong spliterator4 = spliterator3.trySplit();
			
			assertEquals(4, spliterator1.getExactSizeIfKnown());
			assertEquals(4, spliterator1.estimateSize());			
			assertEquals(2, spliterator2.getExactSizeIfKnown());
			assertEquals(2, spliterator2.estimateSize());
			assertEquals(2, spliterator3.getExactSizeIfKnown());
			assertEquals(2, spliterator3.estimateSize());			
			assertEquals(null, spliterator4);			
		}
		
		
	}
	
	@Nested
	@Order(3)
	class TestTryAdvance extends AbstractTestWord {
		
		@Test
		@Order(1)
		public void testValidation() {
			assertThrows(NullPointerException.class, () -> new Word(1).spliterator().tryAdvance((LongConsumer) null));
		}
		
		@Test
		@Order(2)
		public void testZero() {
			for (final int size : SIZES) {
				final StringBuffer actual = new StringBuffer();

				new Word(size).spliterator().forEachRemaining((LongConsumer) l -> actual.append(l));
				
				assertEquals(getStringZero(size), actual.reverse().toString());
			}
		}
		
		@Test
		@Order(3)
		public void testRandom() {
			for (final int size : SIZES) {
				for (int i = 0; i < ITERATIONS; i++) {
					final String value = getStringRandom(size);
					final StringBuffer actual = new StringBuffer();
					
					Word.of(value).spliterator().forEachRemaining((LongConsumer) l -> actual.append(l));
					
					assertEquals(value, actual.reverse().toString());
				}			
			}
		}
		
	}
		
}
