package curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.springboot.model.Logradouro;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Long>, JpaSpecificationExecutor<Logradouro> {
	
	//Valida se o logradouro do formulário já existe no banco
	@Query ("select l from Logradouro l where l.bairro = ?1 and l.cep = ?2 and l.complemento = ?3 and l.localidade = ?4 "
			+ " and l.nomeLogradouro = ?5 and l.numeroLogradouro = ?6 and l.uf = ?7 ")
	public Logradouro findLogradouroIfExists(String bairro, String cep, String complemento, String localidade,
											 String nomeLogradouro, String numeroLogradouro, String uf);
	
}