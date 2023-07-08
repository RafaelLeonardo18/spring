package curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import curso.springboot.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long>, JpaSpecificationExecutor<Telefone> {

}
