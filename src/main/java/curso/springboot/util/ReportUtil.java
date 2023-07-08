package curso.springboot.util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Retorna um array de bytes para ser descarregado pelo navegador
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte [] gerarRelatorio(List dados, String relatorio, ServletContext servletContext ) throws Exception {
		//Prepara a lista de objetos para ser impressa no relatório
		JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(dados);
		//Carrega o diretório do relatório jasper
		String caminhoJasper = servletContext.getRealPath("reports") + File.separator + relatorio + ".jasper";
		//Preenche os dados do relatório
		JasperPrint jasperPrinter = JasperFillManager.fillReport(caminhoJasper, new HashMap(), collection);
		//Realiza a exportação do arquivo em formato PDF
		return JasperExportManager.exportReportToPdf(jasperPrinter);
	}

}