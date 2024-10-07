# Projeto Aletheia

### Integrantes:

Natan Junior Rodrigues Lopes   RM: 552626

Pedro Lucca Medeiros Miranda   RM: 553873

Pedro Moreira de Jesus         RM: 553912

## Apresentação do projeto

A Aletheia trata-se de um sistema que tem
o objetivo de colher informações de clientes,
médicos e consultas, para posteriormente utilizando
Inteligência Artificial e Ciência de Dados,
prever e classificar padrões de consultas que
indicam fraudes ou golpes que possam prejudicar
uma empresa.

Com o intuito de colher as informações que usaremos
para criar nosso Dataset, nós prototipamos um Frontend.
Um aplicativo mobile responsável por colher dados de
consultas, pacientes e dentistas. O backend de nossa
aplicação está sendo feito com Java utilizando o
Spring framework.

## Diagramas
##
### Diagrama das entidades da camada de modelo
##
![img.png](img.png)
##
### Diagrama lógico relacional do banco de dados
##
![img_1.png](img_1.png)

##

## Tabela de endpoints
##
| Categoria       | Método | Endpoint                      | Descrição                        |
|-----------------|--------|------------------------------|----------------------------------|
| **Dentists**    | GET    | `/api/dentists`              | Retorna todos os dentistas      |
|                 | GET    | `/api/dentists/{id}`         | Retorna um dentista específico  |
|                 | POST   | `/api/dentists`              | Cria um novo dentista           |
|                 | DELETE | `/api/dentists/{id}`         | Deleta um dentista específico   |
| **Patients**    | GET    | `/api/patients`              | Retorna todos os pacientes      |
|                 | GET    | `/api/patients/{id}`         | Retorna um paciente específico  |
|                 | POST   | `/api/patients`              | Cria um novo paciente           |
|                 | DELETE | `/api/patients/{id}`         | Deleta um paciente específico   |
| **Claims**      | GET    | `/api/claims`                | Retorna todas as reclamações    |
|                 | GET    | `/api/claims/{id}`           | Retorna uma reclamação específica|
|                 | POST   | `/api/claims`                | Cria uma nova reclamação        |
|                 | DELETE | `/api/claims/{id}`           | Deleta uma reclamação específica|
| **Consultations**| GET    | `/api/consultations`        | Retorna todas as consultas      |
|                 | GET    | `/api/consultations/{id}`    | Retorna uma consulta específica |
|                 | POST   | `/api/consultations`         | Cria uma nova consulta          |
|                 | DELETE | `/api/consultations/{id}`    | Deleta uma consulta específica  |

# Como Rodar

Siga os passos abaixo para configurar e rodar o projeto localmente:

### Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

- [Java JDK 21+](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/) para clonar o repositório

### Passos para Rodar

1. **Clone o Repositório**

   Abra o terminal e execute o seguinte comando:

   ```bash
   git clone https://github.com/seu-usuario/odontoprev-spring.git
   ```

2. **Navegue até o Diretório do Projeto**


   Acesse o diretório clonado:

   ```bash
   Copiar código
   cd odontoprev-spring
   ```
3. **Instale as Dependências**

   Execute o comando abaixo para baixar as dependências e compilar o projeto:

   ```bash
   Copiar código
   mvn clean install
   ```
4. **Execute o Projeto**

   Inicie a aplicação com o seguinte comando:

   ```bash
   Copiar código
   mvn spring-boot:run
   ```

5. **Acesse a API**

   Abra o navegador ou um cliente REST (como o Postman) e acesse:

   ```arduino
   Copiar código
   http://localhost:8080
   ```

6. **Testando os Endpoints**

   Você pode testar os endpoints da API diretamente no seu navegador ou utilizando ferramentas como Postman. A lista completa de endpoints está disponível na seção Endpoints da API deste README.

## Link para o vídeo pitch do projeto

[Acesse clicando aqui](https://youtu.be/MHIPHuJgK2s)
