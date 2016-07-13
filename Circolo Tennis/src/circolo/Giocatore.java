package circolo;


import java.sql.Date;

class Giocatore {
	private final String nome,cognome;
	private final Date data_nascita;
	private final String CF;
	private final String genere;
	private String indirizzo;
	private double classifica_FIT;
	private int classifica_circolo;

	private boolean agonista,socio;
	
	public Giocatore(String nome, String cognome, Date data, String cf, String genere,String indirizzo){
		this.nome=nome;
		this.cognome=cognome;
		this.data_nascita=data;
		this.CF=cf;
		this.genere=genere;
		this.indirizzo=indirizzo;
	}

	//getter and setters

	String getNome() {
		return nome;
	}
	
	String getCognome() {
		return cognome;
	}
	
	Date getData_nascita() {
		return data_nascita;
	}
	
	String getCF() {
		return CF;
	}

    double getClassifica_FIT() {
        return classifica_FIT;
    }

    public void setClassifica_FIT(double classifica_FIT) {
        this.classifica_FIT = classifica_FIT;
    }

    int getClassifica_circolo() {
        return classifica_circolo;
    }

    public void setClassifica_circolo(int classifica_circolo) {
        this.classifica_circolo = classifica_circolo;
    }

    String getGenere() {
        return genere;
    }

    String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean isAgonista() {
        return agonista;
    }

    public void setAgonista(boolean agonista) {
        this.agonista = agonista;
    }

    public boolean isSocio() {
        return socio;
    }

    public void setSocio(boolean socio) {
        this.socio = socio;
    }


}
