export default class Project {
    constructor (project) {
        if (project) {
            this.id = project.id
            this.name = project.name
            this.creationDate = project.creationDate
            this.description = project.description
            this.managers = project.managers
            this.analytics = project.analytics
            this.developers = project.developers
            this.testers = project.testers
        } else {
            this.id = ''
            this.name = ''
            this.creationDate = ''
            this.description = ''
            this.managers = []
            this.analytics = []
            this.developers = []
            this.testers = []
        }
    }
}