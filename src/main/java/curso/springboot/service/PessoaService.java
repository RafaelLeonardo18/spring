package curso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

/*********************************************************************************************
 * Descrição: Classe de serviço que faz a implementação do repositório da entidade Pessoa
 * Autor: Rafael Leonardo de Lima
 * Data: 18/03/2021
 * *******************************************************************************************/

@Service
public class PessoaService {

	private final PessoaRepository pessoaRepository;
	
	//Construtor da classe
	@Autowired
	public PessoaService (PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
/*********************************************************************************************
 * Implementação dos métodos da interface PessoaRepository
 * *******************************************************************************************/
	
	@Transactional (readOnly = true)
	public Pessoa findById(Long id) throws Exception {
		return pessoaRepository.findById(id).orElseThrow(() -> new Exception ("Pessoa não encontrada"));
	}
	
	@Transactional (readOnly = true)
	public Pessoa findPessoaByLogin(String login) {
		return pessoaRepository.findPessoaByLogin(login);
	}
	
	@Transactional (readOnly = true)
	public Pessoa autenthicate (String login, String senha) {
		return pessoaRepository.autenthicate(login, senha);
	}
	
	@Transactional (readOnly = true)
	public List<Pessoa> findAllPessoa() throws Exception{
		return pessoaRepository.findAll();
	}
	
	@Transactional (readOnly = true)
	public List<Pessoa> findAllPessoaByName(String nome) throws Exception{
		return pessoaRepository.findAllPessoaByName(nome);
	}
	
	@Transactional (readOnly = true)
	public Page<Pessoa> findAllPessoaDefaultPagination(Pageable pageable) throws Exception {
		return pessoaRepository.findAll(pageable);
	}
	
	@Transactional (readOnly = true)
	public Page<Pessoa> findAllPessoaByNameWithPagination(String name, Pageable pageable) throws Exception{
		return pessoaRepository.findAllPessoaByNameWithPagination(name, pageable);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public Pessoa save(Pessoa pessoa) throws Exception {
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public void delete(Long id) throws Exception {
		pessoaRepository.deleteById(id);
	}
}