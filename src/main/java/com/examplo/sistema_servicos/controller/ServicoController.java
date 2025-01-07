package com.examplo.sistema_servicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.examplo.sistema_servicos.model.Servico;
import com.examplo.sistema_servicos.repository.ClienteRepository;
import com.examplo.sistema_servicos.repository.ServicoRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Lista todos os serviços
    @GetMapping
    public String listarServicos(Model model) {
        List<Servico> servicos = servicoRepository.findAll();
        model.addAttribute("servicos", servicos);
        return "servicos/lista"; // Template Thymeleaf para listar serviços
    }

    // Mostra o formulário para cadastrar um novo serviço
    @GetMapping("/novo")
    public String novoServicoForm(Model model) {
        model.addAttribute("servico", new Servico());
        model.addAttribute("clientes", clienteRepository.findAll()); // Lista de clientes
        return "servicos/formulario";
    }

    // Salva um novo serviço ou atualiza um existente
    @PostMapping("/salvar")
    public String salvarServico(@ModelAttribute Servico servico) {
        servicoRepository.save(servico);
        return "redirect:/servicos";
    }

    // Edita um serviço existente
    @GetMapping("/editar/{id}")
    public String editarServico(@PathVariable Long id, Model model) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if (servico.isPresent()) {
            model.addAttribute("servico", servico.get());
            model.addAttribute("clientes", clienteRepository.findAll());
            return "servicos/formulario";
        } else {
            return "redirect:/servicos";
        }
    }

    // Deleta um serviço
    @GetMapping("/deletar/{id}")
    public String deletarServico(@PathVariable Long id) {
        servicoRepository.deleteById(id);
        return "redirect:/servicos";
    }

    // API REST: Retorna todos os serviços
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Servico>> listarServicosAPI() {
        List<Servico> servicos = servicoRepository.findAll();
        return ResponseEntity.ok(servicos);
    }
}
