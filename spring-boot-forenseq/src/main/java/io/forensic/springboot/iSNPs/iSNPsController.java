package io.forensic.springboot.iSNPs;

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
@RequestMapping("/api/resources/isnps")
public class iSNPsController {

	@Autowired
	private iSNPsService iSNPsService;

	@RequestMapping("/isnps")
	public List<iSNPs> getAllForenseq() {
		return iSNPsService.getAllForenseqs();
	}

	@RequestMapping("/uploadisnpsfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start iSNPs");
		iSNPsService.readExcelData("uploads/temp.xlsx");
		System.out.println("End iSNPs");
		return "Success";
	}

//	@RequestMapping("/iSNPs/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public iSNPs getRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		return iSNPsService.getForenseq(new iSNPsIdentity(id,sid,locus,allele));
//	}

	@RequestMapping(method = RequestMethod.POST, value = "/iSNPs")
	public void addRazor(@RequestBody iSNPs iSNPs) {
		iSNPsService.addForenseq(iSNPs);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/iSNPs/{id}")
	public void updateRazor(@RequestBody iSNPs iSNPs, @PathVariable String id) {
		iSNPsService.updateForenseq(id, iSNPs);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/iSNPs/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
	public void deleteRazor(@PathVariable String id, @PathVariable String sid, @PathVariable String locus,
			@PathVariable String allele) {
		iSNPsService.deleteForenseq(new iSNPsIdentity(id, sid, locus, allele));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getByID")
	public List<iSNPs> getforenseq(@RequestParam(value = "sampleID") String sid,
			@RequestParam(value = "sampleYear") String sy) {
		return iSNPsService.getForenseqById(sid, sy);
	}
}
