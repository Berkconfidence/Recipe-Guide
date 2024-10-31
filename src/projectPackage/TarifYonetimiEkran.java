package projectPackage;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class TarifYonetimiEkran extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelYonetim;
	private JPanel malzemePanel;
	private File secilenDosya;
	private File kaydedilenKlasor;
	private JTextField textFieldMalzemeAra;
	private JTextField textFieldTarifAd;
	private JTextField textFieldTarifSure;
	private JTextField textFieldMalzemeEkle;
	private JTextField textFieldBirimFiyat;
	private JComboBox<String> comboBoxKategori;
	private JComboBox<String> comboBoxBirim;
	private JScrollPane scrollPaneMalzemeler;
	private JTextArea textAreaTalimatlar;
	private Map<JCheckBox, JTextField> malzemeMap = new HashMap<>();
	private Map<String, List<Object>> malzemeSecimleri = new HashMap<>();
	private JScrollPane scrollPaneTarifler;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TarifYonetimiEkran frame = new TarifYonetimiEkran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TarifYonetimiEkran() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelYonetim = new JPanel();
		panelYonetim.setBounds(0, 0, 800, 600);
		contentPane.add(panelYonetim);
		panelYonetim.setLayout(null);
		
		malzemePanel = new JPanel();
		malzemePanel.setLayout(new BoxLayout(malzemePanel, BoxLayout.Y_AXIS));
		malzemePanel.setBounds(10, 220, 215, 100);
		
		JLabel labelTarifYonetimi = new JLabel("Tarif Yönetimi");
		labelTarifYonetimi.setFont(new Font("Constantia", Font.BOLD | Font.ITALIC, 40));
		labelTarifYonetimi.setBounds(270, 10, 275, 50);
		panelYonetim.add(labelTarifYonetimi);
		
		JLabel labelTarifAd = new JLabel("Tarif Adı");
		labelTarifAd.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifAd.setBounds(10, 80, 100, 25);
		panelYonetim.add(labelTarifAd);
		
		textFieldTarifAd = new JTextField();
		textFieldTarifAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldTarifAd.setBounds(10, 105, 135, 21);
		panelYonetim.add(textFieldTarifAd);
		textFieldTarifAd.setColumns(10);
		
		JLabel labelTarifKategori = new JLabel("Tarif Kategorisi");
		labelTarifKategori.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifKategori.setBounds(175, 80, 135, 25);
		panelYonetim.add(labelTarifKategori);
		
		comboBoxKategori = new JComboBox<String>();
		comboBoxKategori.setFont(new Font("Arial", Font.BOLD, 10));
		comboBoxKategori.setModel(new DefaultComboBoxModel<String>(new String[] {"Ana Yemek", "Çorba", "Meze", "Tatlı", "İçecek"}));
		comboBoxKategori.setBounds(175, 105, 135, 20);
		panelYonetim.add(comboBoxKategori);
		
		JLabel labelTarifSure = new JLabel("Hazırlama Süresi (dk)");
		labelTarifSure.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifSure.setBounds(10, 135, 190, 25);
		panelYonetim.add(labelTarifSure);
		
		textFieldTarifSure = new JTextField();
		textFieldTarifSure.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldTarifSure.setColumns(10);
		textFieldTarifSure.setBounds(10, 160, 135, 21);
		panelYonetim.add(textFieldTarifSure);
		
		JLabel labelTarifMalzeme = new JLabel("Malzemeler");
		labelTarifMalzeme.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifMalzeme.setBounds(10, 190, 95, 25);
		panelYonetim.add(labelTarifMalzeme);
		
		textFieldMalzemeAra = new JTextField();
		textFieldMalzemeAra.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
		textFieldMalzemeAra.setColumns(10);
		textFieldMalzemeAra.setBounds(100, 193, 65, 20);
		panelYonetim.add(textFieldMalzemeAra);
		
		JButton buttonMalzemeAra = new JButton("Ara");
		buttonMalzemeAra.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	malzemeMenu(); 
		    }
		});
		buttonMalzemeAra.setForeground(new Color(255, 255, 255));
		buttonMalzemeAra.setBackground(new Color(45, 199, 110));
		buttonMalzemeAra.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonMalzemeAra.setBounds(172, 193, 57, 20);
		panelYonetim.add(buttonMalzemeAra);
	
		
		JButton buttonMalzemeYenile = new JButton("Yenile");
        buttonMalzemeYenile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		malzemeMenu();
        	}
        });
        buttonMalzemeYenile.setForeground(Color.WHITE);
        buttonMalzemeYenile.setFont(new Font("Dubai Light", Font.BOLD, 15));
        buttonMalzemeYenile.setBackground(new Color(45, 199, 110));
        buttonMalzemeYenile.setBounds(235, 193, 75, 20);
        panelYonetim.add(buttonMalzemeYenile);
		
		scrollPaneMalzemeler = new JScrollPane(malzemePanel);
		scrollPaneMalzemeler.setBounds(10, 220, 300, 100);
		panelYonetim.add(scrollPaneMalzemeler);	
		malzemeMenu();
		
		JLabel labelTarifMalzemeEkle = new JLabel("Malzeme Ekle (Isim / Birim / BirimFiyat)");
		labelTarifMalzemeEkle.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifMalzemeEkle.setBounds(10, 330, 300, 25);
		panelYonetim.add(labelTarifMalzemeEkle);
		
		textFieldMalzemeEkle = new JTextField();
		textFieldMalzemeEkle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMalzemeEkle.setColumns(10);
		textFieldMalzemeEkle.setBounds(10, 355, 115, 21);
		panelYonetim.add(textFieldMalzemeEkle);
		
		comboBoxBirim = new JComboBox<String>();
		comboBoxBirim.setModel(new DefaultComboBoxModel<String>(new String[] {"kg", "g", "L", "ml", "adet"}));
		comboBoxBirim.setBounds(135, 355, 55, 20);
		panelYonetim.add(comboBoxBirim);
		
		textFieldBirimFiyat = new JTextField();
		textFieldBirimFiyat.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
		textFieldBirimFiyat.setColumns(10);
		textFieldBirimFiyat.setBounds(200, 355, 40, 21);
		panelYonetim.add(textFieldBirimFiyat);
		
		JButton buttonMalzemeEkle = new JButton("Ekle");
		buttonMalzemeEkle.setForeground(new Color(255, 255, 255));
		buttonMalzemeEkle.setBackground(new Color(45, 199, 110));
		buttonMalzemeEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager dbManager = new DatabaseManager();
				String malzemeAd = textFieldMalzemeEkle.getText();
				String malzemeBirim = (String) comboBoxBirim.getSelectedItem();
				float malzemeBirimFiyat = 0;
				if(!textFieldBirimFiyat.getText().isEmpty())
					malzemeBirimFiyat = Float.parseFloat(textFieldBirimFiyat.getText());
				
				if(!malzemeAd.isEmpty() && !malzemeBirim.isEmpty() && malzemeBirimFiyat>0)
				{
					dbManager.malzemeEkle(malzemeAd, malzemeBirim, malzemeBirimFiyat);				
					malzemeMenu();
				}
				else
					JOptionPane.showMessageDialog(null, "Bilgileri eksiksiz giriniz");
			}
		});
		buttonMalzemeEkle.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonMalzemeEkle.setBounds(250, 355, 61, 20);
		panelYonetim.add(buttonMalzemeEkle);
		
		JLabel labelTarifTalimat = new JLabel("Talimatlar");
		labelTarifTalimat.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifTalimat.setBounds(10, 385, 85, 25);
		panelYonetim.add(labelTarifTalimat);
		
		textAreaTalimatlar = new JTextArea();
		textAreaTalimatlar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textAreaTalimatlar.setBounds(0, 0, 5, 16);
		panelYonetim.add(textAreaTalimatlar);
		
		JScrollPane scrollPaneTalimatlar = new JScrollPane(textAreaTalimatlar);
		scrollPaneTalimatlar.setBounds(10, 420, 135, 75);
		panelYonetim.add(scrollPaneTalimatlar);
		
		JLabel labelTarifYolu = new JLabel("Tarif Görseli");
		labelTarifYolu.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifYolu.setBounds(175, 385, 95, 25);
		panelYonetim.add(labelTarifYolu);
		
		JButton buttonTarifYolu = new JButton("Görsel Ekle");
		buttonTarifYolu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Fotoğraf Seçiniz");
                            
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
                
                int userSelection = fileChooser.showOpenDialog(null);
                
                if(userSelection == JFileChooser.APPROVE_OPTION)
                {
                    secilenDosya = fileChooser.getSelectedFile();
                    String dosyaYolu = "C:\\Code Java workspace\\RecipeGuide\\recipeImage\\";                 
                    String dosyaAd = textFieldTarifAd.getText()+".png";                  
                    kaydedilenKlasor = new File(dosyaYolu + dosyaAd);                    
                    
                }
			}
		});
		buttonTarifYolu.setForeground(new Color(255, 255, 255));
		buttonTarifYolu.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonTarifYolu.setBackground(new Color(45, 199, 110));
		buttonTarifYolu.setBounds(175, 418, 135, 20);
		panelYonetim.add(buttonTarifYolu);
		
		JButton buttonTarifEkle = new JButton("Tarif Ekle");
		buttonTarifEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String tarifAd = textFieldTarifAd.getText();
				String tarifKategori = (String) comboBoxKategori.getSelectedItem();
				int hazirlamaSuresi=0;
				if(!textFieldTarifSure.getText().isEmpty())
					hazirlamaSuresi = Integer.parseInt(textFieldTarifSure.getText());
				
				String talimatlar = textAreaTalimatlar.getText();
				talimatlar = talimatlar.replace("\n", " ").replace("\r", "");
				String tarifYolu=null;
				if(secilenDosya!=null)
				{
					try {
	                    Files.copy(secilenDosya.toPath(), kaydedilenKlasor.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Dosya kaydedilirken hata oluştu.");
	                }
					tarifYolu = kaydedilenKlasor.toPath().toString();
				}
				
				
				if(tarifAd!=null && tarifKategori!=null && hazirlamaSuresi>0 && talimatlar!=null && tarifYolu!=null && !malzemeMap.isEmpty())
				{					
					DatabaseManager dbManager = new DatabaseManager();
					dbManager.tarifEkle(tarifAd, tarifKategori, hazirlamaSuresi, talimatlar, tarifYolu, malzemeMap);
					tarifleriListele();
				}
				else {
					JOptionPane.showMessageDialog(null, "Bilgileri eksiksiz ve doğru giriniz!");
				}

			}
		});
		buttonTarifEkle.setForeground(Color.WHITE);
		buttonTarifEkle.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonTarifEkle.setBackground(new Color(45, 199, 110));
		buttonTarifEkle.setBounds(10, 515, 96, 20);
		panelYonetim.add(buttonTarifEkle);
		
		JButton buttonTarifGuncelle = new JButton("Guncelle");
		buttonTarifGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String tarifAd = textFieldTarifAd.getText();
				String tarifKategori = (String) comboBoxKategori.getSelectedItem();
				int hazirlamaSuresi=0;
				if(!textFieldTarifSure.getText().isEmpty())
					hazirlamaSuresi = Integer.parseInt(textFieldTarifSure.getText());
				
				String talimatlar = textAreaTalimatlar.getText();
				talimatlar = talimatlar.replace("\n", " ").replace("\r", "");
				String tarifYolu=null;
				if(secilenDosya!=null)
				{
					try {
	                    Files.copy(secilenDosya.toPath(), kaydedilenKlasor.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Dosya kaydedilirken hata oluştu.");
	                }
					tarifYolu = kaydedilenKlasor.toPath().toString();
				}
				
				
				if(tarifAd!=null && tarifKategori!=null && hazirlamaSuresi>0 && talimatlar!=null && tarifYolu!=null && !malzemeMap.isEmpty())
				{					
					DatabaseManager dbManager = new DatabaseManager();
					dbManager.tarifGuncelle(tarifAd, tarifKategori, hazirlamaSuresi, talimatlar, tarifYolu, malzemeMap);
					tarifleriListele();
				}
				else {
					JOptionPane.showMessageDialog(null, "Bilgileri eksiksiz ve doğru giriniz!");
				}
				tarifleriListele();
							
			}
		});
		buttonTarifGuncelle.setForeground(Color.WHITE);
		buttonTarifGuncelle.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonTarifGuncelle.setBackground(new Color(46, 132, 244));
		buttonTarifGuncelle.setBounds(115, 515, 95, 20);
		panelYonetim.add(buttonTarifGuncelle);
		
		JButton buttonTarifSil = new JButton("Sil");
		buttonTarifSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String tarifAd = textFieldTarifAd.getText();
				String tarifKategori = (String) comboBoxKategori.getSelectedItem();
				int hazirlamaSuresi=0;
				if(!textFieldTarifSure.getText().isEmpty())
					hazirlamaSuresi = Integer.parseInt(textFieldTarifSure.getText());
				
				String talimatlar = textAreaTalimatlar.getText();
				talimatlar = talimatlar.replace("\n", " ").replace("\r", "");
				String tarifYolu=null;
				if(secilenDosya!=null)
				{
					try {
	                    Files.copy(secilenDosya.toPath(), kaydedilenKlasor.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Dosya kaydedilirken hata oluştu.");
	                }
					tarifYolu = kaydedilenKlasor.toPath().toString();
				}
				
				
				if(tarifAd!=null && tarifKategori!=null && hazirlamaSuresi>0 && talimatlar!=null && tarifYolu!=null && !malzemeMap.isEmpty())
				{					
					DatabaseManager dbManager = new DatabaseManager();
					dbManager.tarifSil(tarifAd);
					tarifleriListele();
				}
				else {
					JOptionPane.showMessageDialog(null, "Bilgileri eksiksiz ve doğru giriniz!");
				}
			}
		});
		buttonTarifSil.setForeground(Color.WHITE);
		buttonTarifSil.setFont(new Font("Dubai Light", Font.BOLD, 15));
		buttonTarifSil.setBackground(new Color(255, 87, 75));
		buttonTarifSil.setBounds(220, 515, 90, 20);
		panelYonetim.add(buttonTarifSil);
		
		tarifleriListele();
		
		JButton buttonGeriTarifYonetimi = new JButton("Geri");
        buttonGeriTarifYonetimi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AnaEkran anapanel = new AnaEkran();
				anapanel.setVisible(true);
				TarifYonetimiEkran.this.dispose();
        	}
        });
        buttonGeriTarifYonetimi.setForeground(Color.WHITE);
        buttonGeriTarifYonetimi.setFont(new Font("Dubai Light", Font.BOLD, 15));
        buttonGeriTarifYonetimi.setBackground(new Color(255, 85, 76));
        buttonGeriTarifYonetimi.setBounds(670, 515, 90, 20);
        panelYonetim.add(buttonGeriTarifYonetimi);
        
        JButton buttonYenile = new JButton("Yenile");
        buttonYenile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textFieldTarifAd.setText("");
        		textFieldTarifSure.setText("");
        		textFieldMalzemeAra.setText("");
        		textFieldMalzemeEkle.setText("");
        		textFieldBirimFiyat.setText("");
        		textAreaTalimatlar.setText("");
        		secilenDosya=null;
        		malzemeMenu();
        		comboBoxKategori.setSelectedIndex(0);
        		comboBoxBirim.setSelectedIndex(0);
        		
        	}
        });
        buttonYenile.setForeground(Color.WHITE);
        buttonYenile.setFont(new Font("Dubai Light", Font.BOLD, 15));
        buttonYenile.setBackground(new Color(46, 132, 244));
        buttonYenile.setBounds(385, 515, 90, 20);
        panelYonetim.add(buttonYenile);
        
        	
	}
	
	
	public void malzemeMenu()
	{
	    String arananMalzeme = textFieldMalzemeAra.getText().toLowerCase();
	    malzemePanel.removeAll(); 
	    malzemeMap.clear();       
	
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Malzeme> malzemeler = dbManager.getMalzemeler();
	
	    malzemePanel.setLayout(new GridLayout(0, 2)); 
	    int gosterilenMalzemeSayisi = 0; 
	
	    for(Malzeme malzeme : malzemeler)
	    {
	    	if(malzeme.getMalzemeAdi().toLowerCase().contains(arananMalzeme))
	    	{
	            String malzemeAdi = malzeme.getMalzemeAdi();

	            JCheckBox checkBox = new JCheckBox(malzemeAdi);
	            JTextField miktar = new JTextField();
	            miktar.setPreferredSize(new Dimension(5, 20));
	            malzemeMap.put(checkBox, miktar);

	            if(malzemeSecimleri.containsKey(malzemeAdi))
	            {
	                checkBox.setSelected((Boolean) malzemeSecimleri.get(malzemeAdi).get(0));
	                miktar.setText((String) malzemeSecimleri.get(malzemeAdi).get(1));
	            }

	            malzemePanel.add(checkBox);
	            malzemePanel.add(miktar);

	            checkBox.addActionListener(e -> malzemeSecimleri.put(malzemeAdi, 
	                Arrays.asList(checkBox.isSelected(), miktar.getText())));
	            miktar.getDocument().addDocumentListener(new DocumentListener() {
	                public void insertUpdate(DocumentEvent e) { updateMap(); }
	                public void removeUpdate(DocumentEvent e) { updateMap(); }
	                public void changedUpdate(DocumentEvent e) { updateMap(); }
	                
	                private void updateMap() {
	                    malzemeSecimleri.put(malzemeAdi, Arrays.asList(checkBox.isSelected(), miktar.getText()));
	                }
	            });

	            gosterilenMalzemeSayisi++;
	        }
	    }
	
	
	    int gosterilenSatirSayisi = 4;
	    int doldurulacakSatir = gosterilenSatirSayisi - gosterilenMalzemeSayisi;
	
	    for(int i=0; i<doldurulacakSatir; i++)
	    {
	        malzemePanel.add(new JLabel("")); 
	        malzemePanel.add(new JLabel(""));
	    }
	
	    malzemePanel.revalidate(); 
	    malzemePanel.repaint();  
	}	
	
	public void tabloMalzeme()
	{
		malzemePanel.removeAll();
		malzemeMap.clear();
		
		DatabaseManager dbManager = new DatabaseManager();
		List<Malzeme> malzemeler = dbManager.getMalzemeler();
		
		malzemePanel.setLayout(new GridLayout(0, 2));
		for (Malzeme malzeme : malzemeler)
		{
			JCheckBox checkBox = new JCheckBox(malzeme.getMalzemeAdi());
			JTextField miktar = new JTextField();
			miktar.setPreferredSize(new Dimension(5, 20));
			
			malzemeMap.put(checkBox, miktar);
			
			malzemePanel.add(checkBox);
			malzemePanel.add(miktar);		
		}
		
		
	    malzemePanel.revalidate();
	    malzemePanel.repaint();
		
	}
	
	private void tarifleriListele() {
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();

	    String[] columnNames = {"Tarif Adı", "Süre", "Maliyet"};
	    Object[][] data = new Object[tarifler.size()][3];

	    for (int i = 0; i < tarifler.size(); i++) {
	        Tarif tarif = tarifler.get(i);
	        data[i][0] = tarif.getTarifAdi();
	        data[i][1] = tarif.getHazirlamaSuresi() + " dk";
	        data[i][2] = String.format("%.2f TL", dbManager.tarifMaliyet(tarif));
	    }

	    // tabloyu güncelle
	    if(scrollPaneTarifler != null && scrollPaneTarifler.getViewport().getView() != null)
	    {
	        JTable tarifTable = (JTable) scrollPaneTarifler.getViewport().getView();
	        DefaultTableModel tableModel = (DefaultTableModel) tarifTable.getModel();
	        tableModel.setDataVector(data, columnNames); // Mevcut verileri güncelle
	    }
	    else
	    {
	        @SuppressWarnings("serial")
	        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        JTable tarifTable = new JTable(tableModel);
	        tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	        TableColumn column0 = tarifTable.getColumnModel().getColumn(0);
	        column0.setPreferredWidth(150);
	        TableColumn column1 = tarifTable.getColumnModel().getColumn(1);
	        column1.setPreferredWidth(50);
	        TableColumn column2 = tarifTable.getColumnModel().getColumn(2);
	        column2.setPreferredWidth(50);
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	        tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	        tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	        
	        tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt)
	            {
	                int selectedRow = tarifTable.getSelectedRow();
	                if(selectedRow != -1)
	                {
	                	tabloMalzeme();
	                	List<TarifMalzeme> tarifMalzemeler = dbManager.getTarifMalzeme();
	                	List<Malzeme> malzemeler = dbManager.getMalzemeler();
	                    Tarif tarif = tarifler.get(selectedRow);
	                    textFieldTarifAd.setText("");
	                    textFieldTarifAd.setText(tarif.getTarifAdi());
	                    textFieldTarifSure.setText("");
	                    String tarifSure = Integer.toString(tarif.getHazirlamaSuresi());
	                    textFieldTarifSure.setText(tarifSure);
	                    comboBoxKategori.setSelectedItem(tarif.getKategori());
	                    textAreaTalimatlar.setLineWrap(true);
	                    textAreaTalimatlar.setWrapStyleWord(true); 
	                    textAreaTalimatlar.setText(tarif.getTalimatlar());
	                    
	                    String tarifYolu = tarif.getTarifYolu();
	                    if (tarifYolu != null && !tarifYolu.isEmpty())
	                    {
	                        secilenDosya = new File(tarifYolu);
	                        kaydedilenKlasor = secilenDosya;                      
	                    }
	                    
	                    for(TarifMalzeme tarifMalzeme : tarifMalzemeler)
	                    {
	                    	if(tarifMalzeme.getTarifID()==tarif.getTarifID())
	                    	{
	                    		for(Malzeme malzeme : malzemeler)
	                    		{
	                    			if(tarifMalzeme.getMalzemeID()==malzeme.getMalzemeID())
	                    			{
	                    				for (Map.Entry<JCheckBox, JTextField> entry : malzemeMap.entrySet())
	                    				{
	                                        JCheckBox checkBox = entry.getKey();
	                                        JTextField miktarField = entry.getValue();
	                                        
	                                        if (checkBox.getText().equals(malzeme.getMalzemeAdi()))
	                                        {
	                                            checkBox.setSelected(true);                                                                                 
	                                            if(malzeme.getMalzemeBirim().equals("kg") || malzeme.getMalzemeBirim().equals("L"))
	                                            	miktarField.setText(String.valueOf(tarifMalzeme.getMalzemeMiktar()*1000));                                      
	                                            else
	                                            	miktarField.setText(String.valueOf(tarifMalzeme.getMalzemeMiktar()));                                     
	                                            
	                                        }
	                                    }
	                    			}
	                    		}
	                    	}
	                    }
	                    
	                    
	                }
	            }
	        });

	        scrollPaneTarifler = new JScrollPane(tarifTable);
	        scrollPaneTarifler.setBounds(385, 85, 375, 410);
	        panelYonetim.add(scrollPaneTarifler);
	    }

	    panelYonetim.revalidate(); // Paneli yeniden düzenle
	    panelYonetim.repaint(); // Paneli yenile
	}

}


