package edu.upc.dsa.Infraestructure;

import edu.upc.dsa.Domain.Entity.Exceptions.*;
import edu.upc.dsa.Domain.Entity.Juego;
import edu.upc.dsa.Domain.Entity.Usuario;
import edu.upc.dsa.Domain.Entity.VO.RandomId;
import edu.upc.dsa.Domain.Manager;

import java.util.*;

import org.apache.log4j.Logger;

public class ManagerImpl implements Manager {

    private static Manager instance;

    protected List<Juego> juegos;

    protected Map<String, Usuario> usuarios;

    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    public ManagerImpl(){
        this.juegos = new ArrayList<>();
        this.usuarios = new HashMap<>();
    }

    public static Manager getInstance() {
        if (instance==null) instance = new ManagerImpl();
        return instance;
    }

    @Override
    public int size() {
        int size = this.usuarios.size();
        logger.info("size "+size);
        return size;
    }

    public Boolean usuarioExisteId(String idUsuario) {
        for(Usuario usuario : this.usuarios.values()){
            if(usuario.getIdUsuario() == idUsuario){
                return true;
            }
        }
        return false;
    }

    @Override
    public void añadirUsuario(String idUsuario, String nombre, String apellidos) throws UsuarioYaExiste {
        logger.info("Intentamos registrar el usuario con: "+idUsuario+", "+nombre+", "+apellidos+"");
        if(usuarioExisteId(idUsuario)){
            logger.error("No se puede crear el usuario porque ya existe uno con ese ID");
            throw new UsuarioYaExiste();
        }
        Usuario usuario = new Usuario(idUsuario,nombre,apellidos);
        this.usuarios.put(usuario.getIdUsuario(),usuario);
        logger.info("Se ha realizado correctamente");

    }

