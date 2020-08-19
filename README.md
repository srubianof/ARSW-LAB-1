
#Black List Search

## Part I - Introduction to threads in JAVA
Change the beginning with start() to run(). How does the output change? Why?

La diferencia entre usar start() y run() es: 
* Cuando utilizamos start() en un thread basicamente lo que hace es crear un nuevo hilo y ejecutar el metodo run() dentro de el de manera asincrona, y finalmente este nuevo hilo, queda en estado de RUNNABLE. 
* Cuando utilizamos solamente run(), ejecutamos este metodo dentro del hilo y su ejecucion es de manera sincrona.

## Part II - Black List Search Exercise:
### Join
![Imagen](https://media.discordapp.net/attachments/742236419757703229/745444994185428993/carbon-2.png)

## PARTE-3-Performance Evaluation:
Una posible solucion es poner el numero de ocurrencias (BLACK_LIST_ALARM_COUNT) como un numero atomico de manera que cuando este sea cero, la ejecucion de los hilos subsecuentes se detenga. 

## PARTE-4-Performance Evaluation:

When starting the program, run the jVisualVM monitor, and as the tests run, check and record the CPU and memory consumption in each case.
- Un Hilo
 ![imagen](https://media.discordapp.net/attachments/742236419757703229/745473513242230804/carbon-5.png)
 ![image](https://media.discordapp.net/attachments/742236419757703229/745473783090905199/Screen_Shot_2020-08-18_at_9.46.33_PM.png?width=1536&height=1208)

- As many threads as processing cores - 4 hilos
![Imagenes](https://media.discordapp.net/attachments/742236419757703229/745475149087768686/carbon-7.png)
![image](https://media.discordapp.net/attachments/742236419757703229/745474559876137090/Screen_Shot_2020-08-18_at_9.49.38_PM.png?width=1536&height=1208)
- As many threads as twice the number of processing cores - 8 hilos
![Imagenes](https://media.discordapp.net/attachments/742236419757703229/745475163986067556/carbon-8.png)
![i](https://media.discordapp.net/attachments/742236419757703229/745474908372336750/Screen_Shot_2020-08-18_at_9.51.03_PM.png?width=1536&height=1208)
- Cincuenta Hilos
![Imagenes](https://media.discordapp.net/attachments/742236419757703229/745475969686700052/carbon-9.png)
![a](https://media.discordapp.net/attachments/742236419757703229/745475875289694274/Screen_Shot_2020-08-18_at_9.54.51_PM.png?width=1536&height=1208)
- Cien hilos
![Imagenes](https://media.discordapp.net/attachments/742236419757703229/745476360050573414/carbon-10.png)
![i](https://media.discordapp.net/attachments/742236419757703229/745476298608083074/Screen_Shot_2020-08-18_at_9.56.34_PM.png?width=1536&height=1208)

* With the above, and with the given execution times, make a graph of solution time vs. Number of threads.
![i](https://media.discordapp.net/attachments/742236419757703229/745479444894056488/Picture1.png)

* Why is the best performance not achieved with the 500 threads? How does this performance compare when 200 are used?.
Al revisar los resultados vemos que luego de 8 hilos el tiempo de ejecucion converge a un tiempo muy reducido, en este caso entre mayor cantidad de hilos, menor es el tiempo de ejecucion. En este caso la diferencia de tiempo de ejecucion entre 200 y 500 hilos es despreciable de manera que podemos decir que el comportamiento tiende a ser el mismo.

* How does the solution behave using as many processing threads as cores compared to the result of using twice as much?
Al tener la misma cantidad de hilos como nucleos de procesamiento, el rendimiento es la mitad que cuando tenemos el doble de hilos. 

* According to the above, if for this problem instead of 100 threads in a single CPU could be used 1 thread in each of 100 hypothetical machines, Amdahls law would apply better ?. If x threads are used instead of 100/x distributed machines (where x is the number of cores of these machines), would it be improved? Explain your answer.
El comportamiento no mejora pues hay un gasto innecesario de recursos fisicos; el rendimiento del programa es mas alto al tener 100 hilos en una misma CPU que al tener 100 CPUs con 1 hilo. 