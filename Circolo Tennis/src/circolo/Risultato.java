package circolo;

public enum Risultato {
	SEI_ZERO("6-0"),
	SEI_UNO("6-1"),
	SEI_DUE("6-2"),
	SEI_TRE("6-3"),
	SEI_QUATTRO("6-4"),
	SETTE_CINQUE("7-5"),
	SETTE_SEI("7-6"),
	ZERO_SEI("0-6"),
	UNO_SEI("1-6"),
	DUE_SEI("2-6"),
	TRE_SEI("3-6"),
	QUATTRO_SEI("4-6"),
	CINQUE_SETTE("5-7"),
	SEI_SETTE("6-7");
	
	private String risultato;
	
	Risultato(String risultato){
		this.risultato=risultato;
	}
	
	public String getRisultato() {
		return risultato;
	}
	
}


