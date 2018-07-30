package analytics;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class StanfordNLP {
    private String document;

    void addDocument(Collection<String> sentences) {
        List<String> text = new ArrayList<>();
        for(String sentence: sentences){
            String tweet = sentence.replace(".", "");
            text.add(tweet);
        }

        this.document = Joiner.on(". ").join(text);
    }

    List<Integer> score() {
        List<Integer> scores = Lists.newArrayList();
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(this.document);
        pipeline.annotate(document);
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree annotatedTree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(annotatedTree);
            scores.add(score);
        }
        return scores;
    }
}