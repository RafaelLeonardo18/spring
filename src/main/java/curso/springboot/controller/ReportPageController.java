package curso.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportPageController {
	
	//Direciona para a página de relatórios
	@GetMapping ("/relatorios")
	public String relatorio() {
		return "relatorio/relatorios";
	}

}