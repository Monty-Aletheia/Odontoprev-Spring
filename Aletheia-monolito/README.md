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

Nesta Sprint 4 desenvolvemos a integração com LLM
para geração de um relatório para cada um dos pacientes.
O LLM que utilizamos foi o ChatGPT com o Spring AI e sua 
dependência da OpenAI

# Como Rodar

Siga os passos abaixo para configurar e rodar o projeto localmente:

### Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

- [Java JDK 21+](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/) para clonar o repositório
- [Docker](https://www.docker.com/products/docker-desktop/) para rodar a aplicação no container

### Passos para Rodar o projeto

1. **Clone o Repositório**

   Abra o terminal e execute o seguinte comando:

   ```bash
   git clone https://github.com/Monty-Aletheia/Odontoprev-Spring.git
   ```

2. **Navegue até o Diretório do Projeto**

   Acesse o diretório clonado:

   ```bash
   cd odontoprev-spring
   ```
   
3. **Adicione a chave de API presente no txt enviado**
   
   No arquivo enviado via portal do aluno, adicionamos nossa 
   chave de API da OpenAI, pois a mesma não pode ser enviada para o github.


4. **Rode o programa**

   Para rodar o programa, utilize o comando 

   ```bash
   docker compose up --build
   ```

   Este comando irá subir todos os serviços, o monolito, o servidor rabbitmq e o nosso microserviço. 
   Para acessar os serviços, entre em localhost:8080 e se logue como admin:
   
   email: admin@aletheia.com
   senha: admin123


## Link para o vídeo pitch do projeto

[Acesse clicando aqui](https://youtu.be/MHIPHuJgK2s)

## Link para o vídeo explicando o projeto Java (Sprint 4)
[Acesse clicando aqui]()
