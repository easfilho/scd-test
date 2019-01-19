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
    E a data de validade para a votação deve ser a data atual mais o tempo informado
    E devo receber um status "CREATED"

  Cenario: Deve abrir uma sessão de votação com tempo limite default
    Dado uma pauta
    E que não é inforamdo o tempo de votação
    Quando abrir a sessão de votação
    Entao a sessão de votação deve ser aberta
    E a data de validade para a votação deve ser a data atual mais 1 minuto
    E devo receber um status "CREATED"