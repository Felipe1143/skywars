package felipe221.skywars.load;

import felipe221.skywars.Main;

public class DatabaseLoad {

	public static void load() {
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`players_stats_team` " +
						"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`players_stats_solo` " +
				"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`players_stats` (" +
				"  `uuid` VARCHAR(40) NOT NULL," +
				"  `trail` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `coins` INT NOT NULL DEFAULT 0," +
				"  `xp` INT NOT NULL DEFAULT 0," +
				"  `win_effect` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `tematica` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `kill_effect` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `cage_type` VARCHAR(60) NOT NULL DEFAULT 'NONE'," +
				"  `cage_material` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `kit` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  PRIMARY KEY (`uuid`)," +
				"  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE);");
	}
}
