package circolo;


import java.sql.Date;

class VisitaMedica {
	private int numero_visita;
	private String CF_paziente;
	private Date data_visita;
	private boolean Agonistica;
	
	public VisitaMedica(String CF,Date data,int numero){
		this.CF_paziente=CF;
		this.data_visita=data;
		this.numero_visita=numero;
	}

	public boolean isAgonistica() {
		return Agonistica;
	}

	public void setAgonistica(boolean agonistica) {
		Agonistica = agonistica;
	}

	public int getNumero_visita() {
		return numero_visita;
	}

	public String getCF_paziente() {
		return CF_paziente;
	}

	public Date getData_visita() {
		return data_visita;
	}
	

}
