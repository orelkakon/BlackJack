import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
	
	public Card[] playingCards;
	public boolean[] playedCards;
	public Player[] Players; 
	public Player Dealer;
	
	public Table(int numberPlayers, String[] names) {
		refillPacket();
		Players = new Player[numberPlayers];
		for(int i = 0; i < numberPlayers; i++)
			Players[i] = new Player(names[i]);
		Dealer = new Player("Dealer");
		
	}
	
	
	private boolean[] fillBoolPacket() {
		boolean[] boolPacket = new boolean[52];
		for(int i = 0; i < 52; i++)
			boolPacket[i] = false;
		return boolPacket;
	}
 
	private Card[] fillNewPacket() {
		Card[] packet = new Card[52]; 
		packet[0] = new Card('A','C');
		packet[1] = new Card('A','D');
		packet[2] = new Card('A','H');
		packet[3] = new Card('A','L');
		int inc = 4;
		for(int i = 2; i < 10; i++) {
			packet[inc] = new Card((i+"").charAt(0) ,'C'); inc++;
			packet[inc] = new Card((i+"").charAt(0) ,'D'); inc++;
			packet[inc] = new Card((i+"").charAt(0) ,'H'); inc++;
			packet[inc] = new Card((i+"").charAt(0) ,'L'); inc++;
		}
		// TEN - 10
		packet[inc] = new Card('T','C'); inc++;
		packet[inc] = new Card('T','D'); inc++;
		packet[inc] = new Card('T','H'); inc++;
		packet[inc] = new Card('T','L'); inc++;
		// J - PRINCE  
		packet[inc] = new Card('J','C'); inc++;
		packet[inc] = new Card('J','D'); inc++;
		packet[inc] = new Card('J','H'); inc++;
		packet[inc] = new Card('J','L'); inc++;
		// Q - QUEEN
		packet[inc] = new Card('Q','C'); inc++;
		packet[inc] = new Card('Q','D'); inc++;
		packet[inc] = new Card('Q','H'); inc++;
		packet[inc] = new Card('Q','L'); inc++;
		// K - KING
		packet[inc] = new Card('K','C'); inc++;
		packet[inc] = new Card('K','D'); inc++;
		packet[inc] = new Card('K','H'); inc++;
		packet[inc] = new Card('K','L');
		return packet;
	}

	public void refillPacket() {
		this.playingCards = fillNewPacket();
		this.playedCards = fillBoolPacket();
	}

	public boolean everyoneStopPlay() {
		for(int i = 0; i < Players.length; i++) {
			if(Players[i].keepingPlay)
				return false;
		}
		return true;
	}

	public boolean everyoneWaiting() {
		for(int i = 0; i < Players.length; i++) {
			if(!Players[i].waitFinishRound)
				return false;
		}
		if(!Dealer.waitFinishRound)
			return false;
		return true;
	}
	
	public void renewRound() {
		for(int i = 0; i < Players.length; i++)
			Players[i].renewTurn();
		Dealer.renewTurn();
	}

	public boolean packetOver () {
		for(int i = 0; i < playedCards.length; i++) {
			if(playedCards[i])
				return false;
		}
		return true;
	}

	public Card getRandomCard() {
		List<Integer> free = freeCards();
		Random r = new Random();
		int chosenCard = r.nextInt(free.size());
		playedCards[free.get(chosenCard)] = false;
		return playingCards[free.get(chosenCard)];
	}
	
	private List<Integer> freeCards() {
		List<Integer> free = new ArrayList<Integer>();
		for (int i = 0; i < playedCards.length; i++) {
			if(!playedCards[i])
				free.add(i);
		}
		return free;
	}

	public boolean someoneNotWaitNotLose() {
		for(int i = 0; i < Players.length; i++) {
			if(!Players[i].waitFinishRound)
				if(Players[i].evaluateMyHand() <= 21)
					return true;
		}
		if(!Dealer.waitFinishRound)
			if(Dealer.evaluateMyHand() <= 21)
				return true;
		return false;
	}

	public void evaluateWinner() {
		for (int i = 0; i < Players.length; i++) {
			if(Players[i].evaluateMyHand() > 21 || (Dealer.evaluateMyHand() <= 21 && Players[i].evaluateMyHand() <= Dealer.evaluateMyHand()) ) {
				System.out.println("Dealer win this round against "+ Players[i].getName()+" and earn "+ Players[i].currentBid+" !");
				Players[i].myMoney -= Players[i].currentBid;
				Dealer.myMoney += Players[i].currentBid;
			}
			else {
				System.out.println(Players[i].getName() + " win this round and earn "+ 2 * Players[i].currentBid+" !");
				Players[i].myMoney += 2 * Players[i].currentBid;
				Dealer.myMoney -= 2 * Players[i].currentBid;
			}
		}
	}
}
