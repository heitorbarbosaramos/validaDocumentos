package validaDocumentos;

public class ValidaDocumentos {

	private final static Integer[] MULTIPLICADORES_RG = { 2, 3, 4, 5, 6, 7, 8, 9 };
	private final static Integer[] MULTIPLICADORES_CPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private final static Integer[] MULTIPLICADORES_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static String replace(String numDoc) {return numDoc.replace(".", "").replace("-", "").replace("/", "").replace(" ", "");}
	

	private static boolean calculador(String numDoc, Integer[] multiplicadores, Integer quantDigVer) {
		Integer dig, b, a;
		numDoc = replace(numDoc);
		Integer h = --quantDigVer;
		for (int y = h; y >= 0; y--) {
			Integer res = 0;
			a = 0;
			b = 1;
			for (int i = y; i <= (numDoc.length() - 2); i++) {

				Integer mult = Integer.parseInt(numDoc.substring(a, b)) * multiplicadores[i];
				res += mult;
				a++;
				b++;
			}

			dig = res % 11;

			if (numDoc.length() == 9) {// CASO FOR UM RG
				dig = 11 - dig;

				switch (dig) {
				case 10:
					return numDoc.substring(8, 9).toLowerCase().equals("x") ? true : false;
				case 11:
					return numDoc.substring(8, 9).toLowerCase().equals("0") ? true : false;
				default:
					return (dig.equals(Integer.parseInt(numDoc.substring(8, 9)))) ? true : false;
				}
			} else {

				dig = dig < 2 ? 0 : 11 - dig;
			}
			if(!dig.equals(Integer.parseInt(numDoc.substring(a, b)))) {return false;}
		}
		return true;
	}

	public static boolean validaRG(String numDoc) {
		Boolean result = false;
		if (numDoc.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\w{1}") || numDoc.matches("\\d{2}.?\\d{3}.?\\d{3}-\\w{1}")
				|| numDoc.matches("\\d{9}") || numDoc.matches("\\d{8}\\w{1}")) {
			result = calculador(numDoc, MULTIPLICADORES_RG, 1);
		}
		return result;
	}

	public static boolean validaCNPJ(String numDoc) {
		Boolean result = false;
		if (numDoc.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}") || numDoc.matches("\\d{14}") || numDoc.matches("\\d{8}/\\d{4}-\\d{2}") || numDoc.matches("\\d{12}-\\d{2}")) {
			result = calculador(numDoc, MULTIPLICADORES_CNPJ, 2);
		}
		return result;
	}

	public static boolean validaCPF(String numDoc) {
		Boolean result = false;
		if (numDoc.matches("\\d{3}\\.\\d{3}\\.?\\d{3}-\\d{2}") || numDoc.matches("\\d{11}") || numDoc.matches("\\d{9}-\\d{2}")) {
			result = calculador(numDoc, MULTIPLICADORES_CPF, 2);
		}
		return result;
	}

	public static void main(String[] args) {

		System.out.println(validaRG("56.843.539-4"));
		System.out.println(validaCPF("111.444.777-35"));
		System.out.println(validaCNPJ("11.444.777/0001-61"));

	}
}
