package edu.upc.dsa;

import edu.upc.dsa.Domain.Entity.Exceptions.*;
import edu.upc.dsa.Domain.Entity.Juego;
import edu.upc.dsa.Domain.Entity.Usuario;
import edu.upc.dsa.Domain.Manager;
import edu.upc.dsa.Infraestructure.ManagerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ManagerImplTest {
    Manager manager;

    @Before
    public void serUp() throws UsuarioYaExiste, JuegoYaExiste{
        this.manager = new ManagerImpl();

        this.manager.añadirUsuario("469823467", "Itziar", "Mensa");
        this.manager.añadirUsuario("723678", "Paula", "Mensa");
        this.manager.añadirUsuario("469883", "Mònica", "Minguito");

        this.manager.crearJuego("746736","Mario Bross", 3);
        this.manager.crearJuego("438726","Tetris", 2);
        this.manager.crearJuego("7432678","Monopoli", 4);
    }

    @After
    public void tearDown(){
        this.manager = null;
    }

    @Test
    public void testAñadirUsuario() throws UsuarioYaExiste {
        Assert.assertEquals(3,this.manager.numUsuarios());
        this.manager.añadirUsuario("7326743689", "Óscar", "Boullosa");
        Assert.assertEquals(4,this.manager.numUsuarios());

    }

    @Test
    public void testCrearJuego() throws JuegoYaExiste {
        Assert.assertEquals(3,this.manager.numJuegos());
        this.manager.crearJuego("47832", "Candy Crush", 5738);
        Assert.assertEquals(4,this.manager.numJuegos());
    }

    @Test
    public void testInicioPartida() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        Assert.assertEquals(true, usuarios.get(0).getPartida());
        Assert.assertEquals(1, usuarios.get(0).getNivelUsuario());
        Assert.assertEquals(50, usuarios.get(0).getPuntosUsuario());
    }

    @Test
    public void testNivelActual() throws UsuarioNoExiste, PartidaInactiva, JuegoNoExiste, PartidaActiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        List<String> nivel = this.manager.nivelActual("469823467");
        Assert.assertEquals("1",nivel.get(0));
        String idPartida = usuarios.get(0).getPartidaActual();
        Assert.assertEquals(idPartida,nivel.get(1));
    }

    @Test
    public void testPuntuacionActual() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        this.manager.inicioPartida("746736","469823467");
        int punt = this.manager.puntuacionActual("469823467");
        Assert.assertEquals(50,punt);
    }

    @Test
    public void testPasarNivel() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        this.manager.pasarNivel("469823467", 70, "16/11/2022");
        Assert.assertEquals(70,usuarios.get(0).getPuntosUsuario());
        Assert.assertEquals(2,usuarios.get(0).getNivelUsuario());
        this.manager.pasarNivel("469823467", 100, "16/11/2022");
        Assert.assertEquals(100,usuarios.get(0).getPuntosUsuario());
        Assert.assertEquals(3,usuarios.get(0).getNivelUsuario());
        this.manager.pasarNivel("469823467", 0, "16/11/2022");
        Assert.assertEquals(200,usuarios.get(0).getPuntosUsuario());
        Assert.assertEquals(0,usuarios.get(0).getNivelUsuario());
        Assert.assertEquals(false,usuarios.get(0).getPartida());
    }

    @Test
    public void testFinalizarPartida() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        int punt = this.manager.puntuacionActual("469823467");
        Assert.assertEquals(50,punt);
        Assert.assertEquals(1,usuarios.get(0).getNivelUsuario());
        this.manager.finalizarPartida(usuarios.get(0));
        Assert.assertEquals(0,usuarios.get(0).getPuntosUsuario());
        Assert.assertEquals(0,usuarios.get(0).getNivelUsuario());
        Assert.assertEquals(false,usuarios.get(0).getPartida());
        Assert.assertEquals("",usuarios.get(0).getPartidaActual());
    }

    @Test
    public void testListaUsuariosJuego() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        List<Juego> juegos = this.manager.listaJuegos();
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        List<Usuario> usar = this.manager.listaUsuariosJuego(juegos.get(0));
        Assert.assertEquals(usuarios.get(0),usar.get(0));
        this.manager.inicioPartida("746736","723678");
        this.manager.pasarNivel("723678",90,"17/11/2022");
        List<Usuario> usar2 = this.manager.listaUsuariosJuego(juegos.get(0));
        Assert.assertEquals("723678",usar2.get(0).getIdUsuario());
        Assert.assertEquals("469823467",usar2.get(1).getIdUsuario());

    }

    @Test
    public void testPartidasUsuarios() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        String part1 = usuarios.get(0).getPartidaActual();
        this.manager.finalizarPartida(usuarios.get(0));
        this.manager.inicioPartida("438726","469823467");
        String part2 = usuarios.get(0).getPartidaActual();
        this.manager.finalizarPartida(usuarios.get(0));
        this.manager.inicioPartida("7432678","469823467");
        String part3 = usuarios.get(0).getPartidaActual();
        List<String> partidas = this.manager.PartidasUsuario("469823467");
        Assert.assertEquals(3,partidas.size());
        Assert.assertEquals(part1,partidas.get(0));
        Assert.assertEquals(part2,partidas.get(1));
        Assert.assertEquals(part3,partidas.get(2));

    }

    @Test
    public void testActividadUsuario() throws UsuarioNoExiste, JuegoNoExiste, PartidaActiva, PartidaInactiva {
        List<Usuario> usuarios = this.manager.listaUsuarios();
        this.manager.inicioPartida("746736","469823467");
        this.manager.pasarNivel("469823467", 80, "16/11/2022");
        this.manager.pasarNivel("469823467", 110, "17/11/2022");
        List<String> actividad = this.manager.actividadUsuario("469823467","746736");
        Assert.assertEquals("2, 80, 16/11/2022", actividad.get(0));
        Assert.assertEquals("3, 110, 17/11/2022", actividad.get(1));
    }
}
