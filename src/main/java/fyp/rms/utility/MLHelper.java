package fyp.rms.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Debug.Random;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.REPTree;

public class MLHelper {
	private static List<MLModel> models;
	private static Instances emptyInstances;

	public static void initialize() {
		FastVector attributes = new FastVector(5);
		attributes.addElement(new Attribute("type"));
		attributes.addElement(new Attribute("day"));
		attributes.addElement(new Attribute("time"));
		attributes.addElement(new Attribute("position"));
		attributes.addElement(new Attribute("duration"));
		emptyInstances = new Instances("dining", attributes, 0);
		emptyInstances.setClassIndex(emptyInstances.numAttributes() - 1);
		models = new ArrayList<MLModel>();
		for (int i = 1; i <= 2; ++i) {
			models.add(new MLModel(i));
			System.out.println("models size = " + models.size());
		}
	}

	public static Integer calculate(Integer id, Integer type, Integer day,
			Integer time, Integer position) {
		try {
			return models.get(id - 1).calculate(type, day, time, position,
					emptyInstances);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}