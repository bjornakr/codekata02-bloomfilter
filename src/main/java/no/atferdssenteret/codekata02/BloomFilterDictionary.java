package no.atferdssenteret.codekata02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.lang.ArrayUtils;

public class BloomFilterDictionary {
    private int noOfBits;
    private int noOfHashes;
    private BitSet bitSet;
    private int noOfWords = 0;

    public BloomFilterDictionary() {
	initialize(64000 * 8, 3);
    }

    public BloomFilterDictionary(int noOfBits, int noOfHashes) {
	if (noOfBits <= 0) {
	    throw new IllegalArgumentException("Number of bits must be greater than zero.");
	}
	if (noOfHashes <= 0) {
	    throw new IllegalArgumentException("Number of hashes must be greater than zero.");
	}

	initialize(noOfBits, noOfHashes);
    }

    private void initialize(int noOfBits, int noOfHashes) {
	this.noOfBits = noOfBits;
	this.noOfHashes = noOfHashes;
	bitSet = new BitSet(noOfBits);
    }

    public int size() {
	return noOfWords;
    }

    public void loadDictionary(InputStream dictionaryStream) throws IOException {
//	BufferedInputStream buf = new BufferedInputStream(dictionaryStream);
//	List<String> words = FileUtils.readLines(dictionaryFile);
	Reader r = new InputStreamReader(dictionaryStream); 
	BufferedReader buff = new BufferedReader(r);
	String word = null;

	while ((word = buff.readLine()) != null) {
	    learnWord(word);
	}
    }

    public void learnWord(String word) {
	for (int bitIndex : createBitIndexesFrom(word)) {
	    bitSet.set(bitIndex);
	}
	noOfWords++;
    }

    public boolean lookUp(String word) {
	for (int bitIndex : createBitIndexesFrom(word)) {
	    if (!bitSet.get(bitIndex)) {
		return false;
	    }
	}
	return true;
    }

    private int[] createBitIndexesFrom(String word) {
	int[] bitIndexes = new int[noOfHashes];
	byte[] digest = digest(word);

	while (digest.length < bytesRequiredForHashes()) {
	    byte[] reversedDigest = Arrays.copyOf(digest, digest.length);
	    ArrayUtils.reverse(reversedDigest);
	    digest = ArrayUtils.addAll(digest, reversedDigest);
	}

	for (int hashNo = 0; hashNo < noOfHashes; hashNo++) {
	    bitIndexes[hashNo] = computeSingleBitIndex(digest, hashNo);
	}
	return bitIndexes;
    }

    private int bytesRequiredForHashes() {
	return noOfHashes * 4;
    }

    private int computeSingleBitIndex(byte[] digest, int hashNo) {
	int offset = hashNo * 4;
	int digestPartAsInt = byteArrayToInt(digest, offset);
	return Math.abs(digestPartAsInt) % noOfBits;
    }

    private byte[] digest(String word) {
	try {
	    return MessageDigest.getInstance("MD5").digest(word.getBytes());
	}
	catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

    private int byteArrayToInt(byte[] b, int offset) {
	int value = 0;
	for (int i = 0; i < 4; i++) {
	    int shift = (4 - 1 - i) * 8;
	    value += (b[i + offset] & 0x000000FF) << shift;
	}
	return value;
    }

    public void printBitSetDistributionReport() {
	int interval = 20;
	int step = bitSet.size() / interval;
	int[] noOfBitsSet = new int[interval];

	for (int i = 0; i < bitSet.size(); i++) {
	    if (bitSet.get(i)) {
		noOfBitsSet[i / step]++;
	    }
	}

	for (int i = 0; i < noOfBitsSet.length; i++) {
	    System.out.println(i + ": " + noOfBitsSet[i] + " of " + step);
	}
    }
}
