public enum Word{
	Book("Book","noun","A set of pages.");
	//key word
	private String keyword;
	//each part of speech
	private String speech;
	//each definition
	private String definition;
	
	/**
	 * The constructor of Word
	 * @param speech
	 * @param definition
	 */
	private Word(String keyword, String speech, String definition) {
		this.keyword = keyword;
		this.speech = speech;
		this.definition = definition;
	}
	

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString(){
		return keyword+ " "+ "[" +speech+ "] : "+definition;
	}

}
