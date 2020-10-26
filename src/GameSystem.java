import java.util.*;
public class GameSystem {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to BlackJack game !");
		System.out.println("Rules are simple - play black jack and you gambale X : win get 2 * X , lose get 0");
		System.out.println("Please insert the number of players (must be between 1-5)");
		int numberPlayers = input.nextInt();
		if(numberPlayers < 1 || numberPlayers > 5) {
			System.out.println("Wrong inputs - bye bye");
			System.exit(0);
		}
		System.out.println("Please insert the names of players one by one");
		String[] names = new String[numberPlayers];
		for(int i = 0; i < numberPlayers; i++) {
			names[i] = input.next();
		}
		Table blackJackTable = new Table(numberPlayers, names);
		System.out.println("Hello to everyone - we start the game !");
		boolean firstTime = true;
		while(true) {
			firstTime = true;
			for(int i = 0; i < numberPlayers; i++) {
				System.out.println(blackJackTable.Players[i].getName()+ " its your turn. Insert your bid (up from 0) or 0 if you out this round");
				int bid = input.nextInt();
				if(bid == 0)
					blackJackTable.Players[i].keepingPlay = false;
				else
					blackJackTable.Players[i].currentBid = bid;
			}
			if(blackJackTable.everyoneStopPlay())
				break;
			//if we here so at least 1 player gave a bid.
			else {
				while(!blackJackTable.everyoneWaiting()  && blackJackTable.someoneNotWaitNotLose()) {
					if(firstTime) {
						firstTime = false;
						for(int i = 0; i < numberPlayers; i++) {
							if(blackJackTable.Players[i].keepingPlay) {
								System.out.println("Card of "+blackJackTable.Players[i].getName()+":");
								blackJackTable.Players[i].addCard(blackJackTable.getRandomCard());
								blackJackTable.Players[i].getLastCard().printCard();
								System.out.println(blackJackTable.Players[i].getName()+" you have sum of: "+ blackJackTable.Players[i].evaluateMyHand());
							}
						}
						System.out.println("Card of Dealer:");
						blackJackTable.Dealer.addCard(blackJackTable.getRandomCard());
						blackJackTable.Dealer.getLastCard().printCard();
						System.out.println("Dealer you have sum of: "+ blackJackTable.Dealer.evaluateMyHand());

						continue;
					}
					for(int i = 0; i < numberPlayers; i++) {
						if(blackJackTable.Players[i].keepingPlay && !blackJackTable.Players[i].waitFinishRound) {
							if(blackJackTable.Players[i].evaluateMyHand() < 21) {
								System.out.println(blackJackTable.Players[i].getName()+" please insert y to get more card or n if not");
								String wantmore = input.next();
								char wantMore = wantmore.charAt(0);
								if(wantMore == 'y') {
									System.out.println("Card of "+blackJackTable.Players[i].getName()+":");
									blackJackTable.Players[i].addCard(blackJackTable.getRandomCard());
									blackJackTable.Players[i].getLastCard().printCard();
									System.out.println(blackJackTable.Players[i].getName()+" you have sum of: "+ blackJackTable.Players[i].evaluateMyHand());
								}
								else 
									blackJackTable.Players[i].waitFinishRound = true;
							}
							else if(blackJackTable.Players[i].evaluateMyHand() == 21)
								blackJackTable.Players[i].waitFinishRound = true;
						}
					}
					if(!blackJackTable.Dealer.waitFinishRound) {
						if(blackJackTable.Dealer.evaluateMyHand() < 21) {
							System.out.println("Dealer please insert y to get more card or n if not");
							String wantmore = input.next();
							char wantMore = wantmore.charAt(0);
							if(wantMore == 'y') {
								System.out.println("Card of Dealer:");
								blackJackTable.Dealer.addCard(blackJackTable.getRandomCard());
								blackJackTable.Dealer.getLastCard().printCard();
								System.out.println("Dealer you have sum of: "+ blackJackTable.Dealer.evaluateMyHand());
							}
							else 
								blackJackTable.Dealer.waitFinishRound = true;
						}
						else if(blackJackTable.Dealer.evaluateMyHand() < 21) 
							blackJackTable.Dealer.waitFinishRound = true;
					}
				}
			}
			// turn 1 is over - now we will ask if want more cards.
			blackJackTable.evaluateWinner();
			blackJackTable.renewRound();
			System.out.println("Results:");
			summerizeResults(numberPlayers,blackJackTable);
		}

		System.out.println("Bye Bye");
		input.close();
	}

	private static void summerizeResults(int numberPlayers, Table blackJackTable) {
		for(int i = 0; i < numberPlayers; i++) {
			String name = blackJackTable.Players[i].getName();
			int money = blackJackTable.Players[i].myMoney;
			System.out.println("Player: "+name+" , Money: "+money);
		}
		System.out.println("Dealer , Money: "+blackJackTable.Dealer.myMoney);
	}
}
