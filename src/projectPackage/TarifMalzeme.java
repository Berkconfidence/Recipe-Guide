package projectPackage;

public class TarifMalzeme {
	
	private int tarifID;
	private int malzemeID;
	private float malzemeMiktar;
	
	public TarifMalzeme(int tarifID, int malzemeID, float malzemeMiktar) {
		super();
		this.tarifID = tarifID;
		this.malzemeID = malzemeID;
		this.malzemeMiktar = malzemeMiktar;
	}
	
	public float getMalzemeMiktar() {
		return malzemeMiktar;
	}

	public void setMalzemeMiktar(float malzemeMiktar) {
		this.malzemeMiktar = malzemeMiktar;
	}

	public int getTarifID() {
		return tarifID;
	}

	public int getMalzemeID() {
		return malzemeID;
	}
	
	

}
