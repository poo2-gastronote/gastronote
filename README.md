
# 🍽️ GastroNote

**GastroNote** é um sistema de gerenciamento digital de receitas culinárias, desenvolvido como projeto acadêmico da disciplina **Programação Orientada a Objetos II**. O objetivo principal é oferecer uma solução prática e eficiente para cadastrar, consultar e organizar receitas — incluindo seus ingredientes, tempo de preparo, valor calórico, porções e nível de dificuldade.

---

## 🎯 Objetivo

O sistema foi projetado para atender desde entusiastas da cozinha até estudantes de gastronomia e profissionais da área, promovendo a substituição de métodos tradicionais de anotação (como cadernos e papéis) por uma interface intuitiva, organizada e acessível. O projeto visa incentivar o uso da tecnologia no dia a dia culinário, otimizando o processo de criação e consulta de receitas.

---

## ✅ Funcionalidades

- Cadastro de **3 tipos de receitas**:
  - **Lanche** (com tipo e temperatura)
  - **Refeição** (com bebida e acompanhamento)
  - **Sobremesa** (com nível de doce e categoria)
- Inclusão de **ingredientes relacionados a cada receita**
- Consulta de receitas com **detalhamento completo**
- Exclusão de receitas por nome
- Integração com banco de dados **PostgreSQL**
- Interface gráfica feita com **Java Swing**
- Estrutura modular seguindo o padrão **MVC**

---

## 🧰 Tecnologias e Ferramentas Utilizadas

- **Java** (Linguagem de programação principal)
- **Java Swing** (Interface gráfica)
- **NetBeans IDE** (Ambiente de desenvolvimento)
- **PostgreSQL** (Banco de dados relacional)
- **JDBC** (Conexão entre Java e banco)
- **Padrões de Projeto**:
  - **Singleton** nos formulários
  - **MVC** na arquitetura do sistema

---

## 📋 Requisitos e Estrutura

- Implementação de 3 tipos de receitas com atributos específicos.
- Relacionamento entre **Receitas e Ingredientes** (1:N) com chave estrangeira.
- Boas práticas de codificação e separação por camadas.
- Scripts SQL disponíveis para criação das tabelas.

---

## ✅ Boas Práticas Adotadas

- **MVC (Model-View-Controller)**: Separação clara entre lógica de negócios, visual e controle.
- **Singleton nos formulários**: Garantia de que cada tela seja única na aplicação.

---

## 💡 Conclusão

O GastroNote demonstra a aplicação prática de conceitos de Programação Orientada a Objetos aliada a uma base de dados relacional, promovendo organização, facilidade e eficiência no gerenciamento de receitas culinárias. O sistema pode ser estendido com novas funcionalidades, como exportação de receitas, busca por ingredientes e muito mais!

---

## 👩‍💻 Desenvolvido por

- **Isadora Baía**  
- **Pedro Zanatta**  
- **Gustavo Barros**

---

## 📜 Licença

Este projeto é acadêmico, desenvolvido com fins educacionais e não possui fins comerciais.
