
/*
Requires JQuery
*/

var postsConfig = {
    voteUrl: false,
    fetchUrlByDate:false,
    fetchUrlByVote: false,
    isAuthenticated: false,
    commentUrl: false,
    csrf_token: false,
    alertContainer: false,
}


function setPostsConfig(c){
    postsConfig = c
}


function isAuthenticated() {
    if (!!!postsConfig.isAuthenticated){
        displayError('Authenticate first')
        return false
    }
    return true
}


function fetchPosts(byDate){
    const url = byDate ? postsConfig.fetchUrlByDate : postsConfig.fetchUrlByVote

    const settings = {
        url: url,
        type: "GET",
        dataType: 'html',
        data: {
        }
    }

    $.ajax(settings)
        .then(function(postsHtml){
            if(byDate){
                $("#DateBtn").addClass("active")
                $("#VoteBtn").removeClass("active")
            }
            else{
                $("#DateBtn").removeClass("active")
                $("#VoteBtn").addClass("active")
            }
            $("#postsContainer").empty().append(postsHtml)

            $(".post-thumbs-up").click(function(){
                likePost($(this).data("post-id"))
            })
            $(".post-thumbs-down").click(function(){
                disLikePost($(this).data("post-id"))
            })
        })
        .catch(function(err){
            console.log(err)
        })

}


function postsByVote(){
    fetchPosts(false)
}


function postsByDate(){
    fetchPosts(true)
}

function updateVote(postId, upVote){

    if (!isAuthenticated()){
        return
    }

    const settings = {
        type: "GET",
        url: postsConfig.voteUrl,
        data: {like: upVote, postId: postId, sessionId: postsConfig.isAuthenticated},
        contentType: 'application/json',
        dataType: "json",
    }

    $.ajax(settings)
        .then(function(post){
            $('#post' + postId).find('.post-thumbs-up-text').first().text("+"+post.likes)
            $('#post' + postId).find('.post-thumbs-down-text').first().text("-"+post.dislikes)
        })
        .catch(function(err){
            alert("oh noo!")
            alert(err.responseJSON.error)
        })

}


function likePost(postId){
    updateVote(postId, true)

}

function disLikePost(postId){
    updateVote(postId, false)
}


function submitComment(postId, content) {
    if (!isAuthenticated()){
        return
    }


    const settings = {
        type: "POST",
        url: postsConfig.commentUrl,
        data: JSON.stringify({writer: postsConfig.username, content: content, postId: postId}),
        contentType: 'application/json',
        dataType: "json",
    }

    $.ajax(settings)
        .then(function(post){
            if (!!post.is_executed){
                var el =    `<div class="list-group-item border-0">
                    <div class="d-flex w-100 justify-content-between">
                    <h7 class="mb-1">${post.comment.writer}</h7>
                    </div>
                    <p class="mb-1">${post.comment.content}</p>
                    </div> `

                $("#" + postsConfig.commentsContainer).append($(el))

            }else {
                displayError(post.error_msg)
            }
        })
        .catch(function(err){
            console.log(err)
        })

}




function displayError(msg) {

    var el = `<div class="alert alert-danger alert-dismissible fade show" role="alert">
        ${msg}.
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    </div>`

    $("#" + postsConfig.alertContainer).append($(el))

}
