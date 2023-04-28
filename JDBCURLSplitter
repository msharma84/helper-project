public class JDBCURLSplitter {

	public static void main(String[] args) {
		
		String url = "jdbc:postgresql://localhost:5432/bludb?currentSchema=mydb";
		int slashing = url.indexOf("//") + 2;
		String sub = url.substring(slashing, url.indexOf("/", slashing));
		String[] splitted = sub.split(":");
	}

}
