package curso.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Role;
import curso.springboot.repository.RoleRepository;

/*********************************************************************************************
 * Descrição: Classe de serviço que faz a implementação do repositório da entidade Role
 * Autor: Rafael Leonardo de Lima
 * Data: 20/03/2021
 * *******************************************************************************************/

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
/*********************************************************************************************
 * Implementação dos métodos da interface RoleRepository
 * *******************************************************************************************/	
	
	@Transactional (readOnly = true)
	public Role findById(Long id) throws Exception{
		return roleRepository.findById(id).orElseThrow(() -> new Exception ("Perfil não encontrado"));
	}
	
	@Transactional (readOnly = true)
	public List <Role> findAllRole () throws Exception{
		List <Role> roles = new ArrayList<Role>();
		for (Role r : roleRepository.findAll()) {
			r.setRoleName(r.getRoleName().split("_")[1]);
			roles.add(r);
		}
		return roles;
	}
	
	@Transactional (rollbackFor = Exception.class)
	public Role save (Role role) throws Exception{
		return roleRepository.save(role);
	}
	
	@Transactional (rollbackFor = Exception.class)
	public void delete(Long id) throws Exception{
		roleRepository.deleteById(id);
	}
}