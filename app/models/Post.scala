package models

import org.joda.time.DateTime
import play.api.libs.json.{JsValue, Json, Writes}


case class Post(id:Long, title: String, content: String, creationDateTime: DateTime,  var likes: Int, var dislikes: Int){

  def shortContent(maxChars: Int = 300): String =  this.content.substring(0, maxChars.min(this.content.length)) + "..."

  def dateToString(): String = creationDateTime.toString("MMM dd yyyy")
  def timeToString(): String = creationDateTime.toString("HH:mm")

  def increaseLikes(): Unit = this.likes = this.likes + 1
  def increaseDislikes(): Unit = this.dislikes = this.dislikes + 1

}



object Post {

  // dummyID simulates the pk of a post in the DB.
  private var dummyID = 0L


  private def createId() = {
    dummyID +=1
    dummyID
  }


  def apply(title: String, content: String, creationDateTime: DateTime): Post = {
    val id =  createId()
    val p = new Post(id, title, content, creationDateTime, 0,0)
    posts +=p
    p
  }

  private def randomDateTime() = {
    val minusDays = scala.util.Random.nextInt((100))
    val minusYears = scala.util.Random.nextInt(5) //random posts of max 5 years old
    DateTime.now().minusDays(minusDays).minusYears(minusYears)
  }

  //generates a random post
  private def randomPost(title: String, content: String): Post = {
    val dt = randomDateTime()
    val likes = scala.util.Random.nextInt(100)
    val dislikes = scala.util.Random.nextInt(100)
    val id = createId()

    Post(id, title, content, dt, likes, dislikes)
  }


   implicit val implicitPostWrites: Writes[Post] = new Writes[Post] {
      override def writes(post: Post): JsValue = {
        Json.obj(
          "id" -> post.id,
          "title" -> post.title,
          "content" -> post.content,
          "likes" -> post.likes,
          "dislikes" -> post.dislikes,
        )
      }
    }



