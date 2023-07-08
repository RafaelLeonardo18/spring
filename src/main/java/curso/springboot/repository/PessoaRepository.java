package curso.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.springboot.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {
	
	//Método dinâmico de pesquisa por nome
	@Query ("select p from Pessoa p where p.nome like ?1%")
	public List <Pessoa> findAllPessoaByName(String nome);
	
	//Busca um usuário pelo login
	@Query("select p from Pessoa p where p.login = ?1")
	public Pessoa findPessoaByLogin(String login);
	
	//Faz a autenticação do login e senha
	@Query ("select p from Pessoa p where p.login = ?1 and p.senha = ?2")
	public Pessoa autenthicate(String login, String senha);
	
	//Método dinâmico de pesquisa por nome com paginação
	@Query ("select p from Pessoa p where p.nome like ?1%")
	public Page <Pessoa> findAllPessoaByNameWithPagination(String nome, Pageable pageable);

}