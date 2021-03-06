import java.util.Random;

/*
 * Evaluator.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class Evaluator implements OptionHandler {
	private long seed = 2026875034;
	private Random random;
	private int folds = 10;
	private Classifier classifier;
	private TrainTestSets tts;

	public Evaluator() {
		seed = 2026875034;
		random = new Random(seed);
		folds = 10;
	}

	public Evaluator(Classifier clfr, String[] options) throws Exception {
		classifier = clfr;
		folds = 10;
		seed = 2026875034;
		random = new Random(seed);
		setOptions(options);
	}

	// If have both train and test set then run simple evaluation
	// If just have a training set -- use k-fold cross evaluation
	public Performance evaluate() throws Exception {
		Performance p1 = new Performance(tts.getTrainingSet().getAttributes());

		if (tts.getTestingSet().getExamples().size() == 0) {
			// have only testing data, do fold eval
			// Pass random to DataSet
			this.tts.getTrainingSet().setRandom(random);
			tts.getTrainingSet().setFolds(folds);

			for (int i = 0; i < folds; i++) {
				Classifier foldCopy = (Classifier) Utils.deepClone(this.classifier);
				TrainTestSets foldEvaluate = tts.getTrainingSet().getCVSets(i);
				foldCopy.train(foldEvaluate.getTrainingSet());
				Performance foldPerf = foldCopy.classify(foldEvaluate.getTestingSet());
				p1.add(foldPerf);
			}
		} else {
			// have both training and testing data
			classifier.train(tts.getTrainingSet());
			p1 = classifier.classify(tts.test);
		}

		return p1;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long s1) {
		seed = s1;
		random = new Random(seed);
	}

	public void setOptions(String args[]) throws Exception {
		if (args.length % 2 != 0) {
			throw new Exception("Incorrect Number of Arguments");
		} else {
			tts = new TrainTestSets(args);
			classifier.setOptions(args);

			for (int i = 0; i < args.length; i = i + 2) {
				if (args[i].equals("-x")) {
					this.folds = Integer.valueOf(args[i + 1]);
				}
				if (args[i].equals("-s")) {
					this.setSeed(Long.parseLong(args[i + 1]));
				}
			}

		}
	} // End of setOptions Evaluator

}
