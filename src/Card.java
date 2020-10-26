public class Card {

	private char Number; // A...T J Q K 
	private char Kind; // C-clover(black) D-diamond(red) H-heart(red) L-leaf(black)

	public Card(char num, char kind) {
		this.Number = num;
		this.Kind = kind;
	}
	
	
	public char getNumber() {
		return this.Number;
	}

	public char getKind() {
		return this.Kind;
	}

	public void printCard() {
		System.out.println(this.Number +" , "+this.Kind);
	}

}
