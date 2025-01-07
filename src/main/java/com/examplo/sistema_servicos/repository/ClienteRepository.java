package com.examplo.sistema_servicos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examplo.sistema_servicos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos customizados podem ser adicionados aqui, se necessário
}
