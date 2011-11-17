/**
 * Copyright (C) 2010 Cubeia Ltd <info@cubeia.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cubeia.poker.player;

import java.io.Serializable;
import java.util.Set;

import com.cubeia.poker.action.ActionRequest;
import com.cubeia.poker.action.PossibleAction;
import com.cubeia.poker.hand.Card;
import com.cubeia.poker.hand.Hand;

public interface PokerPlayer extends Serializable {

	/**
	 * Returns the players pocket cards. Note that this might
	 * be a defensive copy and should NOT be modified.
	 * Use {@link #addPocketCard(Card, boolean)} to add a card to the player's hand.
	 * @return the player's hand, never null.
	 */
	public Hand getPocketCards();
	
	/**
	 * Get the players pocket cards that are public (visible to all).
	 * @return set of visible pocket cards, never null
	 */
	public Set<Card> getPublicPocketCards();
	
	/**
	 * Get the players pocket cards that are private (only visible to player).
	 * @return set of visible pocket cards, never null
	 */
	public Set<Card> getPrivatePocketCards();
	
	public void addPocketCard(Card card, boolean publicCard);
	
	public void clearHand();
	
	public boolean getSitOutNextRound();
	
	public void setSitOutNextRound(boolean b);
	
	/**
	 * Gets the player's id.
	 * @return
	 */
	public int getId();

	public int getSeatId();

	public long getBetStack();
	
	public void removeFromBetStack(long amount);

	/**
	 * Move amount from balance to betstack
	 * @param amount the amount to move
	 */
	public void addBet(long amount);

	public void clearActionRequest();

	public void setActionRequest(ActionRequest possibleActions);

	public ActionRequest getActionRequest();

	public void setHasActed(boolean b);

	public void setHasFolded(boolean b);

	public boolean hasFolded();

	public boolean hasActed();

	public void setHasOption(boolean b);
	
	public boolean hasOption();

	public void enableOption(PossibleAction option);

	public void setSitOutStatus(SitOutStatus status);

	public SitOutStatus getSitOutStatus();

	public boolean hasPostedEntryBet();

	public void setHasPostedEntryBet(boolean b);

	public boolean isSittingOut();

	public void clearBalance();
	
	public long getBalance();
    
    public void setStartingBalance(long startingBalance);
    
    public long getStartingBalance();
	

	/**
	 * Adds (or removes) chips to the player's chip stack.
	 * 
	 * @param chips chips to add (positive) or remove (negative)
	 */
	public void addChips(long chips);
	
	public boolean isAllIn();
	
	public void sitIn();

    /**
     * Returns the amount of currency that is not currently available 
     * but will be added to the {@link #getBalance()} in the future.
     * This will be nonzero if a player does a buy in during a hand.
     * @return the pending balance
     */
    public long getPendingBalance();
	
    /**
     * Add the given amount to the pending balance.
     * @param amount amount to add
     */
    public void addPendingAmount(long amount);
    
    /**
     * move the full amount in betstack to returnedChips
     */
    public void returnAllBets();
    
    /**
     * move the amount from betstack to returnedChips
     */
    public void returnBetAmount(long amount);
    
    /**
     * Adds the pending balance to the ordinary balance.
     * @param maxBuyIn, the total resulting balance should not be higher than this
     * @return returns true if there was a pending balance committed
     */
    public boolean commitPendingBalance(long maxBuyIn);

    public boolean isSitInAfterSuccessfulBuyIn();

    public void setSitInAfterSuccessfulBuyIn(boolean sitIn);
    
    /** 
     * Get the timestamp for when the player was set as sitting out.
     * Will be null if the player is currently not in a sit out state.
     * 
     * @return UTC milliseconds or null 
     */
    public Long getSitOutTimestamp();

	public boolean isExposingPocketCards();

	public void setExposingPocketCards(boolean b);

	/**
	 * Reset all hand/round specific flags to prepare player for a new hand.
	 */
    public void resetBeforeNewHand();
    
}