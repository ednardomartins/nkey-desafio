package com.nkey.desafio;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nkey.domain.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesafioApplicationTests {

	private static final String URL_API_IFAC = "https://testapi.io/api/ednardomartins/ifac/produtos";
	private static final String URL_API_IFAC_ERRO = "https://testapi.io/api/ednardomartins/ifac/produto";

	@Test
	public void ExtrairJsonIFACTeste() throws Exception {
		DesafioApplication main = new DesafioApplication();
		URL urlIFAC = new URL(URL_API_IFAC);

		String json = main.getJson(urlIFAC);
		Assert.assertEquals(json,
				"[{	\"categoria\": 1.0,	\"codigoReferencia\": \"1\",	\"eanCode\": 1,	\"nome\": \"CHUTEIRA NIKE\",	\"variantes\": [		{			\"apresentacao\": \"CHUTEIRA ADIDAS\",			\"codigoReferencia\": \"1\",			\"estoqueQuantidade\": 10946.0,			\"ipi\": 0.0,			\"listaPreco\": [				{					\"preco\": 344.94,					\"nome\": \"B2B\"				},				{					\"preco\": 355.31,					\"nome\": \"B2C\"				}			]		}	]},{	\"categoria\": 1.0,	\"codigoReferencia\": \"1\",	\"eanCode\": 2,	\"nome\": \"CHUTEIRA NIKE\",	\"variantes\": [		{			\"apresentacao\": \"CHUTEIRA NIKE\",			\"codigoReferencia\": \"1\",			\"estoqueQuantidade\": 10946.0,			\"ipi\": 0.0,			\"listaPreco\": [				{					\"preco\": 400.94,					\"nome\": \"B2B\"				},				{					\"preco\": 440.31,					\"nome\": \"B2C\"				}			]		}	]}]");
		HttpURLConnection connection = main.ExtrairJsonIFAC(urlIFAC);
		int code = connection.getResponseCode();
		Assert.assertEquals(connection.getHeaderField("Authorization").toString(), "Bearer ifactest");
		Assert.assertEquals(code, 200);
	}

	@Test
	public void ExtrairJsonIFACURLErroTeste() throws ProtocolException, IOException {
		DesafioApplication main = new DesafioApplication();
		URL urlIFAC = new URL(URL_API_IFAC_ERRO);
		try {
			main.getJson(urlIFAC);
		} catch (Exception ex) {
			Assert.assertEquals(ex.getMessage(),
					"Server returned HTTP response code: 405 for URL: https://testapi.io/api/ednardomartins/ifac/produto");
		}
	}

	@Test
	public void ExtrairJsonIFACURLNulaTeste() throws ProtocolException, IOException {
		DesafioApplication main = new DesafioApplication();
		URL urlIFAC = null;
		try {
			main.getJson(urlIFAC);
		} catch (Exception ex) {
			Assert.assertEquals(ex.getMessage(), "URL é null");
		}
	}

	@Test
	public void EnviarJsonROBLOXYTeste() throws ProtocolException, IOException {
		DesafioApplication main = new DesafioApplication();
		List<Produto> produtos = new ArrayList<Produto>();
		HttpURLConnection connection = main.EnviarJsonROBLOXY(produtos);
		int code = connection.getResponseCode();
		Assert.assertEquals(connection.getHeaderField("Authorization").toString(), "Bearer" + " robloxytest");
		Assert.assertEquals(code, 200);

	}

}
