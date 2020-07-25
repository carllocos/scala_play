package models

import play.api.Logger

case class User(username: String, password: String)



object User{


  var users = Set(User("root", "root"))

  def findUser(usn: String): Option[User] = users.find(_.username == usn)
  def create(un: String, psw: String): User = {
    Logger.logger.debug(s"Creating user(${un}, ${psw}")
    val us =User(un, psw)
    users = users + us
    Logger.logger.debug(s"Total users: ${users}")
    us
  }


  def userExist(usn: String): Boolean = findUser(usn).nonEmpty


}


