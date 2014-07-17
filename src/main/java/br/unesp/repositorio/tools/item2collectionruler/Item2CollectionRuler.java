package br.unesp.repositorio.tools.item2collectionruler;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.unesp.repositorio.tools.item2collectionruler.gui.GUI;

public class Item2CollectionRuler {

	private static Options options;

	public static void main(String[] args) throws ParseException {

		options = new Options();

		options.addOption("h", "help", false, "print this message.");
		options.addOption("H", "handle", true, "default handle when not found collection.");
		options.addOption("c", "collumn", true, "collumn to verify.");
		options.addOption("i", "input-file", true, "defines input csv file.");
		options.addOption("o", "output-file", true, "defines output csv file.");
		options.addOption("m", "map-file", true, "defines xml map file.");


		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse( options, args);

		String handle = "12345678/1";
		String collumn = "dc.description.affiliation[]";
		String input = "";
		String output = "";
		String map = "";

		//If no command def
		if(cmd.getOptions().length==0){
			new GUI().setVisible(true);
		}else{
			try{
				if(cmd.hasOption("h")){
					showHelp();
				}else{
					if(!cmd.hasOption("i")||!cmd.hasOption("o")||!cmd.hasOption("m")){
						System.err.println("Error: Input file, Output file and Map file are required!");
						showHelp();
					}else{
						input = cmd.getOptionValue("i");
						output = cmd.getOptionValue("o");
						map = cmd.getOptionValue("m");
						if(cmd.hasOption("H")){
							handle = cmd.getOptionValue("H");
						}
						if(cmd.hasOption("c")){
							collumn = cmd.getOptionValue("c");
						}
						System.out.println("Info: Organizing, this process may take a while.");
						Item2Collection i2c = new Item2Collection(new File(input), new File(output), new File(map), handle, collumn);
						i2c.organizeItensOnCollections();
						System.out.println("Info: Organized");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				showHelp();
			}

		}

	}

	private static void showHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Item2CollectionRuler", options);

	}

}
