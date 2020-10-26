import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String Name;
	public List<Card> myHand;
	public int myMoney;
	public boolean keepingPlay;
	public int currentBid;
	public boolean waitFinishRound;
	
	public Player(String name) {
		this.Name = name;
		myHand = new ArrayList<Card>(0);
		myMoney = 0;
		keepingPlay = true; 
		waitFinishRound = false;
	}
	
	public void stopPlay() {
		keepingPlay = false;
	}
	
	public void addCard(Card card) {
		myHand.add(card);
	}
	
	public void renewTurn() {
		this.myHand.clear();
		this.keepingPlay = true;
		this.currentBid = 0;
		waitFinishRound = false;
	}
	
	public int evaluateMyHand() {
		int sum = 0;
		for(Card card : myHand) {
			char number = card.getNumber();
			if(number >= '2' && number <= '9')
				sum += Character.getNumericValue(number);
			else if(number == 'T' || number == 'J' || number == 'Q' || number == 'K')
				sum += 10;
		}
		for(Card card : myHand) {
			char number = card.getNumber();
			if(number == 'A') {
				int lowA = sum + 1;
				int highA = sum + 11;
				if(highA <= 21)
					sum = highA;
				else
					sum = lowA;
			}
		}
		return sum;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setBid(int bid) {
		this.currentBid = bid;
	}
	
	public Card getLastCard() {
		return this.myHand.get(myHand.size() - 1);
	}
}
