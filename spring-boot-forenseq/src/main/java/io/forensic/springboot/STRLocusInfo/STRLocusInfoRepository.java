package io.forensic.springboot.STRLocusInfo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface STRLocusInfoRepository extends CrudRepository<STRLocusInfo, STRLocusInfoIdentity> {

	@Query(value = "select * from STR_LOCUS_INFO where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);
	
	@Query(value = "SELECT sample_ID FROM Person WHERE Sample_ID = :sid and sample_year = :sy ;", nativeQuery = true)
	List<String> findExistByID(@Param("sid") String sid, @Param("sy") String sy);
}
