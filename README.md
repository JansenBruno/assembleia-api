# 🏛 Assembleia API

## 📖 Visão Geral
Assembleia API é um sistema para votação, permitindo que associados votem em pautas. A API suporta abertura e encerramento de votações, validação de CPF via integração com serviços externos, e comunicação assíncrona via Kafka para notificar outros sistemas sobre os resultados.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Kafka** (Mensageria para notificação dos resultados) - TO-DO
- **Docker** (Para facilitar o deploy) 
- **JUnit & Mockito** (Testes unitários) 

---

## ⚙️ Configuração e Execução

### 🔹 Pré-requisitos
Antes de rodar o projeto, você precisará de:
- **Java 17+**
- **Maven 3.8+**
- **Docker & Docker Compose** (se quiser rodar PostgreSQL e Kafka localmente)

### 🔹 Como rodar o projeto
Clone o repositório:
```sh
git clone https://github.com/seu-usuario/assembleia-api.git
cd assembleia-api

```
docker-compose up -d
```
mvn clean install
mvn spring-boot:run


