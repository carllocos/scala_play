package forms

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}

object LoginForm {


  val usernameCheckConstraint: Constraint[String] = Constraint("")({ username =>
    val us = User.findUser(username)

    if(us.isDefined)
      Valid
    else
      Invalid(Seq(ValidationError("Username does not exist")))
  })


  def validateUserForm(username: String, password:String) = {
    val us = User.findUser(username)

    if(us.isDefined && us.head.password == password)
      Some(LoginData(username, password))
    else
      None
  }

  val form = Form(
    mapping(
      "username" -> nonEmptyText.verifying(usernameCheckConstraint),
      "password" -> nonEmptyText,
    )(LoginData.apply)(LoginData.unapply).verifying(
      "Username or password wrong!",
      fields =>
        fields match {
          case loginData => validateUserForm(loginData.username, loginData.password).isDefined
        }
    )
  )

  case class LoginData(username: String, password: String)

}

