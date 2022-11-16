package edu.upc.dsa.Domain;

import edu.upc.dsa.Domain.Entity.Exceptions.*;
import edu.upc.dsa.Domain.Entity.Juego;
import edu.upc.dsa.Domain.Entity.Usuario;

import java.util.List;

public interface Manager {
    public int size();
    public void añadirUsuario(String idUsuario, String nombre, String apellidos) throws UsuarioYaExiste;
    public void crearJuego(String idJuego, String descripcion, int niveles) throws JuegoYaExiste;
    public void inicioPartida(String idJuego, String idUsuario) throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva;
    public List<String> nivelActual(String idUsuario) throws UsuarioNoExiste,PartidaInactiva;
    public int puntuacionActual(String idUsuario) throws UsuarioNoExiste,PartidaInactiva;
    public void pasarNivel(String idUsuario, int puntos, String fecha) throws UsuarioNoExiste,PartidaInactiva;
    public void finalizarPartida(Usuario usuario) throws UsuarioNoExiste,PartidaInactiva;
    public List<Usuario> listaUsuariosJuego(Juego juego) throws JuegoNoExiste;
    public List<String> PartidasUsuario(String idUsuario) throws UsuarioNoExiste;
    public List<String> actividadUsuario(String idUsuario, String idJuego) throws UsuarioNoExiste, JuegoNoExiste;
    public int numUsuarios();
    public int numJuegos();
}
