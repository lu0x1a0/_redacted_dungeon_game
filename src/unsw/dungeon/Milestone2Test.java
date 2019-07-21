package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;


import org.junit.jupiter.api.Test;

import javafx.application.Platform;

class Milestone2Test {

	@Test
	void floorSwitchGoalTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		testD.addEntity(player);
		testD.setPlayer(player);

		FloorSwitch singleSwitch = new FloorSwitch(1,1, testD);
		GoalLeafFloorSwitch goal1 = new GoalLeafFloorSwitch();
		goal1.addFloorSwitch(singleSwitch);
		testD.addGoal(goal1);
		testD.addEntity(singleSwitch);
		
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal1.isComplete());
		
		player.moveRight(); 
		
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void treasureCollectGoalTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		Treasure singleTreasure = new Treasure(1,1, testD);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD.addGoal(goal1);
		testD.addEntity(player);
		testD.setPlayer(player);
		testD.addEntity(singleTreasure);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		
		player.moveRight(); 
		
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void exitGoalTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		Exit exit = new Exit(1,1, testD);
		GoalLeafExit goal1 = new GoalLeafExit(exit);

		testD.addGoal(goal1);
		testD.addEntity(player);
		testD.setPlayer(player);
		testD.addEntity(exit);
		
		assertEquals(false, exit.getPlayerIsTouching());
		assertEquals(false, goal1.isComplete());
		
		player.moveRight(); 
		
		assertEquals(true, exit.getPlayerIsTouching());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void monsterGoalTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		
		Sword sword = new Sword(1,1,testD);
		Enemy monster = new Enemy(2,1, testD);
		
		GoalLeafEnemiesKilled goal1 = new GoalLeafEnemiesKilled();
		goal1.addEnemy(monster);

		testD.addEntity(monster);
		testD.addEntity(sword);
		testD.addEntity(player);
		testD.setPlayer(player);
		testD.addGoal(goal1);
		
		assertEquals(false, goal1.isComplete());
		assertEquals(false, player.hasSword());
		assertEquals(true, monster.isAlive());
		
		player.moveRight();
		
		assertEquals(false, goal1.isComplete());
		assertEquals(true, player.hasSword());
		assertEquals(true, monster.isAlive());
		
		player.waveSword();
		
		assertEquals(true, goal1.isComplete());
		assertEquals(true, player.hasSword());
		assertEquals(false, monster.isAlive());
		
	}
	
	@Test
	void compositeANDtest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD.addEntity(singleSwitch);
		
		GoalCompositeComponent andGoal = new GoalCompositeComponent(true);
		
		andGoal.addChild(goal1);
		andGoal.addChild(goal2);
		testD.addGoal(andGoal);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal2.isComplete());
		assertEquals(false, andGoal.isComplete());
		player.moveRight(); 
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
		assertEquals(false, andGoal.isComplete());
		//Treasure is collected but final goal is still incomplete
		
		
		player.moveRight(); 
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal2.isComplete());
		assertEquals(true, andGoal.isComplete());
	}
	

	@Test
	void compositeORtest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD.addEntity(singleSwitch);
		
		GoalCompositeComponent orGoal = new GoalCompositeComponent(false);
		
		orGoal.addChild(goal1);
		orGoal.addChild(goal2);
		testD.addGoal(orGoal);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal2.isComplete());
		assertEquals(false, orGoal.isComplete());
		player.moveRight(); 
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
		assertEquals(true, orGoal.isComplete());
		//Treasure is collected but final goal is still incomplete
		player.moveRight(); 
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal2.isComplete());
		assertEquals(true, orGoal.isComplete());
	}

	@Test
	void compositeANDtestReverse() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD.addEntity(singleSwitch);
		
		GoalCompositeComponent andGoal = new GoalCompositeComponent(true);
		
		andGoal.addChild(goal1);
		andGoal.addChild(goal2);
		testD.addGoal(andGoal);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal2.isComplete());
		assertEquals(false, andGoal.isComplete());
		player.moveRight(); 
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
		assertEquals(false, andGoal.isComplete());
		//Treasure is collected but final goal is still incomplete
				
		player.moveRight(); 
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal2.isComplete());
		assertEquals(true, andGoal.isComplete());
		//Final goal compelete
		
		
		
		//Player moves off floor switch thus making goal then incomplete
		player.moveRight();
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal2.isComplete());
		assertEquals(false, andGoal.isComplete());
	}
	
	
	
	@Test
	void collectSword() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Sword s1 = new Sword(1,1,testD);

		testD.addEntity(s1);
		testD.addEntity(player);
		testD.setPlayer(player);

		player.moveRight();
        assertEquals(player.getX(),1);
        
		assertEquals(true, player.hasSword());
		assertEquals(true, s1.isCollected());
		
	}
	
	@Test
	void multipleCollectSword() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Sword s1 = new Sword(1,1,testD);
		Sword s2 = new Sword(2,1,testD);
		
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(player);
		testD.setPlayer(player);
		assertEquals(false, player.hasSword());
		
		player.moveRight();
		
		assertEquals(true, player.hasSword());
		assertEquals(true, s1.isCollected());
		assertEquals(false, s2.isCollected());
		
		
		player.moveRight();
		
		assertEquals(true, player.hasSword());
		assertEquals(true, s1.isCollected());
		assertEquals(false, s2.isCollected());
		
	}
	
	@Test
	void collectKey() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Key k1 = new Key(1,1,testD, 0);

		testD.addEntity(k1);
		testD.addEntity(player);
		testD.setPlayer(player);
		assertEquals(false, player.hasSword());
		
		player.moveRight();
		
		assertEquals(k1, player.getKey());
		assertEquals(true, k1.isCollected());
		
	}
	
	
	@Test
	void multipleCollectKey() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Key k1 = new Key(1,1,testD, 0);
		Key k2 = new Key(2,1,testD, 0);

		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(player);
		testD.setPlayer(player);
		assertEquals(null, player.getKey());
		assertEquals(false, k1.isCollected());
		assertEquals(false, k2.isCollected());

		
		player.moveRight();
		
		assertEquals(k1, player.getKey());
		assertEquals(true, k1.isCollected());
		assertEquals(false, k2.isCollected());

		player.moveRight();
		
		assertEquals(k1, player.getKey());
		assertEquals(true, k1.isCollected());
		assertEquals(false, k2.isCollected());
		
	}

	@Test
	void collectBomb() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Bomb b1 = new Bomb(1,1,testD);

		testD.addEntity(b1);
		testD.addEntity(player);
		testD.setPlayer(player);
		assertEquals(0, player.countBombs());
		assertEquals(false, b1.isCollected());
		
		player.moveRight();
		
		assertEquals(1, player.countBombs());
		assertEquals(true, b1.isCollected());
		
	}
	
	
	@Test
	void multipleCollectBomb() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Bomb b1 = new Bomb(1,1,testD);
		Bomb b2 = new Bomb(2,1,testD);

		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(player);
		testD.setPlayer(player);
		assertEquals(0, player.countBombs());
		assertEquals(false, b1.isCollected());
		assertEquals(false, b2.isCollected());

		
		player.moveRight();
		
		assertEquals(1, player.countBombs());
		assertEquals(true, b1.isCollected());
		assertEquals(false, b2.isCollected());

		player.moveRight();
		
		assertEquals(2, player.countBombs());
		assertEquals(true, b1.isCollected());
		assertEquals(true, b2.isCollected());
		
	}
	
	@Test
	void collectTreasure() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Treasure t1 = new Treasure(1,1,testD);

		testD.addEntity(t1);
		testD.addEntity(player);
		testD.setPlayer(player);

		assertEquals(false, t1.isCollected());
		
		player.moveRight();
		
		assertEquals(true, t1.isCollected());
		
	}
	
	
	@Test
	void multipleCollectTreasure() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(0, 1, testD);
		Treasure t1 = new Treasure(1,1,testD);
		Treasure t2 = new Treasure(2,1,testD);

		testD.addEntity(t1);
		testD.addEntity(t2);
		testD.addEntity(player);
		testD.setPlayer(player);

		assertEquals(false, t1.isCollected());
		assertEquals(false, t2.isCollected());

		
		player.moveRight();
		
		assertEquals(true, t1.isCollected());
		assertEquals(false, t2.isCollected());

		player.moveRight();
		

		assertEquals(true, t1.isCollected());
		assertEquals(true, t2.isCollected());
		
	}
	
	@Test
	void testBoulder(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(0, 1, testD);
		Boulder b1 = new Boulder(1,1,testD);
		Boulder b2 = new Boulder(1,3,testD);
		
		testD.addEntity(b1);
		testD.addEntity(b2);
		
		testD.addEntity(player);
		player.moveRight();
		player.moveRight();
		player.moveUp();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveDown();
		player.moveLeft();
		player.moveLeft();
		assertEquals(testD.getMap().get(new Coord(2,3)).contains(b1),true);
		assertEquals(testD.getMap().get(new Coord(1,3)).contains(b2),true);
		assertEquals(player.getX(),3);
		assertEquals(player.getY(),3);
	}
	
	@Test
	void testWall(){
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Wall w1 = new Wall(0,0,testD);
		Wall w2 = new Wall(0,1,testD);
		Wall w3 = new Wall(0,2,testD);
		Wall w4 = new Wall(1,0,testD);
		Wall w5 = new Wall(2,0,testD);
		Wall w6 = new Wall(2,1,testD);
		Wall w7 = new Wall(2,2,testD);
		testD.addEntity(w1);
		testD.addEntity(w2);
		testD.addEntity(w3);
		testD.addEntity(w4);
		testD.addEntity(w5);
		testD.addEntity(w6);
		testD.addEntity(w7);
		testD.addEntity(player);
        player.moveRight();
        assertEquals(player.getX(),1);
		player.moveUp();
        assertEquals(player.getY(),1);
        player.moveUp();
        assertEquals(player.getY(),1);
        
	}
	
	@Test
	void testBombBlow(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(2,2, testD);
		Boulder b1 = new Boulder(1,1,testD);
		Boulder b2 = new Boulder(1,3,testD);
		testD.setPlayer(player);
		Bomb bo1 = new Bomb(1,2,testD);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(bo1);
		
		testD.addEntity(player);
		player.moveLeft();
		player.litBomb();
		player.moveRight();
		player.moveRight();
		
		Timer timer= new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
		            @Override public void run() {
		        		assertEquals(testD.getMap().containsKey(new Coord(1,1)),false);
		        		assertEquals(testD.getMap().containsKey(new Coord(1,3)),false);
		        		assertEquals(testD.getMap().containsKey(new Coord(3,3)),true);
						timer.cancel();
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	@Test
	void testbombsuicide(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(2,2, testD);
		Boulder b1 = new Boulder(1,1,testD);
		Boulder b2 = new Boulder(1,3,testD);
		testD.setPlayer(player);
		Bomb bo1 = new Bomb(1,2,testD);

		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(bo1);
		
		testD.addEntity(player);
		player.moveLeft();
		player.litBomb();
		
		Timer timer= new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
		            @Override public void run() {
		        		assertEquals(testD.getMap().containsKey(new Coord(1,1)),false);
		        		assertEquals(testD.getMap().containsKey(new Coord(1,3)),false);
						timer.cancel();
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	@Test
	void testunlockDoor(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(2,2, testD);
		Key k1 = new Key(2,1,testD,1);
		Key k2 = new Key(2,3,testD,2);
		Door d1 = new Door(1,1,testD,1);
		Door d2 = new Door(1,3,testD,2);
		testD.setPlayer(player);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(d1);
		testD.addEntity(d2);
		testD.addEntity(player);
		player.moveDown();
		player.moveUp();
		player.moveLeft();
		player.moveUp();
		assertEquals(d1.ispassable(),false);
		player.moveUp();
		assertEquals(player.getY(),2);
		assertEquals(d1.ispassable(),false);
		player.moveDown();
		assertEquals(player.getY(),2);
		assertEquals(d2.ispassable(),true);
		player.moveDown();
		assertEquals(player.getY(),3);
	}

	@Test
	void testEnemyKillPlayer(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(2,2, testD);
		Enemy e = new Enemy(2,3,testD);
		testD.setPlayer(player);
		testD.addEntity(player);
		testD.addEntity(e);
		
		com.sun.javafx.application.PlatformImpl.startup(()->{});
		e.start();
		Timer timer= new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
		            @Override public void run() {
		        		assertEquals(testD.getMap().get(new Coord(2,2)).contains(player),false);
						timer.cancel();
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
		com.sun.javafx.application.PlatformImpl.exit();
	}
	
	@Test
	void bombKillEnemy(){
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Bomb b1 = new Bomb(0,2,testD);
		Enemy e = new Enemy(0,1,testD);
		testD.addEntity(b1);
		testD.addEntity(player);
		testD.addEntity(e);
		player.moveLeft();
		player.litBomb();
		player.moveRight();
		player.moveRight();
		Timer timer= new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
		            @Override public void run() {
		        		assertEquals(testD.getMap().containsKey(new Coord(0,1)),false);
						timer.cancel();
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	
	@Test
	void invincibilityTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		Potion invincibilityPotion = new Potion(1,1,testD);
		Enemy monster = new Enemy(2,1, testD);
		
		testD.addEntity(monster);
		testD.addEntity(invincibilityPotion);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false,player.hasPotion());
		
		player.moveRight();

		assertEquals(true,player.hasPotion());
		assertEquals(true, monster.isAlive());
		
		player.moveRight();
		
		assertEquals(true, player.hasPotion());
		assertEquals(false, monster.isAlive());
		
	}
	
	@Test
	void swordKillEnemy() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		Sword sword = new Sword(1,1,testD);
		Enemy monster = new Enemy(2,1, testD);
		
		testD.addEntity(monster);
		testD.addEntity(sword);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false,player.hasSword());
		
		player.moveRight();

		assertEquals(true,player.hasSword());
		assertEquals(true, monster.isAlive());
		
		player.waveSword();
		
		assertEquals(true, player.hasSword());
		assertEquals(false, monster.isAlive());
		
	}
	
	@Test
	void swordExpirationTest() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		Sword sword = new Sword(1,1,testD);
		Enemy monster = new Enemy(2,1, testD);
		
		testD.addEntity(monster);
		testD.addEntity(sword);
		testD.addEntity(player);
		testD.setPlayer(player);
		
		assertEquals(false,player.hasSword());
		
		player.moveRight();

		assertEquals(true,player.hasSword());
		assertEquals(true, monster.isAlive());
		
		player.waveSword();
		
		assertEquals(true, player.hasSword());
		assertEquals(false, monster.isAlive());
		
		Enemy monster2 = new Enemy(2,1, testD);
		testD.addEntity(monster2);
		assertEquals(true, player.hasSword());
		player.waveSword();

		assertEquals(false, monster2.isAlive());
		
		Enemy monster3 = new Enemy(2,1, testD);
		testD.addEntity(monster3);
		assertEquals(true, player.hasSword());
		player.waveSword();

		assertEquals(false, monster3.isAlive());
		
		Enemy monster4 = new Enemy(2,1, testD);
		testD.addEntity(monster4);
		assertEquals(true, player.hasSword());
		player.waveSword();

		assertEquals(false, monster4.isAlive());
		
		Enemy monster5 = new Enemy(2,1, testD);
		testD.addEntity(monster5);
		assertEquals(true, player.hasSword());
		player.waveSword();

		assertEquals(false, monster5.isAlive());
		
		assertEquals(false, player.hasSword());
		
	}
	
}
