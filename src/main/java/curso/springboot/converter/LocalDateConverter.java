package curso.springboot.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String data) {
		if (data != null && !data.isEmpty()) {
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(data, timeFormatter);
		} else {
			return null;
		}
	}
}