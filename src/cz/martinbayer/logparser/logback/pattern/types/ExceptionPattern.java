package cz.martinbayer.logparser.logback.pattern.types;

import cz.martinbayer.logparser.logback.pattern.PatternParser;
import cz.martinbayer.logparser.logback.pattern.TypedPattern;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;
import cz.martinbayer.logparser.logback.pattern.model.RegexQuantity;

public class ExceptionPattern extends TypedPattern {

	private static final String GROUP_NAME = "exception";
	private static final String EXCEPTION_REGEX = "\\S*(Exception|Error):[^\\n\\r]*(\\n|\\r|\\r\\n)((\\s*at[^\\n\\r]*(\\n|\\r|\\r\\n))|(Caused by:[^\\n\\r]*(\\n|\\r|\\r\\n))|(\\s*\\.{3} [0-9]+ common frames omitted(\\n|\\r|\\r\\n)))";

	@Override
	public String getGroupName() {
		return GROUP_NAME;
	}

	@Override
	protected String initRegex() {
		StringBuffer sb = new StringBuffer();
		sb.append(PatternParser.GROUP_START).append(EXCEPTION_REGEX)
				.append(RegexQuantity.ZERO_TO_MANY.getQuantity())
				.append(PatternParser.GROUP_END);
		return sb.toString();
	}

	@Override
	protected void initRegexProperty() {
		// none for level property
	}

	@Override
	protected RegexQuantity getQuantity() {
		return RegexQuantity.ZERO_TO_ONE;
	}

	@Override
	public ConversionWordsEnum getConvWord() {
		return ConversionWordsEnum.EXCEPTION;
	}
}
