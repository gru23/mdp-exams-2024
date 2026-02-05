package net.etfbl.years;

public class JokeEntity {
	private String id;
	private String setup;
	private String punchline;
	
	public JokeEntity() {
		super();
	}

	public JokeEntity(String id, String setup, String punchline) {
		super();
		this.id = id;
		this.setup = setup;
		this.punchline = punchline;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getPunchline() {
		return punchline;
	}

	public void setPunchline(String punchline) {
		this.punchline = punchline;
	}

	@Override
	public String toString() {
		return "JokeEntity [id=" + id + ", setup=" + setup + ", punchline=" + punchline + "]";
	}
}
