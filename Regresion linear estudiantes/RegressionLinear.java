import java.util.Arrays;

public class RegressionLinear {
    private double[] pesos;
    private double bias;
    private boolean isFitted;
    private double[] featureMeans;
    private double[] featureStds;
    private double targetMean;
    private double targetStd;
    private boolean useScaling;
    
    // Constructores
    public RegressionLinear() {
        this(false);
    }
    
    public RegressionLinear(boolean useScaling) {
        this.useScaling = useScaling;
        this.isFitted = false;
    }
    
    // Getters
    public double[] getPesos() {
        return pesos != null ? Arrays.copyOf(pesos, pesos.length) : null;
    }
    
    public double getBias() {
        return bias;
    }
    
    public boolean isFitted() {
        return isFitted;
    }
    
    // Entrena el modelo y minimiza en el proceso
    
    public void fit(double[][] X, double[] y, int iteraciones, double ritmodeAprendizaje) {
        if (X.length != y.length) {
            throw new IllegalArgumentException("La lista X y Y no tienen el mismo numero de variables");
        }
        
        int nSamples = X.length;
        int nFeatures = X[0].length;
        

        this.pesos = new double[nFeatures];
        this.bias = 0;
        
        double[][] XScaled;
        double[] yScaled;
        

        if (useScaling) {
            XScaled = scaleFeatures(X);
            yScaled = scaleTarget(y);
        } else {
            XScaled = X;
            yScaled = y;
        }
        
        for (int iter = 0; iter < iteraciones; iter++) {
            double[] predicciones = predictInternal(XScaled);
            double[] errors = new double[nSamples];
            

            for (int i = 0; i < nSamples; i++) {
                errors[i] = predicciones[i] - yScaled[i];
            }
            
            for (int j = 0; j < nFeatures; j++) {
                double gradient = 0;
                for (int i = 0; i < nSamples; i++) {
                    gradient += errors[i] * XScaled[i][j];
                }
                pesos[j] -= ritmodeAprendizaje * (gradient / nSamples);
            }
            
            double biasGradient = 0;
            for (int i = 0; i < nSamples; i++) {
                biasGradient += errors[i];
            }
            bias -= ritmodeAprendizaje * (biasGradient / nSamples);
        }
        
        if (useScaling) {
            ajustarPesosParaEscala();
        }
        
        this.isFitted = true;
    }
    

     // Predice las salidas para cada dato de entrada
    
    public double[] predict(double[][] X) {
        if (!isFitted) {
            throw new IllegalStateException("Primero minimiza el modelo");input
        }
        
        if (X[0].length != pesos.length) {
            throw new IllegalArgumentException("Las dimensiones de entrada no estan correctas");
        }
        
        double[][] XScaled = useScaling ? scaleFeatures(X) : X;
        double[] predicciones = predictInternal(XScaled);
        
        if (useScaling) {
            for (int i = 0; i < predicciones.length; i++) {
                predicciones[i] = predicciones[i] * targetStd + targetMean;
            }
        }
        
        return predicciones;
    }
    
    /**
     * Internal prediction method (assumes scaled data if scaling is used)
     */
    private double[] predictInternal(double[][] X) {
        double[] predicciones = new double[X.length];
        
        for (int i = 0; i < X.length; i++) {
            predicciones[i] = bias;
            for (int j = 0; j < pesos.length; j++) {
                predicciones[i] += pesos[j] * X[i][j];
            }
        }
        
        return predicciones;
    }
    
    /**
     * Computes R² score (coefficient of determination)
     */
    public double score(double[][] X, double[] y) {
        if (!isFitted) {
            throw new IllegalStateException("Primero minimiza el modelo");
        }
        
        double[] predicciones = predict(X);
        return calcularRCuadrado(y, predicciones);
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
     * Ajusta pesos y bias despues de entrenar con la data escalada
     */
    private void ajustarPesosParaEscala() {
        // Ajustar bias
        double biasAdjustment = 0;
        for (int j = 0; j < pesos.length; j++) {
            biasAdjustment += pesos[j] * featureMeans[j] / featureStds[j];
        }
        bias = bias * targetStd + targetMean - biasAdjustment * targetStd;
        
        // Ajustar pesos
        for (int j = 0; j < pesos.length; j++) {
            pesos[j] = pesos[j] * targetStd / featureStds[j];
        }
    }
    

    private double calcularRCuadrado(double[] yTrue, double[] yPred) {
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


    @Override
    public String toString() {
        if (!isFitted) {
            return "El modelo no esta minimizado";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("RegressionLinear [pesos=");
        sb.append(Arrays.toString(pesos));
        sb.append(", bias=");
        sb.append(bias);
        sb.append("]");
        return sb.toString();
    }
}
