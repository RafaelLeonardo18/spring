package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import curso.springboot.model.Pessoa;
import curso.springboot.service.PessoaService;
import curso.springboot.util.ReportUtil;

@Controller
public class ReportPrinterController {
	
	private final ReportUtil reportUtil;
	private final PessoaService pessoaService;
	
	@Autowired
	public ReportPrinterController(ReportUtil reportUtil, PessoaService pessoaService) {
		this.reportUtil = reportUtil;
		this.pessoaService = pessoaService;
	}
	
	//Gera o relatório de pessoas para download
	@GetMapping ("**/relatoriopessoas")
	public void imprimirRelatório(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List <Pessoa> listaPessoas = new ArrayList<Pessoa>();
		for (Pessoa p : pessoaService.findAllPessoa()) {
			if (p.getLogin() == null) {
				p.setLogin("Não informado");
			}
			listaPessoas.add(p);
		}
		byte [] arquivoPDF = reportUtil.gerarRelatorio(listaPessoas, "Pessoas", request.getServletContext());
		//Tamanho do conteúdo da resposta
		response.setContentLength(arquivoPDF.length);
		//Define o tipo do arquivo do download
		response.setContentType("application/octet-stream");
		//Define as variáveis do cabeçalho
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		response.setHeader(headerKey, headerValue);
		//Finaliza a resposta para o navegador
		response.getOutputStream().write(arquivoPDF);
	}

}