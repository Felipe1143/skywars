package felipe221.skywars.load;

import felipe221.skywars.Main;

public class DatabaseLoad {

	public static void load() {
		//global stats
		Main.getDatabaseManager().query("USE `minecraft`");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`player_stats_team` " +
						"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`player_stats_solo` " +
				"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		//week stats
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `player_stats_solo_week` (\n" +
				"  `uuid` varchar(45) NOT NULL,\n" +
				"  `wins` int NOT NULL DEFAULT '0',\n" +
				"  `kills` int NOT NULL DEFAULT '0',\n" +
				"  `losses` int NOT NULL DEFAULT '0',\n" +
				"  `games` int NOT NULL DEFAULT '0',\n" +
				"  `arrow_hit` int NOT NULL DEFAULT '0',\n" +
				"  `block_placed` int NOT NULL DEFAULT '0',\n" +
				"  `block_broken` int NOT NULL DEFAULT '0',\n" +
				"  PRIMARY KEY (`uuid`),\n" +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `player_stats_team_week` (\n" +
				"  `uuid` varchar(45) NOT NULL,\n" +
				"  `wins` int NOT NULL DEFAULT '0',\n" +
				"  `kills` int NOT NULL DEFAULT '0',\n" +
				"  `losses` int NOT NULL DEFAULT '0',\n" +
				"  `games` int NOT NULL DEFAULT '0',\n" +
				"  `arrow_hit` int NOT NULL DEFAULT '0',\n" +
				"  `block_placed` int NOT NULL DEFAULT '0',\n" +
				"  `block_broken` int NOT NULL DEFAULT '0',\n" +
				"  PRIMARY KEY (`uuid`),\n" +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");

		//month stats
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `player_stats_solo_month` (\n" +
				"  `uuid` varchar(45) NOT NULL,\n" +
				"  `wins` int NOT NULL DEFAULT '0',\n" +
				"  `kills` int NOT NULL DEFAULT '0',\n" +
				"  `losses` int NOT NULL DEFAULT '0',\n" +
				"  `games` int NOT NULL DEFAULT '0',\n" +
				"  `arrow_hit` int NOT NULL DEFAULT '0',\n" +
				"  `block_placed` int NOT NULL DEFAULT '0',\n" +
				"  `block_broken` int NOT NULL DEFAULT '0',\n" +
				"  PRIMARY KEY (`uuid`),\n" +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `player_stats_team_month` (\n" +
				"  `uuid` varchar(45) NOT NULL,\n" +
				"  `wins` int NOT NULL DEFAULT '0',\n" +
				"  `kills` int NOT NULL DEFAULT '0',\n" +
				"  `losses` int NOT NULL DEFAULT '0',\n" +
				"  `games` int NOT NULL DEFAULT '0',\n" +
				"  `arrow_hit` int NOT NULL DEFAULT '0',\n" +
				"  `block_placed` int NOT NULL DEFAULT '0',\n" +
				"  `block_broken` int NOT NULL DEFAULT '0',\n" +
				"  PRIMARY KEY (`uuid`),\n" +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");

		//resets
		Main.getDatabaseManager().query("DELIMITER $$ " +
				"CREATE EVENT restart_month " +
				"ON SCHEDULE EVERY '1' MONTH " +
				"STARTS '2023-05-05 01:30:00' " +
				"DO " +
				"BEGIN " +
				"DROP TABLE `minecraft`.`player_stats_solo_month`;  " +
				"DROP TABLE `minecraft`.`player_stats_team_month`;  " +
				"CREATE TABLE `player_stats_solo_month` ( " +
				"  `uuid` varchar(45) NOT NULL, " +
				"  `wins` int NOT NULL DEFAULT '0', " +
				"  `kills` int NOT NULL DEFAULT '0', " +
				"  `losses` int NOT NULL DEFAULT '0', " +
				"  `games` int NOT NULL DEFAULT '0', " +
				"  `arrow_hit` int NOT NULL DEFAULT '0', " +
				"  `block_placed` int NOT NULL DEFAULT '0', " +
				"  `block_broken` int NOT NULL DEFAULT '0', " +
				"  PRIMARY KEY (`uuid`), " +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`) " +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; " +
				"CREATE TABLE `player_stats_team_month` ( " +
				"  `uuid` varchar(45) NOT NULL, " +
				"  `wins` int NOT NULL DEFAULT '0', " +
				"  `kills` int NOT NULL DEFAULT '0', " +
				"  `losses` int NOT NULL DEFAULT '0', " +
				"  `games` int NOT NULL DEFAULT '0', " +
				"  `arrow_hit` int NOT NULL DEFAULT '0', " +
				"  `block_placed` int NOT NULL DEFAULT '0', " +
				"  `block_broken` int NOT NULL DEFAULT '0', " +
				"  PRIMARY KEY (`uuid`), " +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`) " +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; " +
				" " +
				"END$$ " +
				"DELIMITER ; " +
				" " +
				"DELIMITER $$ " +
				"CREATE EVENT restart_week " +
				"ON SCHEDULE EVERY '1' WEEK " +
				"STARTS '2023-05-05 01:30:00' " +
				"DO  " +
				"BEGIN  " +
				"DROP TABLE `minecraft`.`player_stats_solo_week`;  " +
				"DROP TABLE `minecraft`.`player_stats_team_week`;  " +
				"CREATE TABLE `player_stats_solo_week` ( " +
				"  `uuid` varchar(45) NOT NULL, " +
				"  `wins` int NOT NULL DEFAULT '0', " +
				"  `kills` int NOT NULL DEFAULT '0', " +
				"  `losses` int NOT NULL DEFAULT '0', " +
				"  `games` int NOT NULL DEFAULT '0', " +
				"  `arrow_hit` int NOT NULL DEFAULT '0', " +
				"  `block_placed` int NOT NULL DEFAULT '0', " +
				"  `block_broken` int NOT NULL DEFAULT '0', " +
				"  PRIMARY KEY (`uuid`), " +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`) " +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; " +
				"CREATE TABLE `player_stats_team_week` ( " +
				"  `uuid` varchar(45) NOT NULL, " +
				"  `wins` int NOT NULL DEFAULT '0', " +
				"  `kills` int NOT NULL DEFAULT '0', " +
				"  `losses` int NOT NULL DEFAULT '0', " +
				"  `games` int NOT NULL DEFAULT '0', " +
				"  `arrow_hit` int NOT NULL DEFAULT '0', " +
				"  `block_placed` int NOT NULL DEFAULT '0', " +
				"  `block_broken` int NOT NULL DEFAULT '0', " +
				"  PRIMARY KEY (`uuid`), " +
				"  UNIQUE KEY `uuid_UNIQUE` (`uuid`) " +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; " +
				"END$$ " +
				"DELIMITER ; ");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`player_stats` (" +
				"  `uuid` VARCHAR(40) NOT NULL," +
				"  `trail` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `coins` INT NOT NULL DEFAULT 0," +
				"  `level` INT NOT NULL DEFAULT 1," +
				"  `xp` INT NOT NULL DEFAULT 0," +
				"  `win_effect` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `tematica` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `kill_effect` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  `cage_type` VARCHAR(60) NOT NULL DEFAULT 'COMUN'," +
				"  `cage_material` VARCHAR(45) NOT NULL DEFAULT 'GLASS'," +
				"  `kit` VARCHAR(45) NOT NULL DEFAULT 'NONE'," +
				"  PRIMARY KEY (`uuid`)," +
				"  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE);");
	}
}
