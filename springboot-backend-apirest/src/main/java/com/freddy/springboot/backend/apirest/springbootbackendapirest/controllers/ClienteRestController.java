package com.freddy.springboot.backend.apirest.springbootbackendapirest.controllers;
import com.freddy.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.freddy.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        //MANEJO DE ERRORES

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null) {
            response.put("mensaje", "El cliente id: ".concat(id.toString().concat(" no existe en la bd")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente nuevoCliente = null;
        Map<String, Object> response = new HashMap<>();

        //MANEJO DE ERRORES

        try {
            nuevoCliente = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "el cliente ha sido creado con exito");
        response.put("cliente", nuevoCliente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente ,@PathVariable Long id){

        //MANEJO DE ERRORES

        Cliente clienteActual = clienteService.findById(id);
        Cliente update = null;
        Map<String, Object> response = new HashMap<>();

        if(clienteActual == null) {
            response.put("mensaje", "El cliente id: ".concat(id.toString().concat(" no existe en la bd")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            update = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al actualizar la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "el cliente ha sido actualizado con exito");
        response.put("cliente", update);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        //MANEJO DE ERRORES

        Map<String, Object> response = new HashMap<>();

        try{
            clienteService.delete(id);
        } catch (DataAccessException e){
            response.put("mensaje", "error al borrar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "cliente eliminado con exito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
