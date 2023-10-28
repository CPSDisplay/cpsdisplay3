package fr.dams4k.cpsdisplay;

public class References {
	public static final String MOD_ID = "cpsdisplay";
	public static final String MOD_NAME = "CPSDisplay";
	public static final String MOD_VERSION = "3.0.0";
	
	public static final ReleaseType RELEASE_TYPE = ReleaseType.ALPHA.v(2);

    public static final String MOD_GITHUB_LASTEST_RELEASE = "https://api.github.com/repos/CPSDisplay/cpsdisplay3/releases/latest";
	public static final String CURSEFORGE_URL = "https://www.curseforge.com/minecraft/mc-mods/cpsdisplay";

	public enum ReleaseType {
		ALPHA("a"),
		BETA("b"),
		RELEASE("r");

		private final String name;
		private int version = 0;

		ReleaseType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public ReleaseType v(int version) {
			this.version = version;
			return this;
		}

		public int getVersion() {
			return version;
		}

		public String getString() {
			return this.getName() + getVersion();
		}

		public static ReleaseType getFromString(String str) {
			ReleaseType DEFAULT = ReleaseType.RELEASE;

			if (str.length() < 2) return DEFAULT;
			int releaseVersion = 0;
			try {
				releaseVersion = Integer.parseInt(str.substring(1));
			} catch (Exception e) {}

			String releaseName = str.substring(0, 1);

			switch (releaseName) {
				case "a":
					return ReleaseType.ALPHA.v(releaseVersion);
				case "b":
					return ReleaseType.BETA.v(releaseVersion);
				default:		
					return DEFAULT.v(releaseVersion);
			}
		}
	}
}
