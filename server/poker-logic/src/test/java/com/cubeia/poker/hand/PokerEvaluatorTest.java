package com.cubeia.poker.hand;

import org.junit.Assert;
import org.junit.Test;


public class PokerEvaluatorTest {

	PokerEvaluator eval = new PokerEvaluator();
	
	@Test
	public void testCompareEmptyHands_1() throws Exception {
		Hand hand1 = new Hand("2C 2C 2C 2H AS").sort();
		Hand hand2 = new Hand("2C 2C 2C 2H AS").sort();
		
		int comp = eval.compareEmptyHands(hand1, hand2);
		Assert.assertTrue(comp == 0);
	}
	
	@Test
	public void testCompareEmptyHands_2() throws Exception {
		Hand hand1 = new Hand("2C 2C 2C 2H AS").sort();
		Hand hand2 = new Hand("KC KC KC KH KS").sort();
		int comp = eval.compareEmptyHands(hand1, hand2);
		Assert.assertTrue(comp > 0);
	}
	
	@Test
	public void testCompareEmptyHands_3() throws Exception {
		Hand hand1 = new Hand("3C QD 4H").sort();
		Hand hand2 = new Hand("KC 2C TD").sort();
		int comp = eval.compareEmptyHands(hand1, hand2);
		Assert.assertTrue(comp < 0);
	}
}
