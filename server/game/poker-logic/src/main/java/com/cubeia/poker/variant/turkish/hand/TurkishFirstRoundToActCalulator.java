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


package com.cubeia.poker.variant.turkish.hand;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

import com.cubeia.poker.hand.Card;
import com.cubeia.poker.hand.Hand;
import com.cubeia.poker.hand.Rank;
import com.cubeia.poker.player.PokerPlayer;
import com.cubeia.poker.rounds.betting.PlayerToActCalculator;
import com.cubeia.poker.util.PokerUtils;

public class TurkishFirstRoundToActCalulator implements PlayerToActCalculator {

    private static final long serialVersionUID = 1470908665729381611L;

    private TurkishHandStrengthEvaluator evaluator;

    public TurkishFirstRoundToActCalulator(int seatIdToStartBettingFrom, Rank lowestRank) {
    	evaluator = new TurkishHandStrengthEvaluator(lowestRank);
    }

   
    @Override
    public PokerPlayer getFirstPlayerToAct(SortedMap<Integer, PokerPlayer> seatingMap, List<Card> communityCards) {
        Comparator<Hand> thc = evaluator.createHandComparator(seatingMap.size());
        
        Hand pairOfKings = new Hand("KS KH");

        for (PokerPlayer p : seatingMap.values()) {
            if (!p.isSittingIn()) {
                continue; // Don't include sitting out or folded players
            }
            Hand cards = p.getPocketCards();

            Hand pHand = new Hand(cards);
            if ( thc.compare(pHand, pairOfKings) > 0) {
                return p;
            }
        }
        return null;
    }

    @Override
    public PokerPlayer getNextPlayerToAct(int lastActedSeatId, SortedMap<Integer, PokerPlayer> seatingMap) {
        PokerPlayer next = null;

        List<PokerPlayer> players = PokerUtils.unwrapList(seatingMap, lastActedSeatId + 1);
        for (PokerPlayer player : players) {
            if (!player.hasFolded() && !player.hasActed() && !player.isAllIn()) {
                next = player;
                break;
            }
        }
        return next;
    }

}