package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.service.PessoaService;
import curso.springboot.service.TelefoneService;

@Controller
public class TelefoneController {
	
	private final PessoaService pessoaService;
	
	private final TelefoneService telefoneService;
	
	//Construtor da classe
	@Autowired
	public TelefoneController(PessoaService pessoaService, TelefoneService telefoneService) {
		this.pessoaService = pessoaService;
		this.telefoneService = telefoneService;
	}
	
/**********************************************************************************************
 * Métodos da tela de telefones
 * ********************************************************************************************/
	
	//Redireciona para a tela de telefones do usuário
	@RequestMapping (method = RequestMethod.GET, value = "**/telefone/{idPessoa}")
	public ModelAndView telaTelefone (@PathVariable ("idPessoa") Long idPessoa) throws Exception {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefone");
		Pessoa pessoa = pessoaService.findById(idPessoa);
		modelAndView.addObject("objetoPessoa", pessoa);
		return modelAndView;
	}
		
	//Lista os telefones cadastrados do usuário
	@RequestMapping (method = RequestMethod.GET, value = "**/listartelefones/{idPessoa}")
	public ModelAndView carregaTelefones(@PathVariable("idPessoa") Long idPessoa) throws Exception {
		ModelAndView modelView = new ModelAndView("cadastro/telefone");
		modelView.addObject("telefones", pessoaService.findById(idPessoa).getTelefones());
		modelView.addObject("objetoPessoa", pessoaService.findById(idPessoa));
		modelView.addObject("numero", "Número");
		modelView.addObject("tipo", "Tipo");
		return modelView;
	}
		
	//Grava um telefone para o usuário selecionado
	@RequestMapping (method = RequestMethod.POST, value = "**/addTelefone/{idPessoa}")
	public ModelAndView gravaTelefone (@PathVariable ("idPessoa") Long idPessoa, Telefone telefone) throws Exception {
		ModelAndView modelView = new ModelAndView ("cadastro/telefone");
		telefone.setPessoa(pessoaService.findById(idPessoa));
		telefoneService.save(telefone);
		Pessoa pessoa = pessoaService.findById(idPessoa);
		modelView.addObject("objetoPessoa", pessoa);
		return modelView;
	}
		
	//Exclui um telefone de um usuário
	@RequestMapping (method = RequestMethod.GET, value = "**/removertelefone/{idTelefone}")
	public ModelAndView excluiTelefone(@PathVariable("idTelefone") Long idTelefone) throws Exception {
		ModelAndView modelView = new ModelAndView("cadastro/telefone");
		Telefone telefone = telefoneService.findById(idTelefone);
		telefoneService.delete(idTelefone);
		modelView.addObject("objetoPessoa", telefone.getPessoa());
		return modelView;
	}

}