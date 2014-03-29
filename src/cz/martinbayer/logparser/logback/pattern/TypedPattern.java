package cz.martinbayer.logparser.logback.pattern;

import java.util.regex.Matcher;

import cz.martinbayer.logparser.logback.pattern.model.ConversionWord;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;
import cz.martinbayer.logparser.logback.pattern.model.RegexQuantity;

public abstract class TypedPattern {

	/* String placed between brackets */
	protected String stringSettings;

	/**
	 * This String contains settings of the conversion word translated to
	 * regular expression. To initialize this property,
	 * {@link #initRegexProperty()} method must be invoked. This method is
	 * performed only if {@link #propertyPresent} values is True. stringSettings
	 * initialized by {@link #initRegexProperty()} is used
	 */
	protected String regexSettings;

	private boolean propertyPresent = false;

	/**
	 * Pattern group is created which is named by {@link #getGroupName()} result
	 * 
	 * @param convWord
	 * @return
	 */
	public synchronized String getRegex(ConversionWord word) {
		defaultValues();
		initStringProperties(word.getProperties());
		if (propertyPresent) {
			initRegexProperty();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(?<").append(getGroupName()).append(">");
		sb.append(initRegex());
		sb.append(")").append(getQuantity().getQuantity());
		return sb.toString();
	}

	private void defaultValues() {
		stringSettings = null;
		regexSettings = null;
		propertyPresent = false;
	}

	protected RegexQuantity getQuantity() {
		return RegexQuantity.DEFAULT;
	}

	protected abstract String initRegex();

	/**
	 * This method will check if the properties are set for conversion word.<br />
	 * Example:<br />
	 * There is date time of the event configured in the pattern like
	 * '%d{MM/dd/yy HH:mm:ss.SSS}' so the property representing the date pattern
	 * is present.
	 * 
	 * @param propertyString
	 * @param convWord
	 */
	private void initStringProperties(String propertyString) {
		/*
		 * there must be at least curly brackets and value between - '{value}',
		 * also right bracket needs to be present after left bracket
		 */
		int leftBracketIdx = -1;
		int rightBracketIdx = -1;
		if (propertyString == null
				|| propertyString.length() < 3
				|| (leftBracketIdx = propertyString.indexOf("{")) < 0
				|| (rightBracketIdx = propertyString.indexOf("}")) <= leftBracketIdx) {
			return;
		}
		this.propertyPresent = true;
		this.stringSettings = propertyString.substring(leftBracketIdx + 1,
				rightBracketIdx);

	}

	/**
	 * Convert value of stringSettings to settings. It is invoked after
	 * initStringProperties method
	 */

	protected abstract void initRegexProperty();

	/**
	 * Returns true if the property is configured for the conversion word. It is
	 * false by default and can be initialized by invoking
	 * {@link #initStringProperties(String)}
	 */
	public boolean isPropertyPresent() {
		return propertyPresent;
	}

	/**
	 * Group name is needed to be able to use {@link Matcher#group(String)}
	 * function
	 * 
	 * @return pattern group name
	 */
	public abstract String getGroupName();

	/**
	 * @return {@link ConversionWordsEnum} object which is a conversion word for
	 *         actual pattern
	 */
	public abstract ConversionWordsEnum getConvWord();
}
