# language: pt

Funcionalidade: Contar Votos
  Eu como administrador das sessões de votação
  Quero contar os votos
  Para para saber se algo foi aprovado ou não

  Cenario: Deve contar os votos
    Dado uma sessão de votação
    E que existem 3 votos para "true"
    E que existem 2 votos para "false"
    Quando contar os votos
    Então devo receber o total de 5 votos
    E devo receber 3 votos para "true"
    E devo receber 2 votos para "false"
    E devo receber um status "OK"

  Cenario: Deve retornar uma contagem sem votos
    Dado uma sessão de votação
    E que existem 0 votos para "true"
    E que existem 0 votos para "false"
    Quando contar os votos
    Então devo receber o total de 0 votos
    E devo receber 0 votos para "true"
    E devo receber 0 votos para "false"
    E devo receber um status "OK"