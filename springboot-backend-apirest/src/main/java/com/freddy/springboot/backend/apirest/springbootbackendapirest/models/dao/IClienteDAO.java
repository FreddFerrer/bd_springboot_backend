package com.freddy.springboot.backend.apirest.springbootbackendapirest.models.dao;

import com.freddy.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDAO extends CrudRepository<Cliente, Long> {
}
