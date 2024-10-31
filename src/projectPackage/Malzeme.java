package projectPackage;

public class Malzeme {

	private int malzemeID;
	private String malzemeAdi;
	private String toplamMiktar;
	private String malzemeBirim;
	private float birimFiyat;
	
	public Malzeme(int malzemeID, String malzemeAdi, String toplamMiktar, String malzemeBirim, float birimFiyat) {
		super();
		this.malzemeID = malzemeID;
		this.malzemeAdi = malzemeAdi;
		this.toplamMiktar = toplamMiktar;
		this.malzemeBirim = malzemeBirim;
		this.birimFiyat = birimFiyat;
	}

	public Malzeme() {
		// TODO Auto-generated constructor stub
	}

	public void setMalzemeBirim(String malzemeBirim) {
		this.malzemeBirim = malzemeBirim;
	}

	public String getMalzemeAdi() {
		return malzemeAdi;
	}

	public void setMalzemeAdi(String malzemeAdi) {
		this.malzemeAdi = malzemeAdi;
	}

	public float getBirimFiyat() {
		return birimFiyat;
	}

	public void setBirimFiyat(float birimFiyat) {
		this.birimFiyat = birimFiyat;
	}

	public int getMalzemeID() {
		return malzemeID;
	}

	public String getToplamMiktar() {
		return toplamMiktar;
	}

	public String getMalzemeBirim() {
		return malzemeBirim;
	}
	
}
