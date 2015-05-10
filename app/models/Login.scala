package models

import scala.slick.driver.MySQLDriver.simple._

import controllers.Tables._
import controllers.Tables.db

object Login {

  def checkUserLogin(username: String, password: String): Int = {
    db.withSession {
      implicit session =>
        val filteredUsers = users.filter(x => x.username === username && x.userPass === password).list
        if (filteredUsers.length < 1) return -1
        filteredUsers(0).userId
    }
  }

  def canCreatedNewUser(username: String): Boolean = {
    db.withSession {
      implicit session =>
        val filteredUsers = users.filter(x => x.username === username).list
        if (filteredUsers.length > 0) return false
        true

    }
  }

  def createNewUser(email: String, username: String, password: String): Int = {
    db.withSession {
      implicit session =>
        users.map(u => (u.userEmail, u.userLevel, u.username, u.userPass)).insert(email, 1, username, password)
        val newUserCreated = users.filter(x => x.username === username).list
        return newUserCreated(0).userId
    }
  }

}