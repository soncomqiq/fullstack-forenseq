package io.forensic.springboot.forenseqX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources/forenseqx")
public class ForenseqXController {
	
	@Autowired
	private ForenseqXService forenseqXService;

	@RequestMapping("/forenseqxs")
	public List<ForenseqX> getAllForenseq() {
		return forenseqXService.getAllForenseqs();
	}
	
	@RequestMapping("/uploadforenseqxfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start X STRs");
		forenseqXService.readExcelData("uploads/temp.xlsx");
		System.out.println("End X STRs");
		return "Success";
	}
	
//	@RequestMapping("/forenseqx/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public ForenseqX getRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		return forenseqXService.getForenseq(new ForenseqXIdentity(id,sid,locus,allele));
//	}
	
	@RequestMapping(method=RequestMethod.POST, value="/forenseqxs")
	public void addRazor(@RequestBody ForenseqX forenseqX) {
		forenseqXService.addForenseq(forenseqX);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/forenseqx/{id}")
	public void updateRazor(@RequestBody ForenseqX forenseqX,@PathVariable String id) {
		forenseqXService.updateForenseq(id, forenseqX);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getByID")
	public List<ForenseqX> getforenseq(@RequestParam(value = "sampleID") String sid,
			@RequestParam(value = "sampleYear") String sy) {
		return forenseqXService.getForenseqById(sid, sy);
	}
}
