package io.forensic.springboot.forenseq;

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
@RequestMapping("/api/resources/forenseq")
public class ForenseqController {

	@Autowired
	private ForenseqService forenseqService;

	@RequestMapping("/forenseqs")
	public List<Forenseq> getAllForenseq() {
		return forenseqService.getAllForenseqs();
	}

	@RequestMapping("/uploadforenseqfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start Autosomal");
		forenseqService.readExcelData("uploads/temp.xlsx");
		System.out.println("End Autosomal");
		return "Success";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getByID")
	public List<Forenseq> getforenseq(@RequestParam(value = "sampleID") String sid,
			@RequestParam(value = "sampleYear") String sy) {
		return forenseqService.getForenseqById(sid, sy);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/forenseqs")
	public void addForenseq(@RequestBody Forenseq forenseq) {
		forenseqService.addForenseq(forenseq);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/forenseq/{id}")
	public void updateForenseq(@RequestBody Forenseq forenseq, @PathVariable String id) {
		forenseqService.updateForenseq(id, forenseq);
	}

//	@RequestMapping(method=RequestMethod.DELETE, value="/forenseq/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public void deleteRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		forenseqService.deleteForenseq(new ForenseqIdentity(id,sid,locus,allele));
//	}
}
