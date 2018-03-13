package main;

import java.io.IOException;
import algorithms.bpr_baseline;
import algorithms.ItemPopularity;

public class BPR_baseline extends main {
	public static void main(String argv[]) throws IOException {
		String method = "bpr";
		double w0 = 2000;    // learning rate
		boolean showProgress = true;  // whether evaluate after every iter
		boolean showLoss = false;   // whether show loss after every iter						
		double alpha = 0.4;   // Popularity parameter	
		int factors = 10; 	// number of latent factors.
		int maxIter = 100; 	// maximum iterations.
		boolean adaptive = false; 	// Whether to use adaptive learning rate 
		double reg = 0.01; 	// regularization parameters
		String datafile = "data/Tmall_purchase";
		int showbound = 400;   // no evaluate outcome before showbound
		int showcount = 1;   // outcome at every showtime iter 
		int paraK = 1;   // DNS parameter

		if (argv.length > 0) {
			//dataset_name = argv[0];
			//method = argv[1];
			w0 = Double.parseDouble(argv[2]);
			showProgress = Boolean.parseBoolean(argv[3]);
			showLoss = Boolean.parseBoolean(argv[4]);
			factors = Integer.parseInt(argv[5]);
			maxIter = Integer.parseInt(argv[6]);
			reg = Double.parseDouble(argv[7]);
			alpha = Double.parseDouble(argv[8]);
			datafile = argv[9];
			showbound = Integer.parseInt(argv[10]);
			showcount = Integer.parseInt(argv[11]);
		}

		ReadRatings_HoldOneOut(datafile);	
		System.out.printf("%s: showProgress=%s, factors=%d, maxIter=%d, reg=%.6f, w0=%.6f, alpha=%.4f,paraK=%d\n",
				method, showProgress, factors, maxIter, reg, w0, alpha, paraK);
		System.out.println("====================================================");		
		ItemPopularity popularity = new ItemPopularity(trainMatrix, testRatings, topK, threadNum);
		evaluate_model(popularity, "Popularity");		
		double init_mean = 0;
		double init_stdev = 0.01;		
		{
			bpr_baseline bpr = new bpr_baseline(trainMatrix, testRatings, topK, threadNum, 
					factors, maxIter, w0, false, reg, init_mean, init_stdev, showProgress,showbound,showcount,paraK);
			evaluate_model(bpr, "bpr_baseline");
		}
		
	} // end main
}
