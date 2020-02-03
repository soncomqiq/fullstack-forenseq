package io.forensic.springboot.Analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

	@Autowired
	private AnalysisService service;

	@RequestMapping("/getkinship")
	public KinshipResult getKinShip() {
		service.updateFreq();
		return service.getKinshipResult("uploads/temp.xlsx");
	}

}
