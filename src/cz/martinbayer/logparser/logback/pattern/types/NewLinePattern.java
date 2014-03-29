package cz.martinbayer.logparser.logback.pattern.types;

import cz.martinbayer.logparser.logback.pattern.PatternParser;
import cz.martinbayer.logparser.logback.pattern.TypedPattern;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;

public class NewLinePattern extends TypedPattern {

	private static final String GROUP_NAME = "newline";
	private static final String NEW_LINE_REGEX = "\\n|\\r|\\r\\n";

	@Override
	public String getGroupName() {
		return GROUP_NAME;
	}

	@Override
	protected String initRegex() {
		StringBuffer sb = new StringBuffer();
		sb.append(PatternParser.GROUP_START).append(NEW_LINE_REGEX)
				.append(PatternParser.GROUP_END);
		return sb.toString();
	}

	@Override
	protected void initRegexProperty() {
		// none for level property
	}

	@Override
	public ConversionWordsEnum getConvWord() {
		return ConversionWordsEnum.NEW_LINE;
	}
}
