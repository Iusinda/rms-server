package fyp.rms.utility;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Attribute;
import weka.core.Debug.Random;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.REPTree;

public class MLHelper {
	private static Instances emptyset;

	public static void initialize() {
		FastVector attributes = new FastVector(5);
		attributes.addElement(new Attribute("type"));
		attributes.addElement(new Attribute("day"));
		attributes.addElement(new Attribute("time"));
		attributes.addElement(new Attribute("position"));
		attributes.addElement(new Attribute("duration"));
		emptyset = new Instances("dining", attributes, 0);
		emptyset.setClassIndex(emptyset.numAttributes() - 1);
	}

	private Integer id;
	private REPTree tree;

	public MLHelper(Integer id) throws Exception {
		this.id = id;
		build();
	}

	public void build() throws Exception {
		Instances dataset = new Instances(new BufferedReader(new FileReader(
				"data/" + id + ".arff")));
		dataset.setClassIndex(dataset.numAttributes() - 1);
		tree = new REPTree();
		tree.buildClassifier(emptyset);
		System.out.println(tree);
		tree.buildClassifier(dataset);
		System.out.println(tree);
		// Random rand = new Random(System.currentTimeMillis());
		// int folds = 10;
		// Instances randData = new Instances(data);
		// randData.randomize(rand);
		// randData.stratify(folds);
	}

	public Integer estimate(Integer type, Integer day, Integer time,
			Integer position) throws Exception {
		Instance ticket = new Instance(5);
		ticket.setValue(0, type);
		ticket.setValue(1, day);
		ticket.setValue(2, time);
		ticket.setValue(3, position);
		ticket.setDataset(emptyset);
		return (int) tree.classifyInstance(ticket);
	}
}