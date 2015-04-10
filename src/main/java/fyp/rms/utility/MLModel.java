package fyp.rms.utility;

import java.io.BufferedReader;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fyp.rms.server.TicketController;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;

public class MLModel {
	private Integer id;
	private REPTree tree;
	private static final Logger logger = LoggerFactory
			.getLogger(TicketController.class);

	public MLModel(Integer id) {
		this.id = id;
		tree = new REPTree();
		try {
			build();
		} catch (Exception e) {
			logger.info("Model for Restaurant " + id + " is not built.");
		}
	}

	public void build() throws Exception {
		Instances dataset = new Instances(new BufferedReader(new FileReader(
				"data/" + id + ".arff")));
		dataset.setClassIndex(dataset.numAttributes() - 1);
		tree.buildClassifier(dataset);
		logger.info("Model for Restaurant " + id + ":" + tree);
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