package projectPackage;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class TarifAramaEkran extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel malzemePanel;
	private JTable tarifTable;
	private JScrollPane scrollPaneTarifler;
	private JTextField textFieldTarifAd;
	private JTextField textFieldMalzemeSecimi;
	private JTextField textFieldMaliyetMin;
	private JTextField textFieldMaliyetMax;
	private JTextField textFieldMalzemeSayisiMin;
	private JTextField textFieldMalzemeSayisiMax;
	private JRadioButton radioButtonHizli;
	private JRadioButton radioButtonYavas;
	private JRadioButton radioButtonArtan;
	private JRadioButton radioButtonAzalan;
	private ButtonGroup buttonGroupSure;
	private ButtonGroup buttonGroupMaliyet;
	private JComboBox<String> comboBoxKategori;
	private Map<String, Boolean> malzemeMap = new HashMap<>();
	private List<Tarif> filtrelenmisTarifler = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TarifAramaEkran frame = new TarifAramaEkran();
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
	public TarifAramaEkran() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		
		
		malzemePanel = new JPanel();
		malzemePanel.setLayout(new BoxLayout(malzemePanel, BoxLayout.Y_AXIS));
		malzemePanel.setBounds(10, 180, 130, 100);
		
		JButton buttonGeri = new JButton("Geri");
		buttonGeri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnaEkran anapanel = new AnaEkran();
				anapanel.setVisible(true);
				TarifAramaEkran.this.dispose();
			}
		});
		buttonGeri.setForeground(Color.WHITE);
		buttonGeri.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		buttonGeri.setBackground(new Color(255, 85, 76));
		buttonGeri.setBounds(680, 520, 90, 20);
		contentPane.add(buttonGeri);
		
		JLabel labelTarifSecenekleri = new JLabel("Tarif Seçenekleri");
		labelTarifSecenekleri.setFont(new Font("Constantia", Font.BOLD | Font.ITALIC, 40));
		labelTarifSecenekleri.setBounds(250, 10, 320, 55);
		contentPane.add(labelTarifSecenekleri);
		
		JLabel labelTarifAd = new JLabel("Tarif Adı");
		labelTarifAd.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelTarifAd.setBounds(10, 60, 100, 25);
		contentPane.add(labelTarifAd);
		
		textFieldTarifAd = new JTextField();
		textFieldTarifAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldTarifAd.setColumns(10);
		textFieldTarifAd.setBounds(10, 85, 157, 21);
		contentPane.add(textFieldTarifAd);		
		textFieldTarifAd.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	tarifleriListele();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	tarifleriListele();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	tarifleriListele();
	        }
	    });
		
		JLabel labelMalzemeSecimi = new JLabel("Malzeme Seçimi");
		labelMalzemeSecimi.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelMalzemeSecimi.setBounds(10, 115, 130, 25);
		contentPane.add(labelMalzemeSecimi);
		
		textFieldMalzemeSecimi = new JTextField();
		textFieldMalzemeSecimi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMalzemeSecimi.setColumns(10);
		textFieldMalzemeSecimi.setBounds(10, 140, 157, 21);
		contentPane.add(textFieldMalzemeSecimi);
		textFieldMalzemeSecimi.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	malzemeBul();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	malzemeBul();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	malzemeBul();
	        }
	    });
		
		JScrollPane scrollPaneMalzemeler = new JScrollPane(malzemePanel);
		scrollPaneMalzemeler.setBounds(10, 170, 157, 100);
		contentPane.add(scrollPaneMalzemeler);
		
		JButton buttonMalzemeyeGoreAra = new JButton("Ara");
		buttonMalzemeyeGoreAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				malzemeFiltreleme();
			}
		});
		buttonMalzemeyeGoreAra.setForeground(Color.WHITE);
		buttonMalzemeyeGoreAra.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		buttonMalzemeyeGoreAra.setBackground(new Color(45, 199, 110));
		buttonMalzemeyeGoreAra.setBounds(10, 275, 157, 21);
		contentPane.add(buttonMalzemeyeGoreAra);
		
		JLabel labelHazırlamaSuresi = new JLabel("Hazırlama Süresi");
		labelHazırlamaSuresi.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelHazırlamaSuresi.setBounds(10, 305, 130, 25);
		contentPane.add(labelHazırlamaSuresi);
		
		radioButtonHizli = new JRadioButton("Hızlı");
		radioButtonHizli.setFont(new Font("Arial", Font.PLAIN, 15));
		radioButtonHizli.setBounds(6, 330, 57, 21);
		contentPane.add(radioButtonHizli);
		
		radioButtonYavas = new JRadioButton("Yavaş");
		radioButtonYavas.setFont(new Font("Arial", Font.PLAIN, 15));
		radioButtonYavas.setBounds(70, 330, 72, 21);
		contentPane.add(radioButtonYavas);
		
		buttonGroupSure = new ButtonGroup();
		buttonGroupSure.add(radioButtonHizli);
		buttonGroupSure.add(radioButtonYavas);
        
        radioButtonHizli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	hazirlamaSuresiFiltreleme();
            }
        });

        radioButtonYavas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	hazirlamaSuresiFiltreleme();
            }
        });
		
		JLabel labelMaliyet = new JLabel("Tarif Maliyeti");
		labelMaliyet.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelMaliyet.setBounds(10, 355, 130, 25);
		contentPane.add(labelMaliyet);
		
		radioButtonArtan = new JRadioButton("Artan");
		radioButtonArtan.setFont(new Font("Arial", Font.PLAIN, 15));
		radioButtonArtan.setBounds(6, 380, 61, 21);
		contentPane.add(radioButtonArtan);
		
		radioButtonAzalan = new JRadioButton("Azalan");
		radioButtonAzalan.setFont(new Font("Arial", Font.PLAIN, 15));
		radioButtonAzalan.setBounds(70, 380, 70, 21);
		contentPane.add(radioButtonAzalan);
		
		buttonGroupMaliyet = new ButtonGroup();
		buttonGroupMaliyet.add(radioButtonArtan);
		buttonGroupMaliyet.add(radioButtonAzalan);
		
		radioButtonArtan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	maliyetFiltreleme();
            }
        });

		radioButtonAzalan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	maliyetFiltreleme();
            }
        });
		
		textFieldMaliyetMin = new JTextField();
		textFieldMaliyetMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMaliyetMin.setColumns(10);
		textFieldMaliyetMin.setBounds(10, 410, 50, 21);
		contentPane.add(textFieldMaliyetMin);
		textFieldMaliyetMin.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }
	    });
		
		textFieldMaliyetMax = new JTextField();
		textFieldMaliyetMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMaliyetMax.setColumns(10);
		textFieldMaliyetMax.setBounds(76, 410, 50, 21);
		contentPane.add(textFieldMaliyetMax);
		textFieldMaliyetMax.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	maliyetFiltrelemeText();
	        }
	    });
		
		
		JLabel labelCizgi = new JLabel("-");
		labelCizgi.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labelCizgi.setBounds(62, 410, 15, 15);
		contentPane.add(labelCizgi);
		
		JLabel labelKategori = new JLabel("Tarif Kategori");
		labelKategori.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelKategori.setBounds(10, 441, 130, 25);
		contentPane.add(labelKategori);
		
		comboBoxKategori = new JComboBox<String>();
		comboBoxKategori.setFont(new Font("Arial", Font.BOLD, 10));
		comboBoxKategori.setModel(new DefaultComboBoxModel<String>(new String[] {"Ana Yemek", "Çorba", "Meze", "Tatlı", "İçecek"}));
		comboBoxKategori.setBounds(10, 465, 130, 21);
		contentPane.add(comboBoxKategori);
		comboBoxKategori.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        kategoriFiltreleme();
		    }
		});
		
		
		JLabel labelMalzemeSayısı = new JLabel("Malzeme Sayısı");
		labelMalzemeSayısı.setFont(new Font("Dubai Light", Font.BOLD, 17));
		labelMalzemeSayısı.setBounds(10, 496, 130, 25);
		contentPane.add(labelMalzemeSayısı);
		
		textFieldMalzemeSayisiMin = new JTextField();
		textFieldMalzemeSayisiMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMalzemeSayisiMin.setColumns(10);
		textFieldMalzemeSayisiMin.setBounds(10, 520, 50, 21);
		contentPane.add(textFieldMalzemeSayisiMin);
		textFieldMalzemeSayisiMin.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }
	    });
		
		textFieldMalzemeSayisiMax = new JTextField();
		textFieldMalzemeSayisiMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMalzemeSayisiMax.setColumns(10);
		textFieldMalzemeSayisiMax.setBounds(76, 520, 50, 21);
		contentPane.add(textFieldMalzemeSayisiMax);
		textFieldMalzemeSayisiMax.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        	malzemeSayisiFiltrelemeText();
	        }
	    });
		
		JLabel labelCizgi2 = new JLabel("-");
		labelCizgi2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labelCizgi2.setBounds(62, 520, 15, 15);
		contentPane.add(labelCizgi2);
		
		tarifleriListele();
		malzemeMenu();
		
		JButton buttonTabloYenile = new JButton("Yenile");
		buttonTabloYenile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldTarifAd.setText("");
				textFieldMalzemeSecimi.setText("");
				textFieldMaliyetMin.setText("");
				textFieldMaliyetMax.setText("");
				textFieldMalzemeSayisiMin.setText("");
				textFieldMalzemeSayisiMax.setText("");
				radioButtonHizli.setSelected(false);
			    radioButtonYavas.setSelected(false);
			    radioButtonArtan.setSelected(false);
			    radioButtonAzalan.setSelected(false);
			    buttonGroupSure.clearSelection();
			    buttonGroupMaliyet.clearSelection();
			    comboBoxKategori.setSelectedIndex(0);
				malzemeMenu();
				DatabaseManager dbManager = new DatabaseManager();
				filtrelenmisTarifler = dbManager.getTarifler();
				tarifleriListele();
			}
		});
	    buttonTabloYenile.setForeground(Color.WHITE);
	    buttonTabloYenile.setFont(new Font("Dubai Light", Font.BOLD, 15));
	    buttonTabloYenile.setBackground(new Color(46, 132, 244));
	    buttonTabloYenile.setBounds(260, 520, 95, 20);
	    contentPane.add(buttonTabloYenile);
		
	}
	
	private void tarifleriListele()
	{
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = filtrelenmisTarifler.isEmpty() ? dbManager.getTarifler() : filtrelenmisTarifler;

	    String aramaTerimi = textFieldTarifAd.getText().toLowerCase();
	    List<Tarif> filtreliTarifler = new ArrayList<>();
	    for(Tarif tarif : tarifler)
	    {
	        if(tarif.getTarifAdi().toLowerCase().contains(aramaTerimi))
	        {
	            filtreliTarifler.add(tarif);
	        }
	    }
	    
	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    Object[][] data = new Object[filtreliTarifler.size()][5];
	    
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();

	    for(int i = 0; i < filtreliTarifler.size(); i++)
	    {
	        Tarif tarif = filtreliTarifler.get(i);
	        data[i][0] = tarif.getTarifAdi();
	        data[i][1] = tarif.getKategori();
	        data[i][2] = tarif.getHazirlamaSuresi() + " dk";
	        data[i][3] = String.format("%.2f TL", dbManager.tarifMaliyet(tarif));
	        data[i][4] = "%";

	        tarifYeterlilikDurumu.add(tarifRenk(tarif));
	    }

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            if (tarifYeterlilikDurumu.get(row))
	            {
	                c.setBackground(new Color(45, 199, 110));
	            }
	            else {
	                c.setBackground(new Color(255, 85, 76));
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	       
	}
	
	public void malzemeMenu()
	{
	    malzemePanel.removeAll();
	    malzemeMap.clear();

	    DatabaseManager dbManager = new DatabaseManager();
	    List<Malzeme> malzemeler = dbManager.getMalzemeler();

	    malzemePanel.setLayout(new GridLayout(0, 1));
	    for(Malzeme malzeme : malzemeler)
	    {
	        JCheckBox checkBox = new JCheckBox(malzeme.getMalzemeAdi());
	        // malzemeMapte var mı yok mu kontrol
	        checkBox.setSelected(malzemeMap.getOrDefault(malzeme.getMalzemeAdi(), false));
	        // checkbox seçilinde malzemeMap'e koy
	        checkBox.addActionListener(e -> {
	            malzemeMap.put(malzeme.getMalzemeAdi(), checkBox.isSelected());
	        });
	        malzemePanel.add(checkBox);
	    }

	    malzemePanel.revalidate();
	    malzemePanel.repaint();
	}
	
	public void malzemeBul()
	{
	    String arananMalzeme = textFieldMalzemeSecimi.getText().toLowerCase();
	    malzemePanel.removeAll();

	    DatabaseManager dbManager = new DatabaseManager();
	    List<Malzeme> malzemeler = dbManager.getMalzemeler();

	    malzemePanel.setLayout(new GridLayout(0, 1));
	    int gosterilenMalzemeSayısı = 0;

	    for(Malzeme malzeme : malzemeler)
	    {
	        if (malzeme.getMalzemeAdi().toLowerCase().contains(arananMalzeme))
	        {
	            JCheckBox checkBox = new JCheckBox(malzeme.getMalzemeAdi());

	            // Eğer malzeme daha önce seçildiyse durumu koru
	            if(malzemeMap.containsKey(malzeme.getMalzemeAdi()) && malzemeMap.get(malzeme.getMalzemeAdi()))
	            {
	                checkBox.setSelected(true);
	            }

	            // Seçim durumunu güncelle
	            checkBox.addActionListener(e -> {
	                malzemeMap.put(malzeme.getMalzemeAdi(), checkBox.isSelected());
	            });

	            malzemePanel.add(checkBox);
	            gosterilenMalzemeSayısı++;
	        }
	    }

	    int gosterilenSatırSayısı = 4;
	    int doldurulacakSatır = gosterilenSatırSayısı - gosterilenMalzemeSayısı;

	    for(int i = 0; i < doldurulacakSatır; i++)
	    {
	        malzemePanel.add(new JLabel(""));
	    }

	    malzemePanel.revalidate();
	    malzemePanel.repaint();
	}

	public void malzemeFiltreleme()
	{
		DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();
	    List<Malzeme> malzemeler = dbManager.getMalzemeler();
	    List<Malzeme> malzemelerSecilmis = new ArrayList<Malzeme>();
		
	    for(Map.Entry<String, Boolean> entry : malzemeMap.entrySet()){
	        if (entry.getValue())
	        {
	            String malzemeAd = entry.getKey();
	            for(Malzeme malzeme : malzemeler)
	            {
	                if(malzeme.getMalzemeAdi().equals(malzemeAd))
	                {
	                    malzemelerSecilmis.add(malzeme);
	                }
	            }
	        }
	    }
		
		String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    List<Object[]> filtreliTarifler = new ArrayList<>();
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();
	    
	    for(Tarif tarif : tarifler)
	    {
	        int eslesenMalzemeSayisi = 0;
	        boolean isValid = false;
	        List<Malzeme> tarifMalzemeleri = dbManager.getTarifinMalzemeleri(tarif);
	        
	        for(Malzeme tarifMalzeme : tarifMalzemeleri)
	        {
                for(Malzeme malzeme : malzemelerSecilmis)
                {
                    if(tarifMalzeme.getMalzemeID() == malzeme.getMalzemeID())
                    {
                        eslesenMalzemeSayisi++;
                        isValid = true;
                        break;
                    }
                }
	        }

	        if(isValid)
	        {
	            int eslesmeYuzdesi = (100 * eslesenMalzemeSayisi) / tarifMalzemeleri.size();
	            filtreliTarifler.add(new Object[]{
	                    tarif.getTarifAdi(),
	                    tarif.getKategori(),
	                    tarif.getHazirlamaSuresi() + " dk",
	                    String.format("%.2f TL", dbManager.tarifMaliyet(tarif)),
	                    "%" + eslesmeYuzdesi
	            });
	            tarifYeterlilikDurumu.add(tarifRenk(tarif));
	        }
	    }
	    
	    filtreliTarifler.sort((a, b) -> {
	        int yuzdeA = Integer.parseInt(a[4].toString().replace("%", ""));
	        int yuzdeB = Integer.parseInt(b[4].toString().replace("%", ""));
	        return Integer.compare(yuzdeB, yuzdeA);
	    });
	        

	    Object[][] data = new Object[filtreliTarifler.size()][5];
	    for(int i = 0; i < filtreliTarifler.size(); i++)
	    {
	        data[i] = filtreliTarifler.get(i);
	    }
	    
	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            if (row < tarifYeterlilikDurumu.size() && tarifYeterlilikDurumu.get(row)) {
	                c.setBackground(new Color(45, 199, 110));
	            } else {
	                c.setBackground(new Color(255, 85, 76));
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
		
		tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
            	int selectedRow = tarifTable.getSelectedRow();
                if (selectedRow != -1)
                {
                    
                	Object[] seciliTarifBilgileri = filtreliTarifler.get(selectedRow);
                    String seciliTarifAdi = seciliTarifBilgileri[0].toString();


                    Tarif seciliTarif = null;
                    for(Tarif tarif : tarifler)
                    {
                        if(tarif.getTarifAdi().equals(seciliTarifAdi))
                        {
                            seciliTarif = tarif;
                            break;
                        }
                    }             	
                    if (seciliTarif != null)
                    {
                    	List<Malzeme> tarifMalzemeleri = dbManager.getTarifinMalzemeleri(seciliTarif);
                        
                        int arananMalzemeSayisi = malzemelerSecilmis.size();
                        StringBuilder arananMalzemeler = new StringBuilder();
                        for(Malzeme malzeme : malzemelerSecilmis)
                        {
                            arananMalzemeler.append(malzeme.getMalzemeAdi()).append(", ");
                        }
                        if(arananMalzemeler.length() > 0)
                        	arananMalzemeler.setLength(arananMalzemeler.length()-2);  //virgülü siler
                        
                        int tarifMalzemeSayisi = tarifMalzemeleri.size();
                        StringBuilder tarifMalzemeler = new StringBuilder();
                        for(Malzeme malzeme : tarifMalzemeleri)
                        {
                            tarifMalzemeler.append(malzeme.getMalzemeAdi()).append(", ");
                        }
                        if(tarifMalzemeler.length() > 0)
                        	tarifMalzemeler.setLength(tarifMalzemeler.length()-2);
                        
                        StringBuilder eslesenMalzemeler = new StringBuilder();
                        int eslesenMalzemeSayisi = 0;
                        for(Malzeme malzemeSecilmis : malzemelerSecilmis)
                        {
                            for(Malzeme malzemeTarif : tarifMalzemeleri)
                            {
                                if (malzemeSecilmis.getMalzemeID() == malzemeTarif.getMalzemeID())
                                {
                                    eslesenMalzemeler.append(malzemeSecilmis.getMalzemeAdi()).append(", ");
                                    eslesenMalzemeSayisi++;
                                    break;
                                }
                            }
                        }
                        if(eslesenMalzemeler.length() > 0)
                        	eslesenMalzemeler.setLength(eslesenMalzemeler.length() - 2);
                        
                        String mesaj = String.format(
                            "Aranan malzeme sayısı (%d): %s\n" +
                            "Tarifteki malzeme sayısı (%d): %s\n" +
                            "Eşleşen malzemeler (%d): %s",
                            arananMalzemeSayisi, arananMalzemeler.toString(),
                            tarifMalzemeSayisi, tarifMalzemeler.toString(),
                            eslesenMalzemeSayisi, eslesenMalzemeler.toString()
                        );
                        
                        JOptionPane.showMessageDialog(null, mesaj, "Tarif Detayları", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                }
            }
        });
	
	}
	
	public void hazirlamaSuresiFiltreleme()
	{   
		DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();

	    if (radioButtonHizli.isSelected()) {
	        tarifler.sort(Comparator.comparingInt(Tarif::getHazirlamaSuresi));
	    } else if (radioButtonYavas.isSelected()) {
	        tarifler.sort((t1, t2) -> Integer.compare(t2.getHazirlamaSuresi(), t1.getHazirlamaSuresi()));
	    }

	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    Object[][] data = new Object[tarifler.size()][5];
	    
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();
	    for(int i = 0; i < tarifler.size(); i++)
	    {
	        Tarif tarif = tarifler.get(i);
	        data[i][0] = tarif.getTarifAdi();
	        data[i][1] = tarif.getKategori();
	        data[i][2] = tarif.getHazirlamaSuresi() + " dk";
	        data[i][3] = String.format("%.2f TL", dbManager.tarifMaliyet(tarif));
	        data[i][4] = "%";

	        tarifYeterlilikDurumu.add(tarifRenk(tarif));
	    }

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            if
	            (tarifYeterlilikDurumu.get(row))
	            {
	                c.setBackground(new Color(45, 199, 110)); // Yeterli malzeme varsa yeşil
	            }
	            else
	            {
	                c.setBackground(new Color(255, 85, 76)); // Yeterli malzeme yoksa kırmızı
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	}

	public void maliyetFiltreleme()
	{		
		DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();
	    
		String sureTipi = "";
        if(radioButtonArtan.isSelected())
            sureTipi = "Artan";
        else if(radioButtonAzalan.isSelected())
            sureTipi = "Azalan";
        
        Map<Tarif, Float> tarifMaliyetMap = new HashMap<>();
        for(Tarif tarif : tarifler)
        {
            tarifMaliyetMap.put(tarif, dbManager.tarifMaliyet(tarif));
        }
        
        if(sureTipi.equals("Artan")) {
            tarifler.sort(Comparator.comparing(tarifMaliyetMap::get));
        } else {
            tarifler.sort(Comparator.comparing(tarifMaliyetMap::get).reversed());
        }
        		          
	    
	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    Object[][] data = new Object[tarifler.size()][5];
	    
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();
	  
	    for(int i = 0; i < tarifler.size(); i++)
	    {
	        Tarif tarif = tarifler.get(i);
	        data[i][0] = tarif.getTarifAdi();
	        data[i][1] = tarif.getKategori();
	        data[i][2] = tarif.getHazirlamaSuresi() + " dk";
	        data[i][3] = String.format("%.2f TL", dbManager.tarifMaliyet(tarif));
	        data[i][4] = "%";
	        
	        tarifYeterlilikDurumu.add(tarifRenk(tarif));	        
	    }

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            if(tarifYeterlilikDurumu.get(row))
	            {
	                c.setBackground(new Color(45, 199, 110));
	            }
	            else {
	                c.setBackground(new Color(255, 85, 76));
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	}

	public void maliyetFiltrelemeText()
	{
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();

	    String sureTipi = "";
	    if (radioButtonArtan.isSelected()) {
	        sureTipi = "Artan";
	    } else if (radioButtonAzalan.isSelected()) {
	        sureTipi = "Azalan";
	    }

	    Map<Tarif, Float> tarifMaliyetMap = new HashMap<>();
	    for (Tarif tarif : tarifler) {
	        tarifMaliyetMap.put(tarif, dbManager.tarifMaliyet(tarif));
	    }

	    if (sureTipi.equals("Artan")) {
	        tarifler.sort(Comparator.comparing(tarifMaliyetMap::get));
	    }
	    else if(sureTipi.equals("Azalan")){
	        tarifler.sort(Comparator.comparing(tarifMaliyetMap::get).reversed());
	    }

	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    List<Object[]> filtreliTarifler = new ArrayList<>();
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();

	    String maliyetMinText = textFieldMaliyetMin.getText();
	    String maliyetMaxText = textFieldMaliyetMax.getText();

	    for(Tarif tarif : tarifler)
	    {
	        float tarifMaliyet = dbManager.tarifMaliyet(tarif);
	        boolean isValid = false;
	        try {
	            float maliyetMin = maliyetMinText.isEmpty() ? Float.NEGATIVE_INFINITY : Float.parseFloat(maliyetMinText);
	            float maliyetMax = maliyetMaxText.isEmpty() ? Float.POSITIVE_INFINITY : Float.parseFloat(maliyetMaxText);
	            if(tarifMaliyet > maliyetMin && tarifMaliyet < maliyetMax)
	            {
	                isValid = true;
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Geçersiz maliyet değeri!" + e.getMessage());
	        }

	        if(isValid)
	        {
	            filtreliTarifler.add(new Object[]{
	                    tarif.getTarifAdi(),
	                    tarif.getKategori(),
	                    tarif.getHazirlamaSuresi() + " dk",
	                    String.format("%.2f TL", tarifMaliyet),
	                    "%"
	            });
	            tarifYeterlilikDurumu.add(tarifRenk(tarif));
	        }
	    }

	    Object[][] data = new Object[filtreliTarifler.size()][5];
	    for(int i = 0; i < filtreliTarifler.size(); i++)
	    {
	        data[i] = filtreliTarifler.get(i);
	    }
	    

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            if (row < tarifYeterlilikDurumu.size() && tarifYeterlilikDurumu.get(row)) {
	                c.setBackground(new Color(45, 199, 110));
	            } else {
	                c.setBackground(new Color(255, 85, 76));
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	}

	public void kategoriFiltreleme()
	{   
		DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();	    

	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    //out of bounds olduğu için dinamik dizi yapıyoruz sonra Object dizisine aktarıyoruz
	    List<Object[]> filtreliTarifler = new ArrayList<>();	    
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();
	    for(int i = 0; i < tarifler.size(); i++)
	    {
	        Tarif tarif = tarifler.get(i);	           
	        if(tarif.getKategori().equals(comboBoxKategori.getSelectedItem()))
	        {
	            filtreliTarifler.add(new Object[]{
	                    tarif.getTarifAdi(),
	                    tarif.getKategori(),
	                    tarif.getHazirlamaSuresi() + " dk",
	                    String.format("%.2f TL", dbManager.tarifMaliyet(tarif)),
	                    "%"
	            });
	            tarifYeterlilikDurumu.add(tarifRenk(tarif));
	        }	                
	    }
	    
	    Object[][] data = new Object[filtreliTarifler.size()][5];
	    for(int i = 0; i < filtreliTarifler.size(); i++)
	    {
	        data[i] = filtreliTarifler.get(i);
	    }

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            if
	            (tarifYeterlilikDurumu.get(row))
	            {
	                c.setBackground(new Color(45, 199, 110)); // Yeterli malzeme varsa yeşil
	            }
	            else
	            {
	                c.setBackground(new Color(255, 85, 76)); // Yeterli malzeme yoksa kırmızı
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	}

	public void malzemeSayisiFiltrelemeText()
	{
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();
	    List<TarifMalzeme> tarifMalzemeler = dbManager.getTarifMalzeme();


	    String[] columnNames = {"Tarif Adı", "Kategori", "Süre", "Maliyet", "Eşleşme Yüzdesi"};
	    List<Object[]> filtreliTarifler = new ArrayList<>();
	    List<Boolean> tarifYeterlilikDurumu = new ArrayList<>();

	    String malzemeMinText = textFieldMalzemeSayisiMin.getText();
	    String malzemeMaxText = textFieldMalzemeSayisiMax.getText();

	    for(Tarif tarif : tarifler)
	    {
	    	int malzemeSayısı = 0;
	    	for(TarifMalzeme tarifMalzeme : tarifMalzemeler)
	    	{
	    		if(tarif.getTarifID()==tarifMalzeme.getTarifID())
	    			malzemeSayısı++;
	    	}
	        boolean isValid = false;
	        try {
	            float malzemeMin = malzemeMinText.isEmpty() ? Float.NEGATIVE_INFINITY : Float.parseFloat(malzemeMinText);
	            float malzemeMax = malzemeMaxText.isEmpty() ? Float.POSITIVE_INFINITY : Float.parseFloat(malzemeMaxText);
	            if(malzemeSayısı > malzemeMin && malzemeSayısı < malzemeMax)
	            {
	                isValid = true;
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Geçersiz maliyet değeri!" + e.getMessage());
	        }

	        if(isValid)
	        {
	            filtreliTarifler.add(new Object[]{
	                    tarif.getTarifAdi(),
	                    tarif.getKategori(),
	                    tarif.getHazirlamaSuresi() + " dk",
	                    String.format("%.2f TL", dbManager.tarifMaliyet(tarif)),
	                    "%"
	            });
	            tarifYeterlilikDurumu.add(tarifRenk(tarif));
	        }
	    }

	    Object[][] data = new Object[filtreliTarifler.size()][5];
	    for(int i = 0; i < filtreliTarifler.size(); i++)
	    {
	        data[i] = filtreliTarifler.get(i);
	    }

	    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tarifTable = new JTable(tableModel);
	    tarifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    tarifTable.getColumnModel().getColumn(0).setPreferredWidth(120);
	    tarifTable.getColumnModel().getColumn(1).setPreferredWidth(75);
	    tarifTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    tarifTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tarifTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tarifTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    tarifTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            if (row < tarifYeterlilikDurumu.size() && tarifYeterlilikDurumu.get(row)) {
	                c.setBackground(new Color(45, 199, 110));
	            } else {
	                c.setBackground(new Color(255, 85, 76));
	            }
	            return c;
	        }
	    });

	    if (scrollPaneTarifler != null) {
	        contentPane.remove(scrollPaneTarifler);
	    }
	    scrollPaneTarifler = new JScrollPane(tarifTable);
	    scrollPaneTarifler.setBounds(260, 85, 510, 420);
	    contentPane.add(scrollPaneTarifler);

	    contentPane.revalidate();
	    contentPane.repaint();

	    filtrelenmisTarifler = tarifler;
	    
	    tarifTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                eksikMalzemeMaliyet();
            }
        });
	}

	public void eksikMalzemeMaliyet()
	{
		int selectedRow = tarifTable.getSelectedRow();
        if(selectedRow != -1)
        {
        	DatabaseManager dbManager = new DatabaseManager();
        	List<Tarif> tarifler = dbManager.getTarifler();
        	List<Malzeme> malzemeler = dbManager.getMalzemeler();
        	List<TarifMalzeme> tarifMalzemeler = dbManager.getTarifMalzeme();
        	Map<Malzeme, Float> eksikMalzemeler = new HashMap<>();
            Tarif tarif = tarifler.get(selectedRow);
            
            float toplamMaliyet = 0.0f;                                  
            for(TarifMalzeme tarifMalzeme : tarifMalzemeler)
            {
            	if(tarifMalzeme.getTarifID()==tarif.getTarifID())
            	{
            		for(Malzeme malzeme : malzemeler)
            		{
            			if(tarifMalzeme.getMalzemeID()==malzeme.getMalzemeID())
            			{
            				float malzemeToplamMiktar = Float.parseFloat(malzeme.getToplamMiktar().replaceAll("[^0-9.]", ""));
            				float tarifMalzemeMiktar = tarifMalzeme.getMalzemeMiktar();
            				float malzemeBirimFiyat = malzeme.getBirimFiyat();
            				if(malzemeToplamMiktar<tarifMalzemeMiktar)
            				{
            					float maliyet = (tarifMalzemeMiktar-malzemeToplamMiktar)*malzemeBirimFiyat;
            					toplamMaliyet += maliyet;
            					eksikMalzemeler.put(malzeme, maliyet);
            				}
            			}
            		}
            	}
            }
            if(toplamMaliyet>0)
            {
            	StringBuilder message = new StringBuilder();
                for(Map.Entry<Malzeme, Float> entry : eksikMalzemeler.entrySet())
                {
                    Malzeme malzeme = entry.getKey();
                    Float maliyet = entry.getValue();
                    message.append(malzeme.getMalzemeAdi()).append(": ")
                           .append(String.format("%.2f TL", maliyet)).append("\n");
                }
                message.append("----------------------------------\n"+"Toplam Maliyet: ").append(String.format("%.2f TL", toplamMaliyet));

                JOptionPane.showMessageDialog(this,
                    message.toString(),
                    "Malzeme Maliyeti",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
	}

	public boolean tarifRenk(Tarif tarif)
	{
		DatabaseManager dbManager = new DatabaseManager();
	    List<Malzeme> malzemeler = dbManager.getMalzemeler();
	    List<TarifMalzeme> tarifMalzemeler = dbManager.getTarifMalzeme();

	    for(TarifMalzeme tarifMalzeme : tarifMalzemeler)
	    {
	        if(tarifMalzeme.getTarifID() == tarif.getTarifID())
	        {
	            for(Malzeme malzeme : malzemeler)
	            {
	                if(tarifMalzeme.getMalzemeID() == malzeme.getMalzemeID())
	                {
	                    float toplamMiktar = Float.parseFloat(malzeme.getToplamMiktar().replaceAll("[^0-9.]", ""));
	                    float malzemeMiktar = tarifMalzeme.getMalzemeMiktar();
	                    if (toplamMiktar < malzemeMiktar)
	                    {
	                        return false; 
	                    }
	                }
	            }
	        }
	    }
	    return true;
		
	}
	
}
