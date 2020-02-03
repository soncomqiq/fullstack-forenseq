package io.forensic.springboot.Other;

import java.util.List;

public class Locus {
	private List<String> autosomLocus;
	private List<String> xLocus;
	private List<String> yLocus;

	public Locus() {

	}

	public Locus(List<String> autosomLocus, List<String> xLocus, List<String> yLocus) {
		super();
		this.autosomLocus = autosomLocus;
		this.xLocus = xLocus;
		this.yLocus = yLocus;
	}

	public List<String> getAutosomLocus() {
		return autosomLocus;
	}

	public void setAutosomLocus(List<String> autosomLocus) {
		this.autosomLocus = autosomLocus;
	}

	public List<String> getxLocus() {
		return xLocus;
	}

	public void setxLocus(List<String> xLocus) {
		this.xLocus = xLocus;
	}

	public List<String> getyLocus() {
		return yLocus;
	}

	public void setyLocus(List<String> yLocus) {
		this.yLocus = yLocus;
	}
}
