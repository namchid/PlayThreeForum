package models

import scala.slick.driver.MySQLDriver.simple._
import scala.xml.Node

import controllers.Tables._
import controllers.Tables.db

object Profile {
    
    var lastTenPostGlobal: Seq[Node] = Nil
    
def databaseQueries(userId: Int): (String, String, Int, String, Seq[Node])  = {
    var username = ""
    var userEmail = ""
    var userPostCount = 0
    var lastPostDate = ""
    var lastTenPosts: Seq[Node] = Nil

    db.withSession {
      implicit session =>
        val filteredUsers = (users.filter(x => x.userId === userId).list)
        val correctUser = filteredUsers(0)
        val postsHistory = posts.filter(x => x.userId === userId).list

        username = correctUser.username
        userEmail = correctUser.userEmail
        userPostCount = postsHistory.size

        userPostCount match {
          case 0 =>
          case _ =>
            val lastPost = postsHistory.last
            val lastTenPostsHistory = postsHistory.takeRight(10).map(x => x.topicId).toList.reverse
            val map = topicsMap.toMap

            lastPostDate = lastPost.postDate.toString().split(" ")(0)
            lastTenPosts = lastTenPostsHistory.map(x =>
              <div class="history">{ username } posted in <a class="jump_to_topic" title={ x.toString() } href="#">{ map(x); }</a></div>).asInstanceOf[Seq[Node]]
              
              //sssh
              lastTenPostGlobal = lastTenPosts
        }

    }
    (username, userEmail, userPostCount, lastPostDate, lastTenPosts)
  }
}