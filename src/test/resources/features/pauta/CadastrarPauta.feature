# language: pt

Funcionalidade: Cadastrar Pauta
  Eu como criador de pautas
  Quero criar uma pauta
  Para que possa abrir uma votação

  Cenario: Deve incluir uma pauta
    Dado uma pauta para tratar de "Criação do Corpo de Bombeiro na Cidade"
    Quando incluir a pauta
    Entao a pauta deve ser salva
    E devo receber um status "CREATED"

  Cenario: Deve validar uma pauta sem assunto
    Dado uma pauta sem assunto
    Quando incluir a pauta
    Entao a pauta não deve salva
    E devo receber um status "BAD_REQUEST"