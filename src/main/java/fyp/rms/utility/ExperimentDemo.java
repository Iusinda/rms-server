package fyp.rms.utility;

import weka.classifiers.Classifier;
import weka.classifiers.trees.M5P;
import weka.core.Instances;
import weka.core.Range;
import weka.core.Utils;
import weka.experiment.ClassifierSplitEvaluator;
import weka.experiment.CrossValidationResultProducer;
import weka.experiment.Experiment;
import weka.experiment.InstancesResultListener;
import weka.experiment.PairedCorrectedTTester;
import weka.experiment.PairedTTester;
import weka.experiment.PropertyNode;
import weka.experiment.RandomSplitResultProducer;
import weka.experiment.RegressionSplitEvaluator;
import weka.experiment.ResultMatrix;
import weka.experiment.ResultMatrixPlainText;
import weka.experiment.SplitEvaluator;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.DefaultListModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for demonstrating the use of the Experiment class for one classifier
 * and one or more datasets. Cross-validation or random splits are possible, as
 * well as classification or regression.
 * 
 * @author fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ExperimentDemo {
	private static final Logger logger = LoggerFactory
			.getLogger(ExperimentDemo.class);

	/**
	 * Expects the following parameters:
	 * <ul>
	 * <li>-classifier "classifier incl. parameters"</li>
	 * <li>-exptype "regression"</li>
	 * <li>-splittype "crossvalidation|randomsplit"</li>
	 * <li>-runs "# of runs"</li>
	 * <li>-folds "# of cross-validation folds"</li>
	 * <li>-percentage "percentage for randomsplit"</li>
	 * <li>-result "arff file for storing the results"</li>
	 * <li>-t "dataset" (can be supplied multiple times)</li>
	 * </ul>
	 * 
	 * @param args
	 *            the commandline arguments
	 * @throws Exception
	 *             if something goes wrong
	 */
	public static void demo(String classifier, Integer folds, Integer runs,
			String input, String output) throws Exception {

		// 1. setup the experiment
		logger.info("Setting up...");
		Experiment exp = new Experiment();
		exp.setPropertyArray(new Classifier[0]);
		exp.setUsePropertyIterator(true);

		// classification or regression
		SplitEvaluator se = new RegressionSplitEvaluator();
		Classifier sec = ((RegressionSplitEvaluator) se).getClassifier();

		// crossvalidation or randomsplit
		CrossValidationResultProducer cvrp = new CrossValidationResultProducer();
		cvrp.setNumFolds(folds);
		cvrp.setSplitEvaluator(se);
		PropertyNode[] propertyPath = new PropertyNode[2];
		try {
			propertyPath[0] = new PropertyNode(se, new PropertyDescriptor(
					"splitEvaluator", CrossValidationResultProducer.class),
					CrossValidationResultProducer.class);
			propertyPath[1] = new PropertyNode(sec, new PropertyDescriptor(
					"classifier", se.getClass()), se.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		exp.setResultProducer(cvrp);
		exp.setPropertyPath(propertyPath);

		// runs
		logger.info("Set upper limit of Runs: " + classifier);
		exp.setRunLower(1);
		exp.setRunUpper(runs);

		// classifier
		logger.info("Set Classifier: " + classifier);
		String[] classifiers = Utils.splitOptions(classifier);
		String classname = classifiers[0];
		classifiers[0] = "";
		Classifier c = new M5P();
		exp.setPropertyArray(new Classifier[] { c });

		// datasets
		logger.info("Set Input: " + input);
		DefaultListModel model = new DefaultListModel();
		File file = new File(input);
		if (!file.exists())
			throw new IllegalArgumentException("File '" + input
					+ "' does not exist!");
		model.addElement(file);
		exp.setDatasets(model);

		// result
		logger.info("Set Output: " + output);
		InstancesResultListener irl = new InstancesResultListener();
		irl.setOutputFile(new File(output));
		exp.setResultListener(irl);

		// 2. run experiment
		logger.info("Initializing...");
		exp.initialize();
		logger.info("Running...");
		exp.runExperiment();
		logger.info("Finishing...");
		exp.postProcess();

		// 3. calculate statistics and output them
		logger.info("Evaluating...");
		PairedTTester tester = new PairedCorrectedTTester();
		Instances result = new Instances(new BufferedReader(new FileReader(
				irl.getOutputFile())));
		tester.setInstances(result);
		tester.setSortColumn(-1);
		tester.setRunColumn(result.attribute("Key_Run").index());
		tester.multiResultsetFull(0, result
				.attribute("Correlation_coefficient").index());
		// output results for reach dataset
		logger.info("****** Result:");
		ResultMatrix matrix = tester.getResultMatrix();
		for (int i = 0; i < matrix.getColCount(); i++) {
			logger.info(matrix.getColName(i));
			logger.info("    Perc. correct: " + matrix.getMean(i, 0));
			logger.info("    StdDev: " + matrix.getStdDev(i, 0));
		}
	}
}