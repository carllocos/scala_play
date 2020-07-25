package models

import play.api.Logger
import play.api.libs.json.{Json, Reads}

case class Comment(writer: String,content: String, postId: Long)


object Comment{
  implicit val parseComment: Reads[Comment] = Json.reads[Comment]

  private var comments: Set[Comment] = Set()

  def retrieveComments(post: Post): List[Comment] =  {
    comments.filter(_.postId == post.id).toList
  }

  def addComment(writer: String, postId: Long, content:String)  = {
    comments += Comment(writer, content, postId)
    Logger.logger.debug("all comments "+ comments)
  }

}
