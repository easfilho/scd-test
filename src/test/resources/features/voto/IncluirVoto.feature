# language: pt

Funcionalidade: Incluir Voto
  Eu como cooperativado
  Quero dar meu voto
  Para que eu possa participar da pauta de votação

  Esquema do Cenario: Cenario: Deve incluir um voto em uma sessão aberta
    Dado uma sessão de votação aberta
    E um voto para "<voto>"
    Quando incluir o voto
    Entao o voto é salvo
    E devo receber um status "CREATED"
    Exemplos:
      | voto  |
      | true  |
      | false |

  Cenario: Deve validar inclusão de voto em uma sessão fechada
    Dado uma sessão de votação fechada
    E um voto para "<voto>"
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "PRECONDITION_FAILED"