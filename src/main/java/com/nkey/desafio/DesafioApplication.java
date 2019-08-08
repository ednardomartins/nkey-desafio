package com.nkey.desafio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nkey.domain.Preco;
import com.nkey.domain.Produto;
import com.nkey.domain.Variante;

@SpringBootApplication
public class DesafioApplication {

	protected DesafioApplication() {
	}

	private static final String URL_API_IFAC = "https://testapi.io/api/ednardomartins/ifac/produtos";
	//private static final String URL_API_ROBLOXY = "https://testapi.io/api/ednardomartins/robloxy/produto";
	private static List<Produto> produtoIFAC;
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
		HttpURLConnection con = null;
		try {
			URL urlIFAC = new URL(URL_API_IFAC);
			con = ExtrairJsonIFAC(urlIFAC);
			String json = new Gson().toJson(produtoIFAC);
			json = json.substring(1, json.length() -1).replace("\\\"", "");
			String novoJson = "{ \"product\": " + json + "}";
			System.out.println(novoJson);

//			Random random = new Random();
//	        URL url = new URL("https://testapi.io/api/ednardomartins/robloxy/produto");
//	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	        connection.setRequestMethod("PUT");
//	        connection.setDoOutput(true);
//	        
//	        //Authentication Bearer
//	        connection.setRequestProperty("Authorization", "Bearer" + "robloxytest");
//	        //connection.setRequestProperty("Content-Type", "application/json");
//	        connection.setRequestProperty("Accept", "application/json");
//	        
//	        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
//	        osw.write(novoJson , random.nextInt(30), random.nextInt(20));
//	        osw.flush();
//	        osw.close();
//	        connection.connect();
//	        System.err.println(connection.getResponseCode());
//	        connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.disconnect();
			
		}

	}

	public static HttpURLConnection ExtrairJsonIFAC(URL urlIFAC) throws IOException, ProtocolException {
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
					Produto categoria;
					Variante variante;
					Preco preco;

					JsonArray arrayCategoria = els.getValue().getAsJsonArray();
					for (int n = 0; n < arrayCategoria.size(); n++) {
						JsonObject issue = (JsonObject) arrayCategoria.get(n);
						categoria = new Produto();
						categoria.setCategoria(issue.get("categoria").toString());
						categoria.setReferencia(issue.get("codigoReferencia").toString());
						categoria.setEan(issue.get("eanCode").toString());
						categoria.setNome(issue.get("nome").toString());
						
						JsonArray arrayVariantes = issue.get("variantes").getAsJsonArray();
						for (int m = 0; m < arrayVariantes.size(); m++) {
							JsonObject issueVariantes = (JsonObject) arrayVariantes.get(m);
							variante = new Variante();
							variante.setApresentacao(issueVariantes.get("apresentacao").toString());
							variante.setReferencia( issueVariantes.get("codigoReferencia").toString());
							variante.setQuantidadeEstoque(issueVariantes.get("estoqueQuantidade").toString());
							variante.setIpi(issueVariantes.get("ipi").toString());
							
							JsonArray arrayListaPrecos = issueVariantes.get("listaPreco").getAsJsonArray();
							for (int z = 0; z < arrayListaPrecos.size(); z++) {
								JsonObject issuePrecos = (JsonObject) arrayListaPrecos.get(z);
								preco = new Preco();
								preco.setPreco(issuePrecos.get("preco").toString());
								preco.setNome(issuePrecos.get("nome").toString());
								variante.addPreco(preco);
							}
							
							categoria.addVariante(variante);
						}
						produtoIFAC.add(categoria);
					}
				}
			}
			break;
		case 500:
			System.out.println("Status 500");
			break;
		}
		return con;

	}

	

	public static String getJson(URL url) {
		if (url == null)
			throw new RuntimeException("URL é null");

		String html = null;
		StringBuilder sB = new StringBuilder();
		try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
			while ((html = bR.readLine()) != null)
				sB.append(html);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sB.toString();
	}
}
