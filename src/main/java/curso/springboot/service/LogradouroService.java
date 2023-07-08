package curso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Logradouro;
import curso.springboot.repository.LogradouroRepository;

/*********************************************************************************************
 * Descrição: Classe de serviço que faz a implementação do repositório da entidade Logradouro
 * Autor: Rafael Leonardo de Lima
 * Data: 18/03/2021
 * *******************************************************************************************/

@Service
public class LogradouroService {

	private final LogradouroRepository logradouroRepository;
	
	//Construtor da classe
	@Autowired
	public LogradouroService(LogradouroRepository logradouroRepository) {
		this.logradouroRepository = logradouroRepository;
	}
	
/*********************************************************************************************
 * Implementação dos métodos da interface LogradouroRepository
 * *******************************************************************************************/
	
	@Transactional (readOnly = true)
	public Logradouro findById(Long id) throws Exception{
		return logradouroRepository.findById(id).orElseThrow(() -> new Exception ("Logradouro não encontrado"));
	}
	
	@Transactional (readOnly = true)
	public Logradouro findLograduroIfExists (Logradouro logradouro) throws Exception{
		Logradouro logradouroSearch = logradouroRepository.findLogradouroIfExists(logradouro.getBairro(), logradouro.getCep(), logradouro.getComplemento(),
									  logradouro.getLocalidade(), logradouro.getNomeLogradouro(), logradouro.getNumeroLogradouro(),logradouro.getUf());
		if (logradouroSearch != null && logradouroSearch.equals(logradouro)) {
			logradouro = logradouroSearch;
		}
		return logradouro;
	}
	
	@Transactional (readOnly = true)
	public List<Logradouro> findAllLogradouro() throws Exception{
		return logradouroRepository.findAll();
	}
	
	@Transactional (rollbackFor = Exception.class)
	public Logradouro save(Logradouro logradouro) throws Exception{
		return logradouroRepository.save(logradouro);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public void delete (Long id) throws Exception{
		logradouroRepository.deleteById(id);
	}
}
