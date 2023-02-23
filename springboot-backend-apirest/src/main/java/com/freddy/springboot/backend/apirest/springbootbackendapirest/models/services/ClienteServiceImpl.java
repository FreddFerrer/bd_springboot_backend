package com.freddy.springboot.backend.apirest.springbootbackendapirest.models.services;

import com.freddy.springboot.backend.apirest.springbootbackendapirest.models.dao.IClienteDAO;
import com.freddy.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDAO clientedao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {

        return (List<Cliente>) clientedao.findAll();
    }
    @Transactional
    @Override
    public Cliente save(Cliente cliente) {
        return clientedao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Long id) {
        return clientedao.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        clientedao.deleteById(id);
    }
}
