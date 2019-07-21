package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import org.junit.jupiter.api.Test;

class Milestone2Test {

	@Test
	void test() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		testD.addEntity(player);
		testD.setPlayer(player);
		System.out.println(testD.getPlayer());
		FloorSwitch singleSwitch = new FloorSwitch(1,1, testD);
		GoalLeafFloorSwitch goal1 = new GoalLeafFloorSwitch();
		goal1.addFloorSwitch(singleSwitch);
		testD.addGoal(goal1);
		testD.addEntity(singleSwitch);
		
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal1.isComplete());
		player.moveRight(); //Null pointer exception here
		System.out.println(testD.getPlayerCoord());
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void test2() {
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
		System.out.println("Seg fault here?");
		player.moveRight(); //Null pointer exception here
		System.out.println(testD.getPlayerCoord());
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void test3() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		
		GoalCompositeComponent andGoal = new GoalCompositeComponent(true);
		
		andGoal.addChild(goal1);
		andGoal.addChild(goal2);
		testD.addGoal(andGoal);
		testD.addEntity(player);
		testD.setPlayer(player);
		testD.addEntity(singleSwitch);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		assertEquals(false, singleSwitch.isPressed());
		assertEquals(false, goal2.isComplete());
		assertEquals(false, andGoal.isComplete());
		player.moveRight(); //Null pointer exception here
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
		assertEquals(false, andGoal.isComplete());
		//Treasure is collected but final goal is still incomplete
		
		
		player.moveRight(); //Null pointer exception here
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal2.isComplete());
		assertEquals(true, andGoal.isComplete());
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
        //System.out.println(player.getX());
        assertEquals(player.getX(),1);
		player.moveUp();
        assertEquals(player.getY(),1);
        player.moveUp();
        assertEquals(player.getY(),1);
        
	}
	@Test
	void testCollectNotGoalstuff() {
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Sword s1 = new Sword(0,0,testD);
		Sword s2 = new Sword(0,1,testD);
		Bomb b1 = new Bomb(0,2,testD);
		Bomb b2 = new Bomb(1,0,testD);
		Key k1 = new Key(2,0,testD,0);
		Key k2 = new Key(2,1,testD,1);
		Treasure t1 = new Treasure(2,2,testD);
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(t1);
		testD.addEntity(player);
		player.moveLeft();
        assertEquals(player.countBombs(),1);
		player.moveUp();
		assertEquals(player.hasSword(),true);
		assertEquals(testD.getMap().containsKey(new Coord(0,1)),true);
		assertEquals(testD.getMap().get(new Coord(0,1)).contains(s1),false);
		player.moveUp();
		assertEquals(testD.getMap().containsKey(new Coord(0,0)),true);
		assertEquals(testD.getMap().get(new Coord(0,0)).contains(s1),true);
		player.moveRight();
		assertEquals(testD.getMap().containsKey(new Coord(0,0)),true);
		assertEquals(player.countBombs(),2);
		player.moveRight();
		assertEquals(player.getKey(),k1);
		player.moveDown();
		assertEquals(player.getKey(),k1);
		assertEquals(testD.getMap().get(new Coord(2,1)).contains(k2),true);
		player.moveDown();
		assertEquals(testD.getMap().get(new Coord(2,2)).contains(t1),false);
	}
	@Test
	void testboulder(){
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
	void testbombblow(){
		Dungeon testD = new Dungeon(4, 4);
		Player player = new Player(2,2, testD);
		Boulder b1 = new Boulder(1,1,testD);
		Boulder b2 = new Boulder(1,3,testD);
		
		Bomb bo1 = new Bomb(1,2,testD);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(bo1);
		
		testD.addEntity(player);
		player.moveLeft();
		player.litBomb();
		player.moveLeft();
		player.moveLeft();
		
		assertEquals(testD.getMap().get(new Coord(2,3)).contains(b1),false);
		assertEquals(testD.getMap().get(new Coord(2,3)).contains(b2),false);
	}
	@Test
	void testunlockDoor(){
		Dungeon testD = new Dungeon(3, 4);
		Player player = new Player(1, 2, testD);
		Sword s1 = new Sword(0,0,testD);
		Sword s2 = new Sword(0,1,testD);
		Bomb b1 = new Bomb(0,2,testD);
		Bomb b2 = new Bomb(1,0,testD);
		Key k1 = new Key(2,0,testD,0);
		Key k2 = new Key(2,1,testD,1);
		Treasure t1 = new Treasure(2,2,testD);
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(t1);
		testD.addEntity(player);
	}
	@Test
	void testEnemyKill(){
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Sword s1 = new Sword(0,0,testD);
		Sword s2 = new Sword(0,1,testD);
		Bomb b1 = new Bomb(0,2,testD);
		Bomb b2 = new Bomb(1,0,testD);
		Key k1 = new Key(2,0,testD,0);
		Key k2 = new Key(2,1,testD,1);
		Treasure t1 = new Treasure(2,2,testD);
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(t1);
		testD.addEntity(player);
	}
	@Test
	void testKillEnemy(){
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Sword s1 = new Sword(0,0,testD);
		Sword s2 = new Sword(0,1,testD);
		Bomb b1 = new Bomb(0,2,testD);
		Bomb b2 = new Bomb(1,0,testD);
		Key k1 = new Key(2,0,testD,0);
		Key k2 = new Key(2,1,testD,1);
		Treasure t1 = new Treasure(2,2,testD);
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(t1);
		testD.addEntity(player);
	}
	@Test
	void testfloorSwitch(){
		Dungeon testD = new Dungeon(3, 3);
		Player player = new Player(1, 2, testD);
		Sword s1 = new Sword(0,0,testD);
		Sword s2 = new Sword(0,1,testD);
		Bomb b1 = new Bomb(0,2,testD);
		Bomb b2 = new Bomb(1,0,testD);
		Key k1 = new Key(2,0,testD,0);
		Key k2 = new Key(2,1,testD,1);
		Treasure t1 = new Treasure(2,2,testD);
		testD.addEntity(s1);
		testD.addEntity(s2);
		testD.addEntity(b1);
		testD.addEntity(b2);
		testD.addEntity(k1);
		testD.addEntity(k2);
		testD.addEntity(t1);
		testD.addEntity(player);
	}
	
}
