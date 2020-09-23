package br.appjsf4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 20)
	private String descricao;
	
	@Column(length = 3)
	private String und;
	
	@Column(precision = 10, scale = 2)
	private Double qtd;
	
	@Column(precision = 10, scale = 2)
	private Double valor;
	
	public Produto() {
		super();		
	}
	
	public Produto(Double qtd, Double valor) {
		super();
		this.qtd = qtd;
		this.valor = valor;
	}



	public Produto(String descricao, String und, Double qtd, Double valor) {
		super();
		this.descricao = descricao;
		this.und = und;
		this.qtd = qtd;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnd() {
		return und;
	}

	public void setUnd(String und) {
		this.und = und;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
		
	

}
