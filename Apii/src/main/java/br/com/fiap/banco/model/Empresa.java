package br.com.fiap.banco.model;

public class Empresa {
	private int codigo;
	private String empresa;
	private int numeroFunc;
	
	public Empresa(int codigo, String empresa, int numeroFunc) {
		super();
		this.codigo = codigo;
		this.empresa = empresa;
		this.numeroFunc = numeroFunc;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public int getNumeroFunc() {
		return numeroFunc;
	}
	public void setNumeroFunc(int numeroFunc) {
		this.numeroFunc = numeroFunc;
	}
	
}
