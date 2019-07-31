# nkey-desafio

#### 1 - - Crie um Branch do repositorio

Seu cenário é a integração entre dois sistemas. Mais especificamente obter os dados dos produtos de um sistema ERP chamado IFAC e inserir esses dados no e-comerce ROBLOXY.
Caracteristicas de implementação:

- Os preços devem ter duas casas decimais;
- Deve ser feito o de-para dos campos do ERP antes de envia-los para ROBLOXY
- Entrada JSON e Saida JSON.
- NO preço B2C deve-se aplicar uma taxa de 1.95 antes de enviar para ROBLOXY
- Trabalhe com mock para executar os cenários de leitura e escrita.
- Lembre-se dos testes unitários e integração.

#### 2 - Dados ERP IFAC

>/GET

>Endereço de Acesso: https://testapi.io/api/ednardomartins/ifac/produtos

>Dados Autenticação Header: Authorization Bearer ifactest

>Response:

```javascript
[{
	"codigoReferencia": "1",
	"eanCode": 1,
	"nome": "CHUTEIRA NIKE",
	"variantes": [
		{
			"apresentacao": "CHUTEIRA ADIDAS",
			"codigoReferencia": "1",
			"estoqueQuantidade": 10946.0,
			"ipi": 0.0,
			"listaPreco": [
				{
					"preco": 344.94,
					"nome": "B2B"
				},
				{
					"preco": 355.31,
					"nome": "B2C"
				}
			]
		}
	]
},
{
	"codigoReferencia": "1",
	"eanCode": 2,
	"nome": "CHUTEIRA NIKE",
	"variantes": [
		{
			"apresentacao": "CHUTEIRA NIKE",
			"codigoReferencia": "1",
			"estoqueQuantidade": 10946.0,
			"ipi": 0.0,
			"listaPreco": [
				{
					"preco": 400.94,
					"nome": "B2B"
				},
				{
					"preco": 440.31,
					"nome": "B2C"
				}
			]
		}
	]
}
]
```

---
#### 3 - Estrutura JSON e-comerce ROBLOXY

>/PUT

>Endereço de Acesso: https://testapi.io/api/ednardomartins/robloxy/produto

>Dados Autenticação Header: Authorization Bearer robloxytest

>Request:

```javascript
{ 
	"product": {
		"name": "",
		"referenceCode": "",
		"stockControl": "1",
		"eanCode": "",
		"ipi": 10000,
		"variants": [
			{
				"referenceCode": "",
				"presentation": "",
				"price": "",
				"master": 1,
				"stock": {
					"quantity": ""
				},
				"priceList": [
					{
						"price": "",
						"criteria": "B2C"
					},
					{
						"price": "",
						"criteria": "B2B"
					}
				]
			}
		]
	}
}
```

---

#### 4 - Ao finilizar realize o pull request!!
