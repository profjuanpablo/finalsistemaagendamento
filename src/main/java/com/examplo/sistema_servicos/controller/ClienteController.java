package com.examplo.sistema_servicos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.examplo.sistema_servicos.model.Cliente;
import com.examplo.sistema_servicos.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Lista todos os clientes
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/lista"; // Template Thymeleaf para listar clientes
    }

    // Mostra o formulário para cadastrar um novo cliente
    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario"; // Template para o formulário de cliente
    }

    // Salva um novo cliente ou atualiza um existente
    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    // Edita um cliente existente
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "clientes/formulario";
        } else {
            return "redirect:/clientes";
        }
    }

    // Deleta um cliente
    @GetMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }

    // API REST: Retorna todos os clientes
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Cliente>> listarClientesAPI() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }
}
