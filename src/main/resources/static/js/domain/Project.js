export default class Project {
    constructor (project) {
        if (project) {
            this.id = project.id
            this.name = project.name
            this.creationDate = project.creationDate
            this.description = project.description
            this.responsible = project.responsible
        } else {
            this.id = ''
            this.name = ''
            this.creationDate = ''
            this.description = ''
            this.responsible = []
        }
    }
}