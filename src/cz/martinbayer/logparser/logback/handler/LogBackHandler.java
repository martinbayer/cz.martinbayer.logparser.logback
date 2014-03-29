package cz.martinbayer.logparser.logback.handler;

import java.io.File;

import cz.martinbayer.logparser.fileshandler.LogFileReader;
import cz.martinbayer.logparser.fileshandler.LogFileSemaphoreWatchedStore;
import cz.martinbayer.logparser.logic.ILogParserListener;

/**
 * 
 * @author Martin
 * 
 */
public class LogBackHandler {

	private static LogBackHandler instance;
	private String logbackConfigPattern;
	private File[] filesToParse;
	private String encoding = "UTF-8";
	private LogFileReader logFileReader;
	private LogbackReader logbackReceiver;

	private LogBackHandler(File[] filesToParse, String logbackConfigPattern,
			String encoding) {
		this.filesToParse = filesToParse;
		this.logbackConfigPattern = logbackConfigPattern;
		if (encoding != null) {
			this.encoding = encoding;
		}
		LogFileSemaphoreWatchedStore semaphore = new LogFileSemaphoreWatchedStore(
				5);
		logFileReader = new LogFileReader(semaphore, this.filesToParse,
				this.encoding);
		logbackReceiver = new LogbackReader(semaphore,
				this.logbackConfigPattern);
	}

	public static synchronized LogBackHandler getInstance(File[] filesToParse,
			String logbackConfigPattern, String encoding) {
		if (instance == null) {
			instance = new LogBackHandler(filesToParse, logbackConfigPattern,
					encoding);
		}
		return instance;
	}

	/**
	 * Only one listener can be used at once due to performance reason. Result
	 * can be used for other listeners in upper layer if needed.
	 * 
	 * @param listener
	 */
	public synchronized void doParse(ILogParserListener listener) {
		logbackReceiver.setListener(listener);
		Thread fileReadThread = new Thread(logFileReader);
		Thread logbackReceiverThread = new Thread(logbackReceiver);
		fileReadThread.start();
		logbackReceiverThread.start();

		try {
			logbackReceiverThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
