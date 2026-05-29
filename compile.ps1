New-Item -ItemType Directory -Force target/classes | Out-Null
$sources = Get-ChildItem -Path src/main/java -Recurse -Filter *.java |
    Select-Object -ExpandProperty FullName

if (-not $sources) {
    Write-Host "Nenhum arquivo Java encontrado em src/main/java."
    exit 0
}

& javac -encoding UTF-8 -d target/classes $sources
