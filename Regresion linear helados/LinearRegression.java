import java.util.Arrays;

public class LinearRegression {
    private double[] weights;
    private double bias;
    private boolean isFitted;
    private double[] featureMeans;
    private double[] featureStds;
    private double targetMean;
    private double targetStd;
    private boolean useScaling;
    
    // Constructors
    public LinearRegression() {
        this(false);
    }
    
    public LinearRegression(boolean useScaling) {
        this.useScaling = useScaling;
        this.isFitted = false;
    }
    
    // Getters
    public double[] getWeights() {
        return weights != null ? Arrays.copyOf(weights, weights.length) : null;
    }
    
    public double getBias() {
        return bias;
    }
    
    public boolean isFitted() {
        return isFitted;
    }
    
    /**
     * Trains the linear regression model using gradient descent
     */
    public void fit(double[][] X, double[] y) {
        fit(X, y, 1000, 0.01);
    }
    
    public void fit(double[][] X, double[] y, int iterations, double learningRate) {
        if (X.length != y.length) {
            throw new IllegalArgumentException("X and y must have the same number of samples");
        }
        
        int nSamples = X.length;
        int nFeatures = X[0].length;
        
        // Initialize weights and bias
        this.weights = new double[nFeatures];
        this.bias = 0;
        
        double[][] XScaled;
        double[] yScaled;
        
        // Scale data if requested
        if (useScaling) {
            XScaled = scaleFeatures(X);
            yScaled = scaleTarget(y);
        } else {
            XScaled = X;
            yScaled = y;
        }
        
        // Gradient descent
        for (int iter = 0; iter < iterations; iter++) {
            double[] predictions = predictInternal(XScaled);
            double[] errors = new double[nSamples];
            
            // Calculate errors
            for (int i = 0; i < nSamples; i++) {
                errors[i] = predictions[i] - yScaled[i];
            }
            
            // Update weights
            for (int j = 0; j < nFeatures; j++) {
                double gradient = 0;
                for (int i = 0; i < nSamples; i++) {
                    gradient += errors[i] * XScaled[i][j];
                }
                weights[j] -= learningRate * (gradient / nSamples);
            }
            
            // Update bias
            double biasGradient = 0;
            for (int i = 0; i < nSamples; i++) {
                biasGradient += errors[i];
            }
            bias -= learningRate * (biasGradient / nSamples);
        }
        
        // If we scaled, adjust weights and bias back to original scale
        if (useScaling) {
            adjustWeightsForScaling();
        }
        
        this.isFitted = true;
    }
    
    /**
     * Predicts outputs for given input data
     */
    public double[] predict(double[][] X) {
        if (!isFitted) {
            throw new IllegalStateException("Model must be fitted before prediction");
        }
        
        if (X[0].length != weights.length) {
            throw new IllegalArgumentException("Input features must match trained model dimensions");
        }
        
        double[][] XScaled = useScaling ? scaleFeatures(X) : X;
        double[] predictions = predictInternal(XScaled);
        
        // If we scaled, convert predictions back to original scale
        if (useScaling) {
            for (int i = 0; i < predictions.length; i++) {
                predictions[i] = predictions[i] * targetStd + targetMean;
            }
        }
        
        return predictions;
    }
    
    /**
     * Internal prediction method (assumes scaled data if scaling is used)
     */
    private double[] predictInternal(double[][] X) {
        double[] predictions = new double[X.length];
        
        for (int i = 0; i < X.length; i++) {
            predictions[i] = bias;
            for (int j = 0; j < weights.length; j++) {
                predictions[i] += weights[j] * X[i][j];
            }
        }
        
        return predictions;
    }
    
    /**
     * Computes R² score (coefficient of determination)
     */
    public double score(double[][] X, double[] y) {
        if (!isFitted) {
            throw new IllegalStateException("Model must be fitted before scoring");
        }
        
        double[] predictions = predict(X);
        return calculateRSquared(y, predictions);
    }
    
    /**
     * Alternative scoring method - Mean Squared Error
     */
    public double scoreMSE(double[][] X, double[] y) {
        if (!isFitted) {
            throw new IllegalStateException("Model must be fitted before scoring");
        }
        
        double[] predictions = predict(X);
        return calculateMSE(y, predictions);
    }
    
