package curso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Telefone;
import curso.springboot.repository.TelefoneRepository;

/*********************************************************************************************
 * Descrição: Classe de serviço que faz a implementação do repositório da entidade Telefone
 * Autor: Rafael Leonardo de Lima
 * Data: 19/03/2021
 * *******************************************************************************************/

@Service
public class TelefoneService {

	private final TelefoneRepository telefoneRepository;
	
	@Autowired
	public TelefoneService(TelefoneRepository telefoneRepository) {
		this.telefoneRepository = telefoneRepository;
	}
	
/*********************************************************************************************
 * Implementação dos métodos da interface LogradouroRepository
 * *******************************************************************************************/

	@Transactional (readOnly = true)
	public Telefone findById (Long id) throws Exception{
		return telefoneRepository.findById(id).orElseThrow(() -> new Exception ("Telefone não encontrado!"));
	}
	
	@Transactional (readOnly = true)
	public List<Telefone> findAllTelefone () throws Exception{
		return telefoneRepository.findAll();
	}
	
	@Transactional (rollbackFor = Exception.class)
	public Telefone save (Telefone telefone) throws Exception{
		return telefoneRepository.save(telefone);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public void delete (Long id) throws Exception {
		telefoneRepository.deleteById(id);
	}
}