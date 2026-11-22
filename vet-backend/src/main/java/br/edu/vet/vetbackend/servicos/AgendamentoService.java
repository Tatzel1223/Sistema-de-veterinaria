package br.edu.vet.vetbackend.servicos;

import br.edu.vet.vetbackend.entidades.Agendamento;
import br.edu.vet.vetbackend.repositorios.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));
    }

    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getVeterinario() != null && agendamento.getVeterinario().getId() != null) {
            boolean conflito = repository.existsByVeterinarioIdAndDataHora(
                    agendamento.getVeterinario().getId(), 
                    agendamento.getDataHora()
            );

            if (conflito) {
                throw new RuntimeException("Conflito de horário: Este veterinário já possui um atendimento marcado para este horário!");
            }
        }

        return repository.save(agendamento);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public List<Agendamento> listarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }
}