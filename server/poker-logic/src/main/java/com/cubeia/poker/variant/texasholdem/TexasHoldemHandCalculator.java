package com.cubeia.poker.variant.texasholdem;

import com.cubeia.poker.hand.Hand;
import com.cubeia.poker.hand.HandInfo;
import com.cubeia.poker.hand.HandStrength;
import com.cubeia.poker.hand.HandType;
import com.cubeia.poker.hand.HandTypeEvaluator;
import com.cubeia.poker.hand.calculator.HandCalculator;
import com.cubeia.poker.hand.eval.HandTypeCheckCalculator;

/**
 * <p>Texas Holdem implementation of a Hand Calculator. This is probably
 * the common calculations for most poker games, but variations do exists.</p>
 * 
 * 
 * @author Fredrik Johansson, Cubeia Ltd
 */
public class TexasHoldemHandCalculator implements HandCalculator, HandTypeEvaluator {

	private HandTypeCheckCalculator typeCalculator = new HandTypeCheckCalculator();
	
	@Override
	public HandInfo getBestHandInfo(Hand hand) {
		return getHandStrength(hand);
	}
	
	/* ----------------------------------------------------
	 * 	
	 * 	PUBLIC METHODS
	 *  
	 *  ---------------------------------------------------- */
	
	/* (non-Javadoc)
	 * @see com.cubeia.poker.hand.calculator.HandCalculator#getHandStrength(com.cubeia.poker.hand.Hand)
	 */
	@Override
	public HandStrength getHandStrength(Hand hand) {
		HandStrength strength = null;
		
		// STRAIGHT_FLUSH
		if (strength == null) {
			strength = typeCalculator.checkStraightFlush(hand);
		}
		
		// FOUR_OF_A_KIND
		if (strength == null) {
			strength = typeCalculator.checkManyOfAKind(hand, 4);
		}
		
		// FULL_HOUSE
		if (strength == null) {
			strength = typeCalculator.checkFullHouse(hand);
		}
		
		// FLUSH
		if (strength == null) {
			strength = typeCalculator.checkFlush(hand);
		}
		
		// STRAIGHT
		if (strength == null) {
			strength = typeCalculator.checkStraight(hand);
		}

		// THREE_OF_A_KIND
		if (strength == null) {
			strength = typeCalculator.checkManyOfAKind(hand, 3);
		}
		
		// TWO_PAIRS
		if (strength == null) {
			strength = typeCalculator.checkTwoPairs(hand);
		}
		
		// ONE_PAIR
		if (strength == null) {
			strength = typeCalculator.checkManyOfAKind(hand, 2);
		}
		
		// HIGH_CARD
		if (strength == null) {
			strength = typeCalculator.checkHighCard(hand);
		}
		
		if (strength == null) {
			strength = new HandStrength(HandType.NOT_RANKED);
		}
		
		return strength;
	}
	
}
