package io.forensic.springboot.razor;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RazorRepository extends CrudRepository<Razor, RazorIdentity> {
	
	List<Razor> findByIdSampleYear(String sampleYear);
	List<Razor> findByIdSampleId(String sampleYear);
}
