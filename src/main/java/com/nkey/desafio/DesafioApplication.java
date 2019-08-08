package com.nkey.desafio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nkey.domain.Estoque;
import com.nkey.domain.Preco;
import com.nkey.domain.Produto;
import com.nkey.domain.Variante;

@SpringBootApplication
public class DesafioApplication {

	protected DesafioApplication() {
	}

	private static String URL_API_IFAC = "https://testapi.io/api/ednardomartins/ifac/produtos";
	private static List<Produto> produtoIFAC;

	public void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
		HttpURLConnection con = null;
		try {
			URL urlIFAC = new URL(URL_API_IFAC);
			con = ExtrairJsonIFAC(urlIFAC);
			con = EnviarJsonROBLOXY(produtoIFAC);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.disconnect();
			produtoIFAC.clear();
		}

	}

	public HttpURLConnection EnviarJsonROBLOXY(List<Produto> produtos)
			throws MalformedURLException, IOException, ProtocolException {
		String json = new Gson().toJson(produtos);
		json = json.substring(1, json.length() - 1).replace("\\\"", "");
		String novoJson = "{ \"product\": " + json + "}";
		URL url = new URL("https://testapi.io/api/ednardomartins/robloxy/produto");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestProperty("Authorization", "Bearer" + "robloxytest");
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write(novoJson);
		osw.flush();
		osw.close();
		connection.connect();
		return connection;
	}

	public HttpURLConnection ExtrairJsonIFAC(URL urlIFAC) throws Exception {
		try {

			HttpURLConnection con;
			con = (HttpURLConnection) urlIFAC.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			switch (con.getResponseCode()) {
			case 200:
				System.out.println("JSON recebido!");
				String json = getJson(urlIFAC);
				String novoJson = "{ \"produtos\": " + json + "}";
				JsonParser parser = new JsonParser();
				JsonObject obj = (JsonObject) parser.parse(novoJson);
				Set<Entry<String, JsonElement>> el = obj.entrySet();
				for (Entry<String, JsonElement> els : el) {
					if (els.getKey().equals("produtos")) {
						produtoIFAC = new ArrayList<Produto>();
						Produto produto;
						Variante variante;
						Preco preco;
						Estoque estoque;
						Double taxa = 1.95;
						JsonArray arrayCategoria = els.getValue().getAsJsonArray();
						for (int n = 0; n < arrayCategoria.size(); n++) {
							JsonObject issue = (JsonObject) arrayCategoria.get(n);
							produto = new Produto();
							produto.setCategoria(issue.get("categoria").toString());
							produto.setReferencia(issue.get("codigoReferencia").toString());
							produto.setEan(issue.get("eanCode").toString());
							produto.setNome(issue.get("nome").toString());
							JsonArray arrayVariantes = issue.get("variantes").getAsJsonArray();
							for (int m = 0; m < arrayVariantes.size(); m++) {
								JsonObject issueVariantes = (JsonObject) arrayVariantes.get(m);
								variante = new Variante();
								estoque = new Estoque();
								variante.setApresentacao(issueVariantes.get("apresentacao").toString());
								variante.setReferencia(issueVariantes.get("codigoReferencia").toString());
								variante.setMaster(Double.parseDouble(produto.getCategoria()));
								estoque.setQuantity(issueVariantes.get("estoqueQuantidade").toString());
								variante.setEstoque(estoque);
								JsonArray arrayListaPrecos = issueVariantes.get("listaPreco").getAsJsonArray();
								for (int z = 0; z < arrayListaPrecos.size(); z++) {
									JsonObject issuePrecos = (JsonObject) arrayListaPrecos.get(z);
									preco = new Preco();
									Double valorProduto = 0.0;
									if (issuePrecos.get("nome").toString().equals("\"B2C\"")) {
										valorProduto = Double.parseDouble(issuePrecos.get("preco").toString()) * taxa;
									} else {
										valorProduto = Double.parseDouble(issuePrecos.get("preco").toString());
									}
									preco.setPreco(valorProduto.toString());
									preco.setNome(issuePrecos.get("nome").toString());
									variante.setPreco(valorProduto.toString());
									variante.addPreco(preco);
								}
								produto.setIpi(Double.parseDouble(issueVariantes.get("ipi").toString()));
								produto.addVariante(variante);
							}
							produtoIFAC.add(produto);
						}
					}
				}
				break;
			case 500:
				System.out.println("Status 500");
				break;
			}
			return con;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

	public String getJson(URL url) throws Exception {

		if (url == null)
			throw new RuntimeException("URL é null");
		String html = null;
		StringBuilder sB = new StringBuilder();
		try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
			while ((html = bR.readLine()) != null)
				sB.append(html);
			return sB.toString();

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
