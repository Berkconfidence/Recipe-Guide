package projectPackage;

public class Tarif {
	
    private int tarifID;
    private String tarifAdi;
    private String kategori;
    private int hazirlamaSuresi;
    private String Talimatlar;
    private String TarifYolu;

    public Tarif(int tarifID, String tarifAdi, String kategori, int hazirlamaSuresi, String talimatlar, String TarifYolu) {
		super();
		this.tarifID = tarifID;
		this.tarifAdi = tarifAdi;
		this.kategori = kategori;
		this.hazirlamaSuresi = hazirlamaSuresi;
		this.Talimatlar = talimatlar;
		this.TarifYolu = TarifYolu;
	}

    public int getTarifID() {
        return tarifID;
    }
    
	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getTalimatlar() {
		return Talimatlar;
	}

	public void setTalimatlar(String talimatlar) {
		Talimatlar = talimatlar;
	}

    public String getTarifAdi() {
        return tarifAdi;
    }
    
    public void setTarifAdi(String tarifAdi) {
		this.tarifAdi = tarifAdi;
	}

    public int getHazirlamaSuresi() {
        return hazirlamaSuresi;
    }
    
    public void setHazirlamaSuresi(int hazirlamaSuresi) {
		this.hazirlamaSuresi = hazirlamaSuresi;
	}

    public String getTarifYolu() {
        return TarifYolu;
    }

    public void setTarifYolu(String TarifYolu) {
        this.TarifYolu = TarifYolu;
    
    }
}
