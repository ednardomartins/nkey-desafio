package com.nkey.domain;

import java.util.ArrayList;
import java.util.List;

public class Variante {

	private String referenceCode;
	private String presentation;
	private String price;
	private String quantity;
	private String ipi;
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

	public String getQuantidadeEstoque() {
		return quantity;
	}

	public void setQuantidadeEstoque(String quantidadeEstoque) {
		this.quantity = quantidadeEstoque;
	}

	public String getIpi() {
		return ipi;
	}

	public void setIpi(String ipi) {
		this.ipi = ipi;
	}

	public List<Preco> getPrecos() {
		return priceList;
	}

	public void addPreco(Preco preco) {
		if (this.priceList == null)
			this.priceList = new ArrayList<Preco>();
		this.priceList.add(preco);

	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
