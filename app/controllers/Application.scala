package controllers

import play.api._
import play.api.mvc._

import scala.slick.driver.MySQLDriver.simple._
import play.api.data.Form
import play.api.data.Forms.tuple
import play.api.data.Forms.text
import scala.xml.Node
import play.api.templates.Html
import Tables.db
import Tables._

object Application extends Controller {
    
    var screwItSessionResults = Map[String, Any]()
    var stuff: Seq[Node] = Nil
    var catStuff: Seq[Node] = Nil
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
  
  def post(topicid:Option[String], pageP:Option[String], postid:Option[String]) = Action { request =>
//    Ok(views.html.post())
    def userid = request.session("userId")
//    def userid = request.body.asFormUrlEncoded.get("user_id")(0)
    def post_id = postid.getOrElse("-1")
    def topic_id = topicid.getOrElse("-1")
    def page =  pageP.getOrElse("-1")

    //Ok(models.Post.SetPassed(post_id, topic_id, page, userid))
     stuff = models.Post.SetPassed(post_id, topic_id, page, userid)
    Ok(views.html.post())
    }
    
  def category( catID:Option[String] ) = Action { request =>
        def userid = request.session("userId")
        def cat_id = catID.getOrElse("-1")

     catStuff = models.Category.GoCategories(userid, cat_id)
     Ok(views.html.category())

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
            // Ok("Ok" + userId).withSession(
            //     "userId" -> userId.toString
            // )  
            Redirect("/forum").withSession(
                "userId" -> userId.toString
            )
            //println(@session("userId"))
        }
    }
  }
  
  def createNewUser = Action { request =>
    def email = request.body.asFormUrlEncoded.get("email")(0)
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def password1 = request.body.asFormUrlEncoded.get("password1")(0)
    def password2 = request.body.asFormUrlEncoded.get("password2")(0)
     
     
    if(email == "" || username == "" || password1 == "" || password2 == "" || (password1 != password2)) {
        Redirect("/login")
    } else {
        if(models.Login.canCreatedNewUser(username)) {
            val newUserId = models.Login.createNewUser(email, username, password1)
            val okString = "New user created with userId: " + newUserId
            println("\n" + okString)
            Redirect("/forum")
            //Ok(views.html.index(okString)) //the eff. why can't I do .withSession after this? todo later
        } else {
            Redirect("/login")
        }
    }
  }
  
  
  def newUser = Action {
    Ok(views.html.newUser())
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