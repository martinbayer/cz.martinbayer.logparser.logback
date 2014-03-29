package cz.martinbayer.logparser.logback.pattern.types;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martinbayer.logparser.logback.pattern.TypedPattern;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;

public class DateTimePattern extends TypedPattern {
	enum DateTimePatternLetters {

		G /* Era designator Text AD */,
		y /* Year Year 1996; 96 */,
		M /* Month in year Month July; Jul; 07 */,
		w /* Week in year Number 27 */,
		W /* Week in month Number 2 */,
		D /* Day in year Number 189 */,
		d /* Day in month Number 10 */,
		F /* Day of week in month Number 2 */,
		E /* Day in week Text Tuesday ; Tue */,
		a /* Am / pm marker Text PM */,
		H /* Hour in day ( 0 - 23 ) Number 0 */,
		k /* Hour in day ( 1 - 24 ) Number 24 */,
		K /* Hour in am / pm ( 0 - 11 ) Number 0 */,
		h /* Hour in am / pm ( 1 - 12 ) Number 12 */,
		m /* Minute in hour Number 30 */,
		s /* Second in minute Number 55 */,
		S /* Millisecond Number 978 */,
		z /*
		 * Time zone General time zone Pacific Standard Time ; PST ; GMT - 08 :
		 * 00
		 */,
		Z /* Time zone */;

	}

	public static final String GROUP_NAME = "datetime";

	public static String ISO8601FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * In logback, the pattern is compatible with SimpleDateFormat in java.
	 * Default pattern is specified(ISO8601) in case that no pattern is used
	 * 
	 * @param pattern
	 * @return
	 */
	private String patternToRegex(String pattern) {
		if (pattern == null) {
			pattern = ISO8601FORMAT;
		}
		// HH:mm:ss.SSS
		/* all values which cannot separate the time or date values */
		StringBuffer negSeparators = new StringBuffer(Arrays
				.toString(DateTimePatternLetters.values())
				.replaceAll("^.|.$", "").replaceAll(", ", ""));
		negSeparators.insert(0, "[");
		negSeparators.append("]+");
		Pattern p = Pattern.compile(negSeparators.toString());
		Matcher m = p.matcher(pattern);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String value = m.group();
			m.appendReplacement(sb, String.format("\\\\w{%d}", value.length()));
		}
		return sb.toString();
	}

	@Override
	protected String initRegex() {
		/* pattern is the only constant value for '%d' conversion name */
		return regexSettings;
	}

	@Override
	protected void initRegexProperty() {
		if (stringSettings != null) {
			regexSettings = patternToRegex(stringSettings);
		}
	}

	@Override
	public String getGroupName() {
		return GROUP_NAME;
	}

	@Override
	public ConversionWordsEnum getConvWord() {
		return ConversionWordsEnum.DATE_TIME_OF_EVENT;
	}
}
