# Programa main

## RegressionLineal
### Variables
Las variables que utiliza esta clase son:  
  1. Los pesos (Un a lista de doubles)  
  2. El bias (Un double)  
  3. Si esta minimizado (Un boolean)  
  4. Los promedios (Una lista de doubles)  
  5. targetSTD (Un double)  
  6. Si utiliza escala (Un boolean)  
### Constructores
Hay dos constructores en total
  1. Constructor que se inicia sin escalado automaticamente
  2. Constructor que puedes especificar si el escalado esta desactivado o activado
### Metodos
**Getters**  
Obtener peso - Retorna los pesos.  
Obtener bias - Retorna el bias.   
Esta minimizado - Si esta minimizado los datos  
**Procesos**  
fit - Minimiza la lista de data y procesa los datos de entrada  
Predecir - Predice la salida  
Score - Imprime el coeficiente de determinación  


## RegresionLinealMain
### Variables  
Este programa inicia con cuatro variables, con un arreglo de arreglos de doubles, este siendo la X del plano de regresion lineal y un arreglo de doubles. Esta es la data que entrenara el programa. Luego un arreglo de arreglos de doubles siendo la X y un arreglo de doubles. Esta es la data de prueba para el programa.  
# Cuerpo del programa
El programa primero utiliza la funcion fit minimizando la data que entrenara el programa. Luego de esto, imprimira los datos del peso y el bias de la data que entrena.   
Luego de esto el progrma predice utilizando la data de prueba de X y lo imprime.  
Luego imprime el puntuaje de error.  




