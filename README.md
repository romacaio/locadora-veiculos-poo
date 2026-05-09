# 🚗 Sistema de Locadora de Veículos

Sistema desktop desenvolvido em Java para gerenciamento de uma locadora de veículos, permitindo cadastro de clientes, veículos, controle de locações, devoluções, pagamentos e geração de relatórios.

## 📌 Objetivo do Projeto

Aplicar na prática os principais conceitos de Programação Orientada a Objetos estudados em disciplina acadêmica, como:

- Classes e Objetos
- Encapsulamento
- Herança
- Polimorfismo
- Classes Abstratas
- Interfaces
- Tratamento de Exceções
- Persistência em JSON
- Interface gráfica com Java Swing

---

## 🧩 Principais Funcionalidades

- Cadastro de veículos (Carro, Moto e Caminhão)
- Cadastro de clientes
- Registro de locações
- Registro de devoluções
- Cálculo automático de valor total e multa por atraso
- Registro de pagamentos
- Login com controle de perfis:
    - Administrador
    - Gerente
    - Atendente
- Geração de relatórios PDF:
    - Faturamento mensal
    - Clientes e locações
    - Veículos e locações

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Maven
- Java Swing
- Gson
- iText PDF

---

## ▶️ Como Executar

### Clonar repositório

```bash
git clone <url-do-repositorio>
```

### Gerar build

```bash
mvn clean package
```

### Executar aplicação

```bash
java -jar target/locadora-1.0-SNAPSHOT-jar-with-dependencies.jar
```
