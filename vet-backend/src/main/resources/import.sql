-- Dados de teste carregados automaticamente pelo Hibernate ao iniciar.
-- Senha de todos os usuários: senha123 (gravada como hash BCrypt)
-- Cada comando precisa ficar em UMA linha só.

INSERT INTO veterinarios (nome, email, telefone, crmv, especialidade, senha) VALUES ('Dr. Carlos Silva', 'carlos@vetclinic.com', '(11) 98765-4321', 'CRMV-SP 12345', 'Clínica geral', '$2a$10$wyooDAdOCoz2/BRv2GHOFeJ54UMci5eIB.j62PFjbYWLh5oMmEICS');

INSERT INTO recepcionistas (nome, email, telefone, turno, senha) VALUES ('Ana Costa', 'ana@vetclinic.com', '(11) 97777-1111', 'Manhã', '$2a$10$wyooDAdOCoz2/BRv2GHOFeJ54UMci5eIB.j62PFjbYWLh5oMmEICS');

INSERT INTO clientes (nome, email, telefone, cpf, endereco, senha) VALUES ('Maria Santos', 'maria@email.com', '(11) 91234-5678', '123.456.789-00', 'Rua das Flores, 100', '$2a$10$wyooDAdOCoz2/BRv2GHOFeJ54UMci5eIB.j62PFjbYWLh5oMmEICS');
INSERT INTO clientes (nome, email, telefone, cpf, endereco, senha) VALUES ('João Oliveira', 'joao@email.com', '(11) 99876-5432', '987.654.321-00', 'Av. Brasil, 2500', '$2a$10$wyooDAdOCoz2/BRv2GHOFeJ54UMci5eIB.j62PFjbYWLh5oMmEICS');

-- Maria = cliente 1, João = cliente 2
INSERT INTO pacientes (nome, especie, raca, idade, peso, dono_id, historico_medico, alergias, medicamentos_atuais, data_cadastro) VALUES ('Rex', 'Cachorro', 'Labrador', 5, 30.5, 1, 'Vacinação em dia. Castrado em 2021.', 'Nenhuma alergia conhecida', NULL, '2026-10-01');
INSERT INTO pacientes (nome, especie, raca, idade, peso, dono_id, historico_medico, alergias, medicamentos_atuais, data_cadastro) VALUES ('Mimi', 'Gato', 'Persa', 3, 4.2, 1, 'Vacinação em dia.', 'Alérgica a frango', NULL, '2026-10-01');
INSERT INTO pacientes (nome, especie, raca, idade, peso, dono_id, historico_medico, alergias, medicamentos_atuais, data_cadastro) VALUES ('Thor', 'Cachorro', 'Pastor Alemão', 7, 35.0, 2, 'Displasia coxofemoral leve.', 'Nenhuma alergia conhecida', 'Condroitina 500mg 1x ao dia', '2026-10-01');
