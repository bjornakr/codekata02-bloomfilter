package no.atferdssenteret.codekata02;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitSetTest {
    BitSet bitSet = new BitSet();

    @Before
    public void initializeBitSet() {
	bitSet = new BitSet();
    }

    @Test
    public void testSetIsEmptyWhenInitializing() {
	bitSet = new BitSet();
	assertEquals(0, bitSet.length());

	bitSet = new BitSet(100);
	assertEquals(0, bitSet.length());
    }

    @Test
    public void testSetBit() {
	assertFalse(bitSet.get(0));

	bitSet.set(0);
	assertTrue(bitSet.get(0));
    }

    @Test
    public void testGetBitFromVirginBitSet() {
	assertFalse(bitSet.get(2));
	assertFalse(bitSet.get(Integer.MAX_VALUE));
    }
}
