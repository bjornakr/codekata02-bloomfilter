package no.atferdssenteret.codekata02;

import java.io.File;
import java.io.IOException;

public class App {
    private static BloomFilterDictionary dictionary;
    
    public static void main(String[] args) {
	int dictionarySize = 32000 * 8;
	int noOfHashes = 3;
	
	dictionary = new BloomFilterDictionary(dictionarySize, noOfHashes);
	try {
	    dictionary.loadDictionary(new File("src/main/resources/wordlist.txt"));
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	System.out.println("Dictionary size: " + dictionary.size());
	printLookUpResult("tomato");
	printLookUpResult("tomata");
	printLookUpResult("autogroph");
	printLookUpResult("wobble");
	printLookUpResult("logpainter");
	printLookUpResult("swashbuckler");
	printLookUpResult("swashbackler");
	printLookUpResult("bushwhacker");
	printLookUpResult("stalegrid");
	printLookUpResult("crackblaster");
	printLookUpResult("fac");
	printLookUpResult("stashmugger");
	printLookUpResult("wafflez");
	
	dictionary.printBitSetDistributionReport();
    }

    private static void printLookUpResult(String string) {
	System.out.println("dictionary.lookUp(\"" + string + "\"): " + dictionary.lookUp(string));	
    }
}
