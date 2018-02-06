package br.com.digidev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomUserRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    void deleteByIdAndUserLogin(Long id, String login);

    Page<T> findAllByUserLogin(Pageable pageable,String login);

    T findOneByIdAndUserLogin(Long id, String login);
}
