package io.forensic.springboot.STRLocusInfo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.forensic.springboot.forenseqX.ForenseqX;

@RestController
@RequestMapping("/api/resources/strlocusinfo")
public class STRLocusInfoController {
	
	@Autowired
	private STRLocusInfoService sTRLocusInfoService;

	@RequestMapping("/cedata")
	public List<STRLocusInfo> getAllTopic() {
		return sTRLocusInfoService.getAllTopics();
	}
	
	@RequestMapping("/uploadcedatafile")
	public String readExcel() {
		System.out.println("Start CE_data");
		sTRLocusInfoService.readExcelData("uploads/temp.xlsx");
		System.out.println("End CE_Data");
		return "Success";
	}
	
	@RequestMapping("/uploadtextfile")
	public String textFileRead() {
		try {
			return sTRLocusInfoService.readTextData("uploads/temp.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Something went wrong.";
	}
	
	@RequestMapping("/cedata/{id}&&{sid}&&{locus}&&{genotype}&&{from}")
	public Optional<STRLocusInfo> getTopic(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String genotype,@PathVariable String from) {
		return sTRLocusInfoService.getPerson(new STRLocusInfoIdentity(id,sid, locus, genotype, from));
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cedata")
	public void addTopic(@RequestBody STRLocusInfo sTRLocusInfo) {
		sTRLocusInfoService.addPerson(sTRLocusInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/cedata/{id}")
	public void updateTopic(@RequestBody STRLocusInfo sTRLocusInfo,@PathVariable String id) {
		sTRLocusInfoService.updatePerson(id, sTRLocusInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/cedata/{id}&&{sid}&&{locus}&&{genotype}&&{from}")
	public void deleteTopic(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String genotype,@PathVariable String from) {
		sTRLocusInfoService.deletePerson(new STRLocusInfoIdentity(id,sid, locus, genotype, from));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getByID")
	public List<STRLocusInfo> getforenseq(@RequestParam(value = "sampleID") String sid,
			@RequestParam(value = "sampleYear") String sy) {
		return sTRLocusInfoService.getForenseqById(sid, sy);
	}
}
