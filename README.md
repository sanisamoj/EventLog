# EventLog
Dashboard que centraliza logs do tipo INFO, ERROR e ATTACK em um só local.

## Objetivos
Com vários projetos em produção, atualmente preciso acessar sistemas de logs individuais de cada aplicação. Com este dashboard, 
meu objetivo é unir todos esses registros em um único local, simplificando significativamente o monitoramento e análise dos sistemas online.

## Como funciona
O dashboard divide entre logs lidos e não lidos, e há um filtro neles que indica a severidade (LOW, MEDIUM, HIGH ou CRITICAL), ou pelo tipo (INFO, ERROR e ATTACK).
O repositório padrão do projeto utiliza o retrofit para realizar as requisições em uma API, que gerencia os logs.


