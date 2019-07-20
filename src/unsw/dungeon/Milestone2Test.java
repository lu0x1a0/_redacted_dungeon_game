package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class Milestone2Test {

	@Test
	void test() {
		Dungeon testD = new Dungeon(10, 10);
		Player player = new Player(0, 1, testD);
		FloorSwitch singleSwitch = new FloorSwitch(1,1, testD);
		GoalLeafFloorSwitch goal1 = new GoalLeafFloorSwitch();
		goal1.addFloorSwitch(singleSwitch);
		testD.addGoal(goal1);
		
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
		
		assertEquals(false, singleTreasure.isCollected());
		assertEquals(false, goal1.isComplete());
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
	
	

}
