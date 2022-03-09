import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLIBProxy {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		list.add("Hello");
		list.add("Proxy");
		
		log("Creating Proxy Interface");
		List<String> proxyList = (List<String>) Enhancer.create(List.class, new MyInvocationHandler(list));
		for (int i = 0; i < 3; i++) {
			log(proxyList.get(i));
		}
		
		log("Creating Proxy Class");
		proxyList = (List<String>)Enhancer.create(ArrayList.class, new MyInvocationHandler(list));
		for (int i = 0; i < 3; i++) { 
			log(proxyList.get(i)); 
		}
		
	}
	
	static class MyInvocationHandler implements MethodInterceptor{
		
		private List<String> list;
		
		public MyInvocationHandler(List<String> list) {
			this.list = list;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
				throws Throwable {
			
			if (isFouthGet(method, args)) {
				return "Bow!!";
			}
			
			return proxy.invoke(list, args);
		}
		
		private boolean isFouthGet(Method method, Object[] args) {
			return "get".equals(method.getName()) && ((Integer)args[0]) == 2;
		}
		
	}
	
	public static void log(Object msg ) {
		System.out.println(msg);
	}

}
