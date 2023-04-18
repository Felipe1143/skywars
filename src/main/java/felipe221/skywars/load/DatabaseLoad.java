package felipe221.skywars.load;

import felipe221.skywars.Main;

public class DatabaseLoad {

	public static void load() {
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXIST `minecraft`.`player_stats_team` " +
						"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXIST `minecraft`.`player_stats_solo` " +
				"(`uuid` VARCHAR(50) NOT NULL,`wins` " +
				"INT NOT NULL DEFAULT 0,`kills` INT NOT NULL DEFAULT 0,`losses`" +
				" INT NOT NULL DEFAULT 0,`games` INT NOT NULL DEFAULT 0,`arrow_hit` " +
				"INT NOT NULL DEFAULT 0,`block_placed` INT NOT NULL DEFAULT 0,`block_broken` " +
				"INT NOT NULL DEFAULT 0, PRIMARY KEY (`uuid`), UNIQUE INDEX `uuid_UNIQUE` " +
				"(`uuid` ASC) VISIBLE);");

		//RANKED TABLE SOLO
		Main.getDatabaseManager().query("CREATE TABLE `minecraft`.`ranked_solo` ("
				+ "  `username` VARCHAR(10) NOT NULL,"
				+ "  `kills` VARCHAR(45) NOT NULL,"
				+ "  `wins` VARCHAR(45) NOT NULL,"
				+ "  `games` VARCHAR(45) NOT NULL,"
				+ "  PRIMARY KEY (`username`),"
				+ "  CONSTRAINT `username`"
				+ "    FOREIGN KEY (`username`)"
				+ "    REFERENCES `minecraft`.`players` (`username`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION);"
				+ "");

		//RANKED TABLE TEAM
		Main.getDatabaseManager().query("CREATE TABLE `minecraft`.`ranked_team` ("
				+ "  `username` VARCHAR(10) NOT NULL,"
				+ "  `kills` VARCHAR(45) NOT NULL,"
				+ "  `wins` VARCHAR(45) NOT NULL,"
				+ "  `games` VARCHAR(45) NOT NULL,"
				+ "  PRIMARY KEY (`username`),"
				+ "  CONSTRAINT `username`"
				+ "    FOREIGN KEY (`username`)"
				+ "    REFERENCES `minecraft`.`players` (`username`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION);"
				+ "");

		//SOLO
		Main.getDatabaseManager().query("CREATE TABLE `minecraft`.`solo` ("
				+ "  `username` VARCHAR(10) NOT NULL,"
				+ "  `kills` VARCHAR(45) NOT NULL,"
				+ "  `wins` VARCHAR(45) NOT NULL,"
				+ "  `games` VARCHAR(45) NOT NULL,"
				+ "  PRIMARY KEY (`username`),"
				+ "  CONSTRAINT `username`"
				+ "    FOREIGN KEY (`username`)"
				+ "    REFERENCES `minecraft`.`players` (`username`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION);"
				+ "");

		//TEAM
		Main.getDatabaseManager().query("CREATE TABLE `minecraft`.`ranked_solo` ("
				+ "  `username` VARCHAR(10) NOT NULL,"
				+ "  `kills` VARCHAR(45) NOT NULL,"
				+ "  `wins` VARCHAR(45) NOT NULL,"
				+ "  `games` VARCHAR(45) NOT NULL,"
				+ "  PRIMARY KEY (`username`),"
				+ "  CONSTRAINT `username`"
				+ "    FOREIGN KEY (`username`)"
				+ "    REFERENCES `minecraft`.`players` (`username`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION);"
				+ "");
	}
}
