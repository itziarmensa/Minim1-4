package edu.upc.dsa.Services;

import edu.upc.dsa.Domain.Entity.Exceptions.*;
import edu.upc.dsa.Domain.Entity.Juego;
import edu.upc.dsa.Domain.Entity.TO.JuegoInfo;
import edu.upc.dsa.Domain.Entity.TO.UsuarioInfo;
import edu.upc.dsa.Domain.Entity.Usuario;
import edu.upc.dsa.Domain.Manager;
import edu.upc.dsa.Infraestructure.ManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/juego", description = "Endpoint to juego Service")
@Path("/juegos")
public class Services {

    private Manager manager;

    public Services() throws UsuarioYaExiste, JuegoYaExiste {
        this.manager = ManagerImpl.getInstance();
        if(manager.size() == 0){
            this.manager.añadirUsuario("469823467", "Itziar", "Mensa");
            this.manager.añadirUsuario("723678", "Paula", "Mensa");
            this.manager.añadirUsuario("469883", "Mònica", "Minguito");

            this.manager.crearJuego("746736","Mario Bross", 3);
            this.manager.crearJuego("438726","Tetris", 2);
            this.manager.crearJuego("7432678","Monopoli", 4);

        }
    }

    @POST
    @ApiOperation(value = "añadir un nuevo usuario", notes = "Añadir usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso", response= UsuarioInfo.class),
            @ApiResponse(code = 409, message = "El usuario ya existe")
    })
    @Path("/juego")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response añadirUsuario(UsuarioInfo usuario){
        try{
            this.manager.añadirUsuario(usuario.getId(),usuario.getNombre(),usuario.getApellidos());
        }catch(UsuarioYaExiste e){
            return Response.status(409).entity(usuario).build();
        }
        return Response.status(201).entity(usuario).build();
    }

    @POST
    @ApiOperation(value = "crear juego", notes = "Crear Juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso", response= JuegoInfo.class),
            @ApiResponse(code = 409, message = "El juego ya existe")
    })
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crearJuego(JuegoInfo juego){
        try{
            this.manager.crearJuego(juego.getId(),juego.getDesc(), juego.getNumNiveles());
        }catch(JuegoYaExiste e){
            return Response.status(409).entity(juego).build();
        }
        return Response.status(201).entity(juego).build();
    }

    @PUT
    @ApiOperation(value = "iniciar partida", notes = "Iniciar Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 400, message = "El usuario o el juego no existen"),
            @ApiResponse(code = 403, message = "El usuario está en otra partida")
    })
    @Path("/usuario/{usuarioId}/{juegoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarPartida(@PathParam("usuarioId") String userId, @PathParam("juegoId") String juegoId) {
        try {
            this.manager.inicioPartida(userId, juegoId);
        } catch (PartidaActiva e) {
            return Response.status(403).build();
        } catch (UsuarioNoExiste | JuegoNoExiste e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Nivel actual de un usuario en una partida", notes = "Nivel Actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", responseContainer="List"),
            @ApiResponse(code = 400, message = "El usuario no existe"),
            @ApiResponse(code = 403, message = "El usuario no está en ninguna partida")

    })
    @Path("/usuario/nivel/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response nivelActual(@PathParam("usuarioId") String usuarioId){
        try {
            List<String> lista = this.manager.nivelActual(usuarioId);
            GenericEntity<List<String>> entity = new GenericEntity<List<String>>(lista) {};
            return Response.status(200).entity(entity).build();
        }catch (UsuarioNoExiste e){
            return Response.status(400).build();
        }catch (PartidaInactiva e){
            return Response.status(403).build();
        }

    }

    @GET
    @ApiOperation(value = "Puntuación actual de un usuario en una partida", notes = "Puntuación Actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", response = Usuario.class),
            @ApiResponse(code = 400, message = "El usuario no existe"),
            @ApiResponse(code = 403, message = "El usuario no está en ninguna partida")

    })
    @Path("/usuario/puntuación/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response puntuacionActual(@PathParam("usuarioId") String usuarioId){
        try {
            int nivel = this.manager.puntuacionActual(usuarioId);
            String nivelS = String.valueOf(nivel);
            return Response.status(200).entity(nivelS).build();
        }catch (UsuarioNoExiste e){
            return Response.status(400).build();
        }catch (PartidaInactiva e){
            return Response.status(403).build();
        }

    }

    @PUT
    @ApiOperation(value = "Pasar de nivel en una partida", notes = "Pasar Nivel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 400, message = "El usuario no existe"),
            @ApiResponse(code = 403, message = "El usuario no está en ninguna partida")
    })
    @Path("/usuario/partida/{usuarioId}/{puntos}/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pasarNivel(@PathParam("usuarioId") String userId, @PathParam("puntos") int puntos, @PathParam("fecha") String fecha) {
        try {
            this.manager.pasarNivel(userId, puntos,fecha);
        } catch (PartidaInactiva e) {
            return Response.status(403).build();
        } catch (UsuarioNoExiste e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "Finalizar partida de un usuario", notes = "Finalizar Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 400, message = "El usuario no existe"),
            @ApiResponse(code = 403, message = "El usuario no está en ninguna partida")
    })
    @Path("/usuario/partida/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarPartida(Usuario usuario) {
        try {
            this.manager.finalizarPartida(usuario);
        } catch (PartidaInactiva e) {
            return Response.status(403).build();
        } catch (UsuarioNoExiste e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Lista de usuarios de un juego", notes = "Lista Usuarios Juego")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 400, message = "El juego no existe")

    })
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response usuariosJuego(Juego juego){
        try {
            List<Usuario> lista = this.manager.listaUsuariosJuego(juego);
            GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(lista) {};
            return Response.status(200).entity(entity).build();
        }catch (JuegoNoExiste e){
            return Response.status(400).build();
        }

    }

    @GET
    @ApiOperation(value = "Lista de partidas de un usuario", notes = "Lista Partidas Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", responseContainer="List"),
            @ApiResponse(code = 400, message = "El usuario no existe")

    })
    @Path("/usuario/partidas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response partidasUsuario(@PathParam("idUsuario") String idUsuario){
        try {
            List<String> lista = this.manager.PartidasUsuario(idUsuario);
            GenericEntity<List<String>> entity = new GenericEntity<List<String>>(lista) {};
            return Response.status(200).entity(entity).build();
        }catch (UsuarioNoExiste e){
            return Response.status(400).build();
        }

    }

    @GET
    @ApiOperation(value = "Actividad de un usuario en un juego", notes = "Actividad Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", responseContainer="List"),
            @ApiResponse(code = 400, message = "El usuario o el juego no existen"),
            @ApiResponse(code = 403, message = "El usuario no está en ninguna partida")

    })
    @Path("/usuario/actividad/{idUsuario}/{idJuego}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actividadUsuario(@PathParam("idUsuario") String idUsuario, @PathParam("idJuego") String idJuego){
        try {
            List<String> lista = this.manager.actividadUsuario(idUsuario,idJuego);
            GenericEntity<List<String>> entity = new GenericEntity<List<String>>(lista) {};
            return Response.status(200).entity(entity).build();
        }catch (UsuarioNoExiste | JuegoNoExiste e){
            return Response.status(400).build();
        }catch (PartidaInactiva e){
            return Response.status(403).build();
        }

    }

    @GET
    @ApiOperation(value = "Numero de usuarios totales", notes = "Numero Usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso"),

    })
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response numUsuarios(){
        int num = this.manager.numUsuarios();
        String numS = String.valueOf(num);
        return Response.status(200).entity(numS).build();


    }

    @GET
    @ApiOperation(value = "Numero de juegos totales", notes = "Numero Juegos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso"),

    })
    @Path("/numJuegos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response numJuegos(){
        int num = this.manager.numJuegos();
        String numS = String.valueOf(num);
        return Response.status(200).entity(numS).build();


    }

    @GET
    @ApiOperation(value = "Lista de usuarios totales", notes = "Lista Usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso",response = Usuario.class, responseContainer="List"),

    })
    @Path("/usuarios/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaUsuarios(){
        List<Usuario> lista = this.manager.listaUsuarios();
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(lista) {};
        return Response.status(200).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "Lista de juegos totales", notes = "Lista Juegos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso",response = Juego.class, responseContainer="List"),

    })
    @Path("/juegos/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaJuegos(){
        List<Juego> lista = this.manager.listaJuegos();
        GenericEntity<List<Juego>> entity = new GenericEntity<List<Juego>>(lista) {};
        return Response.status(200).entity(entity).build();

    }




}
