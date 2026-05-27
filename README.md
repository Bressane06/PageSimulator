# PageSimulator

Projeto Java simples para simular a execução de um algoritmo a partir da classe principal `main.PageSimulator`.

## Requisitos

- Java 21
- Maven 3.x, se você quiser compilar com `mvn`

## Estrutura

- `src/main/java/main/PageSimulator.java` - ponto de entrada da aplicação
- `src/main/java/ui/Terminal.java` - implementação simples da interface de saída
- `src/main/java/ui/UserInterface.java` - contrato de interface

## Como compilar

### Usando Maven

Na raiz do projeto, execute:

```bash
mvn clean compile
```

Isso gera os `.class` em `target/classes`.

### Sem Maven, usando apenas o JDK

Se o Maven não estiver instalado no seu terminal, você pode compilar direto com `javac`:

```powershell
New-Item -ItemType Directory -Force target/classes | Out-Null
$sources = Get-ChildItem -Path src/main/java -Filter *.java -Recurse | ForEach-Object { $_.FullName }
& javac -encoding UTF-8 -d target/classes $sources
```

## Como executar

A classe principal espera pelo menos um argumento. Exemplo:

```bash
java -cp target/classes main.PageSimulator fifo
```

Saída esperada:

```text
fifo
Hello World!
```

Se você executar sem argumentos, a aplicação vai falhar porque o código acessa `args[0]` diretamente.

## Como testar

Este projeto ainda não possui testes automatizados. Por enquanto, o teste válido é a execução manual da classe principal com um argumento:

```bash
java -cp target/classes main.PageSimulator fifo
```

Se a saída for parecida com a abaixo, a aplicação está funcionando:

```text
fifo
Hello World!
```

## Observação

O comando `mvn exec:java` só vai funcionar se o plugin `exec-maven-plugin` for adicionado ao `pom.xml`. Hoje, o jeito mais simples é compilar com `mvn clean compile` e executar com `java -cp target/classes main.PageSimulator fifo`.
