package fyp.rms.utility;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.trees.REPTree;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class MLModel {
	private Integer id;
	private REPTree tree;

	public MLModel(Integer id) {
		this.id = id;
		tree = new REPTree();
		try {
			build();
		} catch (Exception e) {
			System.out.println("Model for Restaurant " + id + " is not built.");
		}
	}

	public void build() throws Exception {
		Instances dataset = new Instances(new BufferedReader(new FileReader(
				"data/" + id + ".arff")));
		dataset.setClassIndex(dataset.numAttributes() - 1);
		tree.buildClassifier(dataset);
		System.out.println("Model for Restaurant " + id + ":");
		System.out.println(tree);
		// Random rand = new Random(System.currentTimeMillis());
		// int folds = 10;
		// Instances randData = new Instances(data);
		// randData.randomize(rand);
		// randData.stratify(folds);
	}

	public Integer calculate(Integer type, Integer day, Integer time,
			Integer position, Instances dataset) throws Exception {
		Instance ticket = new Instance(5);
		ticket.setValue(0, type);
		ticket.setValue(1, day);
		ticket.setValue(2, time);
		ticket.setValue(3, position);
		ticket.setDataset(dataset);
		return (int) tree.classifyInstance(ticket);
	}
}
