package curso.springboot.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import curso.springboot.model.Curriculo;

@Service
public class DownloadUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static void download(Curriculo curriculo, HttpServletResponse response) throws Exception{
		response.setContentLength(curriculo.getCurriculoBytes().length);
		response.setContentType(curriculo.getExtensao());
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", curriculo.getNomeArquivo());
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(curriculo.getCurriculoBytes());
	}
	
}