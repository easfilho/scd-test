# language: pt

Funcionalidade: Incluir Voto
  Eu como cooperativado
  Quero dar meu voto
  Para que eu possa participar da pauta de votação

  Esquema do Cenario: Cenario: Deve incluir um voto em uma sessão aberta
    Dado uma sessão de votação aberta
    E um cooperativado que ainda não votou
    E o cooperativado possui um cpf habilitado para votar
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
    E um cooperativado que ainda não votou
    E o cooperativado possui um cpf habilitado para votar
    E um voto para "<voto>"
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "PRECONDITION_FAILED"

  Cenario: Deve validar cooperativado que já tem voto na sessão aberta
    Dado uma sessão de votação aberta
    E um cooperativado que já votou
    E o cooperativado possui um cpf habilitado para votar
    E um voto para "true"
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "PRECONDITION_FAILED"

  Cenario: Deve validar se o cooperativado é informado
    Dado uma sessão de votação aberta
    E o cooperativado não é informado
    E um voto para "true"
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "BAD_REQUEST"

  Cenario: Deve validar se o cooperativado é informado
    Dado uma sessão de votação aberta
    E um cooperativado que ainda não votou
    E um voto não é informado
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "BAD_REQUEST"

  Cenario: Deve validar cooperativado com cpf não habilitado para votar
    Dado uma sessão de votação fechada
    E um cooperativado que ainda não votou
    E o cooperativado possui um cpf não habilitado para votar
    E um voto para "<voto>"
    Quando incluir o voto
    Entao o voto não é salvo
    E devo receber um status "PRECONDITION_FAILED"