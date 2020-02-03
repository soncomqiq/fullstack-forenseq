package io.forensic.springboot.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, PersonIdentity> {

	@Query(value = "SELECT count(*) FROM person", nativeQuery = true)
	public int getNumberOfPerson();

//	
//	List<AutosomalKit> findByIdSampleYear(String sampleYear);
//	List<AutosomalKit> findByIdSampleId(String sampleYear);
}
