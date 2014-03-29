package cz.martinbayer.logparser.logback.pattern;

import java.util.ArrayList;

public class ConfigPatternInfo {

	private ArrayList<String> usedGroups = new ArrayList<>();
	private String parsedConfigPattern = null;
	private String splitPattern;

	public void addGroup(String groupName) {
		usedGroups.add(groupName);
	}

	public ArrayList<String> getGroups() {
		return usedGroups;
	}

	public final String getParsedConfigPattern() {
		return parsedConfigPattern;
	}

	public final void setParsedConfigPattern(String parsedConfigPattern) {
		this.parsedConfigPattern = parsedConfigPattern;
	}

	public final String getSplitPattern() {
		return this.splitPattern;
	}

	public final void setSplitPatternt(String splitPattern) {
		this.splitPattern = splitPattern;
	}
}
