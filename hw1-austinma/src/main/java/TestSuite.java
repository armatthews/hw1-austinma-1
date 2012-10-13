import java.util.*;
import java.io.*;

public class TestSuite {
	private List<TestCase> testCases;
	private Map<String, TestCase> testCasesByID;
	
	public List<TestCase> getTestCases() {
		return testCases;
	}

	public TestSuite(String inputFileName, String outputFileName) {
		testCases = new ArrayList<TestCase>();
		testCasesByID = new HashMap<String, TestCase>();

		// Read in the input file
		try {
			DataInputStream inputStream = new DataInputStream(new FileInputStream(inputFileName));
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

			String inputLine;
			while ((inputLine = inputReader.readLine()) != null) {
				String[] parts = inputLine.split(" ", 2);
				String ID = parts[0];
				String sentence = parts[1];

				InputCase inputCase = new InputCase(ID, sentence);
				TestCase testCase = new TestCase(inputCase);
				testCases.add(testCase);
				testCasesByID.put(ID, testCase);
			}
		} catch (Exception e) {
			System.err.println("Error while reading input file " + inputFileName);
			throw new RuntimeException(e);
		}

		// Read in the output file
		try {
			DataInputStream outputStream = new DataInputStream(new FileInputStream(outputFileName));
			BufferedReader outputReader = new BufferedReader(new InputStreamReader(outputStream));

			String inputLine;
			while ((inputLine = outputReader.readLine()) != null) {
				String[] parts = inputLine.split("\\|");
				String ID = parts[0];
				String spanString = parts[1];
				//String word = parts[2];
				
				String[] spanParts = spanString.split(" ");
				int spanStart = Integer.parseInt(spanParts[0]);
				int spanEnd = Integer.parseInt(spanParts[1]);
				
				testCasesByID.get(ID).addTag(spanStart, spanEnd);
			}
		} catch (Exception e) {
			System.err.println("Error while reading output file " + outputFileName);
			throw new RuntimeException(e);
		}
	}
}
