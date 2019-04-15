export default class Project {
    constructor (project) {
        if (project) {
            this.id = project.id
            this.name = project.name
            this.creationDate = project.creationDate
            this.description = project.description
            this.type = project.type
            this.managers = project.managers || []
            this.analytics = project.analytics || []
            this.developers = project.developers || []
            this.testers = project.testers || []
            this.others = project.others || []
        } else {
            this.id = ''
            this.name = ''
            this.creationDate = ''
            this.description = ''
            this.type = null
            this.managers = []
            this.analytics = []
            this.developers = []
            this.testers = []
            this.others = []
        }
    }
}