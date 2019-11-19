package swehw3;

import javax.faces.bean.*;

// The file used to compute standardDeviation and Mean 
@ManagedBean(name = "winningResult", eager = true)
public class Result {
	private double mean;
	private double standardDeviation;
	
	
	// Get/Set Standard Deviation.
	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	// Get/Set Mean
	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	
}
