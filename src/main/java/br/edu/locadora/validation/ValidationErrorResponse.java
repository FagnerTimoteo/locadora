package br.edu.locadora.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationErrorResponse {
	
	List<Violation> violations = new ArrayList<>();
	
	public void setViolation(Violation violation) {
		this.violations.add(violation);
	}
	
	public List<Violation> getViolations() {
		return Collections.unmodifiableList(this.violations);
	}
}
