package circolo;


import java.sql.Date;

class VisitaMedica {
	private String CF_paziente;
	Date data_visita;
	private int Agonistica;

	public VisitaMedica(String CF,Date data,int numero){
		this.CF_paziente=CF;
		this.data_visita=data;
	}

	public int isAgonistica() {
		return Agonistica;
	}

	public void setAgonistica(int agonistica) {
		Agonistica = agonistica;
	}

	public String getCF_paziente() {
		return CF_paziente;
	}

	public Date getData_visita() {
		return data_visita;
	}
	

}
