package fyp.rms.utility;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Debug.Random;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.REPTree;

public class MLHelper {
	private static int test(){
		System.out.println("*******************************************OMG");
		return 1;
	}
	
	public static int a = test();
	
	public static void main(String args[]) throws Exception {
		// load data
		Instances data = new Instances(
				new BufferedReader(
						new FileReader(
								"/Users/lohris/programming/FYP/Dev/testingworkplace/weka/dining4.arff")));
		data.setClassIndex(data.numAttributes() - 1);
		// build model
		Random rand = new Random(System.currentTimeMillis());
		int folds = 10;
		Instances randData = new Instances(data);
		randData.randomize(rand);
		randData.stratify(folds);
		REPTree repTree = new REPTree();
		repTree.buildClassifier(data);

		// System.out.println(repTree);
		// classify the last instance
		Instance myHouse = data.lastInstance();
		repTree.classifyInstance(myHouse);

		System.out.println(repTree);

	}
}