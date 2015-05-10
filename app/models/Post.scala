package models


import scala.xml.Node
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Timestamp
import scala.slick.lifted.{ ProvenShape, ForeignKeyQuery }
import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.driver.MySQLDriver.simple._

import controllers.Tables._
import controllers.Tables.db



object Post {
 // val passeds
  
  /*
    implicit val getUser2 = GetResult(r => User2(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))
    implicit val getBoard2 = GetResult(r => Board2(r.<<, r.<<, r.<<))
    implicit val getCat2 = GetResult(r => Cat2(r.<<, r.<<, r.<<, r.<<))
*/
  
 //   implicit val getPost2 = GetResult(r => Post2(r.<<, r.<<, r.<<, r.<<, r.<<))


  def get(){
 
  }
    
  def Add(topic_id:String, user_id:String, content:String, pageP:String ) = {
     var topicID = topic_id.toInt
     var userID = user_id.toInt
     var page = pageP.toInt
     <div>content: {content} </div>
     
   //  posts. (-99, content,"date here", topicID, userID )
    
     val timeStamp = null.asInstanceOf[java.sql.Timestamp]
     
    db.withSession{
      implicit session =>
        posts.+= (controllers.PostCaseClass(-99, content,timeStamp, topicID, userID ))
    }
  
     
  //servletContext.getDispatcher("/bar").forward(request, response)   
     
//  var fuckingString = "/posts?topic_id="+topicID+"&page="+page+""
//  get("/posts?topic_id="+topicID+"&page="+page+"")
   //  println("str: "+ str+"\n db: "+ db +"\nuserID: "+userID)
//  edu.cs.trinity.app.Posts.SetPassed(str,db, userID)
 // SetPassed(str,db, user_id)
  //ThreeForumServlet.ThreeForumServlet.get("/posts?topic_id=1&page=2")
  //      servletContext.getDispatcher("/bar").forward(request, response)
  
     // fuck it all
  val fuckyouramp = '&'
  <script type="text/javascript">
	//	alert('fuck it all ' + '{fuckyouramp}');
  var fuckitall = '/post?topic_id={topicID}';
	fuckitall += '{fuckyouramp}page={page}';
  window.location=fuckitall;
	</script>
  <script type="text/javascript" src="js/addPostHelper.js"></script>

}  
    
    
      
