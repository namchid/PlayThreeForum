package controllers

import play.api._
import play.api.mvc._

import scala.slick.driver.MySQLDriver.simple._
import play.api.data.Form
import play.api.data.Forms.tuple
import play.api.data.Forms.text

import Tables.db
import Tables._

object Application extends Controller {

  //login functionality
  def login = Action { implicit request =>
    request.session.get("userId") match {
      case None =>
        Ok(views.html.loginPage()).withNewSession
      case _ =>
        Redirect("/forum")
    }
  }

  def checkLogin = Action { request =>
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def password = request.body.asFormUrlEncoded.get("password")(0)

    if (username == "" || password == "") {
      Redirect("/formErrors")
    } else {
      val userId = models.Login.checkUserLogin(username, password)
      if (userId < 0) Redirect("/formErrors")
      else {
        Redirect("/forum").withSession(
          "userId" -> userId.toString,
          "username" -> username)
      }
    }
  }

  def createNewUser = Action { request =>
    def email = request.body.asFormUrlEncoded.get("email")(0)
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def password1 = request.body.asFormUrlEncoded.get("password1")(0)
    def password2 = request.body.asFormUrlEncoded.get("password2")(0)

    if (email == "" || username == "" || password1 == "" || password2 == "" || (password1 != password2)) {
      Redirect("/formErrors")
    } else {
      if (models.Login.canCreatedNewUser(username)) {
        val newUserId = models.Login.createNewUser(email, username, password1)
        request.session + ("userId" -> newUserId.toString)
        request.session + ("username" -> username)
        Redirect("/forum")
      } else {
        Redirect("/formErrors")
      }
    }
  }
  
  def formErrors = Action {
    Ok(views.html.loginError())
  }

  // forum - boards

  def forum = Action { request =>
    request.session.get("userId") match {
        case None =>
            Redirect("/")
        case _ =>
            Ok(views.html.forum())   
    }
  }

  def post = Action {
    Ok(views.html.post())
  }

  def newUser = Action {
    Ok(views.html.newUser())
  }

  def logout = Action {
    Ok(views.html.loginPage()).withNewSession
  }

  // example custom page
  def namchi = Action {
    Ok(views.html.namchi("Hi"))
  }

  // example database
  def foo() {
    db.withSession {
      implicit session =>
        val usersList = users.list
        println("users:")
        usersList.foreach(println)

    }
  }

}