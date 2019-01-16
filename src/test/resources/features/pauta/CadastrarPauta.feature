# language: pt

Funcionalidade: Cadastrar Pauta
  Eu como criador de pautas
  Quero criar uma pauta
  Para que possa abrir uma votação

  Cenario: Deve incluir uma pauta
    Dado uma pauta para tratar de "Criação do Corpo de Bombeiro na Cidade"
    Quando incluir a pauta
    Entao a pauta deve ser salva