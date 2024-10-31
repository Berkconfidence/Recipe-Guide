package projectPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public List<Tarif> getTarifler()
    {
        List<Tarif> tarifler = new ArrayList<>();     
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT TarifID, TarifAdi, Kategori, HazirlamaSuresi, Talimatlar, TarifYolu FROM tarifler";
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next())
            {
                int id = resultSet.getInt("TarifID");
                String adi = resultSet.getString("TarifAdi");
                String kategori = resultSet.getString("Kategori");
                int sure = resultSet.getInt("HazirlamaSuresi");
                String talimatlar = resultSet.getString("Talimatlar");
                String tarifyolu = resultSet.getString("TarifYolu");
                tarifler.add(new Tarif(id, adi, kategori, sure, talimatlar, tarifyolu));
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return tarifler;
    }
    
    public List<Malzeme> getMalzemeler()
    {
    	List<Malzeme> malzemeler = new ArrayList<>();     
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT MalzemeID, MalzemeAdi, ToplamMiktar, MalzemeBirim, BirimFiyat FROM malzemeler";
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next())
            {
            	int id = resultSet.getInt("MalzemeID");
                String adi = resultSet.getString("MalzemeAdi");
                String toplamMiktar = resultSet.getString("ToplamMiktar");
                String MalzemeBirim = resultSet.getString("MalzemeBirim");
                float BirimFiyat = resultSet.getFloat("BirimFiyat");
                malzemeler.add(new Malzeme(id, adi, toplamMiktar, MalzemeBirim, BirimFiyat));
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return malzemeler;
    }
    
    public List<TarifMalzeme> getTarifMalzeme()
    {
    	List<TarifMalzeme> tarifMalzemeler = new ArrayList<>();     
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT TarifID, MalzemeID, MalzemeMiktar FROM tarifmalzeme";
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next())
            {
            	int tarifID = resultSet.getInt("TarifID");
                int malzemeID = resultSet.getInt("MalzemeID");
                float malzemeMiktar = resultSet.getFloat("MalzemeMiktar");
                tarifMalzemeler.add(new TarifMalzeme(tarifID, malzemeID, malzemeMiktar));
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return tarifMalzemeler;	
    }
    
    public List <Malzeme> getTarifinMalzemeleri(Tarif tarif)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	List<TarifMalzeme> tarifMalzemeKontrol = dbManager.getTarifMalzeme();
        List<Malzeme> malzemeler = dbManager.getMalzemeler();
        List<Malzeme> tarifMalzemeleri = new ArrayList<Malzeme>();
        
        for(TarifMalzeme tarifKontrol : tarifMalzemeKontrol)
        {
        	if(tarifKontrol.getTarifID()==tarif.getTarifID())
        	{
        		for(Malzeme malzeme : malzemeler)
            	{
            		if(tarifKontrol.getMalzemeID()==malzeme.getMalzemeID())
            			tarifMalzemeleri.add(malzeme);
            	}
        	}
        }
        return tarifMalzemeleri;
    }
    
    public void tarifEkle(String tarifAd, String tarifKategori, int hazirlamaSuresi, String talimatlar, String tarifYolu, Map<JCheckBox, JTextField> malzemeMap)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	List<Tarif> tarifler = dbManager.getTarifler();
    	int tarifVar = 0;
    	for(Tarif tarif : tarifler)
    	{
    		if(tarif.getTarifAdi().equals(tarifAd))
    		{
    			tarifVar=1;
    			break;
    		}
    	}
    	if(tarifVar==0)
    	{
    		 String sql = "INSERT INTO tarifler (TarifAdi, Kategori, HazirlamaSuresi, Talimatlar, TarifYolu) VALUES (?, ?, ?, ?, ?)";
    	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    	             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    	            
    	            pstmt.setString(1, tarifAd);
    	            pstmt.setString(2, tarifKategori);
    	            pstmt.setInt(3, hazirlamaSuresi);
    	            pstmt.setString(4, talimatlar);
    	            pstmt.setString(5, tarifYolu);

    	            int affectedRows = pstmt.executeUpdate();  //insert işlemini yapar
    	            if(affectedRows > 0)
    	            {
    	                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {  //primary key'i alır
    	                    if(generatedKeys.next())
    	                    {
    	                        int tarifID = generatedKeys.getInt(1);                    
    	                        malzemeleriEkle(tarifID, malzemeMap);
    	                        JOptionPane.showMessageDialog(null, "Tarif Başarıyla Eklendi!");
    	                    }
    	                }
    	            }
    	            
    	        } catch (SQLException ex) {
    	            ex.printStackTrace();
    	        }
    	}
    	else
    		JOptionPane.showMessageDialog(null, "Bu Tarif Var!");
    		      
    }
    
    public void tarifGuncelle(String tarifAd, String tarifKategori, int hazirlamaSuresi, String talimatlar, String tarifYolu, Map<JCheckBox, JTextField> malzemeMap)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	List<Tarif> tarifler = dbManager.getTarifler();
    	int tarifID = 0;
    	for(Tarif tarif : tarifler)
    	{
    		if(tarif.getTarifAdi().equals(tarifAd))
    		{
    			tarifID=tarif.getTarifID();
    			break;
    		}
    	}
    	if(tarifID!=0)
    	{
    		String sql = "UPDATE tarifler SET TarifAdi = ?, Kategori = ?, HazirlamaSuresi = ?, Talimatlar = ?, TarifYolu = ? WHERE TarifID = ?";
        	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
       	         PreparedStatement pstmt = connection.prepareStatement(sql)) {

       	        pstmt.setString(1, tarifAd);
       	        pstmt.setString(2, tarifKategori);
       	        pstmt.setInt(3, hazirlamaSuresi);
       	        pstmt.setString(4, talimatlar);
       	        pstmt.setString(5, tarifYolu);
       	        pstmt.setInt(6, tarifID);
       	        pstmt.executeUpdate();
       	        
    	   	    String deleteSql = "DELETE FROM tarifmalzeme WHERE TarifID = ?";
    	   	    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
    	   	        deleteStmt.setInt(1, tarifID);
    	   	        deleteStmt.executeUpdate();
    	   	    }
       	        
       	        malzemeleriEkle(tarifID, malzemeMap);
       	        JOptionPane.showMessageDialog(null, "Tarif Güncellendi!");

       	    } catch (SQLException ex) {
       	        ex.printStackTrace();
       	    }
    	}
    	else
    		JOptionPane.showMessageDialog(null, "Bu Tarif Menüde Yok!");
    		
       
    }

    public void tarifSil(String tarifAd)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	List <Tarif> tarifler = dbManager.getTarifler();
    	
    	for(Tarif tarif : tarifler)
    	{
    		if(tarif.getTarifAdi().equals(tarifAd))
    		{
    			String sql = "DELETE FROM tarifler WHERE TarifID = ?";
            	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
           	         PreparedStatement pstmt = connection.prepareStatement(sql)) {

            		pstmt.setInt(1, tarif.getTarifID());         	       
           	        pstmt.executeUpdate();
           	        
        	   	    String deleteSql = "DELETE FROM tarifmalzeme WHERE TarifID = ?";
        	   	    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
        	   	        deleteStmt.setInt(1, tarif.getTarifID());
        	   	        deleteStmt.executeUpdate();
        	   	    }
           	        
           	        JOptionPane.showMessageDialog(null, "Tarif Silindi!");
           	        break;

           	    } catch (SQLException ex) {
           	        ex.printStackTrace();
           	    }
    		}
    	}
    	
    }
    
    public void malzemeEkle(String malzemeAd, String malzemeBirim, float malzemeBirimFiyat)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	List<Malzeme> malzemeler = dbManager.getMalzemeler();
    	int malzemeVar = 0;
    	for(Malzeme malzeme : malzemeler)
    	{
    		if(malzeme.getMalzemeAdi().equals(malzemeAd))
    		{
    			malzemeVar = 1;
    			break;
    		}
    	}
    	if(malzemeVar==0)
    	{
    		String sql = "INSERT INTO malzemeler (MalzemeAdi, ToplamMiktar, MalzemeBirim, BirimFiyat ) VALUES (?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmt.setString(1, malzemeAd);
                
                if(malzemeBirim.equals("g"))
                	pstmt.setString(2, "500 g");
                else if(malzemeBirim.equals("ml"))
                	pstmt.setString(2, "500 ml");
                else if(malzemeBirim.equals("adet"))
                	pstmt.setString(2, "5 adet");
                else if(malzemeBirim.equals("kg"))
                	pstmt.setString(2, "1 kg");
                else
                	pstmt.setString(2, "1 L");
                pstmt.setString(3, malzemeBirim);
                pstmt.setFloat(4, malzemeBirimFiyat);

                pstmt.executeUpdate();  
                JOptionPane.showMessageDialog(null, "Malzeme Başarıyla Eklendi!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    	}
    	else
    		JOptionPane.showMessageDialog(null, "Bu Malzeme Var!");
    	
    }
    
    public void malzemeleriEkle(int tarifID, Map<JCheckBox, JTextField> malzemeMap)
    {
        String sql = "INSERT INTO tarifmalzeme (TarifID, MalzemeID, MalzemeMiktar) VALUES (?, ?, ?)";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            DatabaseManager dbManager = new DatabaseManager();
            List<Malzeme> malzemeler = dbManager.getMalzemeler();
            
            for(Map.Entry<JCheckBox, JTextField> entry : malzemeMap.entrySet())
            {
                JCheckBox checkBox = entry.getKey();
                JTextField miktarField = entry.getValue();

                if(checkBox.isSelected())
                {
                    try {
                        float miktar = Float.parseFloat(miktarField.getText());
                        if(miktar > 0)
                        {
                            String malzemeAdi = checkBox.getText();
                            Malzeme malzeme = null;

                            for(Malzeme malzemeAra : malzemeler)
                            {
                                if(malzemeAra.getMalzemeAdi().equals(malzemeAdi))
                                {
                                    malzeme = malzemeAra;
                                    break; 
                                }
                            }

                            if(malzeme != null)
                            {  
                                pstmt.setInt(1, tarifID);
                                pstmt.setInt(2, malzeme.getMalzemeID());
                                if(malzeme.getMalzemeBirim().equals("kg") || malzeme.getMalzemeBirim().equals("L")) {
                                	miktar = miktar/1000;
                                	pstmt.setFloat(3, miktar);
                                }
                                else {
                                	pstmt.setFloat(3, miktar);
                                }                               	
                                pstmt.addBatch();
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar girin!");
                    }
                }
            }

            pstmt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } 	

    public float tarifMaliyet(Tarif tarif)
    {
    	DatabaseManager dbManager = new DatabaseManager();
    	float maliyet = 0;
    	List<Malzeme> malzemeler = dbManager.getMalzemeler(); 
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT TarifID, MalzemeID, MalzemeMiktar FROM tarifmalzeme";
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next())
            {
            	int tarifId = resultSet.getInt("TarifID");
            	if(tarifId == tarif.getTarifID())
            	{
            		int malzemeId = resultSet.getInt("MalzemeID");
            		for(Malzeme malzeme : malzemeler)
            		{
            			if(malzemeId == malzeme.getMalzemeID())
            			{
            				float malzemeMiktar = resultSet.getFloat("MalzemeMiktar");
                            maliyet = maliyet + (malzeme.getBirimFiyat()*malzemeMiktar);
                            break;
            			}
            		}
                    
            	}
            	
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      

		return maliyet;
    	
    }

}
