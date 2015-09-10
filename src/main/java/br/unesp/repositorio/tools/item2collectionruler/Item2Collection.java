package br.unesp.repositorio.tools.item2collectionruler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import com.google.common.base.Joiner;

import br.unesp.repositorio.base.xmlschema.item2collectionruler.Campus;
import br.unesp.repositorio.base.xmlschema.item2collectionruler.Department;
import br.unesp.repositorio.base.xmlschema.item2collectionruler.ObjectFactory;
import br.unesp.repositorio.base.xmlschema.item2collectionruler.University;
import br.unesp.repositorio.tools.item2collectionruler.tools.TextUtils;

public class Item2Collection {
	private File inputCsv;
	private File outputCsv;
	private File mapXml;
	private String defaultHandle;
	private String metadataName;


	public Item2Collection(File inputCsv, File outputCsv, File mapXml,
			String defaultHandle, String metadataName) {
		super();
		this.inputCsv = inputCsv;
		this.outputCsv = outputCsv;
		this.mapXml = mapXml;
		this.defaultHandle = defaultHandle;
		this.metadataName = metadataName;
	}

	@SuppressWarnings("unchecked")
	public void organizeItensOnCollections() throws JAXBException, IOException{
		University university = loadUniversityMap();
		CSVParser csvParser = new CSVParser(new InputStreamReader(new FileInputStream(inputCsv),"UTF8"), CSVFormat.DEFAULT.withDelimiter(','));
		List<CSVRecord> records = csvParser.getRecords();
		CSVRecord headerRecord = records.get(0);
		String[] header = ((List<String>)IteratorUtils.toList(headerRecord.iterator())).toArray(new String[headerRecord.size()]);
		int metadataIndex = Arrays.asList(header).indexOf(this.metadataName);
		CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(new FileOutputStream(outputCsv),"UTF8"), CSVFormat.DEFAULT.withDelimiter(',').withHeader(header).withRecordSeparator("\n").withQuoteMode(QuoteMode.MINIMAL));
		for(CSVRecord record : records){
			if(!record.equals(headerRecord)){
				List<String> collections = new ArrayList<String>();
				String[] metadata_values = record.get(metadataIndex).split("\\|\\|");
				String collection = "";
				for(String value : metadata_values){
					if(!value.trim().equals(""))
						collection = findCollection(value.trim(), university);
						if(!collections.contains(collection))
						collections.add(collection);
				}
				List<String> newRecord = new ArrayList<String>(header.length);

				for(int i=0; i<header.length ; i++ ){
					if(!header[i].equals("collection")){
						newRecord.add(record.get(i));
					}else{
						while(collections.contains(""))
							collections.remove("");
						if(collections.isEmpty()){
							collections.add(this.defaultHandle);
						}
						newRecord.add(Joiner.on("||").join( collections ));
					}
				}
				csvPrinter.printRecord(newRecord);
				csvPrinter.flush();
			}

		}
		csvPrinter.close();
		csvParser.close();

	}


	private University loadUniversityMap() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<University> element = (JAXBElement<University>) unmarshaller.unmarshal(mapXml);
		return element.getValue();
	}

	private String findCollection(String examineCollumn, University map){
		examineCollumn = TextUtils.removeExtraSpaces(TextUtils.removePuncts(TextUtils.removeAccents(examineCollumn).toLowerCase()));
		for(String university_rule : map.getRules().getMatch()){
			if(examineCollumn.contains(university_rule)){
				for(Campus campus : map.getCampi().getCampus()){
					for(String campus_rule: campus.getRules().getMatch()){
						if(examineCollumn.contains(campus_rule)){
							if(campus.getHandle() != null){
								return (campus.getHandle());
							}else{
								for(Department department : campus.getDepartments().getDepartment()){
									for(String department_rule : department.getRules().getMatch()){
										if(examineCollumn.contains(department_rule)){
											return (department.getHandle());
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return "";
	}

}
