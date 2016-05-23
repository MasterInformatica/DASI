# Guía de instalación y uso
## Instalación

Para la instalación y ejecución del simulador en un equipo, se deberán
tener como mínimo los siguientes requisitos:
* Eclipse Mars o superior (versión >= 4.5).
* Java JDK 1.7 o superior.
* Cliente git en el equipo.

La instalación se hará a través del repositorio del proyecto alojado en
github, en la siguiente url:
         `https://github.com/lcosteroucm/dasi`

Para ello, se descargará el proyecto en una carpeta del sistema de la
manera más sencilla para el usuario:

* A través de un cliente en línea de órdenes:
          `git clone https://github.com/lcosteroucm/dasi`
* A través de un cliente gráfico instalado en el equipo.
* A través del propio eclipse con algún plugin de gestión de repositorios git.

Una vez descargado el repositorio, se procederá a abrir eclipse, e importar
un proyecto existente, indicando la ruta en la que nos hemos descargado el
proyecto cuando se solicite.

Al importarse el proyecto, el árbol de directorios deberá quedar como se
muestra en las siguiente imágen:

![arbol_cerrado.png](doc/images/arbol_cerrado.png "Árbol de directorios del proyecto cerrado")


Para lanzar el proyecto, primero es necesario elegir la organización con la
que se desea ejecutar. En la ruta `config/icaro/aplicaciones/descripcionOrganizaciones/MRS`
aparecen las distintas organizaciones que se pueden escoger para lanzar la
aplicación. La siguiente tabla resume todas ellas:


 Nombre organización | Descripción 
 ------------- |:-------------:| -----:
`descripcionNuevoInicio.xml` | Organización compuesta por 5 robots y 5 víctimas. Es la organización más genérica, ya que aunque se instancian todos los agentes, al seleccionar el escenario pueden dejarse agentes sin ejecutar. **Se recomeinda utilizar esta organización** 
2R3V.xml | Organización compuesta por 2 robots rescatadores, y 3 víctimas a rescatar 
 3R2V.xml | Organización compuesta por 3 robots y 2 víctimas. 

Una vez importado el proyecto y elegida la organización, se procederá a
crear un perfil para poder ejecutarla. En eclipse, se accederá al menú 
“Run > Run Configurations...”, y se procederá a crear una nueva “Java
Application” asociada al proyecto, indicando que la clase principal es
`icaro.infraestructura.clasesGeneradorasOrganizacion.ArranqueSistemaConCrtlGestorO`,
y dentro de la pestaña Arguments, la organización escogida para lanzar el
simulador (Nota: como se muestra en la imagen, el fichero de organización
no debe incluir la extensión .xml a la hora de configurar el
proyecto). Esto se puede ver de manera resumida en los siguientes recortes
de pantalla:

![run_conf_1.png](doc/images/image09.png)
![run_conf_2.png](doc/images/image14.png "Muestra de cómo configurar el proyecto en eclipse.")


Al finalizar de configurar el proyecto, se procederá pulsando el botón de
“Apply” y posteriormente el botón de “Run”


## Ejecución del simulador

Una vez iniciado el simulador, se obtendrá la siguiente pantalla, donde en
la parte superior izquierda debemos seleccionar Load, para elegir el
archivo XML con el escenario que queremos cargar en la aplicación
(compuesto por el mapa, los mineros con su nombre y posición, y los
rescatadores con los mismos datos)

![as](doc/images/image01.png " Pasos necesarios para cargar un nuevo escenario en el simulador")

Para nuestro ejemplo se utilizará el archivo 2R3V.xml, es cual tiene el
detalle de 2 rescatadores y 3 víctimas. El resto de escenarios válidos
pueden encontrarse en la ruta `MRS/escenarios`, aunque por
defecto la aplicación debería mostrar seleccionada esa ruta.

![as](doc/images/image06.png "Selección de un fichero de escenarios")

Una vez cargado el escenario, este se puede modificar pulsando con el botón
sobre las distintas casillas que se desea cambiar. Las opciones de
modificación del escenario son relativas al mapa y no a la configuración de
los agentes, es decir, permite modificar: (1) pasillos, (2) paredes, (3)
derrumbes conocidos, (4) derrumbes desconocidos. La siguiente imagen
muestra el cuadro de diálogo que se muestra cuando se desea modificar el
mapa cargado previamente. Una vez que el escenario es modificado, se puede
almacenar en un fichero en un fichero xml a través del menú de la ventana.

![modificarEscenario.jpg](doc/images/image05.png "Proceso de modificación de un escenario previo a la simulación.")

Una vez que se dispone del escenario deseado, se procede pulsando el botón
Start para comenzar la simulación.Los rescatadores buscan el camino más
óptimo y se dirigen a salvar a las víctimas. En caso de encontrar un
derrumbe lo marca como conocido para saber que ya pasó por ese punto y no
se puede atravesar el pasillo correspondiente.

El proceso de restate de una víctima se muestra mediante el movimiento del
robot hasta la víctima, y la vuelta de los dos agentes juntos hasta la
salida.

Al finalizar la simulación, se muestra una ventana de estadísticas con los
datos generados durante la simulación.

![as](images/image00.png "Ventana de estadísticas mostradas al finalizar el sistema.")
