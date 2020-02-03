package io.forensic.springboot.razor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RazorController {
	
	@Autowired
	private RazorService razorService;

	@RequestMapping("/razors")
	public List<Razor> getAllForenseq() {
		return razorService.getAllForenseqs();
	}
	
//	@RequestMapping("/razor/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public Razor getRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		return razorService.getForenseq(new RazorIdentity(id,sid,locus,allele));
//	}
	
	@RequestMapping(method=RequestMethod.POST, value="/razors")
	public void addRazor(@RequestBody Razor razor) {
		razorService.addForenseq(razor);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/razor/{id}")
	public void updateRazor(@RequestBody Razor razor,@PathVariable String id) {
		razorService.updateForenseq(id, razor);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/razor/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
	public void deleteRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
		razorService.deleteForenseq(new RazorIdentity(id,sid,locus,allele));
	}
}