  //dummy data
  private var posts = Set(
    randomPost( "Hey Jude", "Hey Jude, don't make it bad. Take a sad song and make it better. Remember to let her into your heart. Then you can start to make it better. Hey Jude, don't be afraid. You were made to go out and get her. The minute you let her under your skin. Then you begin to make it better. And anytime you feel the pain. Hey Jude, refrain. Don't carry the world upon your shoulders. For well you know that it's a fool. Who plays it cool. By making his world a little colder. Na-na-na, na, na. Na-na-na, na. Hey Jude, don't let me down. You have found her, now go and get her (let it out and let it in). Remember to let her into your heart (hey Jude). Then you can start to make it better. So let it out and let it in. Hey Jude, begin. You're waiting for someone to perform with. And don't you know that it's just you. Hey Jude, you'll do. The movement you need is on your shoulder. Na-na-na, na, na. Na-na-na, na, yeah. Hey Jude, don't make it bad. Take a sad song and make it better. Remember to let her under your skin. Then you'll begin to make it better. Better better better better better, ah!. Na, na, na, na-na-na na (yeah! Yeah, yeah, yeah, yeah, yeah, yeah). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude (Jude Jude, Judy Judy Judy Judy, ow wow!). Na, na, na, na-na-na na (my, my, my). Na-na-na na, hey Jude (Jude, Jude, Jude, Jude, Jude). Na, na, na, na-na-na na (yeah, yeah, yeah). Na-na-na na, hey Jude (yeah, you know you can make it, Jude, Jude, you're not gonna break it). Na, na, na, na-na-na na (don't make it bad, Jude, take a sad song and make it better). Na-na-na na, hey Jude (oh Jude, Jude, hey Jude, wa!). Na, na, na, na-na-na na (oh Jude). Na-na-na na, hey Jude (hey, hey, hey, hey). Na, na, na, na-na-na na (hey, hey). Na-na-na na, hey Jude (now, Jude, Jude, Jude, Jude, Jude). Na, na, na, na-na-na na (Jude, yeah, yeah, yeah, yeah). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude (na-na-na-na-na-na-na-na-na). Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na (yeah, make it, Jude). Na-na-na na, hey Jude (yeah yeah yeah yeah yeah! Yeah! Yeah! Yeah! Yeah!). Na, na, na, na-na-na na (yeah, yeah yeah, yeah! Yeah! Yeah!). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude"),
    randomPost("Lucy in the Sky With Diamonds", "Picture yourself in a boat on a river. With tangerine trees and marmalade skies. Somebody calls you, you answer quite slowly. A girl with kaleidoscope eyes. Cellophane flowers of yellow and green. Towering over your head. Look for the girl with the sun in her eyes. And she's gone. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Follow her down to a bridge by a fountain. Where rocking horse people eat marshmallow pies. Everyone smiles as you drift past the flowers. That grow so incredibly high. Newspaper taxis appear on the shore. Waiting to take you away. Climb in the back with your head in the clouds. And you're gone. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Picture yourself on a train in a station. With plasticine porters with looking glass ties. Suddenly someone is there at the turnstile. The girl with the kaleidoscope eyes. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds"),
    randomPost( "Back In Black", "Back in black. I hit the sack. I've been too long I'm glad to be back. Yes, I'm let loose. From the noose. That's kept me hanging about. I've been looking at the sky. 'Cause it's gettin' me high. Forget the hearse 'cause I never die. I got nine lives. Cat's eyes. Abusin' every one of them and running wild. 'Cause I'm back. Yes, I'm back. Well, I'm back. Yes, I'm back. Well, I'm back, back. Well, I'm back in black. Yes, I'm back in black. Back in the back. Of a Cadillac. Number one with a bullet, I'm a power pack. Yes, I'm in a bang. With a gang. They've got to catch me if they want me to hang. 'Cause I'm back on the track. And I'm beatin' the flack. Nobody's gonna get me on another rap. So look at me now. I'm just makin' my play. Don't try to push your luck, just get out of my way. 'Cause I'm back. Yes,…"),
    randomPost( "Roccy Racoon", "Now somewhere in the Black Mountain Hills of Dakota. There lived a young boy named Rocky Raccoon. And one day his woman ran off with another guy. Hit young Rocky in the eye. Rocky didn't like that. He said, \"I'm gonna get that boy\". So one day he walked into town. Booked himself a room in the local saloon. Rocky Raccoon checked into his room. Only to find Gideon's Bible. Rocky had come, equipped with a gun. To shoot off the legs of his rival. His rival it seems, had broken his dreams. By stealing the girl of his fancy. Her name was Magill, and she called herself Lil. But everyone knew her as Nancy. Now she and her man, who called himself Dan. Were in the next room at the hoe down. Rocky burst in, and grinning a grin. He said, \"Danny boy, this is a showdown\". But Daniel was hot, he drew first and shot. And Rocky collapsed in the corner. Now the doctor came in, stinking of gin. And proceeded to lie on the table. He said, \"Rocky, you met your match\". And Rocky said, \"Doc, it's only a scratch. And I'll be better, I'll be better, Doc, as soon as I am able\". Now Rocky Raccoon, he fell back in his room. Only to find Gideon's Bible. Gideon checked out, and he left it, no doubt. To help with good Rocky's revival"),
    randomPost("Something", "Something in the way she moves. Attracts me like no other lover. Something in the way she woos me. I don't want to leave her now. You know I believe and how. Somewhere in her smile she knows. That I don't need no other lover. Something in her style that shows me. I don't want to leave her now. You know I believe and how. You're asking me will my love grow. I don't know, I don't know. You stick around, now it may show. I don't know, I don't know. Something in the way she knows. And all I have to do is think of her. Something in the things she shows me. I don't want to leave her now. You know I believe and how"),
    randomPost( "Star Treatment", "I just wanted to be one of The Strokes. Now look at the mess you made me make. Hitchhiking with a monogrammed suitcase. Miles away from any half-useful imaginary highway. I'm a big name in deep space. Ask your mates but golden boy's in bad shape. I found out the hard way. That here ain't no place for dolls like you and me. Everybody's on a barge. Floating down the endless stream of great TV. 1984, 2019. Maybe I was a little too wild in the 70s. Rocket-ship grease down the cracks of my knuckles. Karate bandana. Warp speed chic. Hair down to there. Impressive moustache. Love came in a bottle with a twist off cap. Let's all have a swig and do a hot lap. So who you gonna call?. The martini police. Baby, that isn't how they look tonight, oh no. It took the light forever to get to your eyes. I just wanted to be one of those ghosts. You thought that you could forget. And then I haunt you via the rear view mirror. On a long drive from the back seat. But it's alright 'cause you love me. And you recognize that it ain't how it should be. Your eyes are heavy and the weather's getting ugly. So pull over, I know the place. Don't you know an apparition is a cheap date?. What exactly is it you've been drinking these days?. Jukebox in the corner. Long hot summer. They've got a film up on the wall and it's dark enough to dance. What do you mean you've never seen Blade Runner?. Oh, maybe I was a little too wild in the 70s. Back down to earth with a lounge singer shimmer. Elevator down to my make believe residency. From the honeymoon suite. Two shows a day, four nights a week. Easy money. So who you gonna call?. The martini police. So who you gonna call?. The martini police. Baby, that isn't how they look tonight. It took the light absolutely forever to get to your eyes. And as we gaze skyward, ain't it dark early?. It's the star treatment, yeah. And as we gaze skyward, ain't it dark early?. It's the star treatment. It's the star treatment. The star treatmen"),
    randomPost( "Hey Jude", "Hey Jude, don't make it bad. Take a sad song and make it better. Remember to let her into your heart. Then you can start to make it better. Hey Jude, don't be afraid. You were made to go out and get her. The minute you let her under your skin. Then you begin to make it better. And anytime you feel the pain. Hey Jude, refrain. Don't carry the world upon your shoulders. For well you know that it's a fool. Who plays it cool. By making his world a little colder. Na-na-na, na, na. Na-na-na, na. Hey Jude, don't let me down. You have found her, now go and get her (let it out and let it in). Remember to let her into your heart (hey Jude). Then you can start to make it better. So let it out and let it in. Hey Jude, begin. You're waiting for someone to perform with. And don't you know that it's just you. Hey Jude, you'll do. The movement you need is on your shoulder. Na-na-na, na, na. Na-na-na, na, yeah. Hey Jude, don't make it bad. Take a sad song and make it better. Remember to let her under your skin. Then you'll begin to make it better. Better better better better better, ah!. Na, na, na, na-na-na na (yeah! Yeah, yeah, yeah, yeah, yeah, yeah). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude (Jude Jude, Judy Judy Judy Judy, ow wow!). Na, na, na, na-na-na na (my, my, my). Na-na-na na, hey Jude (Jude, Jude, Jude, Jude, Jude). Na, na, na, na-na-na na (yeah, yeah, yeah). Na-na-na na, hey Jude (yeah, you know you can make it, Jude, Jude, you're not gonna break it). Na, na, na, na-na-na na (don't make it bad, Jude, take a sad song and make it better). Na-na-na na, hey Jude (oh Jude, Jude, hey Jude, wa!). Na, na, na, na-na-na na (oh Jude). Na-na-na na, hey Jude (hey, hey, hey, hey). Na, na, na, na-na-na na (hey, hey). Na-na-na na, hey Jude (now, Jude, Jude, Jude, Jude, Jude). Na, na, na, na-na-na na (Jude, yeah, yeah, yeah, yeah). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude (na-na-na-na-na-na-na-na-na). Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na (yeah, make it, Jude). Na-na-na na, hey Jude (yeah yeah yeah yeah yeah! Yeah! Yeah! Yeah! Yeah!). Na, na, na, na-na-na na (yeah, yeah yeah, yeah! Yeah! Yeah!). Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude. Na, na, na, na-na-na na. Na-na-na na, hey Jude"),
    randomPost("Lucy in the Sky With Diamonds", "Picture yourself in a boat on a river. With tangerine trees and marmalade skies. Somebody calls you, you answer quite slowly. A girl with kaleidoscope eyes. Cellophane flowers of yellow and green. Towering over your head. Look for the girl with the sun in her eyes. And she's gone. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Follow her down to a bridge by a fountain. Where rocking horse people eat marshmallow pies. Everyone smiles as you drift past the flowers. That grow so incredibly high. Newspaper taxis appear on the shore. Waiting to take you away. Climb in the back with your head in the clouds. And you're gone. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Picture yourself on a train in a station. With plasticine porters with looking glass ties. Suddenly someone is there at the turnstile. The girl with the kaleidoscope eyes. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Ah. Lucy in the sky with diamonds. Lucy in the sky with diamonds. Lucy in the sky with diamonds"),
    randomPost( "Back In Black", "Back in black. I hit the sack. I've been too long I'm glad to be back. Yes, I'm let loose. From the noose. That's kept me hanging about. I've been looking at the sky. 'Cause it's gettin' me high. Forget the hearse 'cause I never die. I got nine lives. Cat's eyes. Abusin' every one of them and running wild. 'Cause I'm back. Yes, I'm back. Well, I'm back. Yes, I'm back. Well, I'm back, back. Well, I'm back in black. Yes, I'm back in black. Back in the back. Of a Cadillac. Number one with a bullet, I'm a power pack. Yes, I'm in a bang. With a gang. They've got to catch me if they want me to hang. 'Cause I'm back on the track. And I'm beatin' the flack. Nobody's gonna get me on another rap. So look at me now. I'm just makin' my play. Don't try to push your luck, just get out of my way. 'Cause I'm back. Yes,…"),
    randomPost( "Roccy Racoon", "Now somewhere in the Black Mountain Hills of Dakota. There lived a young boy named Rocky Raccoon. And one day his woman ran off with another guy. Hit young Rocky in the eye. Rocky didn't like that. He said, \"I'm gonna get that boy\". So one day he walked into town. Booked himself a room in the local saloon. Rocky Raccoon checked into his room. Only to find Gideon's Bible. Rocky had come, equipped with a gun. To shoot off the legs of his rival. His rival it seems, had broken his dreams. By stealing the girl of his fancy. Her name was Magill, and she called herself Lil. But everyone knew her as Nancy. Now she and her man, who called himself Dan. Were in the next room at the hoe down. Rocky burst in, and grinning a grin. He said, \"Danny boy, this is a showdown\". But Daniel was hot, he drew first and shot. And Rocky collapsed in the corner. Now the doctor came in, stinking of gin. And proceeded to lie on the table. He said, \"Rocky, you met your match\". And Rocky said, \"Doc, it's only a scratch. And I'll be better, I'll be better, Doc, as soon as I am able\". Now Rocky Raccoon, he fell back in his room. Only to find Gideon's Bible. Gideon checked out, and he left it, no doubt. To help with good Rocky's revival"),
    randomPost("Something", "Something in the way she moves. Attracts me like no other lover. Something in the way she woos me. I don't want to leave her now. You know I believe and how. Somewhere in her smile she knows. That I don't need no other lover. Something in her style that shows me. I don't want to leave her now. You know I believe and how. You're asking me will my love grow. I don't know, I don't know. You stick around, now it may show. I don't know, I don't know. Something in the way she knows. And all I have to do is think of her. Something in the things she shows me. I don't want to leave her now. You know I believe and how"),
    randomPost( "Star Treatment", "I just wanted to be one of The Strokes. Now look at the mess you made me make. Hitchhiking with a monogrammed suitcase. Miles away from any half-useful imaginary highway. I'm a big name in deep space. Ask your mates but golden boy's in bad shape. I found out the hard way. That here ain't no place for dolls like you and me. Everybody's on a barge. Floating down the endless stream of great TV. 1984, 2019. Maybe I was a little too wild in the 70s. Rocket-ship grease down the cracks of my knuckles. Karate bandana. Warp speed chic. Hair down to there. Impressive moustache. Love came in a bottle with a twist off cap. Let's all have a swig and do a hot lap. So who you gonna call?. The martini police. Baby, that isn't how they look tonight, oh no. It took the light forever to get to your eyes. I just wanted to be one of those ghosts. You thought that you could forget. And then I haunt you via the rear view mirror. On a long drive from the back seat. But it's alright 'cause you love me. And you recognize that it ain't how it should be. Your eyes are heavy and the weather's getting ugly. So pull over, I know the place. Don't you know an apparition is a cheap date?. What exactly is it you've been drinking these days?. Jukebox in the corner. Long hot summer. They've got a film up on the wall and it's dark enough to dance. What do you mean you've never seen Blade Runner?. Oh, maybe I was a little too wild in the 70s. Back down to earth with a lounge singer shimmer. Elevator down to my make believe residency. From the honeymoon suite. Two shows a day, four nights a week. Easy money. So who you gonna call?. The martini police. So who you gonna call?. The martini police. Baby, that isn't how they look tonight. It took the light absolutely forever to get to your eyes. And as we gaze skyward, ain't it dark early?. It's the star treatment, yeah. And as we gaze skyward, ain't it dark early?. It's the star treatment. It's the star treatment. The star treatmen")
  )


  def findAll = posts.toList.sortBy(_.creationDateTime)(Ordering[DateTime].reverse)

  def postsByVote = posts.toList.sortBy(post =>post.likes - post.dislikes)(Ordering[Int].reverse)

  def findByTitle(title: String) = posts.find(_.title == title)


  def findById(id: Long): Option[Post] = posts.find(_.id == id)

  def add(post: Post) =
    posts= posts + post

  def postExist(postId: Long): Boolean = findById(postId).nonEmpty

}