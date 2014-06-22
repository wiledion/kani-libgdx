
package com.wiledion.kani.game;

import com.wiledion.kani.world.World;


/**
 * Game class
 * 
 * @author wiledion
 *
 */

public class Game {

    World world;
    Scenario scenario;
    boolean isRunning;

    Game ()   {
    }

    void init() {  
        world.init();
        scenario.load();
    }

    void loadGame(){
    }

    void saveGame(){
    }

    public void newGame(){
        world=null;
        scenario=null;
        init();
    }

    void exitGame(){
        
    }

    void gameOver(){
        
    }

    public void update(){
        scenario.update();
        world.update();
    }

   public void render()  {
        world.render();
   }

   public boolean isRunning(){
   
       return true;
   }

}
