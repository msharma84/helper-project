
public class CheckJVMBit {

	public static void main(String[] args) {
		
		String jvmBit = System.getProperty("sun.arch.data.model");
		System.out.println("JVM Bit Size..."+jvmBit);
	}

}
