package controllers

import play.api._
import play.api.mvc._
// import scala.slick.driver.MySQLDriver.simple._
// import java.sql.Timestamp

// case class UserCaseClass(
//   userId: Int,
//   username: String,
//   userPass: String,
//   userEmail: String,
//   userDate: Timestamp,
//   userLevel: Int)
  
// class User(tag: Tag) extends Table[UserCaseClass](tag, "users") {
//   def userId = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
//   def username = column[String]("user_name")
//   def userPass = column[String]("user_pass")
//   def userEmail = column[String]("user_email")
//   def userDate = column[Timestamp]("user_date")
//   def userLevel = column[Int]("user_level")

//   def * = (userId, username, userPass, userEmail, userDate, userLevel) <> ((UserCaseClass.apply _).tupled, (UserCaseClass).unapply)
// }

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def namchi = Action {
      Ok(views.html.namchi("Hi"))
  }
  
//   val db = Database.forURL("jdbc:mysql://localhost/bpauls", user="bpauls", password="0742985", driver="com.mysql.jdbc.Driver")
//   db.withSession {
//       implicit session =>
//         val users = TableQuery[User].list
//         println("users:")
//         users.foreach(println)
//   }
  
}