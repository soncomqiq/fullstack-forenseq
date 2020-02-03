package io.forensic.springboot.Other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class OtherController {

	@Autowired
	private OtherService service;

	@RequestMapping("/getallautosomalkit")
	@CrossOrigin()
	public List<String> getAllAutosomalKit() {
		return service.getAllAutosomalKit();
	}
	
	@RequestMapping("/getlocusautosomalkit/{kit}")
	public List<String> getLocusAutosomalKit(@PathVariable String kit) {
		return service.getLocusAutosomalKit(kit);
	}
	
	@RequestMapping("/statisticmap/{allele}")
	public List<MapStats> getStatisticMap(@PathVariable String allele) {
		return service.getStatisticMap(allele);
	}

	@RequestMapping("/getallykit")
	@CrossOrigin()
	public List<String> getAllYKit() {
		return service.getAllYKit();
	}
	
	@RequestMapping("/getlocusykit/{kit}")
	@CrossOrigin()
	public List<String> getLocusYKit(@PathVariable String kit) {
		return service.getLocusYKit(kit);
	}

	@RequestMapping("/getallxkit")
	@CrossOrigin()
	public List<String> getAllXKit() {
		return service.getAllXKit();
	}

	@RequestMapping("/getlocusxkit/{kit}")
	@CrossOrigin()
	public List<String> getLocusXKit(@PathVariable String kit) {
		return service.getLocusXKit(kit);
	}
	
	@RequestMapping("/hetero")
	@CrossOrigin()
	public List<Hetero> getHetero() {
		return service.getHetero();
	}
	
	@RequestMapping("/getlocuslist")
	@CrossOrigin()
	public Locus getLocusList() {
		Locus locusList = new Locus();
		locusList.setAutosomLocus(service.getAllLocusAutosom());
		locusList.setxLocus(service.getAllLocusX());
		locusList.setyLocus(service.getAllLocusY());
		return locusList;
	}
	
	@RequestMapping("/locuslist")
	public List<RequestLocus> getLocusListAlign() {
		return service.getLocusListAlign();
	}
	
	@RequestMapping("/alleleinfo")
	@CrossOrigin()
	public List<RequestLocus> getAlleleInfoAlign() {
		return service.getAlleleInfoAlign();
	}

	@RequestMapping("/getstatsgraphinfo/{table}/{locus}")
	@CrossOrigin()
	public List<LocusInfoGraph> getStatsGraphInfo(@PathVariable String locus, @PathVariable String table) {
		return service.getStatsGraph(locus, table);
	}

	@RequestMapping(value = "/findpersonbylocus", method = RequestMethod.POST)
	@CrossOrigin()
	public List<Object[]> getPersonByLocus(@RequestBody List<RequestLocus> locus) {
		return service.getPersonByLocus(locus);
	}
	
	@RequestMapping(value = "/findNumberOfPersonByLocus", method = RequestMethod.POST)
	@CrossOrigin()
	public int getNumberOfPersonByLocus(@RequestBody List<RequestLocus> locus) {
		return service.getPersonByLocus(locus).size();
	}
	
	@RequestMapping(value = "/alignment", method = RequestMethod.POST)
	@CrossOrigin()
	public List<Alignment> getAligenment(@RequestBody RequestLocus body) {
//		System.out.println("body.getLocus()::"+body.getLocus');
		return service.getAlignment(body.getLocus(), body.getAllele());
	}
	
	@RequestMapping(value = "/isnpstat", method = RequestMethod.GET)
	@CrossOrigin()
	public List<iSNPRespone> getISNPStat() {
//		System.out.println("body.getLocus()::"+body.getLocus');
		return service.findISNPStat();
	}
}
