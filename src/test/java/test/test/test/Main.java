//package test.test.test;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gson.Gson;
//import com.multiagent.hawklithm.rpc.utils.GenericsUtils;
//
//public class Main {
//	List<JustTest> list = new ArrayList<JustTest>();
//	Integer tt;
//
//	static public class JustTest {
//		public JustTest() {
//
//		}
//
//		public JustTest(int a, boolean b) {
//			dfasdfa = a;
//			dfasdf = b;
//		}
//
//		int dfasdfa;
//		boolean dfasdf;
//	}
//
//	public static void main(String args[]) {
//		Main test = new Main();
//		try {
//			test.run();
//		} catch (NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void run() throws NoSuchFieldException, SecurityException {
//		Class<?> clazz = Main.class;
//		Field field = clazz.getDeclaredField("list");
//		Class<?> clazz2 = field.getType();
//		Gson gson = new Gson();
//		Main zzz = new Main();
//		zzz.list.add(new JustTest(11, true));
//		Type basicType = null;
//		String zStr = gson.toJson(zzz.list);
//		Type mapMainType = field.getGenericType();
//		Type type = null;
//		// Ϊ��ȷ����ȫת����ʹ��instanceof
//		if (mapMainType instanceof ParameterizedType) {
//			// ִ��ǿ������ת��
//			ParameterizedType parameterizedType = (ParameterizedType) mapMainType;
//			// ��ȡ����������Ϣ����Map
//			basicType = parameterizedType.getRawType();
//			System.out.println("��������Ϊ��" + basicType);
//			// ��ȡ�������͵ķ��Ͳ���
//			Type[] types = parameterizedType.getActualTypeArguments();
//			for (int i = 0; i < types.length; i++) {
//				System.out.println("��" + (i + 1) + "�����������ǣ�" + types[i]);
//				type = types[i];
//			}
//		} else {
//			System.out.println("��ȡ�������ͳ���!: " + mapMainType);
//		}
//
//		List<Object> fff = gson.fromJson(zStr, basicType);
//		for (int index = 0; index < fff.size(); index++) {
//			Object zz = fff.get(index);
//			if (zz instanceof Map) {
//				try {
//					fff.set(index, GenericsUtils.getObjectFromMap((Class<?>) type, (Map) zz));
//				} catch (InstantiationException | IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		for (Object zz : fff) {
//			System.out.println(((JustTest) zz).dfasdfa + "," + ((JustTest) zz).dfasdf);
//		}
//	}
//}
