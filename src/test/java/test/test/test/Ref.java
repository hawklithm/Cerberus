//package test.test.test;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//public class Ref {
//	private ArrayList parameterized_type;
//
//	private static String getListString() {
//		ArrayList test = new ArrayList();
//		for (int i = 0; i < 10; i++) {
//			test.add(new String("test" + i));
//		}
//		System.out.println("ori " + test);
//		return new Gson().toJson(test);
//	}
//
//	public static void main(String[] args) {
//		try {
//			Class gson = Class.forName("com.google.gson.Gson");
//			Class rr = Ref.class;
//			Field parameterized_type_field = rr.getDeclaredField("parameterized_type");
//			Type parameterized_type = parameterized_type_field.getGenericType();
//			System.out.println("field=" + parameterized_type_field + " type=" + parameterized_type);
//
//			Method gett = gson.getMethod("fromJson", String.class, Type.class);
//
//			ArrayList got = (ArrayList) gett.invoke(gson.newInstance(), getListString(),
//					parameterized_type);
//
//			System.out.println("got " + got);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}