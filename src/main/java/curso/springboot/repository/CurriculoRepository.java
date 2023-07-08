package curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.springboot.model.Curriculo;
import curso.springboot.model.Pessoa;

@Repository
public interface CurriculoRepository extends JpaRepository<Curriculo, Long>{
	
	@Query("select c from Curriculo c where c.curriculoBytes = ?1")
	public Curriculo findCurriculoByByteContent(byte[] curriculoBytes);
	
	@Query("select c from Curriculo c where c.pessoa = ?1")
	public Curriculo findCurriculoByPessoa (Pessoa pessoa);

}