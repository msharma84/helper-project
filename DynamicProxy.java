import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

interface Human {
	void walk();
	void talk();
	}

class Person implements Human{

	public void walk() {
		System.out.println("I am Walking");
	}

	public void talk() {
		System.out.println("I am Talking");
	}
	
}

class LoggingHandler implements InvocationHandler{

	private final Object target;
	private Map<String, Integer> calls = new HashMap<>();
	
	public LoggingHandler(Object target){
		this.target = target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, 
	IllegalArgumentException,
	InvocationTargetException {
		
		String name = method.getName();
		
		if(name.contains("toString")) {
			return calls.toString();
		}
		calls.merge(name, 1, Integer::sum);
		return method.invoke(target, args); 
	}
	
}

public class DynamicProxy {

	@SuppressWarnings("unchecked")
	public static <T> T withLogging(T target, Class<T> itf) {
		
		return (T) Proxy.newProxyInstance(
				itf.getClassLoader(),
				new Class<?>[] {itf},
				new LoggingHandler(target));
	}
	
	public static void main(String[] args) {
		
		Person person = new Person();
		Human logged = withLogging(person, Human.class);
		logged.talk();
		logged.walk();
		logged.walk();
		System.out.println(logged);
	}

}
