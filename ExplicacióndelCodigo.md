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
# Main del programa
El programa primero utiliza la funcion fit minimizando la data que entrenara el programa. Luego de esto, imprimira los datos del peso y el bias de la data que entrena.   
Luego de esto el progrma predice utilizando la data de prueba de X y lo imprime.  
Luego imprime el puntuaje de error.  
# Ejecucion del programa

Esta foto muestra la ejecución del programa entrenada con la base de datos de la venta de helados y con unos datos de prueba aleatorios.  
<img width="816" height="152" alt="Link1" src="https://github.com/user-attachments/assets/c18c2697-8765-4d74-8532-883a126978b4" />  

Esta foto muestra la ejecución del programa entrenada con la base de datos de los estudiantes y con unos datos de prueba aleatorios.  
<img width="888" height="155" alt="Link2" src="https://github.com/user-attachments/assets/0286a5fb-656b-4537-8d29-076a7cbe5eb0" />  

## Conclusiones
Trabajo diferente a los anteriores.
Se sabia de antemano el lenguaje.
Tomo arto tiempo entender el tema.
Este trabajo tuvo muchas distinciones a los anteriores. Esto no solo es porque fuera un lenguaje diferente, sino por lo que pedia. Este trabajo solo pedia un solo programa que hiciera todo lo que dificulto un poco la creación del mismo. También fue un poco complicado el problema que se intentaba resolver al implementarlo en una clase. Aunque fuera complicado el problema, se facilito por el hecho de que este lenguaje se había trabajado en semestres anteriores en otras clases. Y por ultimo el tema fue complicado de entender por su complejidad ya que requeria formular y procesar matrices. 



