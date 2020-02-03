package io.forensic.springboot.Analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

	@Autowired
	private AnalysisRepository repository;

	private ArrayList<Data> dataList = new ArrayList<Data>();

	public List<String> getAllAutosomalKit() {
		return repository.findDistinctAutosomalKit();
	}

	public void updateFreq() {
		dataList = new ArrayList<Data>();
		if (queryAlleleForCalculateFreq()) {
			calculateFreq();
		}
	}

	public KinshipResult getKinshipResult(String fileName) {
		KinshipResult result = null;
		try {
			// Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);

			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}

			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();
			ArrayList<InputModel> input = new ArrayList<InputModel>();

			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);
				String parent = "", child = "", country = "";
				Iterator<Row> rowIterator = sheet.iterator();
				// Tracking Current Line
				int line = 1;
				while (rowIterator.hasNext()) {
					// Get the row object
					Row row = rowIterator.next();
					// Every row has columns, get the column iterator and iterate over them
					Iterator<Cell> cellIterator = row.cellIterator();
					// Line 14 to 41 Collect CE_Data of iSNPs
					if (line == 1) {
						List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
						while (cellIterator.hasNext()) {
							// Get the Cell object
							Cell cell = cellIterator.next();
							// check the cell type and process accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								data.add(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								data.add("" + cell.getNumericCellValue());
								break;
							}
						} // end of cell iterator
						parent = data.get(0);
						child = data.get(1);
						country = data.get(2);
						queryRegion(country);
					}

					// Line 2 and above
					if (line >= 2) {
//							System.out.println("this is Line::" + line);
						List<String> data = new ArrayList<String>();
						while (cellIterator.hasNext()) {

							// Get the Cell object
							Cell cell = cellIterator.next();

							// check the cell type and process accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
								break;
							case Cell.CELL_TYPE_NUMERIC:
								data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
							}
						} // end of cell iterator
						input.add(new InputModel(data.get(0), Float.parseFloat(data.get(1)),
								Float.parseFloat(data.get(2)), Float.parseFloat(data.get(3)),
								Float.parseFloat(data.get(4))));
					}
					line += 1; // Counting Line (Add 1/Loop)
				} // end of rows iterator
				Map<String, Float> piList = NoParentTest.getInstance().getPiList(input, country);
				result = new KinshipResult(parent, child, piList);

			} // end of sheets for loop

			// close file input stream
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void calculateFreq() {
		ArrayList<String> regions = new ArrayList<String>();
		for (Data d : dataList) {
			if (!regions.contains(d.country)) {
				regions.add(d.country);
			}
		}
		for (String r : regions) {
			ArrayList<String> locus = new ArrayList<String>();
			for (Data d : dataList) {
				if (r.equals(d.country)) {
					if (!locus.contains(d.locus)) {
						locus.add(d.locus);
					}
				}
			}
			for (String l : locus) {
				int counter = 0;
				for (Data d : dataList) {
					if (r.equals(d.country) && l.equals(d.locus)) {
						counter += d.n;
					}
				}
				for (Data d : dataList) {
					if (r.equals(d.country) && l.equals(d.locus)) {
						d.setFreq(((float) d.n) / counter);
						repository.save(new FreqThai(new FreqThaiIdentity(d.country, d.locus, d.allele), d.freq));
					}
				}
			}
		}
	}

	public boolean queryAlleleForCalculateFreq() {

		List<Object[]> rec = repository.getForenseqData();

		for (int i = 0; i < rec.size(); i++) {
			String[] genotype = rec.get(i)[3].toString().split(",");
			try {
				addData(rec.get(i)[1].toString(), rec.get(i)[2].toString(), Float.parseFloat(genotype[0]));
				addData(rec.get(i)[1].toString(), rec.get(i)[2].toString(), Float.parseFloat(genotype[1]));
			} catch (Exception e) {

			}
		}
		return true;
	}

	public void addData(String region, String locus, Float allele) {
		boolean found = false;
		for (Data d : dataList) {
			if (d.country.equals(region) && d.locus.equals(locus) && d.allele.equals(allele)) {
				found = true;
				d.count();
			}
		}
		if (!found) {
			dataList.add(new Data(region, locus, allele));
		}
	}

	public void queryRegion(String country) {
		System.out.println("QUERY");
		Region region = new Region();
		region.setRegionName(country);
		ObjManager.getInstance().insertRegion(region);
		List<Object[]> rec = repository.queryRegion(country);

		for (int i = 0; i < rec.size(); i++) {
			AlleleFreq newFreq = new AlleleFreq();
			newFreq.setRegionName(country);
			newFreq.setLocus(rec.get(i)[0].toString());
			newFreq.setAllele(Float.parseFloat(rec.get(i)[1].toString()));
			newFreq.setFreq(Float.parseFloat(rec.get(i)[2].toString()));
			ObjManager.getInstance().updateRegion(newFreq);
		}

	}

	public PersonInfo queryPerson(String sampleID) {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setSampleID(sampleID);
		ObjManager.getInstance().insertPersonInfo(personInfo);

		List<Object[]> rec = repository.queryPerson(sampleID);

		for (int i = 0; i < rec.size(); i++) {
			String country = rec.get(i)[1].toString();
			String name = rec.get(i)[2].toString();
			personInfo.setRegion(country);
			personInfo.setName(name);
			queryRegion(country);
		}

		List<Object[]> rec1 = repository.queryForenseq(sampleID);

		for (int i = 0; i < rec1.size(); i++) {
			StrData data = new StrData();
			data.setSampleID(sampleID);
			String[] genotype = rec1.get(i)[2].toString().split(",");
			data.setLocus(rec1.get(i)[1].toString());
			data.setGenotype1(Float.valueOf(genotype[0]));
			data.setGenotype2(Float.valueOf(genotype[1]));
			ObjManager.getInstance().updatePersonInfo(data);
		}

		return personInfo;
	}

	public class Data {
		String country;
		String locus;
		Float allele;
		int n;
		Float freq;

		public Data(String country, String locus, Float allele) {
			this.country = country;
			this.locus = locus;
			this.allele = allele;
			this.n = 1;
		}

		public void count() {
			n++;
		}

		public void setFreq(Float freq) {
			this.freq = freq;
		}
	}
}