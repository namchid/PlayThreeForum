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
    
    var screwItSessionResults = Map[String, Any]()

  def index = Action {
    foo()
    Ok(views.html.index("Your new application is ready."))
  }

  def forum = Action {
    Ok(views.html.forum())
  }
  
  def login = Action {
    Ok(views.html.loginPage()).withNewSession
  }
  
  def checkLogin = Action { request =>
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def password = request.body.asFormUrlEncoded.get("password")(0)
    
    if(username == "" || password == "") {
        Redirect("/login")
    } else {
        val userId = models.Login.getUserId(username, password)
        if(userId < 0) {
            Redirect("/login")
        }
        else {
            Ok("Ok").withSession(
                "userId" -> userId.toString
            )            
        }
    }
  }
  
  
  
  def newUser = Action {
    Ok(views.html.index("todo"))
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