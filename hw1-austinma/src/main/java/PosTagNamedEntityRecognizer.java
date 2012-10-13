import java.util.*;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.*;

public class PosTagNamedEntityRecognizer {
	private StanfordCoreNLP pipeline;
	
	public PosTagNamedEntityRecognizer() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public Map<Integer, Integer> getGeneSpans(String text) {
		Map<Integer, Integer> begin2end = new HashMap<Integer, Integer>();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence : sentences) {
			List<CoreLabel> candidate = new ArrayList<CoreLabel>();
			for(CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String pos = token.get(PartOfSpeechAnnotation.class);
				if(pos.startsWith("NN"))
					candidate.add(token);
				else if(candidate.size() > 0) {
					int begin = candidate.get(0).beginPosition();
					int end = candidate.get(candidate.size() - 1).endPosition();
					
					int offset = 0;
					for(int i = 0; i < begin; i++)
						if(text.charAt(i) == ' ')
							offset++;
					
					begin2end.put(begin-offset, end-offset);
					candidate.clear();
				}
			}
			
			if(candidate.size() > 0) {
				int begin = candidate.get(0).beginPosition();
				int end = candidate.get(candidate.size() - 1).endPosition();
				begin2end.put(begin, end);
				candidate.clear();
			}
		}
		return begin2end;
	}
}
