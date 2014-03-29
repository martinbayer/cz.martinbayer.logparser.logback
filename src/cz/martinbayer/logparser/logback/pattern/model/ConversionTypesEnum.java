package cz.martinbayer.logparser.logback.pattern.model;

import cz.martinbayer.logparser.logback.pattern.TypedPattern;
import cz.martinbayer.logparser.logback.pattern.types.DateTimePattern;
import cz.martinbayer.logparser.logback.pattern.types.ExceptionPattern;
import cz.martinbayer.logparser.logback.pattern.types.FilePattern;
import cz.martinbayer.logparser.logback.pattern.types.LevelPattern;
import cz.martinbayer.logparser.logback.pattern.types.LinePattern;
import cz.martinbayer.logparser.logback.pattern.types.MessagePattern;
import cz.martinbayer.logparser.logback.pattern.types.NewLinePattern;
import cz.martinbayer.logparser.logback.pattern.types.ThreadPattern;

public enum ConversionTypesEnum {

	DATE_TIME_TYPE(ConversionWordsEnum.DATE_TIME_OF_EVENT,
			new DateTimePattern()),
	LEVEL_TYPE(ConversionWordsEnum.LEVEL_OF_EVENT, new LevelPattern()),
	EXCEPTION_TYPE(ConversionWordsEnum.EXCEPTION, new ExceptionPattern()),
	EXTENDED_EXCEPTION_TYPE(ConversionWordsEnum.EXTENDED_EXCEPTION,
			new ExceptionPattern()),
	FILE_TYPE(ConversionWordsEnum.FILE_OF_REQUEST, new FilePattern()),
	LINE_TYPE(ConversionWordsEnum.LINE_OF_REQUEST, new LinePattern()),
	NEW_LINE_TYPE(ConversionWordsEnum.NEW_LINE, new NewLinePattern()),
	THREAD_TYPE(ConversionWordsEnum.THREAD_NAME, new ThreadPattern()),
	MESSAGE_TYPE(ConversionWordsEnum.MESSAGE, new MessagePattern());

	private ConversionWordsEnum words;
	private TypedPattern typedPattern;

	ConversionTypesEnum(ConversionWordsEnum words, TypedPattern typedPattern) {
		this.words = words;
		this.typedPattern = typedPattern;
	}

	public ConversionWordsEnum getWords() {
		return words;
	}

	public TypedPattern getTypedPattern() {
		return typedPattern;
	}
}
