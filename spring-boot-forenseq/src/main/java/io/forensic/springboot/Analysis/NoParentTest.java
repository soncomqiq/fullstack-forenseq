package io.forensic.springboot.Analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class NoParentTest {

	private static NoParentTest instance;

	private NoParentTest() {
	};

	public static NoParentTest getInstance() {
		if (instance == null) {
			synchronized (NoParentTest.class) {
				if (instance == null) {
					instance = new NoParentTest();
				}
			}
		}
		return instance;
	}

	public Map<String, Float> getPiList(ArrayList<InputModel> list, String country) {
		Map<String, Float> PiList = new HashMap<String, Float>();
		for (InputModel s : list) {
			PiList.put(s.getLocus(),
					getPi(s.getLocus(), s.getParent1(), s.getParent2(), s.getChild1(), s.getChild2(), country));
		}
		return PiList;
	}

	private Float getPi(String locus, Float p1, Float p2, Float c1, Float c2, String country) {
//		System.out.println("getPi");
		Float pi = null;
		System.out.println("getPi: IN :" + locus + " " + p1.toString() + " " + p2.toString() + " " + c1.toString() + " "
				+ c2.toString() + " " + country);
		Float freqP1 = ObjManager.getInstance().getFreq(country, locus, p1);
		Float freqP2 = ObjManager.getInstance().getFreq(country, locus, p2);
		if (p1.equals(p2)) {
			if (freqP1.equals(-1f)) {
				pi = 1f;
			} else if (p1.equals(c1)) {
				if (c1.equals(c2)) {
					pi = 1f / freqP1;
				} else {
					pi = 1f / (2 * (freqP1));
				}
			} else if (p1.equals(c2)) {
				pi = 1f / (2 * (freqP1));
			} else {
				pi = 0f;
			}
		} else if (c1.equals(c2)) {
			if (p1.equals(c1)) {
				if (freqP1.equals(-1f)) {
					pi = 1f;
				} else {
					pi = 1f / (2 * (freqP1));
				}
			} else if (p2.equals(c1)) {
				if (freqP2.equals(-1f)) {
					pi = 1f;
				} else {
					pi = 1f / (2 * (freqP2));
				}
			}
		} else if (p1.equals(c1)) {
			if (p2.equals(c2)) {
				if (freqP1.equals(-1f) && freqP2.equals(-1f)) {
					pi = 1f;
				} else {
					pi = (1f / (4 * (freqP1))) + (1f / (4 * (freqP2)));
				}
			} else {
				if (freqP1.equals(-1f)) {
					pi = 1f;
				} else {
					pi = 1f / (4 * (freqP1));
				}
			}
		} else if (p1.equals(c2)) {
			if (p2.equals(c1)) {
				if (freqP1.equals(-1f) && freqP2.equals(-1f)) {
					pi = 1f;
				} else {
					pi = (1f / (4 * (freqP1))) + (1f / (4 * (freqP2)));
				}
			} else {
				if (freqP1.equals(-1f)) {
					pi = 1f;
				} else {
					pi = 1f / (4 * (freqP1));
				}
			}
		} else if (p2.equals(c1)) {
			if (freqP2.equals(-1f)) {
				pi = 1f;
			} else {
				pi = 1f / (4 * (freqP2));
			}
		} else if (p2.equals(c2)) {
			if (freqP2.equals(-1f)) {
				pi = 1f;
			} else {
				pi = 1f / (4 * (freqP2));
			}
		} else {
			pi = 0f;
		}
		System.out.println(">>>" + pi.toString());
		return pi;
	}

	private Float getPiMixed(String locus, Float p1, Float p2, Float c1, Float c2, String pCountry, String cCountry) {
		Float pi = null;
		ObjManager manager = ObjManager.getInstance();

		if (p1.equals(p2)) {
			if (p1.equals(c1)) {
				if (c1.equals(c2)) {
					pi = 1 / manager.getFreq(pCountry, locus, p1);
				} else {
					pi = manager.getFreq(cCountry, locus, c2)
							/ ((manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c2))
									+ (manager.getFreq(pCountry, locus, c2) * manager.getFreq(cCountry, locus, p1)));
				}
			} else if (p1.equals(c2)) {
				pi = manager.getFreq(cCountry, locus, c1)
						/ ((manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c1))
								+ (manager.getFreq(pCountry, locus, c1) * manager.getFreq(cCountry, locus, p1)));
			} else {
				pi = 0f;
			}
		} else if (c1.equals(c2)) {
			if (p1.equals(c1)) {
				pi = 1f / (2 * (manager.getFreq(pCountry, locus, p1)));
			} else if (p2.equals(c1)) {
				pi = 1f / (2 * (manager.getFreq(pCountry, locus, p2)));
			}
		} else if (p1.equals(c1)) {
			if (p2.equals(c2)) {
				pi = (manager.getFreq(cCountry, locus, c1) + manager.getFreq(cCountry, locus, c2))
						/ (2 * ((manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c2))
								+ (manager.getFreq(pCountry, locus, p2) * manager.getFreq(cCountry, locus, c1))));

			} else {
				pi = (0.5f * manager.getFreq(cCountry, locus, c2))
						/ (manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c2))
						+ (manager.getFreq(pCountry, locus, c2) * manager.getFreq(cCountry, locus, c1));
			}
		} else if (p1.equals(c2)) {
			if (p2.equals(c1)) {
				pi = (manager.getFreq(cCountry, locus, c1) + manager.getFreq(cCountry, locus, c2))
						/ (2 * ((manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c2))
								+ (manager.getFreq(pCountry, locus, p2) * manager.getFreq(cCountry, locus, c1))));
			} else {
				pi = (0.5f * manager.getFreq(cCountry, locus, c1))
						/ (manager.getFreq(pCountry, locus, p1) * manager.getFreq(cCountry, locus, c1))
						+ (manager.getFreq(pCountry, locus, c1) * manager.getFreq(cCountry, locus, p1));
			}
		} else if (p2.equals(c1)) {
			pi = (0.5f * manager.getFreq(cCountry, locus, c2))
					/ (manager.getFreq(pCountry, locus, c1) * manager.getFreq(cCountry, locus, c2))
					+ (manager.getFreq(pCountry, locus, c2) * manager.getFreq(cCountry, locus, c1));
		} else if (p2.equals(c2)) {
			pi = (0.5f * manager.getFreq(cCountry, locus, c1))
					/ (manager.getFreq(pCountry, locus, c2) * manager.getFreq(cCountry, locus, c1))
					+ (manager.getFreq(pCountry, locus, c1) * manager.getFreq(cCountry, locus, c2));
		} else {
			pi = 0f;
		}
		System.out.println(">>>" + pi.toString());
		return pi;
	}

}