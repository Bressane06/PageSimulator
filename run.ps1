param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$ArgsToPass
)

java -cp target/classes main.PageSimulator @ArgsToPass
