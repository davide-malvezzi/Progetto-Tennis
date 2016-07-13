package circolo;


import java.sql.Date;

public class Campo {
	Date disponibilità;
	int numero_campo;
	String superficie;
	String posizione; // interno o esterno
	
	public Campo(int numero, String superficie, String posizione){
		this.numero_campo=numero;
		this.superficie=superficie;
		this.posizione=posizione;
	}

	public Date getDisponibilità() {
		return disponibilità;
	}

	public void setDisponibilità(Date disponibilità) {
		this.disponibilità = disponibilità;
	}

	public int getNumero_campo() {
		return numero_campo;
	}

	public void setNumero_campo(int numero_campo) {
		this.numero_campo = numero_campo;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getPosizione() {
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}
	
}
