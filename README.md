# API para Cartão

Uma aplicação de manipulação de Cartão.

## Operações
* Cadastro de Cartão
* Consulta de Saldo do Cartão
* Inclusão de Movimentação do Cartão

## Pagamento
* Para registrar os pagamentos foi criado três entidades, sendo elas:
  * **Card**: Responsavel por registrar os dados de uma cartão de crédito.
  * **card_limit**: Responsável por registrar os dados de limite de uso de uma cartão.
  * **card_transaction**: Responsável por registrar os dados de um pagamento.

![Estrutura_de_Dados](img/EstruturaDados.png)
