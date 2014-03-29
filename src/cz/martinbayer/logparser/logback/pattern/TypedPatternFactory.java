package cz.martinbayer.logparser.logback.pattern;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martinbayer.logparser.logback.pattern.model.ConversionTypesEnum;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWord;
import cz.martinbayer.logparser.logback.pattern.model.ConversionWordsEnum;

public class TypedPatternFactory {

	private static HashMap<String, TypedPattern> typedPatterns = new HashMap<>();
	/** contains group name and particular conversion word */
	private static HashMap<String, ConversionWordsEnum> groupToConversionWord = new HashMap<>();
	static {
		initPatterns();
	}

	/**
	 * This method initializes map with conversion words and particular
	 * patterns. If there are more conversion words possible for pattern they
	 * are all mapped to the instance of the TypedPattern e.g.:<br />
	 * <ul>
	 * <li>map.put("c",loggerPatternInstance)</li>
	 * <li>map.put("lo",loggerPatternInstance)</li>
	 * <li>map.put("logger",loggerPatternInstance)</li>
	 * </ul>
	 */
	private static void initPatterns() {
		for (ConversionTypesEnum type : ConversionTypesEnum.values()) {
			for (String convWord : type.getWords().getConversionWords()) {
				typedPatterns.put(convWord, type.getTypedPattern());
			}
		}
	}

	public static ConfigPatternInfo getRegexPattern(String configPattern) {
		/* use first param to split input and faster the processing */
		boolean splitterFound = false;
		ConfigPatternInfo info = new ConfigPatternInfo();
		String[] partialPatterns = PatternParser
				.getConfigPatternParts(configPattern);
		String[] between = new String[partialPatterns.length - 1];
		String leftSide, rightSide;
		Pattern p;
		Matcher m;
		for (int i = 0; i < between.length; i++) {
			leftSide = "(?<=" + Pattern.quote(partialPatterns[i]) + ")";
			rightSide = "(?=" + Pattern.quote(partialPatterns[i + 1]) + ")";
			p = Pattern.compile(leftSide + "(.*)" + rightSide);
			m = p.matcher(configPattern);
			if (m.find()) {
				between[i] = m.group().replace(")", "").replace("(", "")
						.replace(" ", "\\s").replace("[", "\\[")
						.replace("]", "\\]");
			} else {
				between[i] = null;
			}
		}
		StringBuffer regexPattern = new StringBuffer();
		ConversionWord convWord;
		int i = 0;
		for (String partPattern : partialPatterns) {
			convWord = new ConversionWord(partPattern);
			TypedPattern pattern = typedPatterns.get(convWord.getConvWord());
			if (pattern != null) {
				info.addGroup(pattern.getGroupName());
				regexPattern.append(pattern.getRegex(convWord)).append(
						i < between.length ? between[i++] : "");
				if (!splitterFound) {
					info.setSplitPatternt(pattern.getRegex(convWord));
					splitterFound = true;
				}
			}
		}
		info.setParsedConfigPattern(regexPattern.toString());
		return info;
	}

	public static ConversionWordsEnum getConversionWordByGroupName(
			String groupName) {
		ConversionWordsEnum convWord = groupToConversionWord.get(groupName);
		if (convWord == null) {
			for (TypedPattern pattern : typedPatterns.values()) {
				if (pattern.getGroupName().equals(groupName)) {
					groupToConversionWord.put(groupName, pattern.getConvWord());
					return pattern.getConvWord();
				}
			}
		}
		return convWord;
	}
}
