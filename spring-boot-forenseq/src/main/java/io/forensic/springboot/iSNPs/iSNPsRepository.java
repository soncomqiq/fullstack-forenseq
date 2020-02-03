package io.forensic.springboot.iSNPs;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface iSNPsRepository extends CrudRepository<iSNPs, iSNPsIdentity> {
	
	@Query(value = "select * from isnpdata where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);
}
