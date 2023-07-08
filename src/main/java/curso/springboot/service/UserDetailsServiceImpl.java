package curso.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PessoaService pessoaService;
	
	@Autowired
	public  UserDetailsServiceImpl(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Pessoa pessoa = pessoaService.findPessoaByLogin(username);
		if (pessoa == null) {
			throw new UsernameNotFoundException("Usuário não identificado");
		}
		return new User(pessoa.getUsername(), pessoa.getPassword(), pessoa.isEnabled(), true, true, true, pessoa.getAuthorities());
	}

}