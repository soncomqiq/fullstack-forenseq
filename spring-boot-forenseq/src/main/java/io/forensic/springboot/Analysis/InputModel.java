package io.forensic.springboot.Analysis;

public class InputModel {
	private String locus;
	private float parent1;
	private float parent2;
	private float child1;
	private float child2;
	
	public InputModel() {
		
	}
	
	public InputModel(String locus, float parent1, float parent2, float child1, float child2) {
		this.locus = locus;
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.child1 = child1;
		this.child2 = child2;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	public float getParent1() {
		return parent1;
	}

	public void setParent1(float parent1) {
		this.parent1 = parent1;
	}

	public float getParent2() {
		return parent2;
	}

	public void setParent2(float parent2) {
		this.parent2 = parent2;
	}

	public float getChild1() {
		return child1;
	}

	public void setChild1(float child1) {
		this.child1 = child1;
	}

	public float getChild2() {
		return child2;
	}

	public void setChild2(float child2) {
		this.child2 = child2;
	}
	
}
