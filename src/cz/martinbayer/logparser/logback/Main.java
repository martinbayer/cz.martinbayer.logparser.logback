package cz.martinbayer.logparser.logback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cz.martinbayer.logparser.logback.handler.LogBackHandler;
import cz.martinbayer.logparser.logback.pattern.TypedPatternFactory;
import cz.martinbayer.logparser.logic.ILogParserEvent;
import cz.martinbayer.logparser.logic.ILogParserListener;
import cz.martinbayer.logparser.logic.LogParserPhase;

public class Main {

	public static void main(String[] args) throws IOException {
		String pattern = "%-20(%d{MM/dd/yy HH:mm:ss.SSS} %-5level) %msg %xEx{full} [%thread] [%file:%line]%n";
		String encoding = "UTF-8";
		String filePath = "D:\\School\\Mgr\\Diploma thesis\\logback\\logs";
		LogBackHandler handler = LogBackHandler.getInstance(
				new File(filePath).listFiles(), pattern, encoding);

		// LogBackHandler handler = LogBackHandler
		// .getInstance(
		// new File[] { new File(
		// "D:\\School\\Mgr\\Diploma thesis\\logback\\logs\\client_debug.log")
		// },
		// pattern, encoding);
		final FileOutputStream fs = new FileOutputStream(new File(
				"c:\\ahoj.txt"));
		FunParserListener listener = new FunParserListener();
		handler.doParse(listener);
		fs.flush();
		fs.close();

		System.out.println("count:" + listener.getObjectsCount());

	}
}

class FunParserListener implements ILogParserListener {
	ArrayList<FunObject> objects = new ArrayList<>();
	FunObject object;

	@Override
	public void parsed(ILogParserEvent event) {
		if (event.getPhase() == LogParserPhase.START) {
			object = new FunObject();
		} else if (event.getPhase() == LogParserPhase.FINISH) {
			objects.add(object);
			object = null;
		} else {
			if (object == null || event.getGroupName() == null) {
				throw new NullPointerException("Log event haven't started yet");
			}

			switch (TypedPatternFactory.getConversionWordByGroupName(event
					.getGroupName())) {
			case CALLER_STACK:
				break;
			case CONTEXT_NAME:
				break;
			case DATE_TIME_OF_EVENT:
				object.time = event.getGroupValue();
				break;
			case EXCEPTION:
				object.setError(event.getGroupValue());
				break;
			case EXTENDED_EXCEPTION:
				object.setError(event.getGroupValue());
				break;
			case FILE_OF_REQUEST:
				object.file = event.getGroupValue();
				break;
			case LEVEL_OF_EVENT:
				object.level = event.getGroupValue();
				break;
			case LINE_OF_REQUEST:
				object.line = event.getGroupValue();
				break;
			case LOGGER_CLASS_CONVERSION:
				break;
			case MARKER:
				break;
			case MESSAGE:
				object.message = event.getGroupValue();
				break;
			case METHOD_NAME:
				break;
			case NEW_LINE:
				break;
			case NO_EXCEPTION:
				break;
			case PROPERTY:
				break;
			case THREAD_NAME:
				object.thread = event.getGroupValue();
				break;
			case TIME_SINCE_APP_START:
				break;
			default:
				break;
			}
		}
	}

	public long getObjectsCount() {
		return objects.size();
	}
}

class FunObject {
	private String error;
	String time, message, line, file, level, thread;
	static int count = 0;

	public void setError(String error) {
		count++;
		System.out
				.println("------------------------------------------------------------");
		System.out.println(error);
		System.out
				.println("----------------------------------------------------------"
						+ count);
	}

}