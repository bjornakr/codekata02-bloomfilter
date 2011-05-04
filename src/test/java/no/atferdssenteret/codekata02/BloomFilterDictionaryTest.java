package no.atferdssenteret.codekata02;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

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
	File dictionaryTestFile = new File("src/test/resources/wordlist-test.txt");
	BloomFilterDictionary dictionary = new BloomFilterDictionary();
	try {
	    dictionary.loadDictionary(dictionaryTestFile);
	    assertEquals(3, dictionary.size());

	    String[] testWords = { "swashbuckler", "honeysuckle", "bushwhacker" };
	    for (String testWord : testWords) {
		assertTrue(dictionary.lookUp(testWord));
	    }
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testInitializationShouldFailIfProvidedInvalidNoOfBits() {
	new BloomFilterDictionary(-1, 3);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testInitializationShouldFailIfProvidedInvalidNoOfHashes() {
	new BloomFilterDictionary(1000, 0);
    }
}
