# prueba_tecnica

#### Este proyecto se desarrollo con Spring boot con java 11 y programacion reactiva con Spring webflux
#### Para persistir los datos se uso MySQL con jpa, se uso una base de datos bloqueante ya que la prueba requeria relaciones.
#### No se uso r2dbc que es la base de datos reactiva(No bloqueante) ya que como no permite relaciones,tocaba entonces realizar las relaciones directamente en la bd.
#### En cuanto a arquitectura se desarrollo con arquitectura hexagonal(puertos y adaptadores)

Para correr este proyecto tenga en cuenta las variables de coneccion segun su bd MYSQL,este proyecto tiene los siguientes endpoint: 

###  User(Tener en cuenta los permisos del usuario, excepto para crear)
    -/user/create               Post
    {
    "name": "Juan Pablo Salas",
    "email": "juan.salas@gmail.com",
    "phone": "3017508099",
    "website": "geormu.com"
    }
    -/user/update               Put = igual al json de arriba, pero debes agregar el id
    -/user/delete/{idUser}      Delete

###  Album (Tener en cuenta los permisos del usuario)

    -/album/create                          Post = este por defecto crea su perfil
    {
    "title" : "jamejame",
    "userId" : 1
    }
    -/album/update                         Put = igual al json de arriba, pero debes agregar el id
    -/album/delete/{idAlbum}/{idUser}      Delete
    -/album                                Get = trae todos los albunes,sus permisos y fotos
    -/album/{userId}                       Get = trae todos los albunes de un usuario, sus permisos y fotos

###  Photo(Tener en cuenta los permisos del usuario)    

    -/photo/create/{idUser}                Post 
    {
    "title" : "vacaciones en la forest",
    "url" : "prueba100",
    "albumId" : 6
    }
    -/photo/update/{idUser}                Put = igual al json de arriba, pero debes agregar el id
    -/photo/delete/{idUser}/{idPhoto}      Delete
    -/photo                                Get = trae todas las fotos

###  Permissions(Tener en cuenta los permisos del usuario) Al crear el permiso, en tipo debe ser  write o read

    -/permission/create                     Post 
    {
    "typePermission": "write",
    "album" : 8,
    "user" : 1
    }
    -/permission/update                     Put = igual al json de arriba, pero debes agregar el id(solo cambiar permiso)
    -/permission/delete/{idPermission}      Delete
    -/permission/{albumId}/{permission}     Get = trae los permisos de un usuario segun el album y el tipo de permiso

### Comments

    -/comment/byEmail/{email}               Get= me trae los comentarios por email
    -/comment/byName/{name}                 Get= me trae los comentarios por name