package br.edu.vet.vetbackend.repositorios;

import br.edu.vet.vetbackend.entidades.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByVeterinarioIdAndDataHora(Long veterinarioId, LocalDateTime dataHora);

    List<Agendamento> findByPacienteId(Long pacienteId);

    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}