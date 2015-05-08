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
                        val title = c.catId + "~" + c.catName + "~" + c.catId + "~" + boardName
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
    
    
}