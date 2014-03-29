package cz.martinbayer.logparser.logback.pattern.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ConversionWordsEnum {
	LOGGER_CLASS_CONVERSION("c", "lo", "logger", "C", "class"),

	CONTEXT_NAME("contextName", "cn"),

	DATE_TIME_OF_EVENT("d", "date"),

	FILE_OF_REQUEST("F", "file"),

	CALLER_STACK("caller"),

	LINE_OF_REQUEST("L", "line"),

	MESSAGE("m", "msg", "message"),

	METHOD_NAME("M", "method"),

	NEW_LINE("n"),

	LEVEL_OF_EVENT("p", "le", "level"),

	TIME_SINCE_APP_START("r", "relative"),

	THREAD_NAME("t", "thread"),

	EXCEPTION("ex", "exception", "throwable"),

	/*
	 * same as EXCEPTION but contains package information (jar, lib file of the
	 * source )
	 */
	EXTENDED_EXCEPTION("xEx", "xException", "xThrowable"),

	/* exceptions are ignored */
	NO_EXCEPTION("nopex", "nopexception"),

	MARKER("marker"),

	/**
	 * prints system property. Example: &lt;property scope="context"
	 * name="USER_HOME" value="/home/sebastien" /&gt;<br />
	 * then in the pattern: <pattern>%d{HH:mm:ss.SSS} %marker [%thread] %-5level
	 * %logger{0}:<b>%property{USER_HOME}</b>: - %msg %nopex%n</pattern>
	 */
	PROPERTY("property");

	private List<String> conversionWords;

	ConversionWordsEnum(String... args) {
		conversionWords = Arrays.asList(args);
	}

	public List<String> getConversionWords() {
		return this.conversionWords;
	}

	/**
	 * @return - all conversion words of all elements in enum sorted by its
	 *         length desc
	 */
	public static ArrayList<String> getAllConvWordsDesc() {
		ArrayList<String> convWords = new ArrayList<>();
		for (ConversionWordsEnum convWord : values()) {
			for (String word : convWord.getConversionWords()) {
				convWords.add(word);
			}
		}
		/* sort the list of conversion words by the length desc */
		Collections.sort(convWords, new OrderByLengthDescComparator());
		return convWords;
	}
}

class OrderByLengthDescComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) {
			return 1;
		} else if (o2 == null) {
			return -1;
		} else {
			return o1.length() < o2.length() ? 1 : -1;
		}
	}
}