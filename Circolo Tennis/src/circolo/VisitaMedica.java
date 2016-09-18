package circolo;


import javafx.beans.property.*;

import java.time.LocalDate;

public class VisitaMedica {
	private StringProperty CF_paziente;
	private ObjectProperty<LocalDate> data;
	private IntegerProperty agonistica;



	public VisitaMedica(){
		CF_paziente = new SimpleStringProperty();
		data = new SimpleObjectProperty<>();
		agonistica = new SimpleIntegerProperty();

	}

	public VisitaMedica(String CF,LocalDate data){
		this.CF_paziente.set(CF);
		this.data.set(data);
	}

	public StringProperty getCF_pazienteProperty() {
		return CF_paziente;
	}

	public String getCF_paziente(){
		return CF_paziente.get();
	}

	public void setCF_paziente(String CF_paziente) {
		this.CF_paziente.set(CF_paziente);
	}

	public LocalDate getData() {
		return data.get();
	}

	public ObjectProperty<LocalDate> getDataProperty() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data.set(data);
	}

	public IntegerProperty getAgonisticaProperty() {
		return agonistica;
	}

	public int getAgonistica(){
		return agonistica.get();
	}

	public void setAgonistica(int agonistica){
		this.agonistica.set(agonistica);
	}

}
