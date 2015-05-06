package controllers

import play.api._
import play.api.mvc._

import scala.slick.driver.MySQLDriver.simple._

import Tables.db
import Tables._

object Application extends Controller {
    
  // let's try alphabetizing :)

  def index = Action {
      foo()
    Ok(views.html.index("Your new application is ready."))
  }

  def forum = Action {
    Ok(views.html.forum())
  }
  
  def login = Action {
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