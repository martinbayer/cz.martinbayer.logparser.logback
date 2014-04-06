package cz.martinbayer.logparser.logback.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martinbayer.logparser.fileshandler.LogFileReceiver;
import cz.martinbayer.logparser.fileshandler.LogFileSemaphoreWatchedStore;
import cz.martinbayer.logparser.logback.pattern.ConfigPatternInfo;
import cz.martinbayer.logparser.logback.pattern.TypedPatternFactory;
import cz.martinbayer.logparser.logic.ILogParserEvent;
import cz.martinbayer.logparser.logic.ILogParserListener;
import cz.martinbayer.logparser.logic.LogParserPhase;

public class LogbackReader extends LogFileReceiver {

	private ILogParserListener listener;
	private String logbackConfigPattern;
	private Pattern parsedPattern;
	private ConfigPatternInfo info;
	private Pattern splitPattern;

	public LogbackReader(LogFileSemaphoreWatchedStore semaphore,
			String logbackConfigPattern) {
		super(semaphore);
		this.logbackConfigPattern = logbackConfigPattern;
		initPattern(logbackConfigPattern);
	}

	private void initPattern(String logbackConfigPattern2) {
		info = TypedPatternFactory.getRegexPattern(this.logbackConfigPattern);
		splitPattern = Pattern.compile("(?s)" + info.getSplitPattern());
		parsedPattern = Pattern.compile("(?s)" + info.getParsedConfigPattern());
	}

	/**
	 * Listener must be set before the processing is started otherwise exception
	 * is thrown
	 * 
	 * @param listener
	 */
	public void setListener(ILogParserListener listener) {
		this.listener = listener;
	}

	@Override
	public int handleStoredBuffer(StringBuffer sb, int actualLength) {
		int lastFound = -1;
		if (listener == null) {
			throw new UnsupportedOperationException(
					"Operation is not allows if no listener is registered");
		}
		Matcher localMatcher = splitPattern.matcher(sb.substring(0,
				actualLength));
		// tady se ctou nekolikrat stejne data...wtf
		int firstIdx = -1, lastIdx = -1;
		String firstStr = null, lastStr = null;
		Matcher wholePartMatcher;
		while (localMatcher.find()) {
			lastStr = localMatcher.group("datetime");
			lastIdx = localMatcher.start();
			if (firstIdx >= 0) {
				// System.out.println("[" + firstIdx + "," + lastIdx + "]");
				wholePartMatcher = parsedPattern.matcher(sb.substring(firstIdx,
						lastIdx));
				if (wholePartMatcher.find()) {
					this.listener.parsed(new ILogParserEvent(this,
							LogParserPhase.START, null, null));
					String value;
					for (String group : info.getGroups()) {
						/*
						 * invoke listener only if there is some value for the
						 * group
						 */
						if ((value = wholePartMatcher.group(group)) != null) {
							this.listener.parsed(new ILogParserEvent(this,
									LogParserPhase.PROPERTY, group, value));
						}
					}
					this.listener.parsed(new ILogParserEvent(this,
							LogParserPhase.FINISH, null, null));
				}
			}
			lastFound = lastIdx;
			firstIdx = lastFound;
			firstStr = lastStr;
		}
		return lastFound;
	}

	@Override
	protected void releaseSources() {
		listener = null;

	}
}
