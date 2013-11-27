package net.mosstest.servercore;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MossDebugUtils {

	private static final String[] propertiesToGet = { "awt.toolkit", //$NON-NLS-1$
			"file.encoding", "file.separator", "java.class.version", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"java.home", "java.runtime.name", "java.runtime.version", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"java.specification.name", "java.specification.vendor", //$NON-NLS-1$ //$NON-NLS-2$
			"java.specification.version", "java.vendor", "java.vm.info", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"java.vm.name", "java.vm.specification.name", "java.vm.version", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"os.arch", "os.name", "path.separator", "sun.arch.data.model", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"sun.cpu.endian", "sun.desktop", "user.language" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	};

	public static String getDebugInformation(Exception e) {
		StringBuilder s = new StringBuilder(
				MossDebugUtils.getGitConfig("git.commit.id") + " on "+ MossDebugUtils.getGitConfig("git.branch")+ "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s.append(Messages.getString("MossDebugUtils.MSG_BUILT_ON")+ MossDebugUtils.getGitConfig(Messages.getString("MossDebugUtils.27"))+"\r\n\r\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s.append(Messages.getString("MossDebugUtils.MSG_EXCEPTION_CAUGHT")); //$NON-NLS-1$
		for (StackTraceElement ste : e.getStackTrace()) {
			s.append(ste.toString()).append("\r\n"); //$NON-NLS-1$
		}
		s.append("\r\n"); //$NON-NLS-1$
		s.append(getOsDetails());
		return s.toString();
	}

	public static String getOsDetails() {
		StringBuilder s = new StringBuilder();

		s.append(Messages.getString("MossDebugUtils.MSG_SYS_PROPS")); //$NON-NLS-1$
		s.append(Messages.getString("MossDebugUtils.MSG_CORES") //$NON-NLS-1$
				+ Runtime.getRuntime().availableProcessors() + "\r\n"); //$NON-NLS-1$
		s.append(Messages.getString("MossDebugUtils.MSG_FREEMEM") + Runtime.getRuntime().freeMemory() //$NON-NLS-1$
				+ "\r\n"); //$NON-NLS-1$
		long maxMemory = Runtime.getRuntime().maxMemory();
		s.append(Messages.getString("MossDebugUtils.MSG_MAXMEM") //$NON-NLS-1$
				+ (maxMemory == Long.MAX_VALUE ? Messages.getString("MossDebugUtils.MSG_MEM_NO_LIMIT") : maxMemory) //$NON-NLS-1$
				+ "\r\n"); //$NON-NLS-1$
		s.append(Messages.getString("MossDebugUtils.MSG_TOTAL_MEM") + Runtime.getRuntime().totalMemory() //$NON-NLS-1$
				+ "\r\n"); //$NON-NLS-1$
		File[] roots = File.listRoots();

		for (File root : roots) {
			s.append(Messages.getString("MossDebugUtils.MSG_FS_ROOT") + root.getAbsolutePath() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
			s.append(Messages.getString("MossDebugUtils.MSG_TOTAL_SPACE") + root.getTotalSpace() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$

			s.append(Messages.getString("MossDebugUtils.MSG_FREE_SPACE") + root.getFreeSpace() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
			s.append(Messages.getString("MossDebugUtils.MSG_USABLE_SPACE") + root.getUsableSpace() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		s.append("\r\n"); //$NON-NLS-1$
		s.append(Messages.getString("MossDebugUtils.MSG_JAVA_PROPS")); //$NON-NLS-1$
		for (String key : propertiesToGet) {
			s.append(key + ":" + System.getProperty(key, "") + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return s.toString();
	}

	private static String getGitConfig(String cfgKey) {

		Properties properties = new Properties();
		try {
			properties.load(MossDebugUtils.class.getClassLoader()
					.getResourceAsStream("git.properties")); //$NON-NLS-1$
		} catch (IOException e) {
			return Messages.getString("MossDebugUtils.MSG_IO_EXCEPTION"); //$NON-NLS-1$
		}
		return properties.getProperty(cfgKey);

	}

	public static void main(String[] args) {
		System.out.println(getDebugInformation(new Exception()));
	}
}
