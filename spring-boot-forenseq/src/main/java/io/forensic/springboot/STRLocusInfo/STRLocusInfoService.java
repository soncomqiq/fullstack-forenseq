package io.forensic.springboot.STRLocusInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class STRLocusInfoService {

	@Autowired
	private STRLocusInfoRepository sTRLocusInfoRepository;

	public List<STRLocusInfo> getAllTopics() {
		// return topics;
		List<STRLocusInfo> sTRLocusInfos = new ArrayList<>();
		sTRLocusInfoRepository.findAll().forEach(sTRLocusInfos::add);
		return sTRLocusInfos;
	}

	public Optional<STRLocusInfo> getPerson(STRLocusInfoIdentity id) {
		return sTRLocusInfoRepository.findById(id);
	}

	public void addPerson(STRLocusInfo sTRLocusInfo) {
		sTRLocusInfoRepository.save(sTRLocusInfo);
	}

	public void updatePerson(String id, STRLocusInfo sTRLocusInfo) {
		sTRLocusInfoRepository.save(sTRLocusInfo);
	}

	public String readTextData(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		int line = 1;
		String sampleId = null, sampleYear = null;
		while (sc.hasNextLine()) {
			if (line == 1) {
				sampleYear = sc.nextLine();
			}
			if (line == 2) {
				sampleId = sc.nextLine();
				List<String> tmp = sTRLocusInfoRepository.findExistByID(sampleId, sampleYear);
				if (tmp.size() == 0) {
					sc.close();
					return "Please Enter PersonID first.";
				}
			}
			if (line > 2) {
				String[] tmp = sc.nextLine().split(",");
				System.out.println("Save");
				sTRLocusInfoRepository.save(new STRLocusInfo(
						new STRLocusInfoIdentity(sampleYear, sampleId, tmp[0], tmp[1] + "," + tmp[2], "CE_Data"),
						"Autosomal", "N/A"));
			}
			line++;
		}
		sc.close();
		return "Success";
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
//					System.out.println("This is Autosomal STRs");
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
//							System.out.println("this is Line::"+line);
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

						// Line 14 to 41 Collect CE_Data
						if (line >= 13 && line <= 40) {
							List<String> data = new ArrayList<>();
//							System.out.println("this is Line::"+line);
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
									break;
								}
							} // end of cell iterator
//							System.out.println("data.get(0)::"+data.get(0));
//							System.out.println("data.get(1)::"+data.get(1));
//							System.out.println("sample_year::"+sampleYear);
//							System.out.println("sample_id::"+sampleId);
							sTRLocusInfoRepository
									.save(new STRLocusInfo(
											new STRLocusInfoIdentity(sampleYear, sampleId, data.get(0), data.get(1),
													"Forenseq"),
											"Autosomal", (data.get(2).equals("")) ? "Good" : data.get(2)));
//							System.out.println("Print4");
						}

						line += 1; // Counting Line (Add 1/Loop)
//						System.out.println();
					} // end of rows iterator
				}

				// Second Sheet : Y STRs
				if (sheet.getSheetName().equals("Y STRs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 13 to 36 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 36) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
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
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							sTRLocusInfoRepository.save(new STRLocusInfo(new STRLocusInfoIdentity(sampleYear, sampleId,
									data.get(0), data.get(1), "Forenseq"), "Y",
									(data.get(2).equals("")) ? "Good" : data.get(2)));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

				// Third Sheet : X STRs
				if (sheet.getSheetName().equals("X STRs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 13 to 36 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 19) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
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
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							sTRLocusInfoRepository.save(new STRLocusInfo(new STRLocusInfoIdentity(sampleYear, sampleId,
									data.get(0), data.get(1), "Forenseq"), "X",
									(data.get(2).equals("")) ? "Good" : data.get(2)));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

				// Forth Sheet : iSNPs
				if (sheet.getSheetName().equals("iSNPs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 14 to 41 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 106) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
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
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							sTRLocusInfoRepository
									.save(new STRLocusInfo(
											new STRLocusInfoIdentity(sampleYear, sampleId, data.get(0), data.get(1),
													"Forenseq"),
											"iSNPs", (data.get(2).equals("")) ? "Good" : data.get(2)));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

			} // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deletePerson(STRLocusInfoIdentity id) {
		sTRLocusInfoRepository.deleteById(id);
	}

	public List<STRLocusInfo> getForenseqById(String sid, String sy) {
		List<Object[]> tmp = sTRLocusInfoRepository.findAllByID(sid, sy);
		List<STRLocusInfo> result = new ArrayList<STRLocusInfo>();
		for (int i = 0; i < tmp.size(); i++) {
			result.add(new STRLocusInfo(
					new STRLocusInfoIdentity(sy, sid, tmp.get(i)[2].toString(), tmp.get(i)[3].toString(),
							tmp.get(i)[4].toString()),
					tmp.get(i)[5].toString(), (tmp.get(i)[6]) != null ? tmp.get(i)[6].toString() : "N/A"));
		}
		return result;
	}
}
