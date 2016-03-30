# README #


Clases:
=======
- Animal
- CjtAnimales
- Evento
- CjtEventos

SQLite: 
=======
Es imprescindible el uso de esta herramienta para poder realizar las consultas pertinentes de las mascotas que tenemos, que como minimo seran 3.


Historias de usuaio:
====================
1). Como usuario quiero poder dar de alta una nueva mascota para poder probar el sistema (desglosar).

	- Cuando se dé de alta una mascota, el usuario escribira el nombre de la mascota y escogera el tipo de animal de una lista de animales, ademas añadira el numero del xip de la mascota en cuestion, y una foto. Si no se añade un foto, se pondrá un por defecto. 
	  
	- Cada mascota tendra un identificador unico, que sera su propio nombre.
	  
2). Como usuario quiero poder consultar una mascota ya creada/existentes en el sistema para poder ver todos sus datos (desglosar).
	
	- De cada mascota se podra consultar tanto los datos estables como dinamicos.

3). Como usuario quiero poder modificar una mascota ya creada/existente en el sistema para poder corregir algunos aspectos (desglosar).
	
	- De las mascotas consultadas solo se podra modificar los datos dinamicos, los estables no.
	
	- Si se modifica un dinamico, éste se puede: eliminar o a�adir.

4). Como usuario quiero poder eliminar una mascota ya creada/existente en el sistema para poder actualizarlo (desglosar).
	
	- Se pretende poder eliminar de manera permanente una mascota del sistema.
	
5). Como usuario quiero poder listar las mascotas ya creadas/existentes en el sistema para poder ver su informacion (desglosar).
	
	- Se podran listar las mascotas por tipo, tratamiento especial y vacunacion. En el listado obtenido apareceran las mascotas con sus datos estables y la imagen de �stas.

6). Como usuario quiero poder hacer una agenda para todas las mascotas creadas/existentes en el sistema para poder ver su informacion (desglosar).

	- Se tendra una agenda general, pero tambien se podra mostrar unicamente la agenda para una determinada mascota.

7). Como usuario quiero poder recibir notificaciones de los proximos eventos relacionados con las mascotas creadas/existentes en el sistema para poder ver su informacion (desglosar).

	- Se geraran notificaciones informando el tiempo que queda pendiente para un proximo evento de una mascota determinada. Dicha notificacion se hara con un tiempo de dos dia antes.