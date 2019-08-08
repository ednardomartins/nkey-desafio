package com.nkey.domain;

import java.util.ArrayList;
import java.util.List;

public class Produto {

	private String name;
	private String referenceCode;
	private String stockControl;
	private String eanCode;
	private String ipi;
	private List<Variante> variants;
	
	public List<Variante> getVariantes() {
		return variants;
	}

	public void setVariantes(List<Variante> variantes) {
		if (this.variants == null)
			this.variants = variantes;
	}

	public void addVariante(Variante variante) {
		if (this.variants == null)
			this.variants = new ArrayList<Variante>();
		this.variants.add(variante);
	}



	public String getCategoria() {
		return stockControl;
	}

	public void setCategoria(String categoria) {
		this.stockControl = categoria;
	}

	public String getReferencia() {
		return referenceCode;
	}

	public void setReferencia(String referencia) {
		this.referenceCode = referencia;
	}

	public String getEan() {
		return eanCode;
	}

	public void setEan(String ean) {
		this.eanCode = ean;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	public String getIpi() {
		return ipi;
	}

	public void setIpi(String ipi) {
		this.ipi = ipi;
	}

}
