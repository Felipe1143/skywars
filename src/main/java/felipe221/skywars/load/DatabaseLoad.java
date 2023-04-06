package felipe221.skywars.load;

import felipe221.skywars.Main;

public class DatabaseLoad {

	public static void load() {
		//USER TABLE
		Main.getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `minecraft`.`players` ("
				+ "  `username` VARCHAR(32) NOT NULL,"
				+ "  `xp` INT NOT NULL,"
				+ "  `rankedElo` INT NOT NULL,"
				+ "  `win_effect` VARCHAR(45) NOT NULL,"
				+ "  `kit` VARCHAR(45) NOT NULL,"
				+ "  `cage` VARCHAR(45) NOT NULL,"
				+ "  `ballon` VARCHAR(45) NOT NULL,"
				+ "  PRIMARY KEY (`username`))"
				+ "ENGINE = InnoDB");
		
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
