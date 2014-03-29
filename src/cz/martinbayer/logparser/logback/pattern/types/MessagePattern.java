package cz.martinbayer.logparser.logback.pattern.types;

import cz.martinbayer.logparser.logback.pattern.PatternParser;
import cz.martinbayer.logparser.logback.pattern.TypedPattern;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;

public class MessagePattern extends TypedPattern {

	private static final String GROUP_NAME = "message";

	private static final String MESSAGE_REGEX = ".*?";

	@Override
	public String getGroupName() {
		return GROUP_NAME;
	}

	@Override
	protected String initRegex() {
		StringBuffer sb = new StringBuffer();
		sb.append(PatternParser.GROUP_START).append(MESSAGE_REGEX)
				.append(PatternParser.GROUP_END);
		return sb.toString();
	}

	@Override
	protected void initRegexProperty() {
		// none for level property
	}

	@Override
	public ConversionWordsEnum getConvWord() {
		return ConversionWordsEnum.MESSAGE;
	}
}
