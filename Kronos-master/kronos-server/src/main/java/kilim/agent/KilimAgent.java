package kilim.agent;

import kilim.WeavingClassLoader;
import kilim.analysis.ClassWeaver;
import kilim.tools.Agent;
import kilim.tools.Weaver;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.TreeMap;

@Slf4j
public class KilimAgent {

	public static Weaver weaver = new Weaver(null);
	public static WeavingClassLoader classLoader;

	private static Instrumentation instrumentation;

	/**
	 * JVM hook to statically load the javaagent at startup.
	 *
	 * After the Java Virtual Machine (JVM) has initialized, the premain method
	 * will be called. Then the real application main method will be called.
	 *
	 * @param args
	 * @param inst
	 * @throws Exception
	 */
	public static void premain(String args, Instrumentation inst) throws Exception {
		log.info("premain method invoked with args: {} and inst: {}", args, inst);
		instrumentation = inst;
		instrumentation.addTransformer(new WeaveTransformer());
	}

	/**
	 * JVM hook to dynamically load javaagent at runtime.
	 *
	 * The agent class may have an agentmain method for use when the agent is
	 * started after VM startup.
	 *
	 * @param args
	 * @param inst
	 * @throws Exception
	 */
	public static void agentmain(String args, Instrumentation inst) throws Exception {
		log.info("agentmain method invoked with args: {} and inst: {}", args, inst);
		instrumentation = inst;
		instrumentation.addTransformer(new WeaveTransformer());
		if (Agent.map==null) Agent.map = new TreeMap();
	}

	public static class WeaveTransformer implements ClassFileTransformer {


		public byte[] transform(
				ClassLoader loader,
				String name,
				Class klass,
				ProtectionDomain protectionDomain,
				byte[] bytes)
				throws IllegalClassFormatException {

			String memory = "kilim.WeavingClassLoader";
			if (loader != null && memory.equals(loader.getClass().getName()) && name != null && name.startsWith("io/ruin/")) {
				//log.info("Transform {} {} {}", loader.getClass().getName(), name, Kilim.isWoven(klass));
				String cname = WeavingClassLoader.makeResourceName(name);
				Agent.map.put(cname,bytes);
				ClassWeaver weaver = weaveClass(loader, name);
				return weaver.classFlow.code;
				//return classLoader.weaveClass(cname).classFlow.code;
			}

			/*if(name != null && !name.isEmpty()) {
				ClassWeaver classWeaver = weaver.weave(new ByteArrayInputStream(bytes));
				log.info("Reweaving class {}, {}, {} {}", classWeaver.classFlow.isWoven, loader.getClass().getName(), name, classWeaver.getClassInfos().stream().map(classInfo -> classInfo.className).collect(Collectors.joining(", ")));
				//bytes = classWeaver.getClassInfos().stream().filter(classInfo -> WeaveclassInfo.className)
				///return classWeaver.getClassInfos().get(0).bytes;
				classLoader.define(name,  classWeaver.classFlow.code);

				return classWeaver.classFlow.code;
			}*/
			return bytes;
		}

		public static ClassWeaver weaveClass(ClassLoader loader, String name) {
			String cname = WeavingClassLoader.makeResourceName(name);
			InputStream is = WeavingClassLoader.getByteStream(loader,name,cname,false);
			ClassWeaver cw = weaver.weave(is);
			return cw;
		}

	}


}
