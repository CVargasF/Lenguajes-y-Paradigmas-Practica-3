# Introducción
## Resumen del trabajo
Este trabajo solo propone un solo reto. Crear y diseñar un programa, sea en Java o C++, que pueda calcular regresion lineal simple y múltiple. 
## Requerimientos del programa
El programa necesita implementar una clase con los siguientes atributos.  
peso: Arreglo con el modelo de pesos.  
bias: El bias del termino.  
El programa también necesita unos métodos especificos.  
fit(): Entrena el modelo con la información de entrada.  
predict(): Predice la salida.  
score(): Computa el error de la predicción
data_scaling(): Escala los valores del set de data.  
El pseudocódigo para probar los modelo de regresión linear multiple se debería ver así:  
X_train[][] = ...; // Data de entrada  
y_train[] = ...; // Output data 
LinearRegression mlr = new LinearRegression(...); // Creación de objeto tipo Regresión lineal  
mlr.fit(...); // El objeto se entrena con la información de entrada
print("weights: " + mlr.getWeigths()); // El objeto imprime el peso
print("bias: " + mlr.getBias());  // El objeto imprime el bias
X_test[][] = ...; // Test data  
y_hat[] = mlr.predict(...); // El objeto predice  
for (yi in y_hat)  
print("y_hat: " + yi); // Se imprimen los datos de la predicción  
print("score: " + mlr.score(...));  // Se calculan y se imprimen el error de predicción.  
## Uso del programa con bases de datos
El programa debe correr y debe dar información sobre la predicción de las dos bases de datos siguientes. Una base de datos que tiene data sobre la venta de helados y el otro siendo los resultados de un examen para estudiantes.  
### Data de venta de helados
Entrenar el programa con la data de la venta de helados utilizando la regresion simple que se implemento.  
### Student_exam_scores
Entrenar el programa con la data de los resultados de el examen utilizando regresion multiple que se implemento.  
