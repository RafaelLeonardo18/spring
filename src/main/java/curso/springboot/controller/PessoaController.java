package curso.springboot.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Curriculo;
import curso.springboot.model.Logradouro;
import curso.springboot.model.Pessoa;
import curso.springboot.service.CurriculoService;
import curso.springboot.service.LogradouroService;
import curso.springboot.service.PessoaService;
import curso.springboot.service.RoleService;
import curso.springboot.util.DownloadUtil;

@Controller
public class PessoaController {

	private final PessoaService pessoaService;
	private final LogradouroService logradouroService;
	private final RoleService roleService;
	private final CurriculoService curriculoService;

	// Construtor da classe
	@Autowired
	public PessoaController(PessoaService pessoaService, LogradouroService logradouroService, RoleService roleService,
			CurriculoService curriculoService) {
		this.pessoaService = pessoaService;
		this.logradouroService = logradouroService;
		this.roleService = roleService;
		this.curriculoService = curriculoService;
	}

	/**********************************************************************************************
	 * Métodos da tela de cadastro de pessoas
	 ********************************************************************************************/

	// Carrega a lista de pessoas na tela
	@GetMapping(value = "/listarpessoas")
	public ModelAndView carregarPessoas() throws Exception {
		Page<Pessoa> pessoas = pessoaService.findAllPessoaDefaultPagination(PageRequest.of(0, 5, Sort.by("nome")));
		ModelAndView modelView = new ModelAndView("cadastro/cadastropessoa");
		modelView.addObject("objetoPessoa", new Pessoa());
		modelView.addObject("objetoLogradouro", new Logradouro());
		modelView.addObject("roles", roleService.findAllRole());
		modelView.addObject("pessoas", pessoas);
		return modelView;
	}

	// Carrega a lista de pessoas na tela
	@GetMapping(value = "/pagepessoas")
	public ModelAndView carregarPessoasPaginadas(@PageableDefault(size = 5, sort = "nome") Pageable pageable,
												 @RequestParam ("pesquisaNome") String pesquisaNome) throws Exception {
		Page<Pessoa> pessoas = null;
		if (pesquisaNome != null && !pesquisaNome.isBlank()) {
			pessoas = pessoaService.findAllPessoaByNameWithPagination(pesquisaNome, pageable);
		} else {
			pessoas = pessoaService.findAllPessoaDefaultPagination(pageable);
		}
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("objetoPessoa", new Pessoa());
		modelAndView.addObject("objetoLogradouro", new Logradouro());
		modelAndView.addObject("roles", roleService.findAllRole());
		modelAndView.addObject("pessoas", pessoas);
		modelAndView.addObject("pesquisaNome", pesquisaNome);
		return modelAndView;
	}

	// Salva o registro de uma pessoa no banco de dados
	@PostMapping(value = "**/salvarpessoa", consumes = { "multipart/form-data" })
	public ModelAndView salvarPessoa(Pessoa pessoa, Logradouro logradouro, final MultipartFile file) throws Exception {
		// Verifica se o logradouro já existe no banco de dados
		logradouro = logradouroService.findLograduroIfExists(logradouro);
		logradouro = logradouroService.save(logradouro);
		pessoa.setLogradouro(logradouro);
		pessoa = pessoaService.save(pessoa);
		if (file != null && !file.isEmpty()) {
			Curriculo curriculo = new Curriculo();
			curriculo.setNomeArquivo(file.getOriginalFilename());
			curriculo.setExtensao(file.getContentType());
			curriculo.setCurriculoBytes(file.getBytes());
			curriculo.setPessoa(pessoa);
			curriculo.setDataEnvio(LocalDate.now());
			curriculoService.save(curriculo);
		}
		return this.carregarPessoas();
	}

	// Põe um usuário em edição
	@GetMapping(value = "/editarpessoa/{idPessoa}")
	public ModelAndView editar(@PathVariable("idPessoa") Long idPessoa, 
							   @PageableDefault(sort = "nome", size = 5) Pageable pageable) throws Exception {
		ModelAndView modelView = new ModelAndView("cadastro/cadastropessoa");
		Page<Pessoa> pessoas = pessoaService.findAllPessoaDefaultPagination(pageable);
		Pessoa pessoa = pessoaService.findById(idPessoa);
		modelView.addObject("objetoPessoa", pessoa);
		modelView.addObject("pessoas", pessoas);
		modelView.addObject("roles", roleService.findAllRole());
		if (pessoa.getLogradouro() != null) {
			modelView.addObject("objetoLogradouro", pessoa.getLogradouro());
		} else {
			modelView.addObject("objetoLogradouro", new Logradouro());
		}
		return modelView;
	}

	// Exclui um usuário cadastrado
	@GetMapping(value = "/removerpessoa/{idPessoa}")
	public ModelAndView excluir(@PathVariable("idPessoa") Long idPessoa, 
								@PageableDefault(size = 5, sort = "nome")  Pageable pageable) throws Exception {
		ModelAndView modelView = new ModelAndView("cadastro/cadastropessoa");
		pessoaService.delete(idPessoa);
		Page<Pessoa> pessoas = pessoaService.findAllPessoaDefaultPagination(pageable);
		modelView.addObject("objetoPessoa", new Pessoa());
		modelView.addObject("objetoLogradouro", new Logradouro());
		modelView.addObject("roles", roleService.findAllRole());
		modelView.addObject("pessoas", pessoas);
		return modelView;
	}

	// Pesquisa uma lista de usuários de acordo com o valor digitado pelo usuário
	@PostMapping(value = "**/pesquisarnomes")
	public ModelAndView pesquisarNomes(@RequestParam("pesquisaNome") String pesquisaNome, 
									   @PageableDefault(size = 5, sort = "nome") Pageable pageable) throws Exception {
		ModelAndView modelView = new ModelAndView("cadastro/cadastropessoa");
		Page<Pessoa> pessoas = pessoaService.findAllPessoaByNameWithPagination(pesquisaNome, pageable);
		modelView.addObject("objetoPessoa", new Pessoa());
		modelView.addObject("objetoLogradouro", new Logradouro());
		modelView.addObject("roles", roleService.findAllRole());
		modelView.addObject("pessoas", pessoas);
		modelView.addObject("pesquisaNome", pesquisaNome);
		return modelView;
	}

	// Redireciona um usuário selecionado para a tela de detalhes
	@GetMapping(value = "**/detalhes/{idPessoa}")
	public ModelAndView detalhar(@PathVariable("idPessoa") Long idPessoa) throws Exception {
		ModelAndView modelAndView = new ModelAndView("cadastro/detalhes");
		Pessoa pessoa = pessoaService.findById(idPessoa);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataNascimentoFormatada = pessoa.getDataNascimento().format(timeFormatter);
		modelAndView.addObject("objetoPessoa", pessoa);
		modelAndView.addObject("dataNascimento", dataNascimentoFormatada);
		return modelAndView;
	}

	// Faz o download do curriculo de um usuário
	@GetMapping(value = "**/baixarcurriculo/{idPessoa}")
	public void baixarCurriculo(@PathVariable("idPessoa") Long idPessoa, HttpServletResponse response) throws Exception {
		Pessoa pessoa = pessoaService.findById(idPessoa);
		Curriculo curriculo = curriculoService.findCurriculoByPessoa(pessoa);
		if (curriculo != null) {
			DownloadUtil.download(curriculo, response);
		} else {
			response.setStatus(404);
			response.getOutputStream().print("Arquivo não encontrado!");
		}
	}

}