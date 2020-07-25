package forms

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}

object SignUpForm {


  private val usernameCheckConstraint: Constraint[String] = Constraint("")({ username =>
    val us = User.findUser(username)

    if(us.isDefined)
      Invalid(Seq(ValidationError("Username already taken")))
    else
      Valid

  })

  private val passwordCheckConstraint: Constraint[String] = Constraint("")({ password =>

    if(!password.matches(".*[A-Z].*"))
      Invalid(Seq(ValidationError("Password must contain a capital letter")))
    else if(!password.matches(".*[a-z].*"))
      Invalid(Seq(ValidationError("Password must contain a small letter")))
    else if(!password.matches(".*\\d.*"))
      Invalid(Seq(ValidationError("Password must contain a digit")))
    else
      Valid

  })


  private def validatePasswords(username: String, password:String, confirm_password: String) = {


    if(password == confirm_password)
      Some(SignUpData(username, password, confirm_password))
    else
      None
  }

  val form = Form(
    mapping(
      "username" -> nonEmptyText.verifying(usernameCheckConstraint),
      "password" -> nonEmptyText(minLength = 6).verifying(passwordCheckConstraint),
      "confirm_password" -> nonEmptyText(minLength = 6).verifying(passwordCheckConstraint),
    )(SignUpData.apply)(SignUpData.unapply).verifying(
      "Passwords do not match!",
      fields =>
        fields match {
          case data => validatePasswords(data.username, data.password, data.confirm_password).isDefined
        }
    )
  )

  case class SignUpData(username: String, password: String, confirm_password: String)

}