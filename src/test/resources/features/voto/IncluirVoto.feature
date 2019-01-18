# language: pt

Funcionalidade: Incluir Voto
  Eu como cooperativado
  Quero dar meu voto
  Para que eu possa participar da pauta de votação

  Cenario: Deve incluir um voto em uma sessão aberta
    Dado uma sessão de votação aberta
    E um voto para "true"
    Quando incluir o voto
    Entao o voto é salvo
    E devo receber um status "CREATED"