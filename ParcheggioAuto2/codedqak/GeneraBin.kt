import mapRoomKotlin.Direction

fun main() {
	//itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN,6,0 )
/*	
	itunibo.planner.plannerUtil.loadRoomMap("parkingMap")
	itunibo.planner.plannerUtil.showMap()
	itunibo.planner.plannerUtil.setWallDown(1,4)
	itunibo.planner.plannerUtil.setWallRight(1,5)
	itunibo.planner.plannerUtil.setWallDown(2,4)
	itunibo.planner.plannerUtil.setWallRight(2,5)
	itunibo.planner.plannerUtil.setWallDown(3,4)
	itunibo.planner.plannerUtil.setWallRight(3,5)
	itunibo.planner.plannerUtil.setWallDown(4,4)
	itunibo.planner.plannerUtil.setWallRight(4,5)
	itunibo.planner.plannerUtil.setWallDown(5,4)
	itunibo.planner.plannerUtil.setWallRight(5,5)
	itunibo.planner.plannerUtil.setWallDown(6,4)
	itunibo.planner.plannerUtil.setWallDown(7,4)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 2,2)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 2,1)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 2,0)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 3,2)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 3,1)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 3,0)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 2,2)
	itunibo.planner.plannerUtil.setObstacleWall(Direction.DOWN, 2,1)

	itunibo.planner.plannerUtil.setActionMoveSequence()
	//itunibo.planner.plannerUtil.updateMap("l")
	println("STAMPA")
	itunibo.planner.plannerUtil.showMap()
	itunibo.planner.plannerUtil.saveRoomMap("mappaParcheggio2")
 */
	var matriceParcheggi = arrayOf(intArrayOf(0, 0), intArrayOf(0, 0), intArrayOf(0, 0))
	var ricevuta = ""
	var stop= false
	for (i in 0..2) {
		for (j in 0..1) {
			if (matriceParcheggi[i][j] == 0 && !stop) {
				ricevuta += i
				ricevuta += j
				stop=true
			}
		}
		println()
	}
	println(ricevuta)
}