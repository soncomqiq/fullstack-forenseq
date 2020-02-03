package io.forensic.springboot.forenseqX;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class ForenseqXService {

	@Autowired
	private ForenseqXRepository forenseqXRepository;

	public List<ForenseqX> getAllForenseqs() {
		// return topics;
		List<ForenseqX> forenseqXs = new ArrayList<>();
		forenseqXRepository.findAll().forEach(forenseqXs::add);
		return forenseqXs;
	}

	public Optional<ForenseqX> getForenseq(ForenseqXIdentity id) {
		return forenseqXRepository.findById(id);
	}

	public void addForenseq(ForenseqX forenseqX) {
		forenseqXRepository.save(forenseqX);
	}

	public void updateForenseq(String id, ForenseqX forenseqX) {
		forenseqXRepository.save(forenseqX);
	}

	public void deleteForenseq(ForenseqXIdentity id) {
		forenseqXRepository.deleteById(id);
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
//							System.out.println("\nthis is Line::" + line);
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
						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

				// X STRs sheet
				if (sheet.getSheetName().equals("X STRs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					Map<String, String> locus = new HashMap<String, String>();
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();

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
						}

						// Line 22 and above
						if (line >= 22) {
//							System.out.println("this is Line::" + line);
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
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("locus.get(data.get(0))::" + locus.get(data.get(0)));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("data.get(2)::" + data.get(2));
//							System.out.println("data.get(3)::" + data.get(3));
							if (data.get(2).equalsIgnoreCase("Yes")) {
								forenseqXRepository
										.save(new ForenseqX(
												new ForenseqXIdentity(sampleYear, sampleId, data.get(0), data.get(1),
														data.get(3)),
												data.get(2), locus.get(data.get(0)), data.get(4), ""));
							}
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

	public List<ForenseqX> getForenseqById(String sid, String sy) {
		List<Object[]> tmp = forenseqXRepository.findAllByID(sid, sy);
		List<ForenseqX> result = new ArrayList<ForenseqX>();
		String alignment;
		for (int i = 0; i < tmp.size(); i++) {
			alignment = getAlignment(tmp.get(i)[3].toString(),  tmp.get(i)[4].toString(), sid, sy);
			result.add(new ForenseqX(
					new ForenseqXIdentity(sy, sid, tmp.get(i)[3].toString(), tmp.get(i)[4].toString(),
							tmp.get(i)[5].toString()),
					tmp.get(i)[6].toString(), tmp.get(i)[2].toString(), tmp.get(i)[7].toString(), alignment));
		}
		return result;
	}
	
	public String getAlignment(String locus, String allele, String sampleId, String sampleYear) {
		System.out.println("GOT IN");
		Map<String, List<String>> Motif = new HashMap<String, List<String>>();
		List<String> tmpMotif = forenseqXRepository.findMotifLocus(locus);
		Motif.put(locus, tmpMotif);
		String tmp = forenseqXRepository.findAllForenseqTable(locus, Float.parseFloat(allele), sampleId, sampleYear);
		String result;

			int array_iterate = 0;
			int sequence_iterate = 0;
			int count = 0;
			int count_total = 0;
			String seqAlignment = "";
			String sequence = tmp;
			System.out.println("locus+allele::"+locus+allele);
			System.out.println("SID+SY::"+sampleId+sampleYear);
			System.out.println(sequence);
			System.out.println(sequence.length());
			while ((Motif.get(locus) != null) && (array_iterate < Motif.get(locus).size())
					&& (sequence_iterate <= sequence.length())) {
				System.out.println(seqAlignment);
				System.out.println("array_iterate::" + array_iterate);
				System.out.println("sequence_iterate" + sequence_iterate);
				if (count_total == (int) Float.parseFloat(allele) || sequence_iterate >= sequence.length()) {
					seqAlignment += "(" + Motif.get(locus).get(array_iterate).substring(1) + ")" + count + " ";
					array_iterate += 1;
					count_total += count;
					count = 0;
					System.out.println("FINAL PATH");
					seqAlignment += sequence.substring(sequence_iterate);
				} else {
					if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("1")) {
						try {
							if (sequence
									.substring(sequence_iterate,
											sequence_iterate
													+ Motif.get(locus).get(array_iterate).substring(1).length())
									.equals(Motif.get(locus).get(array_iterate).substring(1))) {
								count += 1;
								count_total += 1;
								sequence_iterate += Motif.get(locus).get(array_iterate).substring(1).length();
							} else {
								seqAlignment += "(" + Motif.get(locus).get(array_iterate).substring(1) + ")" + count
										+ " ";
								array_iterate += 1;
								count = 0;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("2")) {
//						System.out.println("Seq::"+sequence.length());
//						System.out.println("seq_iterate::"+sequence_iterate);
//						System.out.println("Motif"+ Motif.get(locus).get(array_iterate).substring(1).length());
						try {
							seqAlignment += sequence.substring(sequence_iterate,
									sequence_iterate + Motif.get(locus).get(array_iterate).substring(1).length()) + " ";
						} catch (Exception e) {
							e.printStackTrace();
						}
						sequence_iterate += Motif.get(locus).get(array_iterate).substring(1).length();
						array_iterate += 1;
					} else if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("3")) {
						seqAlignment += "No Repeated Data";
						break;
					}
				}
			}
			result = seqAlignment;

		System.out.println("tmp::" + tmp);
		System.out.println("Locus::" + locus);
		System.out.println("Allele::" + allele);
		return result;
	}
}
