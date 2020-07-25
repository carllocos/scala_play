
package controllers

import forms.PostForm
import javax.inject._
import models.{Comment, Post}
import play.api.libs.json.Json
import play.api.mvc._
import models.Comment._
import org.joda.time.DateTime

import scala.language.postfixOps

@Singleton
class PostsController @Inject()(cc: MessagesControllerComponents)
  extends MessagesAbstractController(cc)
{


  def postDetails(postId: Long) = Action { implicit request: Request[AnyContent] =>

    Post.findById(postId).map { post =>
      val comments = Comment.retrieveComments(post)
      Ok(views.html.postDetails(post, comments))
    }.getOrElse(NotFound)
  }


  def postsByDate() = Action { implicit request: Request[AnyContent] =>
    val posts = Post.findAll
    Ok(views.html.postsDetailsBriefView(posts))
  }

  def postsByVote() = Action { implicit request: Request[AnyContent] =>
    val posts = Post.postsByVote
    Ok(views.html.postsDetailsBriefView(posts))
  }


  def addComment() = Action(parse.json) { implicit request =>
    if(request.session.get("user").isEmpty){
      Ok(Json.obj("is_executed" -> false, "error_msg" -> "Authenticate first"))
    }else{

      request.body.validate[Comment].map{
        case Comment(writer, content, postId) =>

          if(Post.postExist(postId)){
            Comment.addComment(writer, postId, content)
            val jsonCom = Json.obj("writer" -> writer, "content" -> content)
            Ok(Json.obj("is_executed" -> true, "comment" ->jsonCom))
          }
          else{
            NotFound(Json.obj("is_executed"-> false, "error_msg" -> "Post Not Found"))
          }
      }.recoverTotal{
        _ => Ok(Json.obj("is_executed" -> false, "error_msg" -> "invalid json"))
      }
    }

  }


  def votePost(like: Boolean, postId: Long) = Action { request =>
    if(request.session.get("user").isDefined){
      Post.findById(postId).map { post =>

        if (like)
          post.increaseLikes()
        else
          post.increaseDislikes()

        Ok(Json.toJson(post))
      }.getOrElse(NotFound(Json.obj("error" -> "Post Not Found")))
    }else{
      NotFound(Json.obj("error" -> "Authenticate first!"))
    }

  }




  def createPost() = Action { implicit request =>

    if(request.session.get("user").isDefined){
      Ok(views.html.create_post(PostForm.form))
    }
    else {
      Redirect(routes.UsersController.login())
    }
  }

  def validateCreatePostForm() = Action { implicit request =>
    if(request.session.get("user").isEmpty){
      Redirect(routes.UsersController.login())
    }else{
      PostForm.form.bindFromRequest()
        .fold(
          hasErrors = { formWithErrors =>
            BadRequest(views.html.create_post(formWithErrors))
          },
          success = { postData: PostForm.PostData =>
            val dt = DateTime.parse(postData.creationDateTime)
            val post = models.Post(postData.title, postData.content, dt)

            Redirect(routes.PostsController.postDetails(post.id))
          }
        )
    }
  }


}


