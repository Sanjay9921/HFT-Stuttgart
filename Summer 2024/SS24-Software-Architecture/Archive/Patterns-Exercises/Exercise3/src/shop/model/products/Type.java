package shop.model.products;

public enum Type {
	
	BLU_RAY("BluRay"), BOOK("Book"), CD("CD"), DOWNLOAD("download"), DVD("DVD"), MISC("misc");
	
	private String name, canonicalName;

	private Type(String name) {
		this.name = name;
		this.canonicalName = trim(this.name());
	}
	
	private static String trim(String str){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(('a' <= c && c <= 'z') || ('0' <= c && c <= '9'))
				sb.append(c);
			else if ('A' <= c && c <= 'Z')
				sb.append((char)('a' + (c - 'A')));				
		}
		return sb.toString();
	}
	
	public static Type parseType(String str){
		String trimmed = trim(str);
		for(Type t : Type.values())
			if(t.canonicalName.equals(trimmed))
				return t;
		return MISC;
	}

	public String getName() {
		return name;
	}

	public String toString(){
		return name;
	}

}
