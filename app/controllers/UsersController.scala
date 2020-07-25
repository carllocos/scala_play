package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import forms.{LoginForm, SignUpForm}
import play.api.Logger

@Singleton
class UsersController @Inject()(cc: MessagesControllerComponents)
  extends MessagesAbstractController(cc)
{

  def login() = Action { implicit request =>
    Logger.logger.debug("Login attempt")
    Ok(views.html.login(LoginForm.form))
  }

  def logout() = Action { implicit request =>
    Logger.logger.debug("Logout attempt")

    val user = request.session.get("user")
    val newSess =  if (user.isDefined)  request.session - "user" else request.session
    Redirect(routes.HomeController.index())
      .withSession(newSess)

  }


  def signUp() = Action {implicit request =>
    Logger.logger.debug("SignUp attempt")
    Ok(views.html.signup(SignUpForm.form))
  }




  def validateLoginForm() = Action { implicit  request =>

    LoginForm.form.bindFromRequest()
      .fold(
        hasErrors = { formWithErrors =>
          BadRequest(views.html.login(formWithErrors))
        },
        success = { loginData: LoginForm.LoginData =>
          val user = models.User.findUser(loginData.username).head

          Redirect(routes.HomeController.index())
            .withSession(request.session + ("user" -> user.username))
        }
      )
  }

  def validateSignUpForm() = Action { implicit  request =>
    SignUpForm.form.bindFromRequest()
      .fold(
        hasErrors = { formWithErrors =>
          BadRequest(views.html.signup(formWithErrors))
        },
        success = { signUpData: SignUpForm.SignUpData =>
          val user = models.User.create(signUpData.username, signUpData.password)

          Redirect(routes.HomeController.index())
            .withSession(request.session + ("user" -> user.username))
        }
      )
  }


}