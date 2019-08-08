package com.nkey.domain;

import java.util.ArrayList;
import java.util.List;

public class Variante {

	private String referenceCode;
	private String presentation;
	private String price;
	private Double master;
	private Estoque stock;
	private List<Preco> priceList;

	public String getApresentacao() {
		return presentation;
	}

	public void setApresentacao(String apresentacao) {
		if (this.presentation == null)
			this.presentation = apresentacao;
	}

	public String getReferencia() {
		return referenceCode;
	}

	public void setReferencia(String referencia) {
		this.referenceCode = referencia;
	}

	public List<Preco> getPrecos() {
		return priceList;
	}

	public void addPreco(Preco preco) {
		if (this.priceList == null)
			this.priceList = new ArrayList<Preco>();
		this.priceList.add(preco);

	}

	public String getPreco() {
		return price;
	}

	public void setPreco(String preco) {
		this.price = preco;
	}

	public Double getMaster() {
		return master;
	}

	public void setMaster(Double categoria) {
		this.master = categoria;
	}

	public Estoque getEstoque() {
		return stock;
	}

	public void setEstoque(Estoque estoque) {
		this.stock = estoque;
	}

}
