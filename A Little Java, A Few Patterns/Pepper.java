package little.java;

public class Pepper extends Kebab {
	Kebab k;

	Pepper(Kebab _k){
		this.k = _k;
	}
	
	@Override
	boolean isVeggie() {
		return k.isVeggie();
	}

	@Override
	Object whatHolder() {
		return k.whatHolder();
	}

}
