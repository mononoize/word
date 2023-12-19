package de.mononoize.tools.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
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
			assertThrows(IllegalArgumentException.class, () -> Word.of("_"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("a"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("!"));
			assertThrows(IllegalArgumentException.class, () -> Word.of(" _"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("_ "));
			assertThrows(IllegalArgumentException.class, () -> Word.of("0a"));
			assertThrows(IllegalArgumentException.class, () -> Word.of("a0"));
		}

		@Test
		@Order(2)
		public void testStatic() {	
			final int size = 32;
			
			final List<Triple<String, String, Integer>> values = new ArrayList<>();
			values.add(Triple.of("00000000 00000000 00000000 00000000", "00000000000000000000000000000000", 0));
			values.add(Triple.of("11111111_11111111_11111111_11111111", "11111111111111111111111111111111", -1));
	        values.add(Triple.of("10010011 10111010 01110111 00001000", "10010011101110100111011100001000", -1816496376));
			values.add(Triple.of("11011001 01001011 10001011_01011110", "11011001010010111000101101011110", -649360546));
			values.add(Triple.of("00010000 11100100_10001010 10101101", "00010000111001001000101010101101", 283413165));
	        values.add(Triple.of("00010010 01010110_11100100_11110001", "00010010010101101110010011110001", 307684593));        
	        values.add(Triple.of("01011101_00100110 11100100 01001010", "01011101001001101110010001001010", 1562829898));
	        values.add(Triple.of("01101011_10100000 11110000_01111111", "01101011101000001111000001111111", 1805709439));
	        values.add(Triple.of("01111101_11100111_10011000 10100100", "01111101111001111001100010100100", 2112329892));
	        values.add(Triple.of("01111110_10101001_00011011_10001101", "01111110101010010001101110001101", 2125011853));
	        	        
	        for (final Triple<String, String, Integer> value : values) {
	        	final Word word = Word.of(value.getLeft());	       
	        	assertEquals(size, word.getSize());
	        	assertEquals(value.getMiddle(), word.toString());
	    		assertEquals(value.getRight(), word.toInteger());
	        }
		}
		
		@Test
		@Order(3)
		public void testDynamicZero() {
			for (final int size : SIZES) {	
				final String zeroValue = getStringZero(size);
				assertWord(size, zeroValue, Word.of(zeroValue));
			}
		}
			
		@Test
		@Order(4)
		public void testDynamicRandom() {
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
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue("_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue("a"));
			assertThrows(IllegalArgumentException.class, () -> new Word(1).setValue("!"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" _"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_ "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0a"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("a0"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("1"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 0"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 1"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_0"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_1"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0 "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("1 "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("0_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("1_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 0 "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 1 "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_0_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_1_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 0_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue(" 1_"));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_0 "));
			assertThrows(IllegalArgumentException.class, () -> new Word(2).setValue("_1 "));
		}

		@Test
		@Order(2)
		public void testStatic() {	
			final int size = 32;
			
			final List<Triple<String, String, Integer>> values = new ArrayList<>();
			values.add(Triple.of("00000000 00000000 00000000 00000000", "00000000000000000000000000000000", 0));
			values.add(Triple.of("11111111_11111111_11111111_11111111", "11111111111111111111111111111111", -1));
	        values.add(Triple.of("10010011 10111010 01110111 00001000", "10010011101110100111011100001000", -1816496376));
			values.add(Triple.of("11011001 01001011 10001011_01011110", "11011001010010111000101101011110", -649360546));
			values.add(Triple.of("00010000 11100100_10001010 10101101", "00010000111001001000101010101101", 283413165));
	        values.add(Triple.of("00010010 01010110_11100100_11110001", "00010010010101101110010011110001", 307684593));        
	        values.add(Triple.of("01011101_00100110 11100100 01001010", "01011101001001101110010001001010", 1562829898));
	        values.add(Triple.of("01101011_10100000 11110000_01111111", "01101011101000001111000001111111", 1805709439));
	        values.add(Triple.of("01111101_11100111_10011000 10100100", "01111101111001111001100010100100", 2112329892));
	        values.add(Triple.of("01111110_10101001_00011011_10001101", "01111110101010010001101110001101", 2125011853));
	        	        
	        for (final Triple<String, String, Integer> value : values) {
	        	final Word word = new Word(size).setValue(value.getLeft());       
	        	assertEquals(size, word.getSize());
	        	assertEquals(value.getMiddle(), word.toString());
	    		assertEquals(value.getRight(), word.toInteger());
	        }
		}
		
		@Test
		@Order(3)
		public void testDynamicZero() {
			for (final int size : SIZES) {	
				final String zeroValue = getStringZero(size);
				assertWord(size, zeroValue, new Word(size).setValue(zeroValue));
			}
		}
			
		@Test
		@Order(4)
		public void testDynamicRandom() {
			for (final int size : SIZES) {	
				for (int i = 0; i < ITERATIONS; i++) { 
					final String randomValue = getStringRandom(size);
					assertWord(size, randomValue,new Word(size).setValue(randomValue));
				}
			}
		}
		
	}
	
}
