package io.forensic.springboot.Other;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.forensic.springboot.person.Person;

public interface OtherRepository extends CrudRepository<Person, String>, OtherRepositoryCustom {

	@Query(value = "SELECT DISTINCT ak.kit FROM AutosomalKit ak", nativeQuery = true)
	public List<String> findDistinctAutosomalKit();

	@Query(value = "SELECT ak.locus FROM AutosomalKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusAutosomalKit(@Param("kit") String kit);

	@Query(value = "SELECT DISTINCT ak.kit FROM YKit ak", nativeQuery = true)
	public List<String> findDistinctYKit();

	@Query(value = "SELECT ak.locus FROM YKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusYKit(@Param("kit") String kit);

	@Query(value = "SELECT DISTINCT Locus FROM forenseq union SELECT DISTINCT Locus FROM forenseqx union SELECT DISTINCT Locus FROM forenseqy;", nativeQuery = true)
	public List<String> findDistinctLocus();

	@Query(value = "SELECT DISTINCT ak.kit FROM XKit ak", nativeQuery = true)
	public List<String> findDistinctXKit();

	@Query(value = "SELECT ak.locus FROM XKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusXKit(@Param("kit") String kit);

	@Query(value = "SELECT DISTINCT locus FROM forenseq;", nativeQuery = true)
	public List<String> findLocusAutosom();

	@Query(value = "SELECT DISTINCT locus FROM forenseqY;", nativeQuery = true)
	public List<String> findLocusY();

	@Query(value = "SELECT DISTINCT locus FROM forenseqX;", nativeQuery = true)
	public List<String> findLocusX();

	@Query(value = "SELECT Allele, COUNT(*) FROM forenseq WHERE _Type = \"Yes\" && locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphA(@Param("locus") String locus);

	@Query(value = "SELECT Allele, COUNT(*) FROM forenseqY WHERE _Type = \"Yes\" && locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphY(@Param("locus") String locus);

	@Query(value = "SELECT Allele, COUNT(*) FROM forenseqX WHERE _Type = \"Yes\" && locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphX(@Param("locus") String locus);

	@Query(value = "SELECT lft.locus, lft.Total, rht.Hetero FROM (SELECT Locus, COUNT(DISTINCT sample_id) AS Total FROM forenseq WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseq WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus "
			+ "UNION SELECT lft.locus, lft.Total, rht.Hetero FROM (SELECT Locus, COUNT(DISTINCT sample_id) AS Total FROM forenseqX WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseqX WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus "
			+ "UNION SELECT lft.locus, lft.Total, rht.Hetero FROM (SELECT Locus, COUNT(DISTINCT sample_id) AS Total FROM forenseqY WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseqY WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus;", nativeQuery = true)
	public List<Object[]> getHetero();

	@Query(value = "(SELECT * FROM Forenseq WHERE Locus = :locus && Allele = :allele ORDER BY Sequence) "
			+ "UNION (SELECT * FROM ForenseqX WHERE Locus = :locus && Allele = :allele ORDER BY Sequence) "
			+ "UNION (SELECT * FROM ForenseqY WHERE Locus = :locus && Allele = :allele ORDER BY Sequence)", nativeQuery = true)
	public List<Object[]> findAllForenseqTable(@Param("locus") String locus, @Param("allele") float allele);

	@Query(value = "(SELECT sequence FROM Forenseq WHERE Locus = :locus && Allele = :allele && sample_year = :sampleYear && sample_id=:sampleId LIMIT 1) "
			+ "UNION (SELECT sequence FROM Forenseq WHERE Locus = :locus && Allele = :allele && sample_year = :sampleYear && sample_id=:sampleId LIMIT 1) "
			+ "UNION (SELECT sequence FROM Forenseq WHERE Locus = :locus && Allele = :allele && sample_year = :sampleYear && sample_id=:sampleId LIMIT 1)", nativeQuery = true)
	public String findAllForenseqTable(@Param("locus") String locus, @Param("allele") float allele,
			@Param("sampleId") String sampleId, @Param("sampleYear") String sampleYear);

	@Query(value = "SELECT isd.Locus, isd.Allele, COUNT(*) AS Amount FROM isnpdata isd GROUP BY isd.Locus, isd.Genotype ORDER BY isd.Locus;", nativeQuery = true)
	public List<Object[]> findISNPStat();

	@Query(value = "(SELECT DISTINCT Locus , Allele FROM Forenseq ORDER BY Locus) UNION (SELECT DISTINCT Locus , Allele FROM ForenseqX ORDER BY Locus) UNION (SELECT DISTINCT Locus , Allele FROM ForenseqY ORDER BY Locus)", nativeQuery = true)
	public List<Object[]> findAlleleInfo();

	@Query(value = "(select * from (select Allele, Province , count(*) from forenseq inner join person ON forenseq.sample_id = person.sample_id && forenseq.sample_year = person.sample_year WHERE Locus = :locus GROUP BY Allele, Province ORDER BY Allele) Table1 inner join Provinces ON Table1.Province = Provinces.engName) "
			+ "union (select * from (select Allele, Province , count(*) from forenseqY inner join person ON forenseqY.sample_id = person.sample_id && forenseqY.sample_year = person.sample_year WHERE Locus = :locus GROUP BY Allele, Province ORDER BY Allele) Table1 inner join Provinces ON Table1.Province = Provinces.engName) "
			+ "union (select * from (select Allele, Province , count(*) from forenseqX inner join person ON forenseqX.sample_id = person.sample_id && forenseqX.sample_year = person.sample_year WHERE Locus = :locus GROUP BY Allele, Province ORDER BY Allele) Table1 inner join Provinces ON Table1.Province = Provinces.engName);", nativeQuery = true)
	public List<Object[]> findAlleleMap(@Param("locus") String locus);

	@Query(value = "select pattern from Pattern_Alignment WHERE Locus = :locus order by seqNo;", nativeQuery = true)
	public List<String> findMotifLocus(@Param("locus") String locus);
}
