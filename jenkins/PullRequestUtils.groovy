def getPullRequestData(pullRequestPayload){
    def pullRequestId = pullRequestPayload.pull_request.id
    def pullRequestTitle = pullRequestPayload.pull_request.title
    def pullRequestAuthor = pullRequestPayload.pull_request.user.login

    return [id: pullRequestId, title: pullRequestTitle, author: pullRequestAuthor]
}
