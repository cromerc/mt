package mt;

import javafx.beans.property.SimpleStringProperty;

public class ListaCargada {
	private final SimpleStringProperty primer;

	private final SimpleStringProperty segundo;

	public ListaCargada(SimpleStringProperty primer, SimpleStringProperty segundo) {
		this.primer = primer;
		this.segundo = segundo;
	}

	public ListaCargada(String primer, String segundo) {
		this.primer = new SimpleStringProperty(primer);
		this.segundo = new SimpleStringProperty(segundo);
	}

	public String getPrimer() {
		return primer.get();
	}

	public void setPrimer(String primer) {
		this.primer.set(primer);
	}

	public SimpleStringProperty primerProperty() {
		return primer;
	}

	public String getSegundo() {
		return segundo.get();
	}

	public void setSegundo(String segundo) {
		this.segundo.set(segundo);
	}

	public SimpleStringProperty segundoProperty() {
		return segundo;
	}
}
