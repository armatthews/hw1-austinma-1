import java.io.StringReader;
import java.util.*;
import java.net.*;
import java.io.*;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;

public class DependencyExample {

	public static String requestURL(String URLString) throws Exception {
		URL url = new URL(URLString);
		URLConnection urlConn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		StringBuilder r = new StringBuilder();

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			r.append(inputLine);
			r.append("\n");
		}
		in.close();

		return r.toString();
	}

	public static boolean wikipediaTest(String possibleGene) {
		// System.out.println(requestURL("http://en.wikipedia.org/w/index.php?search=alkaline+phosphatases"));
		// regex for:
		// <li><div class='mw-search-result-heading'><a
		// href="/wiki/Alkaline_phosphatase"
		// regex that for 'gene'
		return false;
	}

	public static void main(String[] args) throws Exception {
		TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory();
		PosTagNamedEntityRecognizer recognizer = new PosTagNamedEntityRecognizer();

		TestSuite testSuite = new TestSuite("src/main/resources/sample.in.txt", "src/main/resources/sample.out.txt");
		for (TestCase testCase : testSuite.getTestCases()) {
			String sentence = testCase.input.getSentence();
			String sentenceID = testCase.input.getID();

			Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(sentence));

			Map<Integer, Integer> geneSpans = recognizer.getGeneSpans(sentence);
			float fScore = testCase.getFScore(geneSpans);
			if (fScore != 0.0f)
				System.out.println(fScore);
			for (Map.Entry<Integer, Integer> span : geneSpans.entrySet()) {
				// int spanStart = span.getKey();
				// int spanEnd = span.getValue();
				// System.out.println(String.format("%s|%d %d|%s", sentenceID,
				// spanStart, spanEnd, sentence.substring(spanStart, spanEnd)));
			}
		}
	}
}