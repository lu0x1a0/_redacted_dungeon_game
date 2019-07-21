package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

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
		Dungeon testD2 = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD2);
		Treasure singleTreasure = new Treasure(1,1, testD2);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD2.addGoal(goal1);
		testD2.addEntity(player);
		testD2.setPlayer(player);
		testD2.addEntity(singleTreasure);
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
		
		player.moveRight(); 
		
		assertEquals(true, singleTreasure.isCollected());
		assertEquals(true, goal1.isComplete());
	}
	
	@Test
	void exitGoalTest() {
		Dungeon testD2 = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD2);
		Exit exit = new Exit(1,1, testD2);
		GoalLeafExit goal1 = new GoalLeafExit(exit);

		testD2.addGoal(goal1);
		testD2.addEntity(player);
		testD2.setPlayer(player);
		testD2.addEntity(exit);
		
		assertEquals(false, exit.getPlayerIsTouching());
		assertEquals(false, goal1.isComplete());
		
		player.moveRight(); 
		
		assertEquals(true, exit.getPlayerIsTouching());
		assertEquals(true, goal1.isComplete());
	}
	
	
	@Test
	void compositeANDtest() {
		Dungeon testD3 = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD3);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD3);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD3.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD3);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD3.addEntity(singleSwitch);
		
		GoalCompositeComponent andGoal = new GoalCompositeComponent(true);
		
		andGoal.addChild(goal1);
		andGoal.addChild(goal2);
		testD3.addGoal(andGoal);
		testD3.addEntity(player);
		testD3.setPlayer(player);
		
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
		Dungeon testD3 = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD3);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD3);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD3.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD3);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD3.addEntity(singleSwitch);
		
		GoalCompositeComponent orGoal = new GoalCompositeComponent(false);
		
		orGoal.addChild(goal1);
		orGoal.addChild(goal2);
		testD3.addGoal(orGoal);
		testD3.addEntity(player);
		testD3.setPlayer(player);
		
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
		
    	System.out.println(orGoal.printGoal("Parent OR goal: "));
		
		player.moveRight(); 
		assertEquals(true, singleSwitch.isPressed());
		assertEquals(true, goal2.isComplete());
		assertEquals(true, orGoal.isComplete());
	}

	@Test
	void compositeANDtestReverse() {
		Dungeon testD3 = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD3);
		
		//Treasure Goal
		Treasure singleTreasure = new Treasure(1,1, testD3);
		GoalLeafTreasureCollected goal1 = new GoalLeafTreasureCollected();
		goal1.addTreasure(singleTreasure);
		testD3.addEntity(singleTreasure);
		
		//Floor switch goal
		FloorSwitch singleSwitch = new FloorSwitch(2,1, testD3);
		GoalLeafFloorSwitch goal2 = new GoalLeafFloorSwitch();
		goal2.addFloorSwitch(singleSwitch);
		testD3.addEntity(singleSwitch);
		
		GoalCompositeComponent andGoal = new GoalCompositeComponent(true);
		
		andGoal.addChild(goal1);
		andGoal.addChild(goal2);
		testD3.addGoal(andGoal);
		testD3.addEntity(player);
		testD3.setPlayer(player);
		
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
	
	
}