    /**
     * Alternative scoring method - Mean Absolute Error
     */
    public double scoreMAE(double[][] X, double[] y) {
        if (!isFitted) {
            throw new IllegalStateException("Model must be fitted before scoring");
        }
        
        double[] predictions = predict(X);
        return calculateMAE(y, predictions);
    }
    
    /**
     * Data scaling methods
     */
    private double[][] scaleFeatures(double[][] X) {
        int nSamples = X.length;
        int nFeatures = X[0].length;
        
        featureMeans = new double[nFeatures];
        featureStds = new double[nFeatures];
        
        // Calculate means
        for (int j = 0; j < nFeatures; j++) {
            double sum = 0;
            for (int i = 0; i < nSamples; i++) {
                sum += X[i][j];
            }
            featureMeans[j] = sum / nSamples;
        }
        
        // Calculate standard deviations
        for (int j = 0; j < nFeatures; j++) {
            double sumSquares = 0;
            for (int i = 0; i < nSamples; i++) {
                sumSquares += Math.pow(X[i][j] - featureMeans[j], 2);
            }
            featureStds[j] = Math.sqrt(sumSquares / nSamples);
            // Avoid division by zero
            if (featureStds[j] == 0) {
                featureStds[j] = 1;
            }
        }
        
        // Scale features
        double[][] XScaled = new double[nSamples][nFeatures];
        for (int i = 0; i < nSamples; i++) {
            for (int j = 0; j < nFeatures; j++) {
                XScaled[i][j] = (X[i][j] - featureMeans[j]) / featureStds[j];
            }
        }
        
        return XScaled;
    }
    
    private double[] scaleTarget(double[] y) {
        int nSamples = y.length;
        
        // Calculate mean
        double sum = 0;
        for (double value : y) {
            sum += value;
        }
        targetMean = sum / nSamples;
        
        // Calculate standard deviation
        double sumSquares = 0;
        for (double value : y) {
            sumSquares += Math.pow(value - targetMean, 2);
        }
        targetStd = Math.sqrt(sumSquares / nSamples);
        if (targetStd == 0) {
            targetStd = 1;
        }
        
        // Scale target
        double[] yScaled = new double[nSamples];
        for (int i = 0; i < nSamples; i++) {
            yScaled[i] = (y[i] - targetMean) / targetStd;
        }
        
        return yScaled;
    }
    
    /**
     * Adjust weights and bias after training with scaled data
     */
    private void adjustWeightsForScaling() {
        // Adjust bias
        double biasAdjustment = 0;
        for (int j = 0; j < weights.length; j++) {
            biasAdjustment += weights[j] * featureMeans[j] / featureStds[j];
        }
        bias = bias * targetStd + targetMean - biasAdjustment * targetStd;
        
        // Adjust weights
        for (int j = 0; j < weights.length; j++) {
            weights[j] = weights[j] * targetStd / featureStds[j];
        }
    }
    
    /**
     * Utility methods for error calculations
     */
    private double calculateRSquared(double[] yTrue, double[] yPred) {
        double ssResidual = 0;
        double ssTotal = 0;
        double yMean = 0;
        
        for (double value : yTrue) {
            yMean += value;
        }
        yMean /= yTrue.length;
        
        for (int i = 0; i < yTrue.length; i++) {
            ssResidual += Math.pow(yTrue[i] - yPred[i], 2);
            ssTotal += Math.pow(yTrue[i] - yMean, 2);
        }
        
        return 1 - (ssResidual / ssTotal);
    }
    
    private double calculateMSE(double[] yTrue, double[] yPred) {
        double sumSquaredErrors = 0;
        for (int i = 0; i < yTrue.length; i++) {
            sumSquaredErrors += Math.pow(yTrue[i] - yPred[i], 2);
        }
        return sumSquaredErrors / yTrue.length;
    }
    
    private double calculateMAE(double[] yTrue, double[] yPred) {
        double sumAbsoluteErrors = 0;
        for (int i = 0; i < yTrue.length; i++) {
            sumAbsoluteErrors += Math.abs(yTrue[i] - yPred[i]);
        }
        return sumAbsoluteErrors / yTrue.length;
    }
    
    @Override
    public String toString() {
        if (!isFitted) {
            return "LinearRegression (not fitted)";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("LinearRegression [weights=");
        sb.append(Arrays.toString(weights));
        sb.append(", bias=");
        sb.append(bias);
        sb.append("]");
        return sb.toString();
    }
}