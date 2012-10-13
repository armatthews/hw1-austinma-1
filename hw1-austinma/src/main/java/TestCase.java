import java.util.*;

public class TestCase {
	InputCase input;
	Map<Integer, Integer> taggedSpans;

	public TestCase(InputCase input) {
		this.input = input;
		this.taggedSpans = new HashMap<Integer, Integer>();
	}

	public void addTag(int start, int end) {
		taggedSpans.put(start, end);
	}

	public float getPrecision(Map<Integer, Integer> hypothesis) {
		int correct = 0;
		for(Map.Entry<Integer, Integer> span : hypothesis.entrySet()) {
			if(taggedSpans.containsKey(span.getKey()) && taggedSpans.get(span.getKey()) == span.getValue())
				correct++;
		}
		if(hypothesis.size() != 0)
			return 1.0f * correct / hypothesis.size();
		else
			return 1.0f;
	}

	public float getRecall(Map<Integer, Integer> hypothesis) {
		int correct = 0;
		for(Map.Entry<Integer, Integer> span : taggedSpans.entrySet()) {
			if(hypothesis.containsKey(span.getKey()) && hypothesis.get(span.getKey()) == span.getValue())
				correct++;
		}
		if(taggedSpans.size() != 0)
			return 1.0f * correct / taggedSpans.size();
		else
			return 1.0f;
	}

	public float getFScore(Map<Integer, Integer> hypothesis) {
		float p = getPrecision(hypothesis);
		float r = getRecall(hypothesis);
		if( p + r != 0.0f )
			return (2.0f * p * r) / (p + r);
		else
			return 0.0f;
	}
}
