package algorithms;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import data_structure.Rating;
import data_structure.SparseMatrix;

public class ItemPopularity extends TopKRecommender {

	double[] item_popularity;
	public ItemPopularity(SparseMatrix trainMatrix, ArrayList<Rating> testRatings, 
			int topK, int threadNum) {
		super(trainMatrix, testRatings, topK, threadNum);
		item_popularity = new double[itemCount];
	}
	
	public void buildModel() {
		for (int i = 0; i < itemCount; i++) {
			// Measure popularity by number of reviews received.
			item_popularity[i] = trainMatrix.getColRef(i).itemCount();
		}
	}
	
	public double predict(int u, int i) {
		return item_popularity[i];
	}

	@Override
	public void updateModel(int u, int i) {
		trainMatrix.setValue(u, i, 1);
		item_popularity[i] += 1;
	}
	
	public void outputpop() {
		double[] pop = new double[itemCount];
		for (int i =0;i<itemCount;i++) {
			pop[i] = item_popularity[i];
		}

		Arrays.sort(pop);
		int pos = (int)(0.99*itemCount);
		double a = pop[pos];
		double b = pop[itemCount-pos];
		System.out.printf("%f\n",a);
		if (a > b)
			System.out.println("right bigger !");
		else {
			System.out.println("wrong!\n");
			a = b;
		}
		int num = 0;
		
		FileWriter out = null;
		try {
		out = new FileWriter("yuguanghui/ui-factors/topitemlist");
		//out.write("why can't write:\n");
		int i = 0;
		for ( i=0;i<itemCount;i++) {
			
			if (item_popularity[i] >= a) {
				out.write(Integer.toString(i));
				//out.write("whats wrong!");
				out.write('\n');
				num ++;
			}
			
		}	
		System.out.printf("the tpo num is %d",num);
		out.close();
		}catch (Exception e) {   

            e.printStackTrace();   

        }		
		
		
	}
}
