package models

import scala.slick.driver.MySQLDriver.simple._

import controllers.Tables._
import controllers.Tables.db

object Login {
    
    def getUserId(username: String, password: String): Int = {
        db.withSession {
            implicit session =>
                val filteredUsers = users.filter(x => x.username === username && x.userPass === password).list
                if (filteredUsers.length < 1) return -1
                filteredUsers(0).userId
        }        
    }
}