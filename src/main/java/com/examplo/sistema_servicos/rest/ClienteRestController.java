package com.examplo.sistema_servicos.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examplo.sistema_servicos.model.Cliente;
import com.examplo.sistema_servicos.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200") // Permite chamadas de qualquer origem (útil para o Angular)
public class ClienteRestController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Lista todos os clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Busca um cliente pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Adiciona um novo cliente
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    // Atualiza um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cliente.setId(id);
        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // Deleta um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
