package no.atferdssenteret.codekata02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class BloomFilterDictionaryTest {

    @Test
    public void testLearnWord() {
	String testWord = "Swashbuckler";
	BloomFilterDictionary dictionary = new BloomFilterDictionary();
	dictionary.learnWord(testWord);
	assertTrue(dictionary.lookUp(testWord));
    }

    @Test
    public void testLoadDictionary() {
	String[] testWords = { "swashbuckler", "honeysuckle", "bushwhacker" };

	byte[] testWordsForStream = createTestWordsForByteStream(testWords);
	InputStream testWordsStream = new ByteArrayInputStream(testWordsForStream);
	BloomFilterDictionary dictionary = new BloomFilterDictionary();
	try {
	    dictionary.loadDictionary(testWordsStream);
	    assertEquals(testWords.length, dictionary.size());

	    for (String testWord : testWords) {
		assertTrue(dictionary.lookUp(testWord));
	    }
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private byte[] createTestWordsForByteStream(String[] testWords) {
	String testWordsForStream = "";
	for (String testWord : testWords) {
	    testWordsForStream += testWord + "\n";
	}
	return testWordsForStream.getBytes();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializationShouldFailIfProvidedInvalidNoOfBits() {
	new BloomFilterDictionary(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializationShouldFailIfProvidedInvalidNoOfHashes() {
	new BloomFilterDictionary(1000, 0);
    }
}
