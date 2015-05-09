package models

import scala.slick.driver.MySQLDriver.simple._

import controllers.Tables._
import controllers.Tables.db

object Forum {
    val hiddenBoardFormInputs = List("cat_name", "cat_id", "board_id", "board_name", "myPage")
 
    val getBoardsList: List[controllers.BoardCaseClass] = {
        db.withSession {
            implicit session =>
                boards.list
        }
    }
    
    def getFormattedCategories(boardId: Int): List[controllers.CategoryCaseClass] = {
        db.withSession {
            implicit session =>
                categories.filter(x => x.boardId === boardId).list
        }
    }
    
    def getFormattedCategories2(boardId: Int, boardName: String): List[(String, controllers.CategoryCaseClass)] = {
        db.withSession {
            implicit session =>
                val cats = categories.filter(x => x.boardId === boardId).list
                cats.map{
                    c => 
                        //val title = c.catId + "~" + c.catName + "~" + c.catId + "~" + boardName
                        val title = (c.catId.toString).concat("~").concat(c.catName.toString).concat("~").concat(c.catId.toString).concat("~").concat(boardName)
                        (title, c)
                }
        }
    }
    
    
    val getBoardsCategories: List[(String, List[controllers.CategoryCaseClass])] = {
        db.withSession {
            implicit session =>
                boards.list.map {
                    board => (board.boardName, getFormattedCategories(board.boardId))
                }
        }
    }
    
    val getBoardsCategories2: List[(String, List[(String, controllers.CategoryCaseClass)])] = {
        db.withSession {
            implicit session =>
                boards.list.map {
                    board => (board.boardName, getFormattedCategories2(board.boardId, board.boardName))
                }
        }
    }
    
    
    
        
// val getPosts: List[(String, List[(String, controllers.CategoryCaseClass)])] = {
 val getPosts:scala.xml.NodeSeq = {
        val topic_id = 1;
        var nodeSeq = scala.xml.NodeSeq.Empty
        var postsPerPage = 10
        var postCount:Int = 1
        var userID = 1;
        var page = 1;
        var postRes = db.withSession {
            implicit session =>
                 posts.filter(x => x.topicId === { topic_id }).list
        }
        
        var numPages = ( postRes.length / postsPerPage)
        if(postRes.length % postsPerPage != 0)
          numPages += 1;
        
        for(P <- postRes.drop(postsPerPage * (page -1)).take(postsPerPage)){

            var userRes =  db.withSession {
              implicit session =>
                 users.filter(x => x.userId === { P.userId }).list
              }
             // println(x.toString)
            nodeSeq = nodeSeq ++		     {
              <tr>
    			 <td class="message-body"> 
    				 <div class = "row1">
    					{stringtoxml(P.postContent)}
             </div>
    			 </td>
    			 <td class="post-num"><div class="row1">#{postCount + ((page - 1) * postsPerPage)}</div></td>
    		 </tr>
    		 <tr>
             { postCount += 1 }
    			 <td colspan="2" class="edit-post">
             <span class="username-field" title ={ "" + P.userId +"" }>{userRes.head.username}</span>
                   {if(userID == P.userId)
                       <span class="edit" data-post_id ={""+P.postId +""} onclick="updatePostLabel()">edit post</span> 
                    else
                        <span class="" onclick=""> </span>} 
           </td>
    		 </tr>
            }
			  
		}
        
        nodeSeq   
    }    
    
    def stringtoxml(s:String) ={
        val ret = scala.xml.Unparsed(s)
         ret
    }
    
}