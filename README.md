# PageSimulator

Projeto Java simples para simular a execução de um algoritmo a partir da classe principal `main.PageSimulator`.

## Requisitos

- Java 21
- Maven 3.x, se você quiser compilar com `mvn`

## Estrutura

- `src/main/java/main/PageSimulator.java` - ponto de entrada da aplicação
- `src/main/java/main/PageSimuladorOperacoes.java` - parsing e inicialização
- `src/main/java/simulacao/Simulacao.java` - orquestração da simulação
- `src/main/java/arquivos` - implementação do backing store (`FilePageStore`, `PageStore`)
- `src/main/java/memoria` - modelos `Pagina`, `Frame`
- `src/main/java/algoritmos` - algoritmos de substituição (`FIFO`, `LRU`, ...)
- `src/main/java/ui` - saída/terminal

## Como compilar

### Scripts da raiz

O projeto agora inclui scripts simples para compilar e executar sem precisar digitar os comandos completos toda vez:

- Windows PowerShell: `compile.ps1` e `run.ps1`
- Linux/macOS: `compile.sh` e `run.sh`

Exemplos:

```powershell
.\compile.ps1
.\run.ps1 ./teste LRU 5 6 15
```

```bash
chmod +x compile.sh run.sh
./compile.sh
./run.sh ./teste LRU 5 6 15
```

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

A aplicação espera os 5 parâmetros do enunciado na ordem:

```
java -cp target/classes main.PageSimulator [diretorio_das_paginas] [algoritmo] [numero_de_frames] [quantidade_paginas_unicas] [quantidade_paginas_requeridas]
```

Exemplo (gera páginas em `./teste`, usa FIFO, 3 frames, 10 páginas únicas, 50 requisições):

```
java -cp target/classes main.PageSimulator ./teste FIFO 3 10 50
```

O programa gera as páginas (arquivos `0.pag`..`N-1.pag`) no diretório escolhido, executa a simulação e imprime, para cada requisição, a tabela de frames e conteúdo, e ao final exibe o algoritmo, a sequência de requisição e o total de page faults.

## Testes manuais e utilitários

Há um utilitário para gerar e inspecionar rapidamente páginas:

```
java -cp target/classes main.TestGerador <diretorio> <quantidade_paginas>
```

Exemplo:

```
java -cp target/classes main.TestGerador test_pages 5
```

Isso cria os arquivos `0.pag`..`4.pag` em `test_pages` e imprime o conteúdo gerado.

O projeto ainda não tem testes JUnit; para validação automática você pode adicionar testes que verifiquem o comportamento de `algoritmos/FIFO` e `algoritmos/LRU`.

## Observação

O comando `mvn exec:java` só vai funcionar se o plugin `exec-maven-plugin` for adicionado ao `pom.xml`. Hoje o modo suportado é compilar com `mvn clean compile` ou usar `javac` conforme mostrado acima e executar com `java -cp target/classes main.PageSimulator ...`.
