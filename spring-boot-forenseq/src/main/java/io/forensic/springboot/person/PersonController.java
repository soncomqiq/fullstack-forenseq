package io.forensic.springboot.person;

import java.io.IOException;
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
@RequestMapping("/api/resources/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping("/uploadpersonfile")
	public String uploadPersonfile() {
		System.out.println("Start PERSON");
		personService.readExcelData("uploads/temp.xlsx");
		System.out.println("End PERSON");
		return "Success";
	}
	
	@RequestMapping("/ULmanualPerson")
	public void uploadByType(@RequestParam(value = "type") String type){
		personService.readXlsxData("uploads/temp.xlsx");
	}

	@RequestMapping("/numberofperson")
	@CrossOrigin()
	public int getNumberOfPerson() {
		return personService.getNumberOfPerson();
	}

	@RequestMapping("/persons")
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/persons")
	public void addTopic(@RequestBody Person person) {
		personService.addPerson(person);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/persons/{id}")
	public void updatePerson(@RequestBody Person person, @PathVariable String id) {
		personService.updatePerson(id, person);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/persons")
	public void deletePerson(@RequestParam(value = "sampleYear") String yid,
			@RequestParam(value = "sampleID") String sid) {
		personService.deletePerson(new PersonIdentity(yid, sid));
	}
}
