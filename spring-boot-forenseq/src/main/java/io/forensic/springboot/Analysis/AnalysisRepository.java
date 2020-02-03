package io.forensic.springboot.Analysis;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnalysisRepository extends CrudRepository<FreqThai, FreqThaiIdentity>{
	
	@Query(value = "SELECT DISTINCT ak.kit FROM AutosomalKit ak", nativeQuery = true)
	public List<String> findDistinctAutosomalKit();

	@Query(value = "SELECT ak.locus FROM AutosomalKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusAutosomalKit(@Param("kit") String kit);
	
	@Query(value = "SELECT DISTINCT ak.kit FROM YKit ak", nativeQuery = true)
	public List<String> findDistinctYKit();
	
	@Query(value = "SELECT distinct forenseq.sample_ID,country,locus,genotype FROM forenseq "
			+ "JOIN person ON forenseq.Sample_ID = person.Sample_ID;", nativeQuery = true)
	public List<Object[]> getForenseqData();

	@Query(value = "SELECT locus , Allele , Freq FROM freq WHERE country = :country", nativeQuery = true)
	public List<Object[]> queryRegion(String country);

	@Query(value = "SELECT Sample_ID , country , Name FROM person WHERE Sample_ID = :sampleID", nativeQuery = true)
	public List<Object[]> queryPerson(String sampleID);

	@Query(value = "SELECT Sample_ID,locus,genotype FROM forenseq WHERE Sample_ID=:sampleID", nativeQuery = true)
	public List<Object[]> queryForenseq(String sampleID);
}
