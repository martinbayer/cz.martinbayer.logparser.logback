package cz.martinbayer.logparser.logback.pattern;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;

public class PatternParser {

	public static final String CONV_WORD_GROUP_START = "!?(%";
	public static final String DELIMITER = "\\%";
	public static final String FORMAT_MODIFIER = "[\\d.-]";
	public static final String CONVERSION_WORDS = getConvWordsRegex();
	public static final String CONV_WORD_GROUP_END = ")";
	public static final String PROPERTY_GROUP = "(\\{[a-zA-Z0-9\\s\\:./]*\\})";
	public static final String ZERO_TO_MORE_REPETITIONS = "*";
	public static final String GROUP_START = "(";
	public static final String GROUP_END = ")";
	public static final String LOOK_AHEAD = "?=";
	public static final String LOOK_BEHIND = "?<=";

	private static String getConvWordsRegex() {
		ArrayList<String> sortedConvWordsDesc = ConversionWordsEnum
				.getAllConvWordsDesc();
		return getConvWordsAsRegex(sortedConvWordsDesc);
	}

	private static String getConvWordsAsRegex(ArrayList<String> convWords) {
		StringBuffer convWordsRegex = new StringBuffer();
		convWordsRegex.append("(");
		for (String word : convWords) {
			convWordsRegex.append(word).append("|");
		}
		convWordsRegex.deleteCharAt(convWordsRegex.length() - 1).append(")");
		return convWordsRegex.toString();
	}

	/**
	 * Method splits the pattern (e.g. from logback configuration) only to
	 * conversion words and its properties in brackets '{}' if any
	 * 
	 * @param configPattern
	 * @return
	 */
	public static String[] getConfigPatternParts(String configPattern) {
		StringBuffer sb = new StringBuffer();
		sb.append(CONV_WORD_GROUP_START);
		sb.append(FORMAT_MODIFIER).append(ZERO_TO_MORE_REPETITIONS);
		sb.append(CONVERSION_WORDS);
		sb.append(CONV_WORD_GROUP_END);
		sb.append(PROPERTY_GROUP).append(ZERO_TO_MORE_REPETITIONS);

		Pattern p = Pattern.compile(sb.toString());
		Matcher m = p.matcher(configPattern);
		ArrayList<String> partialPatterns = new ArrayList<>();
		while (m.find()) {
			partialPatterns.add(m.group());
		}
		return partialPatterns.toArray(new String[] {});
	}

	public static String getConvWord(String pattern) {
		String convWordsRegex = getConvWordsRegex();
		Pattern p = Pattern.compile(convWordsRegex);
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			return m.group();
		}
		return null;
	}

	public static String getFormat(String pattern) {
		StringBuffer sb = new StringBuffer();
		sb.append(GROUP_START).append(LOOK_BEHIND).append(DELIMITER)
				.append(GROUP_END);
		sb.append(GROUP_START).append(FORMAT_MODIFIER)
				.append(ZERO_TO_MORE_REPETITIONS).append(GROUP_END);
		sb.append(GROUP_START).append(LOOK_AHEAD).append(CONVERSION_WORDS)
				.append(GROUP_END);
		Pattern p = Pattern.compile(sb.toString());
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			return m.group();
		}
		return null;
	}

	public static String getProperties(String pattern) {
		String formatRegex = PROPERTY_GROUP;
		Pattern p = Pattern.compile(formatRegex);
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
}
