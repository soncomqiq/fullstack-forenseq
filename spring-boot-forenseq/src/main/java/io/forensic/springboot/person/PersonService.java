package io.forensic.springboot.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public List<Person> getAllPersons() {
		// return topics;
		List<Person> persons = new ArrayList<>();
		personRepository.findAll().forEach(persons::add);
		return persons;
	}

	public void readExcelData(String fileName) {
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
			String sampleYear = null; // Collect sample_year
			String sampleId = null; // Collect sample_id

			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// First Sheet : AutosomalKit STRs
				if (sheet.getSheetName().equals("Autosomal STRs")) {
//					System.out.println("This is AutosomalKit STRs");
					// every sheet has rows, iterate over them
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();

						// Line 1 to 7
						if (line >= 1 && line <= 7) {
							List<String> data = new ArrayList<>();
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
							if (data.get(0).equals("Created")) {
								String[] tmp = data.get(1).split(" ");
								sampleYear = tmp[2];
							}
							if (data.get(0).equals("Sample")) {
								sampleId = data.get(1);
							}
						}

						// End of Line 7 Save Person Entity
						if (line == 8) {
							Person tmpPerson = new Person(new PersonIdentity(sampleYear, sampleId), "null", "null",
									"null", "null", "null", "null", "null", 0, "null");
							personRepository.save(tmpPerson);
						}

						line += 1; // Counting Line (Add 1/Loop)
//						System.out.println();
					} // end of rows iterator
				}

			} // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// return countriesList;
	}

	public Optional<Person> getPerson(PersonIdentity id) {
		// return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
		return personRepository.findById(id);
	}

	public void addPerson(Person person) {
		// topics.add(topic);
		personRepository.save(person);
	}

	public void updatePerson(String id, Person person) {
		personRepository.save(person);
	}

	public void deletePerson(PersonIdentity id) {
		personRepository.deleteById(id);
	}

	public int getNumberOfPerson() {
		return personRepository.getNumberOfPerson();
	}

	public void readXlsxData(String fileName) {
		System.out.println("uploaded");
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

			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					// Get the row object
					Row row = rowIterator.next();
					// Every row has columns, get the column iterator and iterate over them
					Iterator<Cell> cellIterator = row.cellIterator();

					List<String> data = new ArrayList<>();
					while (cellIterator.hasNext()) {
						// Get the Cell object
						Cell cell = cellIterator.next();
						// check the cell type and process accordingly
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							data.add(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							int number = (int) cell.getNumericCellValue();
							data.add("" + number);
						}
					} // end of cell iterator
					System.out.println("DATA::" + data.toString());
					Person tmpPerson = new Person(new PersonIdentity(data.get(0), data.get(1)), data.get(2),
							data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8),
							Integer.parseInt(data.get(9)), data.get(10));
					personRepository.save(tmpPerson);
				} // end of rows iterator

			} // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
