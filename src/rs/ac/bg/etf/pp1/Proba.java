package rs.ac.bg.etf.pp1;

public class Proba {

	public static class Predmet {
		int tezina;
		char vrsta;

		public void postaviTezinu(int tezina) {
			this.tezina = tezina;
		}

		public void ucitajTezinu() {
			System.out.print(tezina);
		}

		public int zapremina() {
			return 0;
		}

		public int Q() {
			return zapremina() * tezina;
		}

		public void postaviVrstu(char vrsta) {
			this.vrsta = vrsta;
		}

		public char dohvVrstu() {
			return vrsta;
		}

		public int dohvTezinu() {
			return tezina;
		}

		public void ispisi() {
			System.out.print(vrsta);
			System.out.print(Q());
		}

	}

	public static class Sfera extends Predmet {
		int r;

		public Sfera() {
			vrsta = 's';
			r = 1;
		}

		public Sfera(int r) {
			vrsta = 's';
			this.r = r;
		}

		public void postaviPoluprecnik(int r) {
			this.r = r;
		}

		public int zapremina() {
			int z = 4;
			return z * (r / 3);
		}

	}

	public static class Kvadar extends Predmet {

		int a, b, c;

		public Kvadar() {
			vrsta = 'k';
			a = 1;
			b = 2;
			c = 3;
		}

		public Kvadar(int a, int b, int c) {
			vrsta = 'k';
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public Kvadar(int a) {
			vrsta = 'k';
			this.a = a;
			b = a + 1;
			c = a + 2;
		}

		public void postaviStranice(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public int zapremina() {
			int z = a * b * c;
			return z;
		}

	}



	public static void ispis(Predmet p) {
		p.ispisi();
		System.out.println();
		System.out.print(p.dohvTezinu());
	}

	public static void main(String[] args) {	
		Predmet predmeti[]; int i; Sfera s1, s2, s3; Kvadar k1, k2, k3;
		int zapreminaK, zapreminaS;
		int tezinaK, tezinaS;
		predmeti = new Predmet[6];
		s1 = new Sfera();
		s2 = new Sfera(2);
		s3 = new Sfera(3);
		
		k3 = new Kvadar();
		k1 = new Kvadar(2);
		k2 = new Kvadar(3,4,5);
		
		predmeti[0] = s1;
		predmeti[2] = s2;
		predmeti[4] = s3;
		predmeti[1] = k1;
		predmeti[3] = k2;
		predmeti[5] = k3;

		int t = 2;
		i=0;
		while(i<6){
			predmeti[i].postaviTezinu(t); 	
			i++;
		}
		
		zapreminaS = 0;
		tezinaS = 0;
		i = 1;
		while(i<6){
			if(i % 2 == 0) {
				i++;
				continue; 
			}
			zapreminaS = zapreminaS + predmeti[i - 1].zapremina();
			tezinaS = tezinaS + predmeti[i - 1].Q();
			ispis(predmeti[i - 1]);
			i++;
		}
		
		
		zapreminaK = 0; 
		tezinaK = 0;
		i = 1;
		while(i<6){
			if(i % 2 == 1) {
				i++;
				continue; 
			}
			zapreminaS = zapreminaS + predmeti[i - 1].zapremina();
			tezinaS = tezinaS + predmeti[i - 1].Q();
			ispis(predmeti[i - 1]);
			i++;
		}
		
		System.out.println(zapreminaS);
		System.out.println(zapreminaK);
		System.out.println(tezinaS);
		System.out.println(tezinaK);

	}
}

