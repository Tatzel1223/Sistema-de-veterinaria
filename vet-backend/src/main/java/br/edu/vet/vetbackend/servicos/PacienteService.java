package br.edu.vet.vetbackend.servicos;

import br.edu.vet.vetbackend.dto.PacienteRequest;
import br.edu.vet.vetbackend.dto.PacienteResponse;
import br.edu.vet.vetbackend.entidades.Paciente;
import br.edu.vet.vetbackend.excecoes.RecursoNaoEncontradoException;
import br.edu.vet.vetbackend.repositorios.PacienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final PacienteRepository repository;
    private final ClienteService clienteService;

    public PacienteService(PacienteRepository repository, ClienteService clienteService) {
        this.repository = repository;
        this.clienteService = clienteService;
    }

    // filtros opcionais: por dono ou por parte do nome
    @Transactional(readOnly = true)
    public List<PacienteResponse> listar(Long donoId, String nome) {
        List<Paciente> lista;
        if (donoId != null) {
            lista = repository.findByDonoIdOrderByNomeAsc(donoId);
        } else if (nome != null && !nome.isBlank()) {
            lista = repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome.trim());
        } else {
            lista = repository.findAllByOrderByNomeAsc();
        }
        return lista.stream().map(PacienteResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscar(Long id) {
        return PacienteResponse.de(buscarEntidade(id));
    }

    @Transactional
    public PacienteResponse cadastrar(PacienteRequest req) {
        Paciente p = new Paciente();
        copiar(req, p);
        return PacienteResponse.de(repository.save(p));
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest req) {
        Paciente p = buscarEntidade(id);
        copiar(req, p);
        return PacienteResponse.de(p);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Paciente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente " + id + " não encontrado."));
    }

    private void copiar(PacienteRequest req, Paciente p) {
        p.setNome(req.nome().trim());
        p.setEspecie(req.especie().trim());
        p.setRaca(req.raca());
        p.setIdade(req.idade());
        p.setPeso(req.peso());
        p.setDono(clienteService.buscarEntidade(req.donoId()));
        p.setHistoricoMedico(req.historicoMedico());
        p.setAlergias(req.alergias());
        p.setMedicamentosAtuais(req.medicamentosAtuais());
    }
}