  def SetPassed(postID:String, topicID:String, pageP:String, user_ID:String): Seq[Node] = {
    var userID = user_ID
    
    var topic_name:String = "-1";   
    var post_id = postID.toInt
    var topic_id = topicID.toInt
    var page = pageP.toInt
        if(page == -1)
          page = 1
    
  
    var startingLimit = 0;
    var postsPerPage = 10;
    var postCount:Int = 1;
    
    var nodeSeq = scala.xml.NodeSeq.Empty //null.asInstanceOf[Seq[scala.xml.Node]]
  //  var nodeSeq = Nil

    var postRes = db.withSession {
      implicit session =>
        posts.filter(x => x.topicId === { topic_id }).list
    }
    
    var joinedStr:String =  ""
    
    db.withSession{
      implicit session =>
        var slickJoin = for {
          t <- topics if t.topicId === topic_id
          c <- categories if t.catId === c.catId
          b <- boards if c.boardId === b.boardId
        }       yield LiteralColumn ("") ++ b.boardName ++ "~.~" ++ c.catName ++ "~.~" ++ c.catId.asColumnOf[String]
//        slickJoin foreach println
        slickJoin.foreach{ e => 
          joinedStr = joinedStr + e
          }
    }
        
    
    var numPages = ( postRes.length / postsPerPage)
    if(postRes.length % postsPerPage != 0)
      numPages += 1;
        
    for(P <- postRes.drop(postsPerPage * (page -1)).take(postsPerPage)){
	 var userRes =
	    db.withSession {
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
    
    
    
    
    
    <html>
     <head>
        <meta charset="utf-8"/>
        <title>Forum</title>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
		<link href='http://fonts.googleapis.com/css?family=Monoton' rel='stylesheet' type='text/css'/>
        <link href='http://fonts.googleapis.com/css?family=Cutive+Mono' rel='stylesheet' type='text/css'/>
        <link href='http://fonts.googleapis.com/css?family=Audiowide' rel='stylesheet' type='text/css'/>
        <link href="assets/stylesheets/styles.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="/assets/stylesheets/forum-styles.css"/>
        <link rel="stylesheet" type="text/css" href="/assets/stylesheets/about-styles.css"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
        <script type="text/javascript" src="assets/javascripts/setUpNavigation-forum.js"></script>
        <script type="text/javascript" src="assets/javascripts/posts.js"></script>
        <script type="text/javascript" src="assets/javascripts/inner-matrix.js"></script>
        <script type="text/javascript" src="assets/javascripts/addPost.js"></script>
        <script type="text/javascript" src="assets/tinymce/js/tinymce/tinymce.min.js"></script>
        <script type="text/javascript">
					//alert('w');
					console.log('hmmm');
            tinymce.init({{
                selector: 'textarea', 
                plugins: [
                    'advlist autolink lists link image charmap print preview anchor',
                    'searchreplace visualblocks code fullscreen',
                    'insertdatetime media table contextmenu paste'
                ],
                toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image'
            }});
        </script>
    </head>
   <body>
    <div id="navBar"></div>
    <div id="mainContainer">

        <div class="subtitle"><?php echo $topic_name ?></div>
        <ul class="breadcrumb">
            <li><a href="forum">Board: {joinedStr.split("~.~")(1) }</a></li>
            <li><a href="#" class="toCategory" onClick={"onCatClick("+joinedStr.split("~.~")(2)+")"}  >Category: { joinedStr.split("~.~")(0)}</a></li>
            <li><a>Posts</a></li>
        </ul>
       <!--  
        EchoForm("post.php","pagesForm",$pagenames,$pagevals);
        EchoForm("profile.php","profileForm", array("user_id"), array("x"));
        EchoForm("category.php","categoryForm", array("cat_id","cat_name","board_id","board_name","myPage"), array($cat_id, $cat_name, $board_id, $board_name,"1"));
      ?>
			-->
			<div id="pageNav" data-last_page={""+numPages+""}>
			    <ul>
			    	<li id="currentPageNum" title={""+page+""}>page {page} of {numPages}</li> 
						 {for(n <- pageLinks(numPages, topic_id, page))
						    yield n }
         	</ul>
		    </div>
        <div id="hiddenPost" style ="display:none;"><h2>shouldn't see this</h2>  </div>
        <div id="posts">
          
   <table class="posts-table">
			{for(ns <- nodeSeq) 
			  yield ns
			}
	 </table>
                
        </div>
        <h3 id="new-post-label">New Post</h3>
        <div id="post-form-area">
            <form action="/AddPost" method="post" id="post-form">
               {echoHiddenInput("topic_id_post",topic_id.toString())}
               {echoHiddenInput("user_id_post",userID.toString())}
               {echoHiddenInput("formatted_input","    ")}
               {echoHiddenInput("page_post",page.toString())}
                
                <textarea rows="3" type="text" name="new-post" id="new-topic-post" form="post-form" placeholder="say something"></textarea>
                <button name="submit-topic" class="submit-post-button" id="post_submit_bttn" data-post_id="-1">No Takebacks, OK?</button>
            </form>
        </div>
    </div>
    <canvas id="c"></canvas>
    </body>
    </html>

  }
  
    def echoHiddenInput(name:String, inp:String) = {
      var ret:scala.xml.Node = {
        <input id={""+name+""} type="hidden" style="display:none" value={""+inp+""} name={""+name+""}></input>
      } // god that {""+a+""} is so fucking stupid. {a} is not allowed, "{a}" doesnt work, {"\""+a+"\""} -> ""a""  
      ret
    }
  
    def pageLinks(numPages:Int, topicID:Int, page:Int) = {
     // var ret:String = ""
      var fu:scala.xml.NodeSeq = scala.xml.NodeSeq.Empty
       for(i <- 1 to numPages){
         if(i != page)
   	       fu = fu  ++ { <li class ="page" title = {""+i+""} value={""+i+""} onclick={"onPageClick("+i+","+topicID+")"}> <a class="current" href="#">{i}</a></li> }
         else
           fu = fu  ++ { <li class ="page" title = {""+i+""} value={""+i+""} onclick={"onPageClick("+i+","+topicID+")"}> <a class="current" href="#" style="color:gold !important;">{i}</a></li> }

        }
      fu
    }
  
    def stringtoxml(s:String) ={
        val ret = scala.xml.Unparsed(s)
         ret
    }
    
    def myG(s:Option[String]) ={
     s match {
         case Some(n) => n
         case None => "-1"
      }
    }
}  