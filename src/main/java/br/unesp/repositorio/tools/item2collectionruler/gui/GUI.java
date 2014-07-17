package br.unesp.repositorio.tools.item2collectionruler.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import br.unesp.repositorio.tools.item2collectionruler.Item2Collection;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties config;
	private File configFile;
	private JTextField tfRules;
	private JTextField tfIn;
	private JTextField tfOut;
	private JTextField tfColumn;
	private JTextField tfDefault;

	public GUI() {

		initializeComponent();
		initializeConfig();



	}

	private void initializeComponent() {
		setTitle("Item2CollectionRuler");
		setBounds(0,0,640,480);
		getContentPane().setLayout(null);

		tfRules = new JTextField();
		tfIn = new JTextField();
		tfOut = new JTextField();
		tfColumn = new JTextField();
		tfDefault = new JTextField();
		JButton btnFindXML = new JButton("...");
		JButton btnFindCSVin = new JButton("...");
		JButton btnFindCSVout = new JButton("...");
		JButton btnOrganize = new JButton("Organize");
		JButton btnUnesp = new JButton("Visit our repository");
		JLabel logo;
		try{
			logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("resources/logo.png")));
		}catch(NullPointerException e){
			logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("logo.png")));
		}
		

		addLabel("rules.xml: ",10,10);
		tfRules.setBounds(90,10,350,25);
		btnFindXML.setBounds(450,10,30,25);
		addLabel("in.csv: ",10,35);
		tfIn.setBounds(90,35,350,25);
		btnFindCSVin.setBounds(450,35,30,25);
		addLabel("out.csv: ",10,60);
		tfOut.setBounds(90,60,350,25);
		btnFindCSVout.setBounds(450,60,30,25);
		addLabel("csv column: ",10,85);
		tfColumn.setBounds(90,85,350,25);
		addLabel("default: ",10,110);
		tfDefault.setBounds(90,110,350,25);

		btnOrganize.setBounds(270,400,100,30);
		addLabel("JVM Max memory: "+Runtime.getRuntime().maxMemory()/(1024*1024) +" MB",420,400);
		
		logo.setBounds(25,180,150,212);
		btnUnesp.setBounds(10,400,180,25);
		
		
		addLabel("Instructions!",200,180);
		addLabel("1 - Please write your xml map following the namespace in:",200,205);
		addLabel("http://base.repositorio.unesp.br/XMLSchema/Item2CollectionRuler",200,220);
		addLabel("2 - Use a dspace generated csv or equivalent:",200,245);
		addLabel("https://wiki.duraspace.org/display/DSDOC4x/Batch+Metadata+Editing",200,260);
		addLabel("3 - Large files need more JVM max memory.",200,285);
		

		getContentPane().add(tfRules);
		getContentPane().add(tfIn);
		getContentPane().add(tfOut);
		getContentPane().add(tfColumn);
		getContentPane().add(tfDefault);
		getContentPane().add(btnFindXML);
		getContentPane().add(btnFindCSVin);
		getContentPane().add(btnFindCSVout);
		getContentPane().add(btnOrganize);
		getContentPane().add(btnUnesp);
		getContentPane().add(logo);


		btnFindXML.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Select xml", "xml"));
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(true);
				fc.showDialog(null, "Select");
				if(fc.getSelectedFile()!=null){
					tfRules.setText(fc.getSelectedFile().getAbsolutePath());
				}

			}
		});

		btnFindCSVin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Select csv", "csv"));
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(true);
				fc.showDialog(null, "Select");
				if(fc.getSelectedFile()!=null){
					tfIn.setText(fc.getSelectedFile().getAbsolutePath());
				}

			}
		});

		btnFindCSVout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Select csv", "csv"));
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(true);
				fc.showSaveDialog(null);
				if(fc.getSelectedFile()!=null){
					tfOut.setText(fc.getSelectedFile().getAbsolutePath());
				}

			}
		});

		btnOrganize.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mapConfigs();

				Item2Collection i2c = new Item2Collection(
						new File(config.getProperty("csv.in.path")),
						new File(config.getProperty("csv.out.path")),
						new File(config.getProperty("xml.path")),
						config.getProperty("handle.default"),
						config.getProperty("csv.column"));

				try {
					i2c.organizeItensOnCollections();
					JOptionPane.showMessageDialog(GUI.this, "Info: Organized","Sucess",JOptionPane.INFORMATION_MESSAGE);
				} catch (JAXBException e) {
					JOptionPane.showMessageDialog(GUI.this, "Error: Cannot read xml file",e.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(GUI.this, "Error: Cannot read or write files",e.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
				}
			}


		});
		
		btnUnesp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("http://repositorio.unesp.br/"));
				} catch (Exception e) {
					
				} 
				
			}
		});

	}

	private void initializeConfig() {
		config = new Properties();
		configFile = new File("i2cr.config");
		try {
			config.load(new FileReader(configFile));
			tfRules.setText(config.getProperty("xml.path"));
			tfIn.setText(config.getProperty("csv.in.path"));
			tfOut.setText(config.getProperty("csv.out.path"));
			tfColumn.setText(config.getProperty("csv.column"));
			tfDefault.setText(config.getProperty("handle.default"));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error: Cannot found config file, a new file will be created",e.getClass().getSimpleName(),JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error: Cannot read config file",e.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosed(WindowEvent arg0) {
				saveConfig();

			}

		});

	}

	private void addLabel(String text, int x, int y){
		JLabel jLabel = new JLabel(text);
		getContentPane().add(jLabel);
		jLabel.setBounds(x, y,text.length()*7,20);


	}

	private void saveConfig(){
		try {
			config.store(new FileWriter(configFile), "Item2CollectionRuler config file");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error: Unable to save config file",e.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mapConfigs() {
		config.setProperty("xml.path", tfRules.getText());
		config.setProperty("csv.in.path", tfIn.getText());
		config.setProperty("csv.out.path", tfOut.getText());
		config.setProperty("csv.column", tfColumn.getText());
		config.setProperty("handle.default", tfDefault.getText());
	}
}
