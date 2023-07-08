package curso.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Curriculo;
import curso.springboot.model.Pessoa;
import curso.springboot.repository.CurriculoRepository;

@Service
public class CurriculoService {

	private final CurriculoRepository curriculoRepository;
	
	//Construtor da classe
	@Autowired
	public CurriculoService(CurriculoRepository curriculoRepository) {
		this.curriculoRepository = curriculoRepository;
	}
	
/**************************************************************************************************
 * Implementação dos métodos da interface CurriculoRepository
 * ************************************************************************************************/
	
	@Transactional (readOnly = true)
	public Curriculo findById(Long id) throws Exception{
		return curriculoRepository.findById(id).orElseThrow(() -> new Exception("Currículo não encontrado!"));
	}
	
	@Transactional (readOnly = true)
	public List<Curriculo> findAllCurriculo() throws Exception{
		return curriculoRepository.findAll();
	}
	
	@Transactional (readOnly = true)
	public Curriculo findCurriculoByByteContent(byte[] curriculoBytes) throws Exception{
		return curriculoRepository.findCurriculoByByteContent(curriculoBytes);
	}
	
	@Transactional (readOnly = true)
	public Curriculo findCurriculoByPessoa(Pessoa pessoa) throws Exception{
		return curriculoRepository.findCurriculoByPessoa(pessoa);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public Curriculo save(Curriculo curriculo) throws Exception{
		return curriculoRepository.save(curriculo);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public void delete(Long id) throws Exception{
		curriculoRepository.deleteById(id);
	}
	
}