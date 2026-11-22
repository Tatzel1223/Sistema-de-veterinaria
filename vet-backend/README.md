# vet-backend

API REST do sistema de clínica veterinária (Spring Boot 4 + JPA + H2), com assistente de IA (Gemini).

## Como rodar

```bash
# opcional: só a rota /api/ia precisa
export GEMINI_API_KEY=sua_chave
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O banco H2 fica em memória e é recriado a cada inicialização com os dados do `src/main/resources/import.sql`.
Para ver as tabelas, abra `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:vetdb`, usuário `sa`, sem senha).

Usuários de teste (senha `senha123`):

| E-mail | Tipo |
|---|---|
| carlos@vetclinic.com | veterinário |
| ana@vetclinic.com | recepcionista |
| maria@email.com | cliente (dona do Rex e da Mimi) |
| joao@email.com | cliente (dono do Thor) |

## Estrutura

```
entidades/     Cliente, Paciente, Veterinario, Recepcionista (JPA)
repositorios/  interfaces JpaRepository de cada entidade
servicos/      regras de negócio (validações, hash de senha, login)
controllers/   endpoints REST
dto/           objetos de entrada (Request) e saída (Response) da API
excecoes/      exceções e tratador global de erros (JSON padronizado)
config/        bean do BCrypt
ia/            integração com o Gemini
```

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | /api/auth/login | Login (retorna id, nome, email, telefone, tipo) |
| GET | /api/pacientes?donoId=&nome= | Lista pacientes (filtros opcionais) |
| GET | /api/pacientes/{id} | Busca paciente |
| POST | /api/pacientes | Cadastra paciente |
| PUT | /api/pacientes/{id} | Atualiza paciente |
| DELETE | /api/pacientes/{id} | Exclui paciente |
| GET | /api/clientes?nome= | Lista clientes |
| GET | /api/clientes/{id} | Busca cliente |
| GET | /api/clientes/{id}/pacientes | Pets do cliente |
| POST / PUT / DELETE | /api/clientes[/{id}] | CRUD de clientes (não exclui cliente com pets) |
| GET / POST / PUT / DELETE | /api/veterinarios[/{id}] | CRUD de veterinários |
| GET / POST / PUT / DELETE | /api/recepcionistas[/{id}] | CRUD de recepcionistas |
| POST | /api/ia | Pergunta à IA sobre um paciente |

Erros voltam assim: `{"dataHora": "...", "status": 400, "mensagem": "Dados inválidos.", "campos": {"nome": "Nome é obrigatório"}}`
(400 validação, 401 login inválido, 404 não encontrado, 409 conflito, por exemplo e-mail repetido).

## Postman

Importe `postman/Sistema_Vet.postman_collection.json` no Postman (Import → arquivo). A variável `baseUrl` já aponta para `http://localhost:8080/api`.
Cada requisição tem testes automáticos de status. Rode tudo pelo **Collection Runner**: os cadastros salvam o id criado e as requisições seguintes usam esse id.
