package fyp.rms.utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class MLHelper implements ServletContextListener {
	private static List<MLModel> models;
	private static Instances emptyInstances;

	public boolean update(Integer id) {
		try {
			models.get(id - 1).build();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Integer calculate(Integer id, Integer type, Integer day,
			Integer time, Integer position) {
		try {
			return models.get(id - 1).calculate(type, day, time, position,
					emptyInstances);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
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
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		models = null;
		emptyInstances = null;
	}
}