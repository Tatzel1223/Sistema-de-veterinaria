package br.edu.vet.vetbackend.servicos;

import br.edu.vet.vetbackend.dto.VeterinarioRequest;
import br.edu.vet.vetbackend.dto.VeterinarioResponse;
import br.edu.vet.vetbackend.entidades.Veterinario;
import br.edu.vet.vetbackend.excecoes.RecursoNaoEncontradoException;
import br.edu.vet.vetbackend.excecoes.RegraNegocioException;
import br.edu.vet.vetbackend.repositorios.VeterinarioRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repository;
    private final EmailUnicoService emailUnico;
    private final PasswordEncoder encoder;

    public VeterinarioService(VeterinarioRepository repository, EmailUnicoService emailUnico,
                              PasswordEncoder encoder) {
        this.repository = repository;
        this.emailUnico = emailUnico;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listar() {
        return repository.findAll(Sort.by("nome")).stream().map(VeterinarioResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public VeterinarioResponse buscar(Long id) {
        return VeterinarioResponse.de(buscarEntidade(id));
    }

    @Transactional
    public VeterinarioResponse cadastrar(VeterinarioRequest req) {
        if (req.senha() == null || req.senha().isBlank()) {
            throw new RegraNegocioException("A senha é obrigatória no cadastro.");
        }
        emailUnico.garantirDisponivel(req.email());
        garantirCrmvDisponivel(req.crmv());

        Veterinario v = new Veterinario();
        copiar(req, v);
        v.setSenha(encoder.encode(req.senha()));
        return VeterinarioResponse.de(repository.save(v));
    }

    @Transactional
    public VeterinarioResponse atualizar(Long id, VeterinarioRequest req) {
        Veterinario v = buscarEntidade(id);
        if (!v.getEmail().equalsIgnoreCase(req.email())) {
            emailUnico.garantirDisponivel(req.email());
        }
        if (!v.getCrmv().equalsIgnoreCase(req.crmv())) {
            garantirCrmvDisponivel(req.crmv());
        }
        copiar(req, v);
        if (req.senha() != null && !req.senha().isBlank()) {
            v.setSenha(encoder.encode(req.senha()));
        }
        return VeterinarioResponse.de(v);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Veterinario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veterinário " + id + " não encontrado."));
    }

    private void garantirCrmvDisponivel(String crmv) {
        if (repository.existsByCrmvIgnoreCase(crmv)) {
            throw new RegraNegocioException("Já existe um veterinário com o CRMV " + crmv + ".");
        }
    }

    private void copiar(VeterinarioRequest req, Veterinario v) {
        v.setNome(req.nome().trim());
        v.setEmail(req.email().trim().toLowerCase());
        v.setTelefone(req.telefone());
        v.setCrmv(req.crmv().trim());
        v.setEspecialidade(req.especialidade());
    }
}
