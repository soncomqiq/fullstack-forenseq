package io.forensic.springboot.forenseq;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForenseqRepository extends CrudRepository<Forenseq, ForenseqIdentity> {

	@Query(value = "select * from forenseq where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);

	@Query(value = "(SELECT sequence FROM Forenseq WHERE Locus = :locus && Allele = :allele && sample_year = :sampleYear && sample_id=:sampleId LIMIT 1) ", nativeQuery = true)
	public String findAllForenseqTable(@Param("locus") String locus, @Param("allele") float allele,
			@Param("sampleId") String sampleId, @Param("sampleYear") String sampleYear);

	@Query(value = "select pattern from Pattern_Alignment WHERE Locus = :locus order by seqNo;", nativeQuery = true)
	public List<String> findMotifLocus(@Param("locus") String locus);
}
