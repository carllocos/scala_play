package forms


import org.joda.time.DateTime
import play.api.data.Form
import play.api.data.Forms._

object PostForm {


  val form = Form(
    mapping(
      "title" -> nonEmptyText(),
      "content" -> nonEmptyText,
      "creationDateTime" -> nonEmptyText.verifying( "invalid date time format", timeString =>{
        try{
          DateTime.parse(timeString)
          true
        }
        catch{
          case _:Exception => false
        }
      }).verifying("date time cannot be in the past", timeString=>{
        DateTime.now().getMillis() < DateTime.parse(timeString).getMillis()
      })

    )(PostData.apply)(PostData.unapply)
  )

  case class PostData(title: String, content: String, creationDateTime: String)

}

