# language: pt

Funcionalidade: Abrir Sessão Votação
  Eu como administrador de pautas de votação
  Quero abrir uma sessão de votação
  Para que as cooperativados possam votas

  Cenario: Deve abrir uma sessão de votação
    Dado uma pauta
    E que o tempo de votação é de "12:00"
    Quando abrir a sessão de votação
    Entao a sessão de votação deve ser aberta
    E devo receber um status "CREATED"