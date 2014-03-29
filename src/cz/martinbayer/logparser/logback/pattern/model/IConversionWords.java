package cz.martinbayer.logparser.logback.pattern.model;

public interface IConversionWords {
	public static final String[] LOGGER_CLASS_CONVERSION = new String[] { "c",
			"lo", "logger", "C", "class" };

	public static final String[] CONTEXT_NAME = new String[] { "contextName",
			"cn" };

	public static final String[] DATE_OF_EVENT = new String[] { "d", "date" };

	public static final String[] FILE_OF_REQUEST = new String[] { "F", "file" };

	public static final String[] CALLER_STACK = new String[] { "caller" };

	public static final String[] LINE_OF_REQUEST = new String[] { "L", "line" };

	public static final String[] MESSAGE = new String[] { "m", "msg", "message" };

	public static final String[] METHOD_NAME = new String[] { "M", "method" };

	public static final String[] NEW_LINE = new String[] { "n" };

	public static final String[] LEVEL_OF_EVENT = new String[] { "p", "le",
			"level" };

	public static final String[] TIME_SINCE_APP_START = new String[] { "r",
			"relative" };

	public static final String[] THREAD_NAME = new String[] { "t", "thread" };

	public static final String[] EXCEPTION = new String[] { "ex", "exception",
			"throwable" };

	/*
	 * same as EXCEPTION but contains package information (jar, lib file of the
	 * source )
	 */
	public static final String[] EXTENDED_EXCEPTION = new String[] { "xEx",
			"xException", "xThrowable" };

	/* exceptions are ignored */
	public static final String[] NO_EXCEPTION = new String[] { "nopex",
			"nopexception" };

	public static final String[] MARKER = new String[] { "marker" };

	/**
	 * prints system property. Example: &lt;property scope="context"
	 * name="USER_HOME" value="/home/sebastien" /&gt;<br />
	 * then in the pattern: <pattern>%d{HH:mm:ss.SSS} %marker [%thread] %-5level
	 * %logger{0}:<b>%property{USER_HOME}</b>: - %msg %nopex%n</pattern>
	 */
	public static final String[] PROPERTY = new String[] { "property" };
}
