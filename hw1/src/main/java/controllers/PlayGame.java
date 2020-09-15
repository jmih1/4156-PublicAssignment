package controllers;

import io.javalin.Javalin;
import java.io.IOException;
import java.util.Queue;
import models.GameBoard;
import models.Message;
import models.Move;
import models.Player;
import org.eclipse.jetty.websocket.api.Session;

import com.google.gson.Gson;

class PlayGame {

  private static final int PORT_NUMBER = 8080;

  private static Javalin app;
  private static char type;
  private static GameBoard gameboard;
  private static Message message;
  private static Move move;
  private static Player player1;
  private static Player player2;
  

  /** Main method of the application.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {

    app = Javalin.create(config -> {
      config.addStaticFiles("/public");
    }).start(PORT_NUMBER);
    
    gameboard = new GameBoard();
    player1 = new Player();
    

    // Test Echo Server
    app.post("/echo", ctx -> {
      ctx.result(ctx.body());
    });
    
    //app.get("/test", ctx -> ctx.result("Hello World"));
    
    app.get("/newgame", ctx -> ctx.redirect("/tictactoe.html"));
    
    
    app.post("/startgame", ctx -> {
      String body = ctx.body();
      type = body.charAt(body.length() - 1);
      player1.setType(type);
      player1.setId(1);
      gameboard.setP1(player1);
      gameboard.setGameStarted(false);
      gameboard.setTurn(1);
      gameboard.setBoardState(new char[][] {{'0', '0', '0'}, {'0', '0', '0'}, {'0', '0', '0'}});
      gameboard.setWinner(0);
      gameboard.setDraw(false);
      Gson gson = new Gson();
      String gamejson = gson.toJson(gameboard);
      ctx.result(gamejson);
    });
  
    /**
     * Please add your end points here.
     * 
     * 
     * 
     * 
     * Please add your end points here.
     * 
     * 
     * 
     * 
     * Please add your end points here.
     * 
     * 
     * 
     * 
     * Please add your end points here.
     * 
     */

    // Web sockets - DO NOT DELETE or CHANGE
    app.ws("/gameboard", new UiWebSocket());
  }

  /** Send message to all players.
   * @param gameBoardJson Gameboard JSON
   * @throws IOException Websocket message send IO Exception
   */
  private static void sendGameBoardToAllPlayers(final String gameBoardJson) {
    Queue<Session> sessions = UiWebSocket.getSessions();
    for (Session sessionPlayer : sessions) {
      try {
        sessionPlayer.getRemote().sendString(gameBoardJson);
      } catch (IOException e) {
        // Add logger here
    	  
      }
    }
  }

  public static void stop() {
    app.stop();
  }
}
