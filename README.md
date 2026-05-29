# PageSimulator

Simulador em Java do gerenciamento de páginas (memória principal e virtual). A aplicação gera páginas em disco (backing store), cria frames em memória e simula algoritmos de substituição (FIFO, LRU, LFU).

## Requisitos

- JDK 17+ (testado com Java 21)
- Maven 3.x (opcional, facilita empacotar em JAR)

## Principais arquivos / classes

- `src/main/java/main/PageSimulator.java` — ponto de entrada (`main`)
- `src/main/java/main/PageSimuladorOperacoes.java` — leitura/validação de argumentos
- `src/main/java/simulacao/ConfiguracaoSimulacao.java` — modela parâmetros da simulação
- `src/main/java/simulacao/Simulacao.java` — fluxo principal (gera páginas, escolhe algoritmo)
- `src/main/java/simulacao/SimulacaoAlgoritmos.java` — lógica de execução dos algoritmos e impressão de frames
- `src/main/java/arquivos/PageStore.java` — interface do backing store
- `src/main/java/arquivos/FilePageStore.java` — implementação que lê/gera arquivos `*.pag`
- `src/main/java/memoria/Pagina.java` — modelo de página (número + conteúdo)
- `src/main/java/memoria/Frame.java` — modelo do frame (contém `Pagina`)
- `src/main/java/algoritmos/AlgoritmoSubstituicao.java` — interface dos algoritmos
- `src/main/java/algoritmos/FIFO.java`, `LRU.java`, `LFU.java` — implementações
- `src/main/java/ui/UserInterface.java`, `ui/Terminal.java` — saída no terminal

## Compilar

Opções recomendadas:

- Usando Maven (gera o JAR em `target/`):

```bash
mvn clean package
# JAR será: target/PageSimulator-1.0-SNAPSHOT.jar
```

- Usando apenas javac (gera classes em `target/classes`):

PowerShell:
```powershell
New-Item -ItemType Directory -Force target/classes | Out-Null
$sources = Get-ChildItem -Path src/main/java -Filter *.java -Recurse | ForEach-Object { $_.FullName }
& javac -encoding UTF-8 -d target/classes $sources
```

Bash:
```bash
mkdir -p target/classes
find src/main/java -name "*.java" > sources.txt
javac -encoding UTF-8 -d target/classes @sources.txt
rm sources.txt
```

- Scripts incluídos (conforto): `compile.ps1`, `compile.sh` — usam `mvn package` ou `javac` dependendo do ambiente.

## Executar

A aplicação aceita 5 parâmetros (na ordem):

```
<diretorio_das_paginas> <algoritmo> <numero_de_frames> <quantidade_paginas_unicas> <quantidade_paginas_requeridas>
```

Exemplos:

- Executar o JAR (após `mvn package`):

```bash
java -jar target/PageSimulator-1.0-SNAPSHOT.jar ./teste FIFO 3 10 50
```

- Executar a partir de classes compiladas (sem empacotar):

```bash
java -cp target/classes main.PageSimulator ./teste LRU 4 10 50
```

- Usando os scripts (PowerShell):

```powershell
.\compile.ps1
.\run.ps1 .\teste FIFO 3 10 50
```

Após a execução, o programa criará os arquivos `0.pag` .. `N-1.pag` no diretório informado e imprimirá, para cada requisição, quais páginas estão nos frames e seu conteúdo; no final mostra o algoritmo, a sequência de requisições e o total de falhas de página.

## Observações e dicas

- Se quiser reproduzir resultados consistentes para análise, ajuste `SimulacaoAlgoritmos#gerarSequenciaRequisicoes` para usar uma `Random` com semente fixa.
- Para gerar o diagrama de classes (`docs/estrutura.puml`) use a extensão PlantUML no VS Code ou um renderizador online (ex.: PlantUML server) e exporte PNG/SVG.
- Não há testes automatizados no repositório — recomendo adicionar JUnit para validar `algoritmos/*`.

## Perguntas frequentes rápidas

- Como altero o algoritmo? Passe `LRU`, `FIFO` ou `LFU` como segundo parâmetro.
- Onde os arquivos `.pag` são criados? No caminho passado como primeiro parâmetro (será criado se não existir).

---
Se quiser, atualizo `compile.ps1` e `compile.sh` para padronizar a criação do JAR e explicito comandos para Windows/macOS no README.
