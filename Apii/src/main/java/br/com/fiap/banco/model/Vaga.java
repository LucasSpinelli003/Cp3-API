package br.com.fiap.banco.model;

public class Vaga {
	private int codigo;
	private String nome;
	private String vaga;
	private double salario;
	private String data;
	
	public Vaga(int codigo, String nome, String vaga, double salario, String data) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.vaga = vaga;
		this.salario = salario;
		this.data = data;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getVaga() {
		return vaga;
	}
	public void setVaga(String vaga) {
		this.vaga = vaga;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	private Empresa empresa;
	
	
	
}