    public Juego getJuegoById(String idJuego) {
        return this.juegos.stream()
                .filter(x->idJuego.equals(x.getIdJuego()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void crearJuego(String idJuego, String descripcion, int niveles) throws JuegoYaExiste {
        logger.info("Intentamos crear el juego con: "+idJuego+", "+descripcion+", "+niveles+"");
        Juego juego = getJuegoById(idJuego);
        if(juego!=null){
            logger.error("El juego con id: "+idJuego+" ya existe");
            throw new JuegoYaExiste();
        }
        Juego juego1 = new Juego(idJuego,descripcion,niveles);
        this.juegos.add(juego1);
        logger.info("Se ha realizado correctamente");
    }

    public Usuario getUsuarioById(String idUsuario) {
        return this.usuarios.values().stream()
                .filter(x->idUsuario.equals(x.getIdUsuario()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void inicioPartida(String idJuego, String idUsuario) throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva {
        logger.info("Iniciando partida en el juego "+idJuego+" y con el usuario: "+idUsuario+"");
        Usuario usuario = getUsuarioById(idUsuario);
        Juego juego = getJuegoById(idJuego);
        if(usuario==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        if (juego==null){
            logger.error("El juego no existe");
            throw new JuegoNoExiste();
        }
        if(usuario.getPartida()==true){
            logger.error("El usuario está en otra partida");
            throw new PartidaActiva();
        }
        usuario.setNivelUsuario(1);
        usuario.setPuntosUsuario(50);
        usuario.setPartida(true);
        String partida = RandomId.getId();
        usuario.añadirPartida(partida);
        usuario.setPartidaActual(partida);
        usuario.setJuegoActual(idJuego);
        List<Usuario> lista = juego.getListaUsuarios();
        lista.add(usuario);
        logger.info("Se ha iniciado correctamente la partida");
    }

    @Override
    public List<String> nivelActual(String idUsuario) throws UsuarioNoExiste, PartidaInactiva {
        logger.info("Obteniendo nivel actual");
        Usuario usuario = getUsuarioById(idUsuario);
        if(usuario==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        if(usuario.getPartida() == false){
            logger.error("El usuario no está en una partida");
            throw new PartidaInactiva();
        }
        List<String> lista = new LinkedList<>();
        lista.add(String.valueOf(usuario.getNivelUsuario()));
        lista.add(usuario.getPartidaActual());
        logger.info("Se ha efectuado correctamente la petición");
        return lista;
    }

    @Override
    public int puntuacionActual(String idUsuario) throws UsuarioNoExiste, PartidaInactiva {
        logger.info("Obteniendo puntuación actual");
        Usuario usuario = getUsuarioById(idUsuario);
        if(usuario==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        if(usuario.getPartida() == false){
            logger.error("El usuario no está en una partida");
            throw new PartidaInactiva();
        }
        logger.info("Se ha efectuado correctamente la petición");
        return usuario.getPuntosUsuario();

    }

    @Override
    public void pasarNivel(String idUsuario, int puntos, String fecha) throws UsuarioNoExiste, PartidaInactiva {
        logger.info("Pasando de nivel al usuario "+idUsuario+"");
        Usuario usuario = getUsuarioById(idUsuario);
        if(usuario==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        if(usuario.getPartida() == false){
            logger.error("El usuario no está en una partida");
            throw new PartidaInactiva();
        }
        String idJuego = usuario.getJuegoActual();
        Juego juego = getJuegoById(idJuego);
        if(usuario.getNivelUsuario() == juego.getNumNiveles()){
            usuario.setPuntosUsuario(usuario.getPuntosUsuario()+100);
            usuario.setPartida(false);
            usuario.setPartidaActual("");
            usuario.setNivelUsuario(0);
        }else{
            usuario.setNivelUsuario(usuario.getNivelUsuario()+1);
            usuario.setPuntosUsuario(puntos);
        }
        logger.info("Se ha incrementado de nivel correctamente");

    }

    @Override
    public void finalizarPartida(Usuario usuario) throws UsuarioNoExiste, PartidaInactiva {
        logger.info("Finalizando partida de "+usuario.getIdUsuario()+"");
        Usuario usuario1 = getUsuarioById(usuario.getIdUsuario());
        if(usuario1==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        if(usuario.getPartida() == false){
            logger.error("El usuario no está en una partida");
            throw new PartidaInactiva();
        }
        usuario1.setPartida(false);
        usuario1.setPartidaActual("");
        usuario1.setNivelUsuario(0);
        usuario1.setPuntosUsuario(0);
        logger.info("Se ha realizado la acción correctamente");
    }

    @Override
    public List<Usuario> listaUsuariosJuego(Juego juego) throws JuegoNoExiste {
        logger.info("Obteniendo listado de usuarios del juego "+juego.getIdJuego()+"ordenado descendentement");
        Juego juego1 = getJuegoById(juego.getIdJuego());
        if(juego1==null){
            logger.info("El juego no existe");
            throw new JuegoNoExiste();
        }
        List<Usuario> usuarios1 = juego1.getListaUsuarios();
        usuarios1.sort((Usuario o1, Usuario o2)->Double.compare(o2.getPuntosUsuario(), o1.getPuntosUsuario()));
        logger.info("Se han ordenador los usuarios correctamente");
        return usuarios1;
    }

    @Override
    public List<String> PartidasUsuario(String idUsuario) throws UsuarioNoExiste {
        logger.info("Buscando partidas del usuario "+idUsuario+"");
        Usuario usuario = getUsuarioById(idUsuario);
        if(usuario==null){
            logger.error("El usuario no existe");
            throw new UsuarioNoExiste();
        }
        logger.info("Lista encontrada correctamente");
        return usuario.getPartidas();
    }

    @Override
    public List<String> actividadUsuario(String idUsuario, String idJuego) throws UsuarioNoExiste, JuegoNoExiste {
        logger.info("Buscando actividad del usuario "+idUsuario+" en el juego "+idJuego+"");
        return null;
    }

    @Override
    public int numUsuarios() {
        return this.usuarios.size();
    }

    @Override
    public int numJuegos() {
        return this.juegos.size();
    }

    @Override
    public List<Usuario> listaUsuarios() {
        List<Usuario> usuarios1 = new ArrayList<>(this.usuarios.values());
        return usuarios1;
    }

    @Override
    public List<Juego> listaJuegos() {
        return juegos;
    }


}
