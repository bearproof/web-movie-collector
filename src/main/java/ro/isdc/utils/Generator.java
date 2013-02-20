package ro.isdc.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator {
	public static final long SEC_MS = 1000;
	public static final long MIN_MS = SEC_MS * 60;
	public static final long HOUR_MS = MIN_MS * 60;
	public static final long DAY_MS = HOUR_MS * 24;
	public static final long WEEK_MS = DAY_MS * 7;
	public static final long MONTH_MS = DAY_MS * 30;
	public static final long YEAR_MS = DAY_MS * 365;

	public static long mod11Factor = 9876543298765432L;
	// public static long mod11Factor =2765432765432765432L;
	public static final long[] powersOfTen = new long[] { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L,
			1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L };

	private static final String[] LIPSUM = {
			"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur at neque quis diam consequat fringilla. Etiam tincidunt. In lobortis magna sed ipsum. In vulputate neque id enim. Aliquam rhoncus diam. Vivamus enim. Fusce pulvinar. Etiam eu lacus. Etiam dui metus, sollicitudin at, mollis sit amet, ullamcorper et, libero. Suspendisse mauris massa, consectetuer nec, lacinia at, tempus vitae, mauris. Donec turpis nisl, imperdiet sed, vehicula eu, blandit sed, turpis. Mauris et leo at mauris commodo bibendum. Curabitur lobortis, purus id eleifend fringilla, nulla dui auctor pede, in euismod libero diam sed libero. Pellentesque sodales arcu nec odio.",
			"Praesent purus massa, fermentum eu, auctor gravida, vulputate et, est. Nam mauris lectus, laoreet et, vulputate vel, varius quis, nisl. Integer a metus. Etiam placerat consectetuer enim. Mauris metus diam, condimentum nec, bibendum nec, pellentesque ut, dui. Sed mauris mi, molestie eu, condimentum a, interdum ac, metus. Sed libero orci, aliquet nec, sollicitudin sit amet, adipiscing et, augue. Suspendisse augue metus, blandit sit amet, dignissim sit amet, fringilla sit amet, dolor. Donec elit felis, lobortis vitae, semper eu, rutrum a, ligula. Quisque at ligula id dolor molestie hendrerit. Curabitur id odio et lacus mollis fringilla. Etiam tellus orci, sollicitudin quis, malesuada ut, ultricies in, mi. Praesent eget enim. Aliquam justo. In ante. Duis faucibus arcu sit amet massa. Donec sit amet metus consequat felis porta placerat. Sed ut nibh ac est fringilla consequat. Suspendisse potenti. Proin viverra, urna id tempus hendrerit, mi lectus vestibulum neque, et tempus justo eros eu nunc.",
			"Curabitur blandit ornare nunc. Aliquam porttitor nisi eget tortor. Sed imperdiet mattis metus. Pellentesque ut ipsum vitae risus lacinia vestibulum. Aliquam erat volutpat. In id nisl a massa posuere interdum. Aliquam erat volutpat. Fusce orci. Curabitur sit amet nulla id massa lacinia congue. Fusce urna tellus, porta id, pulvinar in, suscipit vel, felis. Donec lorem pede, iaculis ut, luctus commodo, pellentesque non, lectus. Nunc nibh. Morbi eros. Nunc ut nunc. Sed in nunc. Donec blandit mauris at diam.",
			"Donec commodo orci nec nunc. Nam volutpat tellus vel lacus. Aenean nec mi. Vivamus tincidunt, justo a mollis pharetra, arcu tellus dapibus tortor, nec pharetra justo quam dictum lectus. Aliquam egestas, justo sed luctus aliquet, elit augue vulputate risus, ut pretium est dolor sed neque. Ut in urna ut diam cursus tincidunt. Duis luctus. Integer nec lorem consectetuer pede scelerisque suscipit. Vivamus ullamcorper iaculis lectus. Nulla hendrerit. Vivamus facilisis venenatis augue. Vestibulum vel est. Proin eu ipsum nec urna mattis vulputate. Fusce porttitor, lacus in dictum volutpat, massa erat facilisis elit, ac tristique leo erat vitae diam. Nunc consectetuer orci eget purus. Nunc quis massa.",
			"Cras auctor. Proin vel massa in lectus fermentum aliquam. Cras iaculis. Integer dolor. Suspendisse volutpat aliquam lectus. Curabitur ultricies, lectus et tristique fringilla, orci turpis laoreet neque, ac dignissim lorem massa at sapien. Donec felis nunc, tincidunt vel, varius sed, vestibulum in, diam. Fusce cursus. Etiam mauris dui, vulputate sed, vestibulum sit amet, eleifend nec, nulla. Donec mauris enim, mollis et, porttitor at, tincidunt et, tellus. Vestibulum nec nunc. Nulla in mi non sapien sodales ultricies. Duis ante ante, euismod ac, posuere malesuada, elementum ac, purus. Sed fermentum, pede sed elementum blandit, justo velit feugiat massa, vel facilisis tellus urna quis lorem. Aliquam elit nibh, mattis sagittis, elementum ac, tincidunt in, eros. Proin turpis." };
	private static final Object[] DOMAINS = new String[] { "com", "net", "org", "gov" };

	/**
	 * List of generic words
	 */
	public static List<String> genericWords = new ArrayList<String>();

	/**
	 * List of actors/actresses names
	 */
	public static List<String> actorNames = new ArrayList<String>();
	/**
	 * List of movie director names
	 */

	public static List<String> directorNames = new ArrayList<String>();
	/**
	 * List of movie names
	 */

	public static List<String> movieNames = new ArrayList<String>();
	/**
	 * List of male first names
	 */
	public static List<String> maleFirstNames = new ArrayList<String>();
	/**
	 * List of female first names
	 */
	public static List<String> femaleFirstNames = new ArrayList<String>();
	/**
	 * List of last names
	 */
	public static List<String> lastNames = new ArrayList<String>();
	/**
	 * List of companies
	 */
	public static List<String> companies = new ArrayList<String>();
	/**
	 * List of countries
	 */
	public static Map<String, Country> countries = new HashMap<String, Country>();

	/**
	 * Map of Languages
	 */
	public static Map<String, Language> languages = new HashMap<String, Language>();
	/**
	 * List of cities
	 */
	public static List<String> cities = new ArrayList<String>();

	/**
	 * List of fruits
	 */
	public static List<String> fruits = new ArrayList<String>();
	/**
	 * List of vegetables
	 */
	public static List<String> vegetables = new ArrayList<String>();
	/**
	 * List of foods
	 */
	public static List<String> foods = new ArrayList<String>();
	/**
	 * List of products
	 */
	public static List<String> products = new ArrayList<String>();

	/**
	 * All the contents of list files are loaded on class load into ArrayLists.
	 */
	static {
		loadAllLists();
	}

	/**
	 * Loads all the lists into the corresponding Array List objects
	 */
	@SuppressWarnings("unused")
	private static void loadAllLists() {
		long startTime = System.currentTimeMillis();
		loadList("actors.txt", actorNames);
		loadList("directors.txt", directorNames);
		loadList("movies.txt", movieNames);
		loadList("firstnames.male.txt", Generator.maleFirstNames);
		loadList("firstnames.female.txt", Generator.femaleFirstNames);
		loadList("generic.txt", Generator.genericWords);
		loadList("lastnames.txt", Generator.lastNames);
		loadList("companies.txt", Generator.companies);
		loadCountries("countries.txt", Generator.countries);
		loadList("cities.txt", Generator.cities);
		loadList("fruits.txt", Generator.fruits);
		loadList("vegetables.txt", Generator.vegetables);
		loadList("foods.txt", Generator.foods);
		loadList("products.txt", Generator.products);
		loadLanguages("languages.txt", Generator.languages);

		// System.out.println("[Generator]:All lists loaded in:"+(System.
		// currentTimeMillis()-startTime) + " [ms]");
	}

	/**
	 * @param fileName
	 *            The name of the text file to load the resources from. the file
	 *            must be located in the same jar.
	 * @param theList
	 *            The ArrayList object to load the given file into.
	 */
	private static void loadList(String fileName, List<String> theList) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Generator.class.getClassLoader().getResourceAsStream(fileName)));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				theList.add(strLine);
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// System.out.println("[Generator]:" + fileName +
		// ".size():"+theList.size());
	}

	/**
	 * @param fileName
	 *            The name of the text file to load the resources from. the file
	 *            must be located in the same jar.
	 * @param theList
	 *            The ArrayList object to load the given file into.
	 */
	public static void loadListFromFile(String fileName, List<String> theList) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Generator.class.getClassLoader().getResourceAsStream(fileName)));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				theList.add(strLine);
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// System.out.println("[Generator]:" + fileName +
		// ".size():"+theList.size());
	}

	private static void loadLanguages(String fileName, Map<String, Language> theMap) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Generator.class.getClassLoader().getResourceAsStream(fileName)));
			String strLine;
			String[] l;
			while ((strLine = br.readLine()) != null) {
				l = strLine.split(",");
				theMap.put(l[0], new Language(l[0], l[1], l[2]));
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// System.out.println("[Generator]:" + fileName +
		// ".size():"+theList.size());
	}

	private static void loadCountries(String fileName, Map<String, Country> theMap) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Generator.class.getClassLoader().getResourceAsStream(fileName)));
			String strLine;
			String[] l;
			while ((strLine = br.readLine()) != null) {
				l = strLine.split("-");
				theMap.put(l[0], new Country(l[0], l[1]));
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// System.out.println("[Generator]:" + fileName +
		// ".size():"+theList.size());
	}

	public static char getChar(char c1, char c2) {
		return (char) getInt((int) c1, (int) c2);
	}

	public static char getChar() {
		return getChar('A', 'z');
	}

	public static char[] getChars(int size) {
		char[] c = new char[size];
		for (int i = 0; i < c.length; i++) {
			c[i] = getChar();
		}
		return c;
	}

	public static byte getByte(byte low, byte hi) {
		return (byte) Math.floor(low + (hi - low + 1) * Math.random());
	}

	/**
	 * Returns a random short
	 * 
	 * @return The randomly returned integer.
	 */
	public static byte getByte() {
		return getByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
	}

	public static byte[] getBytes(int len) {
		byte[] ba = new byte[len];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = getByte();
		}
		return ba;
	}

	/**
	 * Returns a random short
	 * 
	 * @return The randomly returned integer.
	 */
	public static short getShort(int low, int hi) {
		return (short) Math.floor(low + (hi - low + 1) * Math.random());
	}

	/**
	 * Returns a random short
	 * 
	 * @return The randomly returned integer.
	 */
	public static short getShort() {
		return getShort(-10000, 10000);
	}

	/**
	 * Returns a random integer from the given range.
	 * 
	 * @param low
	 *            The lower boundary of the range from which the integer value
	 *            will be returned.
	 * @param hi
	 *            The upper boundary of the range from which the integer value
	 *            will be returned.
	 * @return The randomly returned integer.
	 */
	public static int getInt(int low, int hi) {
		return (int) getLong(low, hi);
	}

	/**
	 * Returns a random integer
	 * 
	 * @return The randomly returned integer.
	 */
	public static int getInt() {
		return getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns a random long from the given range.
	 * 
	 * @param low
	 *            The lower boundary of the range from which the long value will
	 *            be returned.
	 * @param hi
	 *            The upper boundary of the range from which the long value will
	 *            be returned.
	 * @return The randomly returned long.
	 */
	public static long getLong(long low, long hi) {
		return (long) Math.floor((low + ((double) hi - (double) low + 1.0d) * Math.random()));
	}

	/**
	 * Returns a random long
	 * 
	 * @return The randomly returned long.
	 */
	public static long getLong() {
		return getLong(-10000, 10000);
	}

	/**
	 * Returns a random float from the given range.
	 * 
	 * @param low
	 *            The lower boundary of the range from which the float value
	 *            will be returned.
	 * @param hi
	 *            The upper boundary of the range from which the float value
	 *            will be returned.
	 * @return The randomly returned float.
	 */
	public static float getFloat(float low, float hi) {
		return (float) getDouble(low, hi);
	}

	public static float getFloat() {
		return (float) getDouble(-10000, 10000);
	}

	public static double getDouble(double low, double hi) {
		return low + (hi - low) * Math.random() / 0.9999999999999999;
	}

	public static double getDouble() {
		return getDouble(-10000, 10000);
	}

	/**
	 * Returns a random boolean
	 * 
	 * @return The randomly returned boolean.
	 */
	public static boolean getBoolean() {
		return Math.random() > 0.5;
	}

	/**
	 * Returns a random boolean with a specified probabylity of the true value.
	 * 
	 * @return The randomly returned boolean.
	 */
	public static boolean getBoolean(double trueProbability) {
		return (Math.random() < trueProbability);
	}

	/**
	 * Returns a random date
	 * 
	 * @param minDate
	 *            The lower boundary of the range from which the Date value will
	 *            be returned.
	 * @param maxDate
	 *            The lower boundary of the range from which the Date value will
	 *            be returned.
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDate(Date minDate, Date maxDate) {
		return new Date(getLong(minDate.getTime(), maxDate.getTime()));
	}

	/**
	 * Returns a random date in the future
	 * 
	 * @param minFutureMs
	 *            Date in future with at least this number of milliseconds.
	 * @param maxFutureMs
	 *            Date in future with at most this number of milliseconds.
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDateInFuture(long minFutureMs, long maxFutureMs) {
		long nowMs = System.currentTimeMillis();
		return new Date(getLong(nowMs + minFutureMs, nowMs + maxFutureMs));
	}

	/**
	 * Returns a random date in the future
	 * 
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDateInFuture() {
		long nowMs = System.currentTimeMillis();
		return new Date(getLong(nowMs, nowMs + 1 * YEAR_MS));
	}

	/**
	 * Returns a random date in the future
	 * 
	 * @param minFutureMs
	 *            Date in future with at least this number of milliseconds.
	 * @param maxFutureMs
	 *            Date in future with at most this number of milliseconds.
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDateInPast(long minPastMs, long maxPastMs) {
		long nowMs = (new Date()).getTime();
		return new Date(getLong(nowMs - maxPastMs, nowMs - minPastMs));
	}

	/**
	 * Returns a random date in the future
	 * 
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDateInPast() {
		long nowMs = System.currentTimeMillis();
		return new Date(getLong(nowMs - 1 * YEAR_MS, nowMs - 1));
	}

	/**
	 * Returns a random date
	 * 
	 * @return The randomly returned Date.
	 */
	public static java.util.Date getDate() {
		long nowMs = System.currentTimeMillis();
		return new Date(getLong(nowMs - 100 * YEAR_MS, nowMs + 100 * YEAR_MS));
	}

	/**
	 * Padds the given string with the specified character up to the specified
	 * length.
	 */
	private static String padLeft(String input, int toLen, String chr) {
		StringBuffer sb = new StringBuffer(toLen);
		for (int i = 0; i < toLen - input.length(); i++) {
			sb.append(chr);
		}
		sb.append(input);
		return sb.toString();
	}

	/**
	 * Returns a date as string
	 * 
	 * @param separator
	 *            the date separator character to be used.
	 * @return The String representation of the returned Date.
	 */
	@SuppressWarnings("deprecation")
	public static String getDateAsString(Date date, String separator) {
		return ((1900 + date.getYear()) + separator + padLeft(("" + (date.getMonth() + 1)), 2, "0") + separator + padLeft(("" + date.getDate()), 2, "0"));
	}

	/**
	 * Returns a date as string
	 * 
	 * @param separator
	 *            the date separator character to be used.
	 * @return The String representation of the returned Date.
	 */
	@SuppressWarnings("deprecation")
	public static String getDateTimeAsString(Date date, String separator) {
		return ((1900 + date.getYear()) + separator + padLeft(("" + (date.getMonth() + 1)), 2, "0") + separator + padLeft(("" + date.getDate()), 2, "0") + " " + date.getHours()
				+ ":" + date.getMinutes());
	}

	/**
	 * Returns a date as string
	 * 
	 * @return The String representation of the returned Date.
	 */
	public static String getDateAsString(Date date) {
		return getDateAsString(date, ".");
	}

	/**
	 * Returns a date as string
	 * 
	 * @return The String representation of the returned Date.
	 */
	public static String getDateAsString() {
		return getDateAsString(getDate(), ".");
	}

	/**
	 * Returns a randomly selected item from a given list.
	 * 
	 * @param aList
	 *            The list from which the item will be selected.
	 * @return The selected item.
	 */
	private static String getRandomListItem(List<String> aList) {
		return (String) aList.get(getInt(0, aList.size() - 1));
	}

	/**
	 * Returns a phrase composed of the specified number of words.
	 * 
	 * @param nrWords
	 *            The number of words in the phrase.
	 * @return The phrase containing the given number of words.
	 */
	public static String getPhrase(int nrWords) {
		StringBuffer sb = new StringBuffer(nrWords * 10);

		for (int i = 0; i < nrWords; i++) {
			sb.append(getRandomListItem(genericWords));
			if (i < nrWords - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns a random string of the given length
	 * 
	 * @param len
	 *            The length of the string to be returned.
	 * @return The randomly generated string.
	 */
	public static String getString(int len) {
		StringBuffer sb = new StringBuffer(len);

		while (sb.length() < len) {
			sb.append(getRandomListItem(genericWords));
		}
		return sb.substring(0, len);
	}

	/**
	 * Returns an array of strings between a min and a max length.
	 * 
	 * @param size
	 * @param minLen
	 * @param maxLen
	 * @return
	 */
	public static String[] getStrings(int size, int minLen, int maxLen) {
		String[] result = new String[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = getVarString(minLen, maxLen);
		}
		return result;
	}

	/**
	 * Returns a random string having its length in the specified range.
	 * 
	 * @param len
	 *            The length of the string to be returned.
	 * @return The randomly generated string.
	 */
	public static String getVarString(int min, int max) {
		return getString(getInt(min, max));
	}

	/**
	 * Randomly generates a firstname.
	 * 
	 * @param isMale
	 *            true if a male name should be returned, false for female
	 *            names.
	 * @return the first name.
	 */
	public static String getFirstName(boolean isMale) {
		return getRandomListItem((isMale ? maleFirstNames : femaleFirstNames));
	}

	public static String getFirstName() {
		return getFirstName(getBoolean());
	}

	/**
	 * Randomly selects a valid lastname.
	 * 
	 * @return the selected last name.
	 */
	public static String getLastName() {
		return getRandomListItem((lastNames));
	}

	/**
	 * Randomly selects a valid movie director name.
	 * 
	 * @return the selected movie director name.
	 */
	public static String getDirectorName() {
		return getRandomListItem((directorNames));
	}

	/**
	 * Randomly selects a valid movie name.
	 * 
	 * @return the selected movie name.
	 */
	public static String getMovieName() {
		return getRandomListItem((movieNames));
	}

	/**
	 * Randomly selects a valid actor/actress name.
	 * 
	 * @return the selected actor/actress name.
	 */
	public static String getActorName() {
		return getRandomListItem((actorNames));
	}

	/**
	 * Randomly generates a male/female full name.
	 * 
	 * @param isMale
	 *            true if male name should be returned, false otherwise.
	 * @return The randomly generated name.
	 */
	public static String getFullName(boolean isMale) {
		return getRandomListItem((isMale ? maleFirstNames : femaleFirstNames)) + " " + getRandomListItem(lastNames);
	}

	/**
	 * Randomly generates a male/female full name. Whether male/female should be
	 * returned is also randomly decided.
	 * 
	 * @return The randomly generated name.
	 */
	public static String getFullName() {
		return getRandomListItem((getBoolean() ? maleFirstNames : femaleFirstNames)) + " " + getRandomListItem(lastNames);
	}

	/**
	 * Randomly selects a company name.
	 * 
	 * @return the selected company name.
	 */
	public static String getCompany() {
		return getRandomListItem(companies);
	}

	/**
	 * Randomly selects a country.
	 * 
	 * @return the selected country.
	 */
	public static Country getCountry() {
		String aKey = choose(countries.keySet().toArray()).toString();
		return (Country) countries.get(aKey);
	}

	/**
	 * Selects the country with the specified code.
	 * 
	 * @return the selected country.
	 */
	public static Country getCountry(String digraph) {
		return (Country) countries.get(digraph.trim().toUpperCase());
	}

	/**
	 * Randomly selects a country name.
	 * 
	 * @return the selected country name.
	 */
	public static String getCountryName() {
		String aKey = choose(countries.keySet().toArray()).toString();
		return ((Country) countries.get(aKey)).getName();
	}

	/**
	 * Randomly selects a country code.
	 * 
	 * @return the selected country code.
	 */
	public static String getCountryCode() {
		String aKey = choose(countries.keySet().toArray()).toString();
		return ((Country) countries.get(aKey)).getCode();
	}

	/**
	 * Randomly selects a city name.
	 * 
	 * @return the selected city name.
	 */
	public static String getCity() {
		return getRandomListItem(cities);
	}

	/**
	 * Randomly selects a fruit.
	 * 
	 * @return the selected fruit.
	 */
	public static String getFruit() {
		return getRandomListItem(fruits);
	}

	/**
	 * Randomly selects a vegetable.
	 * 
	 * @return the selected vegetable.
	 */
	public static String getVegetable() {
		return getRandomListItem(vegetables);
	}

	/**
	 * Randomly selects a food.
	 * 
	 * @return the selected food.
	 */
	public static String getFood() {
		return getRandomListItem(foods);
	}

	/**
	 * Randomly selects a product.
	 * 
	 * @return the selected product.
	 */
	public static String getProduct() {
		return getRandomListItem(products);
	}

	/**
	 * Randomly generates a US phone number, eg: 408-555-1212
	 * 
	 * @return the generated phone number.
	 */
	public static String getPhoneNumber() {
		StringBuffer sb = new StringBuffer(12);
		// 408-555-1212
		for (int i = 0; i < 12; i++) {
			if (i == 3 || i == 7) {
				sb.append("-");
			} else {
				sb.append(getInt(0, 9));
			}
		}
		return sb.toString();
	}

	/**
	 * Randomly generates a US zip code, eg: 90003
	 * 
	 * @return the generated phone number.
	 */
	public static String getZipCode() {
		StringBuffer sb = new StringBuffer(12);
		// 90003
		for (int i = 0; i < 5; i++) {
			sb.append(getInt(0, 9));
		}
		return sb.toString();
	}

	/**
	 * Chooses an arbitrary element from an array of objects.
	 * 
	 * @param elements
	 * @return
	 */
	public static Object choose(Object[] elements) {
		int low = 0;
		int hi = elements.length - 1;
		int idx = getInt(low, hi);
		return elements[idx];
	}

	/**
	 * Returns a list of objects of the specified size.
	 * 
	 * @param clazz
	 *            : Class to instantiate the elements of the returned list from.
	 * @param size
	 *            : size of the returned list.
	 * @return
	 */
	public static List<Class<?>> getList(Class<Float> clazz, int size) {
		List<Class<?>> result = new ArrayList<Class<?>>(size);
		try {
			for (int i = 0; i < size; i++) {
				result.add(Class.forName(clazz.getName()));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, Language> getLanguages() {
		return languages;
	}

	private static String getLanguageKey() {
		Object[] a = languages.keySet().toArray();
		return a[getInt(0, a.length - 1)].toString();
	}

	public static String getLanguageCode3() {
		return getLanguageKey().toUpperCase();
	}

	public static String getLanguageCode2() {
		return ((Language) languages.get(getLanguageKey())).getCode2().toUpperCase();
	}

	public static String getLanguageName() {
		return ((Language) languages.get(getLanguageKey())).getName();
	}

	public static Language getLanguage(String code3) {
		return (Language) languages.get(code3.toLowerCase());
	}

	public static String getEmail() {
		String prefix = (getBoolean() ? getNameToken() : getRandomListItem(genericWords));
		return prefix + "@" + getRandomListItem(genericWords) + "." + getCountry().getCode().toLowerCase();
	}

	@SuppressWarnings("unused")
	public static String getURL() {
		String url = "";
		String protocol = choose(new String[] { "http", "https", "ftp" }).toString();
		String www = getBoolean(0.75) ? "www." : "";
		String server = getRandomListItem(genericWords).toLowerCase();
		String domain = "." + choose(DOMAINS).toString().toLowerCase();
		String app = getBoolean(0.7) ? "" : "/" + getPhrase(getInt(1, 3)).trim().replaceAll("\\s+", "/");
		return protocol + "://" + www + server + domain + app;
	}

	public static String getLipsum() {
		return getLipsum(1, "\n");
	}

	public static String getLipsum(int paragraphs) {
		return getLipsum(paragraphs, "\n");
	}

	public static String getLipsum(int paragraphs, String lineSeparator) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < paragraphs; i++) {
			result.append(LIPSUM[i % LIPSUM.length]);
			if (i < paragraphs - 1) {
				result.append(lineSeparator);
			}
		}
		return result.toString();
	}

	public static long getMod11Factor() {
		return Generator.mod11Factor;
	}

	public static void setMod11Factor(long mod11Factor) {
		Generator.mod11Factor = mod11Factor;
	}

	public static long getBankAccountNumber(long minVal, long maxVal) {
		if (minVal < 10 || maxVal * 10 > Long.MAX_VALUE) {
			throw new IllegalArgumentException("minVal or maxVal is out of range.");
		}
		int mod11 = 1, checkDigit, sum, nrOfDigits;
		long result = 0L, acc = 0L, mod11Factor = Generator.getMod11Factor();

		while (mod11 == 1) {
			acc = getLong(minVal / 10, maxVal / 10); // 71912967|2
			nrOfDigits = getNrOfDigits(acc);
			sum = 0;
			for (int i = 1; i <= nrOfDigits; i++) {
				sum += getDigit(acc, i) * getDigit(mod11Factor, i);
			}
			mod11 = sum % 11;
		}
		checkDigit = (mod11 == 0 ? 0 : 11 - mod11);
		result = 10 * acc + checkDigit;
		return result;
	}

	public static long getSelfCheckAccount() {
		return getBankAccountNumber(6, 10);
	}

	private static String getNameToken() {
		return getFullName().toLowerCase().replaceAll("\\s+", ".");
	}

	private static int getNrOfDigits(long nr) {
		return (int) Math.floor(Math.log10(Math.abs(nr != 0 ? nr : 1))) + 1;
	}

	private static long getDigit(long number, int digit) {
		return (number / (long) powersOfTen[digit - 1]) % 10;
	}

	public static void main(String[] args) {
		System.out.println("A random actor name: " + getActorName());
		System.out.println(("A random movie name: " + getMovieName()));
		System.out.println("A random director name: " + getDirectorName());
	}
}
